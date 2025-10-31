package com.mytel.vip_subscriber_management.service.service;

import com.mytel.vip_subscriber_management.database.entity.VipPackage;

import java.util.List;

public interface VipPackageService {

    VipPackage createPackages(VipPackage packages);

    List<VipPackage> getAllPackages();

    VipPackage updatePackages(Long id, VipPackage updated);

    void deletePackages(Long id);
}
