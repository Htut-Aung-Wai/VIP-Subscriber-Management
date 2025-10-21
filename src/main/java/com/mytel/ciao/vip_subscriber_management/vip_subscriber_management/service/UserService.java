package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.dto.UserDto;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.User;

import java.util.List;

public interface UserService {

    User create(User user);

    List<User> createAll(List<User> users);

    User getByVmyCode(String vmyCode);

    User getByPhoneNumber(String phoneNumber);

    List<User> getAll();

    User update(String vmyCode, UserDto userUpdate);

    void delete(String vmyCode);
}
