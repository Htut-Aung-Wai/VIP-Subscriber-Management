package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.Impl;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.VipPackage;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.exception.CommonException;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository.VipPackageRepo;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.VipPackageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VipPackageServiceImpl implements VipPackageService {

    private final VipPackageRepo repo;

    @Override
    public VipPackage createPackages(VipPackage packages) {
        return repo.save(packages);
    }

    @Override
    public List<VipPackage> getAllPackages() {
        return repo.findAll();
    }

    @Override
    public VipPackage updatePackages(Long id, VipPackage updated) {
        VipPackage existingPackages = repo.findById(id)
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

        return repo.save(existingPackages);
    }

    @Override
    public void deletePackages(Long id) {
        if (!repo.existsById(id)) {
            throw new CommonException("ERR_404", "VIP Package with ID " + id + " not found.");
        }
        repo.deleteById(id);
    }
}
