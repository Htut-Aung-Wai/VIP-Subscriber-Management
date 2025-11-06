package com.mytel.vip_subscriber_management.service.service;

import com.mytel.vip_subscriber_management.database.entity.Unit;
import org.springframework.data.domain.Page;

public interface UnitService {

    Unit createUnit(Unit unit);

    Page<Unit> getAllUnit(int page, int size);

    Unit getUnitById(Long id);

    Unit getUnitByUnitName(String unitName);

    Unit getUnitByUnitCode(String unitCode);

    Unit updateUnitByUnitCode(String unitCode, Unit updated);

    void deleteUnit(Long id);
}
