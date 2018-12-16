package com.nanyin.controller;

import com.alibaba.fastjson.JSON;
import com.nanyin.entity.navBar.NavBarCategory;
import com.nanyin.entity.navBar.vo.NavBarCategoryInfos;
import com.nanyin.entity.navBar.vo.NavBarVo;
import com.nanyin.entity.user.User;
import com.nanyin.services.NavBarService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @Auther: NanYin
 * @Date: 12/2/18 17:36
 * @Description:
 */
@Controller
@RequestMapping("/nav")
public class NavbarController {

    @Autowired
    NavBarService navBarService;

    @RequestMapping(value = "/navBar", method = RequestMethod.GET)
    public @ResponseBody List<NavBarVo> findNavBarByUserId(Integer category) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        return navBarService.findNavTree(user.getId(), category);
    }

    @RequestMapping(value = "/navbarCategorys", method = RequestMethod.GET)
    public @ResponseBody List<NavBarCategoryInfos> findCategoryByUserId() {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        List<NavBarCategoryInfos> list = navBarService.findCategoryByUserId(user.getId());
        if(list.size()==0){
            return new LinkedList<NavBarCategoryInfos>();
        }
        return list;
    }

    @RequestMapping(value = "/oneLevel", method = RequestMethod.GET)
    public @ResponseBody List<NavBarCategoryInfos> findOneLevelBarByUserId() {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        List<NavBarCategoryInfos> list = navBarService.findOneLevelBarByUserId(user.getId());
        if(list.size()==0){
            return new LinkedList<NavBarCategoryInfos>();
        }
        return list;
    }

    @RequestMapping(value = "/navbarCategoryTable", method = RequestMethod.GET)
    public @ResponseBody
    Map<String,Object>  navbarCategoryTable() {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("code","");
        try{
            map.put("count",navBarService.findCategoryByUserId(user.getId()).size());
            map.put("data",navBarService.findCategoryByUserId(user.getId()));
            map.put("msg","");
        }catch (Exception e){
            map.put("msg","加载onelevel nav时出现问题");
        }
        return map;
    }

    @RequestMapping(value = "/navManage",method = RequestMethod.GET)
    public String navManage(){
        return "/WEB-INF/jsp/admin/nav/navManage.jsp";
    }

    /*删除一级菜单 */
    @RequestMapping(value = "/navbarCategory/{id}", method = RequestMethod.DELETE)
    public
    @ResponseBody
    boolean deleteOneLevelNavBar(@PathVariable(value = "id") Integer id) {
        User user = (User) SecurityUtils.getSubject().getSession().getAttribute("user");
        try {
            navBarService.deleteOneLevelBarByUserId(user.getId(), id);
            if (!navBarService.checkOneLevelBarIsUsed(id)) {
                navBarService.deleteNavCategoryById(id);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /*添加一级菜单*/
    @RequestMapping(value = "/navbarCategory",method = RequestMethod.POST)
    public @ResponseBody boolean addOneLevelNavBar(NavBarCategory navBarCategory){
        return navBarService.insertSNavCategory(navBarCategory);
    }

    /*修改一级菜单*/
    @RequestMapping(value = "/navbarCategory/{id}",method = RequestMethod.PUT)
    public @ResponseBody boolean updateOneLevelNavBar(){
        return true;
    }

    /*批量删除操作*/
    @RequestMapping(value = "/navbarCategory/batch/{ids}")
    public @ResponseBody boolean batchDeleteOneLevelNavBar(@PathVariable("ids")String ids){
        return true;
    }


    @RequestMapping(value = "/addNavCategoryPage",method = RequestMethod.GET)
    public String addNavCategoryPage(){
        return "/WEB-INF/jsp/admin/nav/addNavCategory.jsp";
    }

    @RequestMapping(value = "/modifyNavCategoryPage",method = RequestMethod.GET)
    public String modifyNavCategoryPage(){
        return "/WEB-INF/jsp/admin/nav/modifyNavCategory.jsp";
    }

}