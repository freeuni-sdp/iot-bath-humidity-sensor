package ge.edu.freeuni.sdp.iot.sensor.bath_humidity.services;

import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data.HouseRegistry;
import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.model.Humidities;
import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.model.Humidity;
import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data.Repository;
import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.data.ObjectFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Giorgi on 25-Jun-16.
 */

@Path("houses")
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
    public Humidities getMeasurements(@PathParam("house_id") String houseId,
                                      @PathParam("num_measurements") int numMeasurements) {

        //TODO returns null, a better solution is needed
        if (!getHouseRegistry().hasHouse(houseId))
            return null;

        List<Humidity> humList = getRepository().getLastN(houseId, numMeasurements);
        Humidities humidities = new Humidities();
        humidities.setNum_returned(humList.size());
        humidities.setMeasurements(humList);
        return humidities;
    }

    @GET
    @Path("{house_id}")
    public Humidity getLastMeasurement(@PathParam("house_id") String houseId) {
        if (!getHouseRegistry().hasHouse(houseId))
            return null;
        return getRepository().getLast(houseId);
    }

    @POST
    @Path("{house_id}")
    public Response addMeasurement(@PathParam("house_id") String houseId, Humidity humidity) {
        if (!getHouseRegistry().hasHouse(houseId))
            return null;
        humidity.setMeasurement_time(getCurrentDataTime());
        getRepository().insert(houseId, humidity);
        return Response.ok().build();//TODO return different status code
    }

    //TODO serialize directly Data object
    private String getCurrentDataTime(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
