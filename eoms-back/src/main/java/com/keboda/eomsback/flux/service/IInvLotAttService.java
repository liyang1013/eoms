package com.keboda.eomsback.flux.service;

import com.keboda.eomsback.flux.pojo.InvLotAtt;

import java.util.Map;

public interface IInvLotAttService {
    Map<String, InvLotAtt> selectMapByKeyLotnumStatus(String lotnum, String status);
}
