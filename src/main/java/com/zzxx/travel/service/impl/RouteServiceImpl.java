package com.zzxx.travel.service.impl;

import com.zzxx.travel.dao.RouteDao;
import com.zzxx.travel.dao.impl.RouteDaoImpl;
import com.zzxx.travel.domain.*;
import com.zzxx.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private static RouteDao rd = new RouteDaoImpl();

    @Override
    public PageBean<Route> findByPage(Integer cid, int currentPage, int pageSize, String condition) {
        //获取数据总条数
        int totalCount = rd.selectCount(cid, condition);
        System.out.println(totalCount);
        //计算总页数
        int totalPage = (totalCount + pageSize - 1) / pageSize;
        //计算当前页起始条数
        int start = (currentPage - 1) * pageSize;
        //获取当前页数据列表
        List<Route> routeList = rd.findByPage(cid, start, pageSize, condition);
        System.out.println(routeList.size());

        //设置pageBean属性
        PageBean<Route> pageBean = new PageBean<>();
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setList(routeList);
        pageBean.setTotalCount(totalCount);
        pageBean.setTotalPage(totalPage);
        pageBean.setList(routeList);

        return pageBean;
    }

    @Override
    public Route findOne(int rid) {
        Route route = rd.findRoute(rid);
        List<RouteImg> routeImgList = rd.findRouteImg(rid);
        Category routeCategory = rd.findRouteCategory(route.getCid());
        Seller seller = rd.findRouteSeller(route.getSid());

        route.setRouteImgList(routeImgList);
        route.setCategory(routeCategory);
        route.setSeller(seller);
        return route;
    }
}
