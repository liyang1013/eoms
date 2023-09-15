package com.keboda.eomsback.timer;

import com.keboda.eomsback.flux.service.IWcsService;
import com.keboda.eomsback.plc.enums.SiemensPLC;
import com.keboda.eomsback.plc.pojo.Car;
import com.keboda.eomsback.plc.pojo.Hoist;
import com.keboda.eomsback.plc.utils.ReadCarUtils;
import com.keboda.eomsback.plc.utils.ReadHoistUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class WcsTimer {

    @Autowired
    private IWcsService iWcsService;

    /**
     * 从PLC获取信息，写入FLUX WCS库
     */
    @Scheduled(cron = "${timer.getPlcInfo.corn}")
    private void getPlcInfo() {

        for (SiemensPLC plc : SiemensPLC.values()) {
            if(plc.name().contains("Car")){//小车

                Short floor = ReadCarUtils.getZAxial(plc.getSiemensS7Net());//楼层
                Car car = Car.builder().plcitem(plc.name().replace("Car","SXC")).floor(floor).build();
                iWcsService.writerCarinfo(car);
            }

            if (plc.name().contains("Hoist")) {//提升机
                Short floor = ReadHoistUtils.getFloor(plc.getSiemensS7Net());//楼层
                Integer taskNo = ReadHoistUtils.getTaskNo(plc.getSiemensS7Net());//getTaskNo
                Hoist hoist = Hoist.builder().plcitem(plc.name().replace("Hoist", "TSJ")).taskno(taskNo).floor(floor).build();
                iWcsService.writerHoistinfo(hoist);
            }
        }
    }
}
