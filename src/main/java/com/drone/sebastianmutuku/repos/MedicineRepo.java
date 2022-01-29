package com.drone.sebastianmutuku.repos;

import com.drone.sebastianmutuku.entities.DroneMedication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepo extends JpaRepository<DroneMedication, Long> {
    List<DroneMedication> findByDroneSerialNumber(String serialNumber);
}
