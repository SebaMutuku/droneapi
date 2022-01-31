package com.drone.sebastianmutuku;

import com.drone.sebastianmutuku.entities.Drone;
import com.drone.sebastianmutuku.repos.BatteryStatusRepo;
import com.drone.sebastianmutuku.repos.DroneRepo;
import com.drone.sebastianmutuku.repos.MedicineRepo;
import com.drone.sebastianmutuku.services.DroneService;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public class DroneServiceTest {
    @MockBean
    BatteryStatusRepo batteryStatusRepo;
    @MockBean
    DroneRepo droneRepo;
    @MockBean
    MedicineRepo medicineRepo;
    @MockBean
    private DroneService droneService;

    @Test
    public void testCreateDrone() throws JSONException {
        Drone drone = new Drone();
        drone.setDroneSerialNumber("37717172821");
        drone.setDroneWeight(78.01);
        drone.setDroneBatteryPercentage(90);
        drone.setDroneModel("LightWeight");
        JSONObject response = new JSONObject();
        response.put("droneId", drone.getDroneId());
        response.put("droneSerialNumber", drone.getDroneSerialNumber());
        response.put("droneModel", drone.getDroneModel());
        response.put("droneWeight", drone.getDroneWeight());
        response.put("droneBatteryPercentage", drone.getDroneBatteryPercentage());
        response.put("droneState", drone.getDroneState());
        response.put("message", "Successfully saved drone [" + drone.getDroneSerialNumber() + "] to database");
        System.out.println("********Response JSON **********" + response);
        Mockito.doReturn(response).when(droneService).createDrone(drone);
        Assertions.assertEquals("37717172821", drone.getDroneSerialNumber());

    }

    @Test
    public void testLoadDrone() {

    }

    @Test
    public void testListIdleDrones() {

    }

    @Test
    public void testUpdateDroneState() {

    }

    @Test
    public void testCheckDroneBatteryLevel() {

    }

    @Test
    public void testChargeAdrone() {

    }

    @Test
    public void testListAllDrones() {
    }
}
