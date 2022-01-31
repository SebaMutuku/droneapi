package com.drone.sebastianmutuku.services;


import com.drone.sebastianmutuku.entities.*;
import com.drone.sebastianmutuku.repos.BatteryStatusRepo;
import com.drone.sebastianmutuku.repos.DroneRepo;
import com.drone.sebastianmutuku.repos.MedicineRepo;
import com.drone.sebastianmutuku.serviceimpl.DroneServiceInterface;
import com.drone.sebastianmutuku.utils.DroneModel;
import com.drone.sebastianmutuku.utils.DroneState;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class DroneService implements DroneServiceInterface {
    JSONObject response;
    @Autowired
    BatteryStatusRepo batteryStatusRepo;

    @Autowired
    DroneRepo droneRepo;

    @Autowired
    MedicineRepo medicineRepo;

    private static void getDroneData(Drone drone, JSONObject data) {
        data.put("droneId", drone.getDroneId());
        data.put("droneSerialNumber", drone.getDroneSerialNumber());
        data.put("droneModel", drone.getDroneModel());
        data.put("droneWeight", drone.getDroneWeight());
        data.put("droneBatteryPercentage", drone.getDroneBatteryPercentage());
        data.put("droneState", drone.getDroneState());
    }

    private static Object droneSize(List<Drone> drone, JSONObject response) {
        if (drone.size() > 0) {
            response.put("responseData", drone);
            response.put("message", "Successfully loaded Data");
            return response;
        }
        response.put("responseData", "[]");
        response.put("message", "Data not found in the database");
        return response;
    }

    @Override
    public Object createDrone(Drone drone) {
        response = new JSONObject();
        Drone instanceOfDrone = new Drone();
        try {
            Drone exists = droneRepo.findByDroneSerialNumber(drone.getDroneSerialNumber());
            if (exists == null) {
                if (drone.getDroneWeight() > 500) {
                    response.put("responseData", "[]");
                    response.put("message", "drone's maximum weight exceeded 500  [" + drone.getDroneWeight() + "]");
                    return response;
                }
                if (drone.getDroneBatteryPercentage() > 100) {
                    response.put("responseData", "[]");
                    response.put("message", "Drone's Battery too charged " + drone.getDroneBatteryPercentage());
                    return response;
                }
                List<DroneModel> droneModelList = Arrays.stream(DroneModel.values()).filter(droneModel -> droneModel.toString().toLowerCase().equals(drone.getDroneModel())).collect(Collectors.toList());
                if (droneModelList.size() > 0) {
                    response.put("responseData", "[]");
                    response.put("message", "Invalid Drone Model " + drone.getDroneModel());
                    return response;
                }
                instanceOfDrone.setDroneModel(drone.getDroneModel());
                instanceOfDrone.setDroneState(DroneState.IDLE + "");
                instanceOfDrone.setDroneWeight(drone.getDroneWeight());
                instanceOfDrone.setDroneSerialNumber(drone.getDroneSerialNumber());
                instanceOfDrone.setDroneBatteryPercentage(drone.getDroneBatteryPercentage());
                droneRepo.save(instanceOfDrone);
                getDroneData(instanceOfDrone, response);
                response.put("message", "Successfully saved drone [" + instanceOfDrone.getDroneSerialNumber() + "] to database");
            } else {
                response.put("responseData", "[]");
                response.put("message", "Drone [" + drone.getDroneSerialNumber() + "] already exist");
            }
            return response;
        } catch (Exception e) {
            response = new JSONObject();
            response.put("responseData", "[]");
            response.put("message", "An error occurred " + e.getMessage());
            return response;
        }

    }

    @Override
    public Object checkMedicinesPerDrone(String serialNumber) {
        response = new JSONObject();
        try {
            List<DroneMedication> details = medicineRepo.findByDroneSerialNumber(serialNumber);
            if (details.size() > 0) {
                response.put("message", "Data for the drone not found on the database");
                response.put("responseData", details);
                return response;
            }
            response.put("message", "Drone serial number not found [" + serialNumber + "]");
            response.put("responseData", "[]");
            return response;

        } catch (Exception e) {
            response.put("message", "An error occurred" + e.getMessage());
            response.put("responseData", "[]");
            return response;
        }
    }

    @Override
    public Object loadDrone(LoadedDroneDao loadedDroneDao) {
        DroneMedication medicineDetails = new DroneMedication();
        response = new JSONObject();
        try {
            Drone drone = droneRepo.findByDroneSerialNumber(loadedDroneDao.getDroneSerialNumber());
            if (drone != null && !drone.getDroneState().isEmpty() && drone.getDroneState().equals(DroneState.IDLE.toString())) {
                drone.setDroneState(DroneState.LOADING.name());
                droneRepo.save(drone);
                if (checkWeightLimit(loadedDroneDao.getMedicines())) {
                    if (drone.getDroneBatteryPercentage() > 25) {
                        loadedDroneDao.getMedicines().stream().forEach(load -> {
                            medicineDetails.setCode(load.getCode());
                            medicineDetails.setImage(load.getImage());
                            medicineDetails.setWeight(load.getWeight());
                            medicineDetails.setName(load.getName());
                            medicineRepo.save(medicineDetails);

                        });
                        drone.setDroneState(DroneState.LOADED.name());
                        droneRepo.save(drone);
                        JSONObject data = new JSONObject();
                        data.put("medicineCode", medicineDetails.getCode());
                        data.put("droneSerialNumber", drone.getDroneSerialNumber());
                        data.put("medicineImage", medicineDetails.getImage());
                        data.put("medicineName", medicineDetails.getName());
                        data.put("medicineWeight", medicineDetails.getWeight());
                        response.put("message", "Successfully loaded drone [" + drone.getDroneSerialNumber() + "]");
                        response.put("responseData", data);
                        return response;
                    }
                    response.put("message", "Drone's battery too low [" + drone.getDroneBatteryPercentage() + "]");
                    response.put("responseData", "[]");
                    return response;

                }
                response.put("message", "Drone maximum weight exceeded " + drone.getDroneWeight());
                response.put("responseData", "[]");
                return response;


            }
            response.put("message", "Drone state is not idle or not found");
            response.put("responseData", "[]");


        } catch (Exception e) {
            response = new JSONObject();
            response.put("responseData", "[]");
            response.put("message", "Invalid Drone Serial Number");
        }
        return response;

    }

    @Override
    public Object listIdleDrones() {
        response = new JSONObject();
        try {
            List<Drone> droneData = droneRepo.findByDroneState(DroneState.IDLE + "");
            return droneSize(droneData, response);
        } catch (Exception e) {
            response = new JSONObject();
            response.put("responseData", "[]");
            response.put("message", "An error occurred " + e.getMessage());
            return response;
        }

    }

    @Override
    public Object updateDroneState(DroneParamsDao droneParamsDao) {
        response = new JSONObject();
        try {
            Drone drone = droneRepo.findByDroneSerialNumber(droneParamsDao.getDroneSerialNumber());
            if (drone != null) {
                List<DroneState> droneState = Arrays.stream(DroneState.values()).filter(state -> state.toString().toLowerCase().equals(drone.getDroneModel())).collect(Collectors.toList());
                if (droneState.size() > 0) {
                    response.put("message", "Invalid Drone State [" + droneParamsDao.getDroneSerialNumber() + "]");
                    response.put("responseData", "[]");
                    return response;
                }
                drone.setDroneState(droneParamsDao.getDroneState());
                droneRepo.save(drone);
                response.put("message", "Successfully updated Drone state [" + drone.getDroneState() + "]");
                response.put("responseData", drone);
                return response;
            }
            response.put("message", "Invalid drone serial number [" + droneParamsDao.getDroneSerialNumber() + "]");
            response.put("responseData", "[]");
            return response;

        } catch (Exception e) {
            response.put("message", "An error occurred" + e.getMessage());
            response.put("responseData", "[]");
            return response;

        }
    }

    @Override
    public Object checkDroneBatteryLevel(String droneSerialNumber) {
        response = new JSONObject();
        try {
            Drone drone = droneRepo.findByDroneSerialNumber(droneSerialNumber);
            if (drone != null) {
                JSONObject data = new JSONObject();
                data.put("droneId", drone.getDroneId());
                data.put("droneSerialNumber", drone.getDroneSerialNumber());
                data.put("droneBatteryLevel", drone.getDroneBatteryPercentage());
                data.put("droneState", drone.getDroneState());
                response.put("responseData", data);
                response.put("message", "Successfully loaded data");
                return response;
            }
            response.put("message", "Drone serialnumber [" + droneSerialNumber + "] not found");
            response.put("responseData", "[]");
            return response;

        } catch (Exception e) {
            response.put("message", "An error occurred" + e.getMessage());
            response.put("responseData", "[]");
            return response;

        }

    }

    @Override
    public Object chargeAdrone(DroneChargeDao droneChargeDao) {
        response = new JSONObject();
        try {
            Drone drone = droneRepo.findByDroneSerialNumber(droneChargeDao.getDroneSerialNumber());
            if (drone != null) {
                if (drone.getDroneBatteryPercentage() > 100) {
                    response.put("responseData", "[]");
                    response.put("message", "Drone's Battery too charged [" + droneChargeDao.getDroneBatteryLevel() + "]");
                    return response;
                }
                if (droneChargeDao.getDroneBatteryLevel() > 100) {
                    response.put("responseData", "[]");
                    response.put("message", "Invalid battery charge passed [" + droneChargeDao.getDroneBatteryLevel() + "]");
                    return response;
                }
                drone.setDroneBatteryPercentage(droneChargeDao.getDroneBatteryLevel());
                droneRepo.save(drone);
                getDroneData(drone, response);
                response.put("message", "Successfully charged Drone [" + droneChargeDao.getDroneSerialNumber() + "]");
                return response;
            }
            response.put("responseData", "[]");
            response.put("message", "Drone Serial Number [" + droneChargeDao.getDroneSerialNumber() + "] doesn't exist ");
            return response;

        } catch (Exception e) {
            response.put("responseData", "[]");
            response.put("message", "An error occurred " + e.getMessage());
            return response;
        }

    }

    @Override
    public Object listAllDrones() {
        response = new JSONObject();
        try {
            List<Drone> drone = droneRepo.findAll();
            return droneSize(drone, response);
        } catch (Exception e) {
            response = new JSONObject();
            response.put("responseData", "");
            response.put("message", "An error occurred " + e.getMessage());
        }
        return response;
    }

    private boolean checkWeightLimit(List<MedicineDetails> medicineDetails) {
        AtomicReference<Double> weight = new AtomicReference<>(0.0);
        medicineDetails.forEach(med -> {
            try {
                if (checkValidMedecine(med)) {
                    weight.set(weight.get() + med.getWeight());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return weight.get() <= 500.00;
    }

    private boolean checkValidMedecine(MedicineDetails medicine) {
        String regex = medicine.getName().replaceAll("[A-Za-z0-9-_]+", "");
        if (regex.length() > 0) {
            return false;
        }
        String code = medicine.getCode().replaceAll("[A-Z0-9-_]+", "");
        return code.length() <= 0;
    }
}
