package ge.edu.freeuni.sdp.iot.sensor.bath_humidity.services;

import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data.HouseRegistry;
import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.model.Humidity;
import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data.Repository;
import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data.ObjectFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Giorgi on 25-Jun-16.
 */

//TODO return null-ebs gadavxedo unda!1

@Path("/houses")
@Consumes( { MediaType.APPLICATION_JSON})
@Produces( { MediaType.APPLICATION_JSON})
public class HumidityService {

    private static final String LOCAL = "http://localhost:8080";
    private static final String HEROKU = "http://iot-webui.herokuapp.com";

    private Set<String> originSet = new HashSet<>(Arrays.asList(LOCAL, HEROKU));

    public Repository getRepository() {
        return ObjectFactory.createRepository();
    }

    public HouseRegistry getHouseRegistry(){
        return ObjectFactory.createHouseRegistry();
    }

    @GET
    @Path("/{house_id}/num_measurements/{num_measurements}")
    public Response getMeasurements(@PathParam("house_id") String houseId,
                                    @PathParam("num_measurements") int numMeasurements,
                                    @HeaderParam("Origin") String origin) {

        if (!getHouseRegistry().hasHouse(houseId))
            return null;

        return addHeaders(Response.ok().entity(getRepository().getLastN(houseId, numMeasurements)), origin);
    }

    @GET
    @Path("/{house_id}")
    public Response getLastMeasurement(@PathParam("house_id") String houseId, @HeaderParam("Origin") String origin) {
        if (!getHouseRegistry().hasHouse(houseId)){
            return null;
        }

        return addHeaders(Response.ok().entity(getRepository().getLast(houseId)), origin);
    }

    @POST
    @Path("{house_id}")
    public Response addMeasurement(@PathParam("house_id") String houseId, Humidity humidity, @HeaderParam("Origin") String origin) {
        if (!getHouseRegistry().hasHouse(houseId))
            return null;

        humidity.setMeasurement_time(getCurrentDataTime());
        getRepository().insert(houseId, humidity);
        return addHeaders(Response.ok(), origin);
    }

    //TODO serialize directly Data object
    private String getCurrentDataTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private Response addHeaders(Response.ResponseBuilder rpBuilder, String origin){
        if (originSet.contains(origin)) {
            rpBuilder.header("Access-Control-Allow-Origin", origin);
            rpBuilder.header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
            rpBuilder.header("Access-Control-Allow-Headers", "Content-Type");
        }
        return rpBuilder.build();
    }
}
