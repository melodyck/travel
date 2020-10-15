package com.zzxx.travel.dao;

import com.zzxx.travel.domain.Category;
import com.zzxx.travel.domain.Route;
import com.zzxx.travel.domain.RouteImg;
import com.zzxx.travel.domain.Seller;

import java.util.List;

public interface RouteDao {
    int selectCount(Integer cid, String condition);
    List<Route> findByPage(Integer cid, int start, int pageSize, String condition);
    List<RouteImg> findRouteImg(int rid);
    Seller findRouteSeller(int sid);
    Category findRouteCategory(int cid);
    Route findRoute(int rid);
}
