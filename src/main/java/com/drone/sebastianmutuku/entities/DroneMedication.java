package com.drone.sebastianmutuku.entities;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class DroneMedication {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long medicationId;
    private String droneSerialNumber;
    private String code;
    private String name;
    private double weight;
    private String image;

}
