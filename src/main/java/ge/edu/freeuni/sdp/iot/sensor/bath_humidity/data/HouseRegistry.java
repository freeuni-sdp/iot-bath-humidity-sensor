package ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data;

public abstract class HouseRegistry {

    private HouseRegistry nextHouseRegistry = null;

    public boolean hasHouse(String houseId){
        boolean result = handleRequest(houseId);
        if (!result && nextHouseRegistry != null) {
            result = this.nextHouseRegistry.hasHouse(houseId);
        }
        return result;
    }
    public void setNext(HouseRegistry next){
        this.nextHouseRegistry = next;
    }

    public abstract boolean handleRequest(String houseId);
}
