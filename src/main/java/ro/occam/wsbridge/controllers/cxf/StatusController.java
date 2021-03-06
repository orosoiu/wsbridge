package ro.occam.wsbridge.controllers.cxf;

import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("/api/cxf/status")
@Service("apacheCxfStatusController")
public class StatusController {

    @GET
    @Path("/")
    public String getStatus() {
        return "Running";
    }
}
