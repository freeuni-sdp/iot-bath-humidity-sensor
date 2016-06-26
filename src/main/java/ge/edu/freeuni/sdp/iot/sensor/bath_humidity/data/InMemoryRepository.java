package ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data;

import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.model.Humidity;

import java.util.*;

/**
 * Created by Giorgi on 25-Jun-16.
 */

public class InMemoryRepository implements Repository{

    private static InMemoryRepository instance;
    private static final int MAX_LIST_SIZE = 1000;

    private Map<String, List<Humidity>> measurements;

    public static InMemoryRepository instance() {
        if (instance==null) {
            instance = new InMemoryRepository(new HashMap<String, List<Humidity>>());
        }
        return instance;
    }

    private InMemoryRepository(Map<String, List<Humidity>> measurements) {
        this.measurements = measurements;
    }

    @Override
    public void insert(String houseId, Humidity humidity) {
        List currList = measurements.get(houseId);
        measurements.get(houseId).add(0, humidity);
        if (currList.size() > MAX_LIST_SIZE){
            currList.remove(MAX_LIST_SIZE);
        }
    }

    @Override
    public Humidity getLast(String houseId) {
        return measurements.get(houseId).get(0);
    }

    @Override
    public List<Humidity> getLastN(String houseId, int number){
        List result = measurements.get(houseId);
        if (result.size() > number)
            result = result.subList(0, number);
        return result;
    }

    @Override
    public boolean hasHouse(String houseId) {
        return measurements.containsKey(houseId);
    }

    @Override
    public void addHouse(String houseId) {
        List<Humidity> list = new LinkedList<>();
        measurements.put(houseId, list);
    }
}
