package com.drone.sebastianmutuku.serviceimpl;

import com.drone.sebastianmutuku.entities.Drone;
import com.drone.sebastianmutuku.entities.DroneChargeDao;
import com.drone.sebastianmutuku.entities.DroneParamsDao;
import com.drone.sebastianmutuku.entities.LoadedDroneDao;

public interface DroneServiceInterface {
    Object createDrone(Drone drone);

    Object checkMedicinesPerDrone(String serialNumber);

    Object loadDrone(LoadedDroneDao loadedDroneDao);

    Object listIdleDrones();

    Object updateDroneState(DroneParamsDao droneParamsDao);

    Object checkDroneBatteryLevel(String droneSerialNumber);

    Object chargeAdrone(DroneChargeDao droneChargeDao);

    Object listAllDrones();

}
