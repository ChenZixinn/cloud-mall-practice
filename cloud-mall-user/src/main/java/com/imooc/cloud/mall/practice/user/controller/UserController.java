package com.imooc.cloud.mall.practice.user.controller;


import com.imooc.cloud.mall.practice.commom.commom.ApiRestResponse;
import com.imooc.cloud.mall.practice.commom.commom.Constant;
import com.imooc.cloud.mall.practice.commom.exception.ImoocMallException;
import com.imooc.cloud.mall.practice.commom.exception.ImoocMallExceptionEnum;
import com.imooc.cloud.mall.practice.user.model.pojo.User;
import com.imooc.cloud.mall.practice.user.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;


/**
 *  用户控制器
 */
@Controller
public class UserController {
    @Autowired
    UserService userService;

    /**
     * 注册接口
     * @param userName 用户名，不能重复
     * @param password 密码，md5+salt加密
     * @return 成功或失败信息
     * @throws ImoocMallException
     */
    @PostMapping("/register")
    @ResponseBody
    public ApiRestResponse register(@RequestParam("userName") String userName, @RequestParam("password")String password) throws ImoocMallException {
        if (StringUtils.isEmpty(userName)){
            // 用户名为空的情况
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)){
            // 密码为空的情况
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASSWORD);
        }
        if (password.length() < 8){
            // 密码长度小于8
            return ApiRestResponse.error(ImoocMallExceptionEnum.PASSWORD_TOO_SHORT);
        }
        userService.register(userName, password);
//        try {
//        } catch (ImoocMallException e) {
//            // 用户名重名错误
//            // ...
//            return ApiRestResponse.error(e.getCode(), e.getMessage());
//        }
        // 插入成功返回成功信息
        return ApiRestResponse.success();
    }

    /**
     * 用户登陆接口
     * @param userName 用户名
     * @param password 密码
     * @param session session
     * @return 登陆成功或失败信息
     * @throws ImoocMallException
     */
    @PostMapping("/login")
    @ResponseBody
    public ApiRestResponse login(@RequestParam("userName") String userName, @RequestParam("password")String password, HttpSession session) throws ImoocMallException {
        if (StringUtils.isEmpty(userName)){
            // 用户名为空的情况
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)){
            // 密码为空的情况
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        // 删除密码
        user.setPassword(null);
        // 保存到session
        session.setAttribute(Constant.IMOOC_MALL_USER, user);
        // 返回用户信息
        return ApiRestResponse.success(user);
    }

    /**
     * 更新个性签名
     * @param signature 个性签名str
     * @param session
     * @return 更新成功或失败信息
     * @throws ImoocMallException
     */
    @PostMapping("/user/update")
    @ResponseBody
    public ApiRestResponse update(@Param("signature") String signature, HttpSession session) throws ImoocMallException {
        User currentUser = (User)session.getAttribute(Constant.IMOOC_MALL_USER);
        if (currentUser == null){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_LOGIN);
        }
        User user = new User();
        user.setId(currentUser.getId());
        user.setPersonalizedSignature(signature);
        userService.updateSignature(user);
        return ApiRestResponse.success();
    }

    /**
     * 退出登陆
     * @param session
     * @return 成功信息
     */
    @PostMapping("/user/logout")
    @ResponseBody
    public ApiRestResponse logout(HttpSession session) {
        session.removeAttribute(Constant.IMOOC_MALL_USER);
        return ApiRestResponse.success();
    }

    /**
     * 管理员登陆接口
     * @param userName 用户名
     * @param password 密码
     * @param session
     * @return 登陆成功或失败信息
     * @throws ImoocMallException
     */
    @PostMapping("/adminLogin")
    @ResponseBody
    public ApiRestResponse adminLogin(@RequestParam("userName") String userName, @RequestParam("password")String password, HttpSession session) throws ImoocMallException {
        if (StringUtils.isEmpty(userName)){
            // 用户名为空的情况
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_USER_NAME);
        }
        if (StringUtils.isEmpty(password)){
            // 密码为空的情况
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_PASSWORD);
        }
        User user = userService.login(userName, password);
        if (!userService.checkAdmin(user)){
            return ApiRestResponse.error(ImoocMallExceptionEnum.NEED_ADMIN);
        };
        // 删除密码
        user.setPassword(null);
        // 保存到session
        session.setAttribute(Constant.IMOOC_MALL_USER, user);
        // 返回用户信息
        return ApiRestResponse.success(user);
    }

    /**
     * 校验是否是管理员
     * @param user
     * @return
     */
    @PostMapping("/checkAdminRole")
    @ResponseBody
    public boolean checkAdminRole(@RequestBody User user){
        return userService.checkAdmin(user);
    }

    /**
     * 返回当前登陆的用户
     * @param session
     * @return User对象
     */
    @GetMapping("/getUser")
    @ResponseBody
    public User getUser(HttpSession session){
        User currentUser = (User) session.getAttribute(Constant.IMOOC_MALL_USER);
        return currentUser;
    }
}
