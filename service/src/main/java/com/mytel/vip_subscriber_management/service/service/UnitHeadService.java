package com.mytel.vip_subscriber_management.service.service;

import com.mytel.vip_subscriber_management.database.dto.UnitHeadResponseDto;
import com.mytel.vip_subscriber_management.database.dto.UnitHeadUpdateDto;
import com.mytel.vip_subscriber_management.database.entity.UnitHead;
import org.springframework.data.domain.Page;

public interface UnitHeadService {

    UnitHead create(UnitHead unitHead, String unitCode);

    // find by full name
    UnitHeadResponseDto getByUnitHeadFullName(String unitHeadFullName);

    UnitHead getById(String id);

    Page<UnitHead> getAll(int page, int size);

    UnitHead updateByUnitCode(UnitHeadUpdateDto dto, String unitCode);

    void deleteByUnitCode(String unitCode);

    /* custom filter methods */
    UnitHead findByUnitCodeOrUnitName(String keyword);

    UnitHead findByPhoneNumber(String phoneNumber);

    UnitHead findByVmyCode(String vmyCode);

    //    For From Date
    Page<UnitHead> findByCreatedAtFromDate(String startDate, int page, int size);

    //    For From Date To Date
    Page<UnitHead> findByCreatedAtFromDateToDate(String startDate, String endDate, int page, int size);

}
