import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data.HouseRegistry;
import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data.Repository;
import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.model.Humidity;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.spy;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.omg.CORBA.Object;

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
        humidity.setHumidity(05.15);
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
    public void getLastMeasurement_HouseIdExistsButNoMeasurement_Return200ErrorCodeWithNoJSON(){

        when(houseRegistry.hasHouse("no_measurement")).thenReturn(true);
        when(repository.getLast("no_measurement")).thenReturn(null);
        Response response = target("houses/no_measurement").request(MediaType.APPLICATION_JSON).get();
        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
        assertNull(response.readEntity(Humidity.class));
    }

//    @Test
//    public void testAddMeasurement(){
//        Humidity newHumidity = new Humidity();
//        newHumidity.setHumidity(75.108);
//        Entity<Humidity> humEntity = Entity.entity(newHumidity, MediaType.APPLICATION_JSON);
//
//        Response response = target("houses/testHouse").request(MediaType.APPLICATION_JSON).header("Origin", "http://localhost:8080")
//                .post(humEntity,
//                Response.class);
//
//        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
//
//        response = target("houses/testHouse/num_measurements/2").request(MediaType.APPLICATION_JSON).get();
//        assertEquals(response.getStatus(), Response.Status.OK.getStatusCode());
////        System.out.println(response.getStatus());
//        System.out.println(response.readEntity(String.class));
//
//    }

}
