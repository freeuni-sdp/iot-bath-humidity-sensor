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

@Path("/houses")
@Consumes( { MediaType.APPLICATION_JSON})
@Produces( { MediaType.APPLICATION_JSON})
public class HumidityService {

    public Repository getRepository() {
        return ObjectFactory.createRepository();
    }

    public HouseRegistry getHouseRegistry(){
        return ObjectFactory.createHouseRegistry();
    }

    @GET
    @Path("/{house_id}/num_measurements/{num_measurements}")
    public Response getMeasurements(@PathParam("house_id") String houseId,
                                    @PathParam("num_measurements") int numMeasurements) {

        if (!getHouseRegistry().hasHouse(houseId))
            return null;

        return addHeaders(Response.ok().entity(getRepository().getLastN(houseId, numMeasurements)));
    }

    @GET
    @Path("/{house_id}")
    public Response getLastMeasurement(@PathParam("house_id") String houseId) {
        if (!getHouseRegistry().hasHouse(houseId)){
            return null;
        }

        return addHeaders(Response.ok().entity(getRepository().getLast(houseId)));
    }

    @POST
    @Path("{house_id}")
    public Response addMeasurement(@PathParam("house_id") String houseId, Humidity humidity) {
        if (!getHouseRegistry().hasHouse(houseId))
            return null;

        humidity.setMeasurement_time(getCurrentDataTime());
        getRepository().insert(houseId, humidity);
        return addHeaders(Response.ok());
    }

    private String getCurrentDataTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private Response addHeaders(Response.ResponseBuilder rpBuilder){
        rpBuilder.header("Access-Control-Allow-Origin", "*");
        rpBuilder.header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS");
        rpBuilder.header("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        return rpBuilder.build();
    }
}
