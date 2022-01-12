package ro.occam.wsbridge.controllers.cxf;

import org.springframework.stereotype.Service;
import ro.occam.wsbridge.dtos.JobAd;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Arrays;
import java.util.Collection;

@Path("/jobs")
@Service
public class JobAdsController {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<JobAd> getAllJobs() {
        return Arrays.asList(JobAd.builder()
                .title("Senior developer wanted")
                .description("Looking for a senior developer who can write a bridge " +
                        "to integrate Spring MVC and Apache CXF frameworks")
                .build());
    }
}

