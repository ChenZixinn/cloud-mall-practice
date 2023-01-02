package com.imooc.cloud.mall.practice.user.service;


import com.imooc.cloud.mall.practice.commom.exception.ImoocMallException;
import com.imooc.cloud.mall.practice.user.model.pojo.User;

public interface UserService {

    void register(String userName, String password) throws ImoocMallException;

    User login(String userName, String password) throws ImoocMallException;

    void updateSignature(User user) throws ImoocMallException;

    boolean checkAdmin(User user) throws ImoocMallException;
}
