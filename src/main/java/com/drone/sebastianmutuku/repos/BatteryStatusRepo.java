package com.drone.sebastianmutuku.repos;

import com.drone.sebastianmutuku.entities.BatteryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BatteryStatusRepo extends JpaRepository<BatteryStatus, Long> {
}
