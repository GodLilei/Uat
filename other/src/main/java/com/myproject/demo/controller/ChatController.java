package com.myproject.demo.controller;

import com.myproject.demo.Dto.ChatResponse;
import com.myproject.demo.Dto.DeferredResultResponse;
import com.myproject.demo.impl.ChatImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;

import javax.annotation.Resource;

@Slf4j
@RestController
public class ChatController {
    @Resource
    private ChatImpl chat;
//    private DeferredResult<ChatResponse> deferredResult = new DeferredResult<>(5000L);

    /**
     * 等待消息发送
     * @param chatResponse 超时时间
     * @return 返回
     */
    @RequestMapping(value = "/waitMessage",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public DeferredResult<ChatResponse> waitMessage(@RequestBody ChatResponse chatResponse) {
        log.info("进入消息等待状态：" + chatResponse.toString());
        Long a = Long.parseLong(chatResponse.getTimeout());
        DeferredResult<ChatResponse> deferredResult = new DeferredResult<>(a);
        chat.process(chatResponse.getUser(), deferredResult);
        return deferredResult;
    }

    /**
     * 消息发送
     * @param chatResponse 消息主体
     */
    @RequestMapping(value = "/sendMessage",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public ChatResponse sendMessage(@RequestBody ChatResponse chatResponse) {
        log.info("消息：" + chatResponse.toString());
        chatResponse.setMessage(chatResponse.getMessage()+"\n");
        chat.settingResult(chatResponse.getUser(),chatResponse);
        return chatResponse;
    }

    /**
     * 断开连接聊天服务器
     * @param chatResponse 消息主体
     */
    @RequestMapping(value = "/closeChat",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void closeChat(@RequestBody ChatResponse chatResponse) {
        log.info("关闭连接：" + chatResponse.getUser());
        chatResponse.setMessage("-----用户: " + chatResponse.getUser() + " 断开连接\n");
        chat.closeMessage(chatResponse.getUser(),chatResponse);
//        return chatResponse;
    }

    /**
     * 重新连接聊天服务器
     * @param chatResponse 请求消息
     */
    @RequestMapping(value = "/reLinkChat",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public void reLinkChat(@RequestBody ChatResponse chatResponse) {
        log.info("重新连接：" + chatResponse.getUser());
        chatResponse.setMessage("-----用户: " + chatResponse.getUser() + " 重新连接\n");
        chat.reLinkChat(chatResponse.getUser(),chatResponse);
//        return chatResponse;
    }

}
