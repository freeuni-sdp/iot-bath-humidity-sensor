import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data.HouseRegistry;
import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data.Repository;
import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.model.Humidity;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.spy;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

import static org.junit.Assert.*;

/**
 * Created by Giorgi on 26-Jun-16.
 */
public class HumidityServiceTest extends JerseyTest{

    private HouseRegistry houseRegistry;
    private Repository repository;
    private Humidity humidity;

    @Override
    protected Application configure() {
        return new ResourceConfig(HumidityServiceSpy.class);
    }


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
        houseRegistry = mock(HouseRegistry.class);
        repository = mock(Repository.class);
        TestFactory.setHouseRegistry(houseRegistry);
        TestFactory.setRepository(repository);

        humidity = new Humidity();
        humidity.setHumidity(50.15);
    }

    @Test
    public void getLastMeasurement_HouseIdDoesNotExist_Return204ErrorCode(){

        when(houseRegistry.hasHouse("no_such_house")).thenReturn(false);
        Response response = target("houses/no_such_house").request(MediaType.APPLICATION_JSON).get();
        assertEquals(response.getStatus(), Response.Status.NO_CONTENT.getStatusCode());
    }

    @Test
    public void getLastMeasurement_HouseIdExists_Return200ErrorCode(){

        when(houseRegistry.hasHouse("truly_existed_house")).thenReturn(true);
        when(repository.getLast("truly_existed_house")).thenReturn(humidity);
        Response response = target("houses/truly_existed_house").request(MediaType.APPLICATION_JSON).get();
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
    }

    @Test
    public void getLastMeasurement_GetLastMeasurement_Return200ErrorCodeWithHumidity(){

        when(houseRegistry.hasHouse("with_measurement")).thenReturn(true);
        when(repository.getLast("with_measurement")).thenReturn(humidity);
        Response response = target("houses/with_measurement").request(MediaType.APPLICATION_JSON).get();
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        Humidity h = response.readEntity(Humidity.class);
        assertTrue(Math.abs(h.getHumidity() - humidity.getHumidity()) < 0.001);
    }


    @Test
    public void getMeasurements_Request2Measurements_Return200ErrorCodeWith2Measurements(){

        List humidities = new ArrayList();
        humidities.add(humidity);
        humidities.add(new Humidity());

        when(houseRegistry.hasHouse("2_measurements")).thenReturn(true);
        when(repository.getLastN("2_measurements", 2)).thenReturn(humidities);
        Response response = target("houses/2_measurements/num_measurements/2").request(MediaType.APPLICATION_JSON).get();
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());

        List<Humidity> list = response.readEntity(new ArrayList<Humidity>().getClass());
        assertEquals(2, list.size());
    }


    @Test
    public void addMeasurement_CheckIfHeadersAreSet_Returns200ErrorCode() {
        when(houseRegistry.hasHouse("headers")).thenReturn(true);
        Response response = target("houses/headers").request(MediaType.APPLICATION_JSON).get();
        assertEquals(response.getHeaderString("Access-Control-Allow-Origin"), "*");
        assertEquals(response.getHeaderString("Access-Control-Allow-Methods"), "POST, GET, PUT, DELETE, OPTIONS");
        assertEquals(response.getHeaderString("Access-Control-Allow-Headers"), "Origin, X-Requested-With, Content-Type, Accept");
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
    }
}
