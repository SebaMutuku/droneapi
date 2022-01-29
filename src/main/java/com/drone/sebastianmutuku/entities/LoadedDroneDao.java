package com.drone.sebastianmutuku.entities;


import lombok.Data;

import java.util.List;


@Data
public class LoadedDroneDao {
    private String droneSerialNumber;
    private List<MedicineDetails> medicines;
}
