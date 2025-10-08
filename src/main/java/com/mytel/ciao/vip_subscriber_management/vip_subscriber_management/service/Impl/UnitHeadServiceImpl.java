package com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.Impl;

import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.entity.UnitHead;
import com.mytel.ciao.vip_subscriber_management.vip_subscriber_management.service.UnitHeadService;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class UnitHeadServiceImpl implements UnitHeadService {
    @Override
    public UnitHead createUnitHead(UnitHead unitHead) {
        return null;
    }

    @Override
    public List<UnitHead> getAllUnitHead() {
        return Collections.emptyList();
    }

    @Override
    public UnitHead getUnitHeadById(Long id) {
        return null;
    }

    @Override
    public UnitHead updateUnitHeadByUnitName(String name) {
        return null;
    }

    @Override
    public void deleteUnitHead(String name) {

    }
}
