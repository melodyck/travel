package com.zzxx.travel.web.servlet;

import com.sun.deploy.net.HttpRequest;
import com.sun.deploy.net.HttpResponse;
import com.zzxx.travel.domain.ResultInfo;
import com.zzxx.travel.domain.User;
import com.zzxx.travel.exception.LoginException;
import com.zzxx.travel.service.UserService;
import com.zzxx.travel.service.impl.UserServiceImpl;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Map;

//整合servlet
@WebServlet("/user/*")
public class UserMethodServlet extends BaseServlet {
    // 当请求发送至此servlet会调用service方法, 因为没有重写service方法就会调用父类的service方法,父类通过反射调用相应的成员方法
    // 定义成员方法
    //注册
    public void register(HttpServletRequest request, HttpServletResponse response){
        //获取参数,封装对象
        Map<String, String[]> map = request.getParameterMap();
        map.forEach((key, value) -> System.out.println(key + ":" + Arrays.toString(value)));
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }

        //注册
        UserService us = new UserServiceImpl();
        boolean flag = us.register(user);
        ResultInfo info = new ResultInfo();
        info.setFlag(flag);
        if (!flag){
            //注册失败设置错误信息
            info.setErrorMsg("注册失败");
        }

        outputJson(request, response, info);
    }
    //激活
    public void active(HttpServletRequest request, HttpServletResponse response){
        //获取识别码
        String code = request.getParameter("code");
        //激活对应帐号
        UserService us = new UserServiceImpl();
        boolean flag = us.activeAccount(code);
        response.setContentType("text/html;charset=utf-8");
        if (flag){
            try {
                response.sendRedirect("/travel/login.html");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            try {
                response.getWriter().write("激活失败,请联系管理员");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    //登录
    public void login(HttpServletRequest request, HttpServletResponse response){
        //参数
        String check = request.getParameter("check");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        //获取正确验证码
        String checkCode = (String) request.getSession().getAttribute("CHECKCODE_SERVER");

        //登录
        ResultInfo info = new ResultInfo();
        UserService us = new UserServiceImpl();
//        System.out.println("username:" + username);
        info.setFlag(false);
        if (username != null && password != null && check != null){
            if (username.equals("")){
                info.setErrorMsg("用户名不能为空");
            }else if (password.equals("")){
                info.setErrorMsg("密码不能为空");
            }else if (check.equals("")){
                info.setErrorMsg("验证码不能为空");
            }else if (!check.equalsIgnoreCase(checkCode)){
                info.setErrorMsg("验证码错误");
            } else {
                try {
                    User user = us.login(username, password);
                    request.getSession().setAttribute("loginUser", user);
                    info.setFlag(true);
                } catch (LoginException e) {
                    info.setErrorMsg(e.getMessage());
                    info.setFlag(false);
                }
            }
        }
        //在响应中写入json
        outputJson(request, response, info);
    }
    //登出
    public void logout(HttpServletRequest request, HttpServletResponse response){
        //移除session中的用户信息,并重定向到主页
        request.getSession().removeAttribute("loginUser");
//        System.out.println(request.getRequestURI());
        try {
            response.sendRedirect(request.getContextPath());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //在session域中寻找用户信息
    public void findUser(HttpServletRequest request, HttpServletResponse response){
        User loginUser = (User) request.getSession().getAttribute("loginUser");
        ResultInfo info = new ResultInfo();
        if (loginUser != null){
            info.setData(loginUser);
            info.setFlag(true);
        }else {
            info.setFlag(false);
        }
        outputJson(request, response, info);
    }
    //查询用户名是否存在
    public void checkUser(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        System.out.println(username);
        ResultInfo info = new ResultInfo();
        UserService us = new UserServiceImpl();
        System.out.println(us.checkUsername(username));
        info.setFlag(!us.checkUsername(username));

        outputJson(request, response, info);
    }
}
