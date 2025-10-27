package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BranchRepo extends JpaRepository<Branch, Long> {

    List<Branch> findByBranchName(String branchName);

    List<Branch> findByBranchManagerName(String branchManagerName);

    Optional<Branch> findByBranchCode(String branchCode);

    boolean existsByBranchCode(String branchCode);

}
