package com.drone.sebastianmutuku.repos;

import com.drone.sebastianmutuku.entities.Drone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DroneRepo extends JpaRepository<Drone, Long> {
    Drone findByDroneSerialNumber(String droneSerialNumber);
    List<Drone> findByDroneState(String droneState);
}
