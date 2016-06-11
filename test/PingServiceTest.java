import ge.edu.freeuni.sdp.iot.sensor.bath_humidity.core.PingService;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;


import static org.junit.Assert.assertEquals;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

public class PingServiceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(PingService.class);
    }

    @Test
    public void testGetPing() {
        Response basilResponse = target("ping").request().get();
        assertEquals(basilResponse.getStatus(), Response.Status.OK.getStatusCode());
    }
}
