package com.mytel.vip_subscriber_management.service.service;

import com.mytel.vip_subscriber_management.database.dto.UnitHeadResponseDto;
import com.mytel.vip_subscriber_management.database.dto.UnitHeadUpdateDto;
import com.mytel.vip_subscriber_management.database.entity.UnitHead;

import java.util.List;

public interface UnitHeadService {

    UnitHead create(UnitHead unitHead, String unitCode);

    UnitHeadResponseDto getByUnitHeadFullName(String unitHeadFullName);

    UnitHead getById(String id);

    List<UnitHead> getAll();

    UnitHead updateByUnitCode(UnitHeadUpdateDto dto, String unitCode);

    void deleteByUnitCode(String unitCode);
}
