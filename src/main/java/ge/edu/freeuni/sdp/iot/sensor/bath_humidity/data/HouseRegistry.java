package ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data;

/**
 * Created by Giorgi on 26-Jun-16.
 */
public abstract class HouseRegistry {

    private HouseRegistry nextHouseRegistry = null;

    public boolean hasHouse(String houseId){
        if (nextHouseRegistry == null)
            return handleRequest(houseId);
        return this.nextHouseRegistry.hasHouse(houseId);
    }
    public void setNext(HouseRegistry next){
        this.nextHouseRegistry = next;
    }

    public abstract boolean handleRequest(String houseId);
}
