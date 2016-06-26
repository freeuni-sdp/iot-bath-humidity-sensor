package ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data;

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
