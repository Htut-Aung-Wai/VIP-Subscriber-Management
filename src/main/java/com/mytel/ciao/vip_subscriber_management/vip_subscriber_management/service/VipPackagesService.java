package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.VipPackages;

import java.util.List;

public interface VipPackagesService {

    VipPackages createPackages(VipPackages packages);

    List<VipPackages> getAllPackages();

    VipPackages updatePackages(Long id, VipPackages updated);

    void deletePackages(Long id);
}
