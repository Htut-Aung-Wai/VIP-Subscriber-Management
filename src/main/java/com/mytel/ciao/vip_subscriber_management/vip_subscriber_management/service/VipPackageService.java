package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.VipPackage;

import java.util.List;

public interface VipPackageService {

    VipPackage createPackages(VipPackage packages);

    List<VipPackage> getAllPackages();

    VipPackage updatePackages(Long id, VipPackage updated);

    void deletePackages(Long id);
}
