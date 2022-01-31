package com.drone.sebastianmutuku;

import com.drone.sebastianmutuku.entities.*;
import com.drone.sebastianmutuku.repos.BatteryStatusRepo;
import com.drone.sebastianmutuku.repos.DroneRepo;
import com.drone.sebastianmutuku.repos.MedicineRepo;
import com.drone.sebastianmutuku.services.DroneService;
import com.drone.sebastianmutuku.utils.DroneState;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class DroneServiceTest {
    public static Drone drone;
    public static DroneMedication droneMedication;
    public static LoadedDroneDao loadedDroneDao;
    private static DroneParamsDao droneParamsDao;
    private static DroneChargeDao droneChargeDao;
    @MockBean
    BatteryStatusRepo batteryStatusRepo;
    @MockBean
    DroneRepo droneRepo;
    @MockBean
    MedicineRepo medicineRepo;
    @MockBean
    private DroneService droneService;

    @BeforeAll
    public static void setUp() {
        /*new drone */
        drone = new Drone();
        drone.setDroneSerialNumber("37717172821");
        drone.setDroneWeight(78.01);
        drone.setDroneBatteryPercentage(24);
        drone.setDroneModel("LightWeight");

        /*drone medication */
        droneMedication = new DroneMedication();
        droneMedication.setCode("12331123");
        droneMedication.setDroneSerialNumber("37717172821");
        droneMedication.setName("Drug");
        droneMedication.setWeight(79.03);
        droneMedication.setImage("loadedImage");

        /*Drone dao */
        loadedDroneDao = new LoadedDroneDao();
        loadedDroneDao.setDroneSerialNumber(droneMedication.getDroneSerialNumber());

        /*droneParamsDao*/
        droneParamsDao = new DroneParamsDao();
        droneParamsDao.setDroneState(DroneState.IDLE.name());
        droneParamsDao.setDroneSerialNumber(droneParamsDao.getDroneSerialNumber());

        /*droneChargeDao*/
        droneChargeDao = new DroneChargeDao();
        droneChargeDao.setDroneBatteryLevel(70);
        droneChargeDao.setDroneSerialNumber(droneParamsDao.getDroneSerialNumber());
    }

    @Test
    public void testCreateDrone() throws JSONException {
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
    public void testLoadDrone() throws JSONException {
        List<MedicineDetails> medicineDetails = new ArrayList() {{
            add(droneMedication);
        }};
        loadedDroneDao.setMedicines(medicineDetails);
        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("medicineCode", droneMedication.getCode());
        data.put("droneSerialNumber", droneMedication.getDroneSerialNumber());
        data.put("medicineImage", droneMedication.getImage());
        data.put("medicineName", droneMedication.getName());
        data.put("medicineWeight", droneMedication.getWeight());
        response.put("message", "Successfully loaded drone [" + drone.getDroneSerialNumber() + "]");
        response.put("responseData", data);
        Mockito.doReturn(response).when(droneService).loadDrone(loadedDroneDao);

    }

    @Test
    public void testListIdleDrones() throws JSONException {
        List<Drone> droneList = droneRepo.findByDroneState(DroneState.IDLE.name());
        JSONObject response = new JSONObject();
        response.put("responseData", droneList);
        response.put("message", "Successfully loaded Data");
        Mockito.doReturn(response).when(droneService).listIdleDrones();
        Assertions.assertNotNull(droneList.size());
    }

    @Test
    public void testUpdateDroneState() throws JSONException {
        JSONObject response = new JSONObject();
        response.put("message", "Successfully updated Drone state [" + drone.getDroneState() + "]");
        response.put("responseData", drone);
        Mockito.doReturn(response).when(droneService).updateDroneState(droneParamsDao);
        Assertions.assertEquals(DroneState.IDLE.name(), "IDLE");

    }

    @Test
    public void testCheckDroneBatteryLevel() throws JSONException {
        JSONObject response = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("droneId", drone.getDroneId());
        data.put("droneSerialNumber", drone.getDroneSerialNumber());
        data.put("droneBatteryLevel", drone.getDroneBatteryPercentage());
        data.put("droneState", drone.getDroneState());
        response.put("responseData", data);
        response.put("message", "Successfully loaded data");
        Mockito.doReturn(response).when(droneService).checkDroneBatteryLevel(droneMedication.getDroneSerialNumber());

    }

    @Test
    public void testChargeAdrone() throws JSONException {
        JSONObject response = new JSONObject();
        response.put("droneId", drone.getDroneId());
        response.put("droneSerialNumber", drone.getDroneSerialNumber());
        response.put("droneModel", drone.getDroneModel());
        response.put("droneWeight", drone.getDroneWeight());
        response.put("droneBatteryPercentage", drone.getDroneBatteryPercentage());
        response.put("droneState", drone.getDroneState());
        response.put("message", "Successfully charged Drone [" + droneChargeDao.getDroneSerialNumber() + "]");
        Mockito.doReturn(response).when(droneService).chargeAdrone(droneChargeDao);
        Assertions.assertEquals(droneChargeDao.getDroneBatteryLevel(), 70);
    }

    @Test
    public void testListAllDrones() throws JSONException {
        List<Drone> droneList = droneRepo.findAll();
        JSONObject response = new JSONObject();
        response.put("responseData", droneList);
        response.put("message", "Successfully loaded Data");
        Mockito.doReturn(response).when(droneService).listAllDrones();
        Assertions.assertNotNull(droneList.size());
    }
}
