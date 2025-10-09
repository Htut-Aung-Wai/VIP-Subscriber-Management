package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.Impl;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.VipPackages;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.exception.CommonException;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository.VipPackagesRepo;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.VipPackagesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VipPackagesServiceImpl implements VipPackagesService {

    private final VipPackagesRepo packagesRepo;

    @Override
    public VipPackages createPackages(VipPackages packages) {
        return packagesRepo.save(packages);
    }

    @Override
    public List<VipPackages> getAllPackages() {
        return packagesRepo.findAll();
    }

    @Override
    public VipPackages updatePackages(Long id, VipPackages updated) {
        VipPackages existingPackages = packagesRepo.findById(id)
                .orElseThrow(() -> new CommonException("ERR_404", "VIP Package with ID " + id + " not found."));

        if (updated.getKindOfVip() != null && !updated.getKindOfVip().isEmpty()) {
            existingPackages.setKindOfVip(updated.getKindOfVip());
        }
        if (updated.getMainBalance() != null && !updated.getMainBalance().isEmpty()) {
            existingPackages.setMainBalance(updated.getMainBalance());
        }
        if (updated.getData() != null && !updated.getData().isEmpty()) {
            existingPackages.setData(updated.getData());
        }
        if (updated.getOnNet() != null) {
            existingPackages.setOnNet(updated.getOnNet());
        }
        if (updated.getOffNet() != null) {
            existingPackages.setOffNet(updated.getOffNet());
        }
        if (updated.getMonth() != null) {
            existingPackages.setMonth(updated.getMonth());
        }

        return packagesRepo.save(existingPackages);
    }

    @Override
    public void deletePackages(Long id) {
        if (!packagesRepo.existsById(id)) {
            throw new CommonException("ERR_404", "VIP Package with ID " + id + " not found.");
        }
        packagesRepo.deleteById(id);
    }
}
