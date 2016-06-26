package ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by Giorgi on 26-Jun-16.
 */
public class OnlineHouseRegistry extends HouseRegistry {

    private static final String ROOT_URI = "https://iot-house-registry.herokuapp.com/houses/";

    private Repository repository;

    public OnlineHouseRegistry(Repository repository) {
        this.repository = repository;
    }

    @Override
    public boolean handleRequest(String houseId) {
        Response response = ClientBuilder.newClient()
                .target(ROOT_URI + houseId)
                .request(MediaType.APPLICATION_JSON).get();

        if (response.getStatus() != Response.Status.OK.getStatusCode())
            return false;

        this.repository.addHouse(houseId);
        return true;
    }
}
