package com.drone.sebastianmutuku.services;

import com.drone.sebastianmutuku.entities.BatteryStatus;
import com.drone.sebastianmutuku.repos.BatteryStatusRepo;
import com.drone.sebastianmutuku.repos.DroneRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@EnableAsync
@Service
public class AsyncDroneService {
    @Autowired
    DroneRepo droneRepo;
    @Autowired
    BatteryStatusRepo batteryStatusRepo;

    @Scheduled(fixedDelayString = "${delay.in.milliseconds}", initialDelay = 3000)
    private void checkBatteryLevels() {
        droneRepo.findAll().stream().forEach(drone -> {
            BatteryStatus batteryStatus = new BatteryStatus();
            batteryStatus.setBatteryCheckDate(new Timestamp(System.currentTimeMillis()));
            batteryStatus.setBatteryPercentage(drone.getDroneBatteryPercentage());
            batteryStatusRepo.save(batteryStatus);
        });
    }
}
