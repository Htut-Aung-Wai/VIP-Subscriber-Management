package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.Impl;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.dto.UserDto;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.User;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.exception.CommonException;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository.UserRepo;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo repo;

    @Override
    public User create(User user) {
        return repo.save(user);
    }

    @Override
    public List<User> createAll(List<User> users) {
        return repo.saveAll(users);
    }

    @Override
    public User getByVmyCode(String vmyCode) {
        return repo.findByVmyCode(vmyCode).orElseThrow(() -> new CommonException("ERR_404", vmyCode + " not found."));
    }

    @Override
    public User getByPhoneNumber(String phoneNumber) {
        return repo.findByPhoneNumber(phoneNumber).orElseThrow(() -> new CommonException("ERR_404", phoneNumber + " not found."));
    }

    @Override
    public List<User> getAll() {
        return repo.findAll();
    }

    @Override
    public User update(String vmyCode, UserDto userUpdate) {
        User user = repo.findByVmyCode(vmyCode).orElseThrow(() -> new CommonException("ERR_404", vmyCode + " not found."));
        user.setVmyCode(user.getVmyCode());
        user.setPhoneNumber(userUpdate.getPhoneNumber());
        user.setIsActive(userUpdate.getIsActive());

        return repo.save(user);
    }

    @Override
    public void delete(String vmyCode) {
        Optional<User> user = repo.findByVmyCode(vmyCode);

        if (!user.isPresent()) {
            throw new CommonException("ERR_404", vmyCode + " not found.");
        }

        User user1 = user.get();
        repo.delete(user1);
    }
}
