/*
 * Copyright © 2015-2026 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * @since 0.0.1
 */

package com.myproject.demo.services;


import com.alibaba.fastjson.JSON;
import com.myproject.demo.Dto.BaseResponse;
import com.myproject.demo.dao.TodayThingsDao;
import com.myproject.demo.entity.Schedule;
import com.myproject.demo.enums.StatusMsg;
import com.myproject.demo.enums.Type;
import com.myproject.demo.utils.HttpClientResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class TodayThingsService {

    @Resource
    private TodayThingsDao todayThingsDao;

    public void sendMessage(){
        String HTTP_URL = "https://oapi.dingtalk.com/robot/send?access_token=abf25116285fad549df7b4ee6effc2e5b74c40c9e95b84591c1ffd7e13e4ff52";
        //https://oapi.dingtalk.com/robot/send?access_token=f2c94de142fc59b69da6fae8880400bf9892513247d21679d245c18a91bfcff1
        String HTTP_URL1 = "https://oapi.dingtalk.com/robot/send?access_token=f2c94de142fc59b69da6fae8880400bf9892513247d21679d245c18a91bfcff1";

        HttpClientResponse httpClientResponse = new HttpClientResponse();
        httpClientResponse.requestPost(HTTP_URL,"{\n" +
                "    \"msgtype\": \"text\", \n" +
                "    \"text\": {\n" +
                "        \"content\": \"日报提醒小助手：写日报！！！\"\n" +
                "    }, \n" +
                "    \"at\": {\n" +
                "        \"atMobiles\": [\n" +
                "            \"15116231720\", \n" +
                "        ], \n" +
                "        \"isAtAll\": true\n" +
                "    }\n" +
                "}");
        log.info("----->日报消息推送>>");
    }

    public String test(String test){
        todayThingsDao.test(test);
        return "OK";
    }

    /**
     * 获取所有任务列表
     * @return
     */
    public BaseResponse getAllSchedule(){
        BaseResponse response = new BaseResponse();
        List<Schedule> scheduleList = todayThingsDao.getAllSchedule();
        if(scheduleList!=null&&!(scheduleList.isEmpty())) {
            for (int i = 0;i<scheduleList.size();i++){
                for (StatusMsg status:StatusMsg.values()) {
                        if (status.getCode().equals(scheduleList.get(i).getStatus())){
                            scheduleList.get(i).setStatusMsg(status.getMsg());
                        }
                }
            }
            for (int i = 0;i<scheduleList.size();i++){
                for (Type type:Type.values()) {
                    if (type.getCode()==scheduleList.get(i).getType()){
                        scheduleList.get(i).setTypeMsg(type.getMsg());
                    }
                }
            }
            response.setData(JSON.parseArray(JSON.toJSONString(scheduleList)));
            response.setCode("200");
            response.setCount(scheduleList.size());
        }else{
            response.setCode("200");
            response.setMsg("暂无数据");
        }
        return response;
    }

    /**
     * 根据完成状态查询任务
     * @param status
     * @return
     */
    public BaseResponse querySchedule(String status){
        BaseResponse response = new BaseResponse();
        for (StatusMsg s: StatusMsg.values()) {
            if(s.getMsg().equals(status)) {
                status = s.getCode();
            }
        }
        List<Schedule> sList = todayThingsDao.queryScheduleByStatus(status);
        if( sList!=null && !(sList.isEmpty())) {
            for (int i = 0;i<sList.size();i++){
                for (StatusMsg SMsg:StatusMsg.values()) {
                    if (SMsg.getCode().equals(sList.get(i).getStatus())){
                        sList.get(i).setStatusMsg(SMsg.getMsg());
                    }
                }
            }
            for (int i = 0;i<sList.size();i++){
                for (Type type:Type.values()) {
                    if (type.getCode()==sList.get(i).getType()){
                        sList.get(i).setTypeMsg(type.getMsg());
                    }
                }
            }
            response.setData(JSON.parseArray(JSON.toJSONString(sList)));
            response.setCode("200");
            response.setCount(sList.size());
        }else{
            response.setCode("200");
            response.setMsg("暂无数据");
        }
        return response;
    }
}
