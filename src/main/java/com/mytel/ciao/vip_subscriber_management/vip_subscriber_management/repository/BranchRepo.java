package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.repository;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UnitRepo extends JpaRepository<Branch, Long> {

    List<Branch> findByUnitName(String unitName);

    List<Branch> findByUnitHeadFullName(String unitHeadFullName);

    Optional<Branch> findByUnitCode(String unitCode);

    boolean existsByUnitCode(String unitCode);

}
