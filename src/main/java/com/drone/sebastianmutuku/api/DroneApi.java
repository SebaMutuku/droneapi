package com.drone.sebastianmutuku.api;


import com.drone.sebastianmutuku.entities.Drone;
import com.drone.sebastianmutuku.entities.DroneChargeDao;
import com.drone.sebastianmutuku.entities.DroneParamsDao;
import com.drone.sebastianmutuku.entities.LoadedDroneDao;
import com.drone.sebastianmutuku.services.DroneService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/drone/", produces = MediaType.APPLICATION_JSON_VALUE)
public class DroneApi {
    @Autowired
    DroneService droneService;
    JSONObject response;


    @PostMapping("createdrone")
    public ResponseEntity<?> createDrone(@RequestBody Drone drone) {
        if (drone != null) {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(droneService.createDrone(drone).toString());
        } else {
            response = new JSONObject();
            response.put("message", "Invalid Request data. Check to ensure all Drone fields are passed");
            response.put("status", HttpStatus.BAD_REQUEST);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response.toString());
        }

    }

    @PostMapping("updatedrone")
    public ResponseEntity updateDroneState(@RequestBody DroneParamsDao droneParamsDao) {
        if (droneParamsDao != null) {
            return new ResponseEntity<>(droneService.updateDroneState(droneParamsDao), HttpStatus.ACCEPTED);
        } else {
            response = new JSONObject();
            response.put("message", "Invalid Drone State data. Check to ensure all Drone fields are passed");
            response.put("status", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("loadDrone/")
    public ResponseEntity loadDroneWithMedication(@RequestBody LoadedDroneDao loadedDroneDao) {
        if (loadedDroneDao != null) {
            return new ResponseEntity<>(droneService.loadDrone(loadedDroneDao), HttpStatus.ACCEPTED);
        } else {
            response = new JSONObject();
            response.put("message", "SerialNumber not passed. Cannot process your data");
            response.put("status", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }

//    @GetMapping("checkloadedDrones/{droneSerialNumber}")
//    public ResponseEntity checkLoadedDrones(@PathVariable("droneSerialNumber") String droneSerialNumber) {
//        if (!droneSerialNumber.trim().isEmpty()) {
//            return new ResponseEntity<>(droneService.checkLoadedDrone(droneSerialNumber), HttpStatus.ACCEPTED);
//        } else {
//            invalidResponse = new JSONObject();
//            invalidResponse.put("message", "SerialNumber not passed. Cannot process your data");
//            invalidResponse.put("status", HttpStatus.BAD_REQUEST);
//            return new ResponseEntity<>(invalidResponse, HttpStatus.BAD_REQUEST);
//        }
//    }

    @GetMapping("checkalldronedicines/{droneSerialNumber}")
    public ResponseEntity checkAllMedicines(@PathVariable("droneSerialNumber") String droneSerialNumber) {
        if (!droneSerialNumber.trim().isEmpty()) {
            return new ResponseEntity<>(droneService.checkMedicinesPerDrone(droneSerialNumber), HttpStatus.ACCEPTED);
        } else {
            response = new JSONObject();
            response.put("message", "SerialNumber not passed. Cannot process your data");
            response.put("status", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("chargedrorone")
    public ResponseEntity chargeAdrone(@RequestBody DroneChargeDao droneChargeDao) {
        if (droneChargeDao != null) {
            return new ResponseEntity<>(droneService.chargeAdrone(droneChargeDao), HttpStatus.ACCEPTED);
        } else {
            response = new JSONObject();
            response.put("message", "SerialNumber not passed. Cannot process your data");
            response.put("status", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("checkdronebatterylevel")
    public ResponseEntity checkDroneBatteryLevel(@PathVariable("droneSerialNumber") String droneSerialNumber) {
        if (!droneSerialNumber.trim().isEmpty()) {
            return new ResponseEntity<>(droneService.checkDroneBatteryLevel(droneSerialNumber), HttpStatus.ACCEPTED);
        } else {
            response = new JSONObject();
            response.put("message", "SerialNumber not passed. Cannot process your data");
            response.put("status", HttpStatus.BAD_REQUEST);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getallunloadeddrone")
    public ResponseEntity getAllUnLoadedDrones() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("response", 1);
        jsonObject.put("first", true);
        return new ResponseEntity<>(droneService.listIdleDrones().toString(), HttpStatus.ACCEPTED);

    }

    @GetMapping("getalldrones")
    public ResponseEntity<?> getAllDrones() {
        return ResponseEntity.status(HttpStatus.FOUND).body(droneService.listAllDrones().toString());

    }

}
