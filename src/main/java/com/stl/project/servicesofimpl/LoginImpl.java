package com.stl.project.servicesofimpl;

import com.stl.project.dto.BaseResponse;
import com.stl.project.entity.LoginPojo;
import com.stl.project.servicesofdatasource.MysqlDB;
import com.stl.project.tools.datachange.JSONChange;
import com.stl.project.tools.httputil.HttpRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Service
public class LoginImpl {

    @Resource
    private MysqlDB mysqlDB;
    @Resource
    private BaseResponse baseResponse;
    @Resource
    private JSONChange jsonChange;

    public BaseResponse normalLogin(HttpSession session,HttpServletResponse response,LoginPojo loginPojo){
        try{
            List<LoginPojo> userMsg = mysqlDB.normalLogin(loginPojo.getPhone());
            if (userMsg.size() != 0){
                if (loginPojo.getPassword().equals(userMsg.get(0).getPassword())){
                    baseResponse.setCode("0");
                    baseResponse.setMessage("true");
                    baseResponse.setData(jsonChange.jsonStringToJSONArray("{\"msg\":\"登录成功\"}"));
                    setCookies(session,response,userMsg.get(0).getUname());
                    return baseResponse;
                }else {
                    baseResponse.setCode("-1");
                    baseResponse.setMessage("false");
                    baseResponse.setData(jsonChange.jsonStringToJSONArray("{\"msg\":\"密码错误\"}"));
                }
            }else{
                baseResponse.setCode("-1");
                baseResponse.setMessage("false");
                baseResponse.setData(jsonChange.jsonStringToJSONArray("{\"msg\":\"无此用户\"}"));
            }
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMessage("false");
            baseResponse.setData(jsonChange.jsonStringToJSONArray("{\"msg\":\"" + e.getMessage() + "\"}"));
        }
        return baseResponse;
    }
    public BaseResponse fastLogin(HttpSession session, HttpServletRequest request, HttpServletResponse response){
        try{
            List<LoginPojo> userMsg = mysqlDB.fastLogin(request.getRemoteAddr());
            if (userMsg.size() != 0){
                baseResponse.setCode("0");
                baseResponse.setMessage("true");
                baseResponse.setData(jsonChange.jsonStringToJSONArray("{\"msg\":\"IP检查正确，成功登录\"}"));
                setCookies(session,response,userMsg.get(0).getUname());
                return baseResponse;
            }else{
                baseResponse.setCode("-1");
                baseResponse.setMessage("false");
                baseResponse.setData(jsonChange.jsonStringToJSONArray("{\"msg\":\"IP[" + request.getRemoteAddr() +
                        "]未注册\"}"));
            }
        }catch (Exception e){
            baseResponse.setCode("-1");
            baseResponse.setMessage("false");
            baseResponse.setData(jsonChange.jsonStringToJSONArray("{\"msg\":\"" + e.getMessage() + "\"}"));
        }
        return baseResponse;
    }

    public List<LoginPojo> queryIP(LoginPojo loginPojo){
        List<LoginPojo> ipMsg = null;
        try{
            ipMsg = mysqlDB.queryIP(loginPojo);
        }catch (Exception e){
            //
        }
        return ipMsg;
    }

    private void setCookies(HttpSession session,HttpServletResponse response, String uname) throws
            UnsupportedEncodingException {
        session.setAttribute("uname",uname);
//        session.setMaxInactiveInterval(60*60);
        Cookie expCookie = new Cookie("name", uname);
//        expCookie.setMaxAge(60*60);
        expCookie.setPath("/");
        response.addCookie(expCookie);
    }
}
