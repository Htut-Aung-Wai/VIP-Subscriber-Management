package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.Branch;

import java.util.List;

public interface UnitService {

    Branch createUnit(Branch branch);

    List<Branch> getAllUnit();

    Branch getUnitById(Long id);

    List<Branch> getUnitByUnitHeadFullName(String unitHeadFullName);

    Branch updateUnitByUnitCode(String unitCode, Branch updated);

    void deleteUnit(Long id);
}
