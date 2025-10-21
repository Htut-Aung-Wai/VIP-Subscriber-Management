package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.Unit;

import java.util.List;

public interface UnitService {

    Unit createUnit(Unit unit);

    List<Unit> getAllUnit();

    Unit getUnitById(Long id);

    List<Unit> getUnitByUnitFullName(String unitFullName);

    Unit updateUnitByUnitCode(String unitCode, Unit updated);

    void deleteUnit(Long id);
}
