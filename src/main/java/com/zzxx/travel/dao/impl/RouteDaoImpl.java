package com.zzxx.travel.dao.impl;

import com.zzxx.travel.dao.RouteDao;
import com.zzxx.travel.domain.Category;
import com.zzxx.travel.domain.Route;
import com.zzxx.travel.domain.RouteImg;
import com.zzxx.travel.domain.Seller;
import com.zzxx.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.RouteMatcher;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private static JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int selectCount(Integer cid, String condition) {
        String _sql = "select count(*) from `tab_route` where 1 = 1";
        //初始化sql语句
        StringBuilder sql = new StringBuilder(_sql);
        //初始化参数列表
        List<Object> params = new ArrayList<>();
        //拼接条件,加入参数
        if (cid != null){
            sql.append(" and cid = ?");
            params.add(cid);
        }
        if (condition != null && !condition.equals("null") && !condition.equals("")){
            sql.append(" and rname like ?;");
            params.add("%" + condition + "%");
        }
        //执行sql
        int totalCount = template.queryForObject(sql.toString(), Integer.class, params.toArray());
        return totalCount;
    }

    @Override
    public List<Route> findByPage(Integer cid, int start, int pageSize, String condition) {
        //初始化sql语句
        String _sql = "select * from `tab_route` where 1 = 1";
        StringBuilder sql = new StringBuilder(_sql);
        //初始化参数列表
        List<Object> params = new ArrayList<>();
        //拼接条件,加入参数
        if (cid != null){
            sql.append(" and cid = ?");
            params.add(cid);
        }
        if (condition != null && !condition.equals("null") && !condition.equals("")){
            sql.append(" and rname like ?");
            params.add("%" + condition + "%");
        }
        sql.append(" limit ?, ?;");
        params.add(start);
        params.add(pageSize);
        System.out.println(params);
        System.out.println(sql);
        //执行sql
        List<Route> routeList = template.query(sql.toString(), new BeanPropertyRowMapper<>(Route.class), params.toArray());
        if (routeList != null && routeList.size() == 0){
            return null;
        }
        return routeList;
    }

    @Override
    public List<RouteImg> findRouteImg(int rid) {
        String sql = "select * from `tab_route_img` where rid = ?";
        List<RouteImg> routeImgList = template.query(sql, new BeanPropertyRowMapper<>(RouteImg.class), rid);
        if (routeImgList == null || routeImgList.size() == 0){
            return null;
        }
        return routeImgList;
    }

    @Override
    public Seller findRouteSeller(int sid) {
        String sql = "select * from `tab_seller` where sid = ?";
        List<Seller> sellers = template.query(sql, new BeanPropertyRowMapper<>(Seller.class), sid);
        if (sellers == null || sellers.size() == 0){
            return null;
        }
        return sellers.get(0);
    }

    @Override
    public Category findRouteCategory(int cid) {
        String sql = "select * from `tab_category` where cid = ?";
        List<Category> categories = template.query(sql, new BeanPropertyRowMapper<>(Category.class), cid);
        if (categories == null || categories.size() == 0){
            return null;
        }
        return categories.get(0);
    }

    @Override
    public Route findRoute(int rid) {
        String sql = "select * from `tab_route` where rid = ?";
        List<Route> routes = template.query(sql, new BeanPropertyRowMapper<>(Route.class), rid);
        if (routes == null || routes.size() == 0){
            return null;
        }
        return routes.get(0);
    }
}
