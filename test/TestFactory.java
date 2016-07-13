import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data.HouseRegistry;
import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data.Repository;

/**
 * Created by Giorgi on 13-Jul-16.
 */
public class TestFactory {

    private static Repository repository;
    private static HouseRegistry houseRegistry;

    public static Repository getRepository(){
        return TestFactory.repository;
    }

    public static HouseRegistry getHouseRegistry(){
        return TestFactory.houseRegistry;
    }

    public static void setHouseRegistry(HouseRegistry houseRegistry) {
        TestFactory.houseRegistry = houseRegistry;
    }

    public static void setRepository(Repository repository) {
        TestFactory.repository = repository;
    }
}
