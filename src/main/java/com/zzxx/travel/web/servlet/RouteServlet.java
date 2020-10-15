package com.zzxx.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zzxx.travel.domain.PageBean;
import com.zzxx.travel.domain.ResultInfo;
import com.zzxx.travel.domain.Route;
import com.zzxx.travel.service.RouteService;
import com.zzxx.travel.service.impl.RouteServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet{
    public void findByPage(HttpServletRequest request, HttpServletResponse response){
        //获取参数
        String _cid = request.getParameter("cid");

        String currentPage = request.getParameter("currentPage");
        String pageSize = request.getParameter("pageSize");
        String condition = request.getParameter("condition");
        System.out.println(condition);
        Integer cid = 0;
        if (_cid == null || "".equals(_cid)){
            cid = null;
        }else {
            cid = Integer.valueOf(_cid);
        }
        if (currentPage == null || "".equals(currentPage)){
            currentPage = "1";
        }
        if (pageSize == null || "".equals(pageSize)){
            pageSize = "8";
        }

        //查询当前页相应路线列表
        RouteService rs = new RouteServiceImpl();
        PageBean<Route> pageBean = rs.findByPage(cid, Integer.parseInt(currentPage), Integer.parseInt(pageSize), condition);
//        System.out.println(pageBean.getList());
        //返回json对象
        outputJson(request,response,pageBean);
    }

    //查找一个完整的Route对象
    public void findOne(HttpServletRequest request, HttpServletResponse response){
        //获取参数
        String _rid = request.getParameter("rid");
        int rid = Integer.parseInt(_rid);
        //获取route
        RouteService rs = new RouteServiceImpl();
        Route route = rs.findOne(rid);
        //响应json
        outputJson(request,response,route);
    }
}
