import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data.HouseRegistry;
import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data.Repository;
import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.services.HumidityService;

import javax.ws.rs.core.Response;

/**
 * Created by Giorgi on 13-Jul-16.
 */
public class HumidityServiceSpy extends HumidityService{

    @Override
    public Repository getRepository() {
        return TestFactory.getRepository();
    }

    @Override
    public HouseRegistry getHouseRegistry(){
        return TestFactory.getHouseRegistry();
    }

}
