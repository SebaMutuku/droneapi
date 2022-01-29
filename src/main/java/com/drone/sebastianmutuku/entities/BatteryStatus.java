package com.drone.sebastianmutuku.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Timestamp;

@Entity
@Data

public class BatteryStatus  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long droneId;
    private String droneSerialNumber;
    private int batteryPercentage;
    private Timestamp batteryCheckDate;
}
