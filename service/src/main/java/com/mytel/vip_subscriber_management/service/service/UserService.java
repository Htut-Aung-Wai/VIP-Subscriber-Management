package com.mytel.vip_subscriber_management.service.service;



import com.mytel.vip_subscriber_management.database.dto.UserDto;
import com.mytel.vip_subscriber_management.database.entity.User;

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
