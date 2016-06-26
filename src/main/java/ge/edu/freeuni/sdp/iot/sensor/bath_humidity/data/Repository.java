package ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data;

import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.model.Humidity;

import java.util.List;

public interface Repository {
    void insert(String houseId, Humidity humidity);
    Humidity getLast(String houseId);
    List<Humidity> getLastN(String houseId, int number);
    boolean hasHouse(String houseId);
    void addHouse(String houseId);
}
