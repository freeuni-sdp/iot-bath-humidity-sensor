package ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data;

public class ObjectFactory {

    protected static HouseRegistry houseRegistry;

    public static Repository createRepository(){
        return InMemoryRepository.instance();
    }

    public static HouseRegistry createHouseRegistry() {
        if (houseRegistry == null){
            houseRegistry = new LocalHouseRegistry(createRepository());
            HouseRegistry nextHr = new OnlineHouseRegistry(createRepository());
            houseRegistry.setNext(nextHr);
        }
        return houseRegistry;
    }

}
