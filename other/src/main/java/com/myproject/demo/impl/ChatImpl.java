package com.myproject.demo.impl;

import com.myproject.demo.Dto.ChatResponse;
import com.myproject.demo.Dto.DeferredResultResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@Slf4j
@Service
public class ChatImpl {
    private final Map<String, Consumer<ChatResponse>> taskMap;
    private final Map<String, Consumer<ChatResponse>> closeMap;
    public ChatImpl(){
        taskMap = new ConcurrentHashMap<>();
        closeMap = new ConcurrentHashMap<>();
    }
    /**
     * 将请求id与setResult映射
     * @param requestId id
     * @param deferredResult setResult
     */
    public void process(String requestId, DeferredResult<ChatResponse> deferredResult) {
        // 请求超时的回调函数
        deferredResult.onTimeout(() -> {
            taskMap.remove(requestId);
            ChatResponse chatResponse = new ChatResponse();
            chatResponse.setUser("");
            chatResponse.setMessage("");
            deferredResult.setResult(chatResponse);
        });

//        Optional.ofNullable(taskMap)
//                .filter(t -> !t.containsKey(requestId))
//                .orElseThrow(() -> new IllegalArgumentException(String.format("requestId=%s is existing", requestId)));

        // 这里的Consumer，就相当于是传入的DeferredResult对象的地址
        // 所以下面settingResult方法中"taskMap.get(requestId)"就是Controller层创建的对象
        //添加等待用户
//        taskMap.putIfAbsent(requestId, deferredResult::setResult);
        taskMap.put(requestId, deferredResult::setResult);
    }

    /**
     * 这里相当于异步的操作方法，所有等待消息的用户全部在taskMap里面
     * 设置DeferredResult对象的setResult方法
     * @param requestId cotroller对应的ID
     * @param chatResponse res
     */
    public void settingResult(String requestId, ChatResponse chatResponse) {
        if (taskMap.containsKey(requestId)) {
            for (Object o : taskMap.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
//                params.add(new BasicNameValuePair((String) entry.getKey(),(String) entry.getValue()));
                log.info("等待列表------------------->" + entry.getKey().toString());
                Consumer<ChatResponse> chatResponseConsumer = (Consumer<ChatResponse>) entry.getValue();
                // 这里相当于DeferredResult对象的setResult方法
                chatResponseConsumer.accept(chatResponse);
//                taskMap.remove(entry.getKey());
            }
        }
    }
    public void closeMessage(String requestId, ChatResponse chatResponse) {
        if (taskMap.containsKey(requestId)) {
            Consumer<ChatResponse> chatResponseConsumer;
            chatResponse.setUser("系统消息");
            closeMap.put(requestId,taskMap.get(requestId));
            taskMap.remove(requestId);
            for (Object o : taskMap.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                chatResponseConsumer = (Consumer<ChatResponse>) entry.getValue();
                // 这里相当于DeferredResult对象的setResult方法
                chatResponseConsumer.accept(chatResponse);
            }
        }
    }

    public void reLinkChat(String requestId, ChatResponse chatResponse){
        if (closeMap.containsKey(requestId)){
            chatResponse.setUser("系统消息");
            taskMap.put(requestId,closeMap.get(requestId));
            closeMap.remove(requestId);
            for (Object o : taskMap.entrySet()) {
                Map.Entry entry = (Map.Entry) o;
                Consumer<ChatResponse> chatResponseConsumer = (Consumer<ChatResponse>) entry.getValue();
                // 这里相当于DeferredResult对象的setResult方法
                chatResponseConsumer.accept(chatResponse);
            }
        }
    }
}
