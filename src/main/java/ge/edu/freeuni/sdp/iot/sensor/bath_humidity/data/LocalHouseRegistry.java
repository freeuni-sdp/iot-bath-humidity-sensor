package ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data;

/**
 * Created by Giorgi on 26-Jun-16.
 */
public class LocalHouseRegistry extends HouseRegistry{

    private Repository repository;

    public LocalHouseRegistry(Repository repository){
        this.repository = repository;
    }

    @Override
    public boolean handleRequest(String houseId) {
        return repository.hasHouse(houseId);
    }
}
