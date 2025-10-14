package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.UnitHead;

import java.util.List;

public interface UnitHeadService {

    UnitHead createUnitHead(UnitHead unitHead);

    List<UnitHead> getAllUnitHead();

    UnitHead getUnitHeadById(Long id);

    List<UnitHead> getUnitHeadByUnitFullName(String unitFullName);

    UnitHead updateUnitHeadByUnitCode(String unitCode, UnitHead updated);

    void deleteUnitHead(Long id);

}
