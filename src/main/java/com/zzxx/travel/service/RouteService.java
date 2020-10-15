package com.zzxx.travel.service;

import com.zzxx.travel.domain.PageBean;
import com.zzxx.travel.domain.Route;

import java.util.List;

public interface RouteService {
    PageBean<Route> findByPage(Integer cid, int currentPage, int pageSize, String condition);
    Route findOne(int rid);
}
