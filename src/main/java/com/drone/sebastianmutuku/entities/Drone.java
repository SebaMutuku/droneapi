package com.drone.sebastianmutuku.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;


@Entity
@Data
public class Drone implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long droneId;
    private String droneSerialNumber;
    private String droneModel;
    private Double droneWeight;
    private int droneBatteryPercentage;

    @JsonIgnore
    private String droneState;

}
