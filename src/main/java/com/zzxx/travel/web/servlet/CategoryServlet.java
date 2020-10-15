package com.zzxx.travel.web.servlet;

import com.zzxx.travel.domain.Category;
import com.zzxx.travel.domain.ResultInfo;
import com.zzxx.travel.service.CategoryService;
import com.zzxx.travel.service.impl.CategoryServiceImpl;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet{
    public void selectAll(HttpServletRequest request, HttpServletResponse response){
        //获取导航栏标签列表
        CategoryService cs = new CategoryServiceImpl();
        List<Category> categoryList = cs.selectAll();
//        System.out.println(categoryList);
        ResultInfo info = new ResultInfo();
        info.setData(categoryList);

        outputJson(request, response, info);
    }
}
