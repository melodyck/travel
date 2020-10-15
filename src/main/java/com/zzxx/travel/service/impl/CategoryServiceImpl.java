package com.zzxx.travel.service.impl;

import com.zzxx.travel.dao.CategoryDao;
import com.zzxx.travel.dao.impl.CategoryDaoImpl;
import com.zzxx.travel.domain.Category;
import com.zzxx.travel.service.CategoryService;
import com.zzxx.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao cd = new CategoryDaoImpl();
//    private Jedis jedis = JedisUtil.getJedis();
    @Override
    public List<Category> selectAll() {
        List<Category> categoryList = new ArrayList<>();
        //1.优先从Redis查询数据
//        Set<Tuple> categories = jedis.zrangeWithScores("category", 0, -1);
        Set<Tuple> categories = null;
        if (categories == null || categories.size() == 0){
            //2.如果Redis中没有相关数据则在向数据库查询
            categoryList = cd.selectAllCategory();
            //2.1查询完数据将其存入Redis用于下次查询
//            categoryList.forEach(category -> {
//                jedis.zadd("category", category.getCid(), category.getCname());
//            });
            return categoryList;
        }else{
            //3.如果Redis中有数据则将其封装为category列表
            categoryList = new ArrayList<>();
            for (Tuple category : categories){
                categoryList.add(new Category((int) category.getScore(), category.getElement()));
            }
            return categoryList;
        }
    }
}
