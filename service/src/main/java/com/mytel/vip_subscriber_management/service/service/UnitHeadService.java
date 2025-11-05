package com.mytel.vip_subscriber_management.service.service;

import com.mytel.vip_subscriber_management.database.dto.UnitHeadResponseDto;
import com.mytel.vip_subscriber_management.database.dto.UnitHeadUpdateDto;
import com.mytel.vip_subscriber_management.database.entity.UnitHead;

import java.util.List;

public interface UnitHeadService {

    UnitHead create(UnitHead unitHead, String unitCode);

    // find by full name
    UnitHeadResponseDto getByUnitHeadFullName(String unitHeadFullName);

    UnitHead getById(String id);

    List<UnitHead> getAll();

    UnitHead updateByUnitCode(UnitHeadUpdateDto dto, String unitCode);

    void deleteByUnitCode(String unitCode);

    /* custom filter methods */
    List<UnitHead> findByUnitCodeOrUnitName(String keyword);

    List<UnitHead> findByPhoneNumber(String phoneNumber);

    List<UnitHead> findByVmyCode(String vmyCode);

    //    For From Date
    List<UnitHead> findByCreatedAtFromDate(String startDate);

    //    For From Date To Date
    List<UnitHead> findByCreatedAtFromDateToDate(String startDate, String endDate);

}
