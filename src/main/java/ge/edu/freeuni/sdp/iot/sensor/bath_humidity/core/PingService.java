package ge.edu.freeuni.sdp.iot.sensor.bath_humidity.core;

import ge.edu.freeuni.sdp.todo.data.RepositoryFactory;
import ge.edu.freeuni.sdp.todo.data.TaskEntity;
import ge.edu.freeuni.sdp.todo.data.Repository;

import javax.ws.rs.*;
import javax.ws.rs.core.*;

@Path("ping")
@Consumes( { MediaType.APPLICATION_JSON})
@Produces( { MediaType.APPLICATION_JSON})
public class PingService {

  @GET
  public Response get() {
    return Response.ok().build();
  }

  @GET
  @Path("{id}")
  public TaskDo getJson(@PathParam("id") String id) {
    TaskDo task = new TaskDo();
    task.setId(id);
    task.setText("test");
    return task;
  }

}
