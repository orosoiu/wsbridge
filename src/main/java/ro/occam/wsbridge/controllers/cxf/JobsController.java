package ro.occam.wsbridge.controllers.cxf;

import org.springframework.stereotype.Service;
import ro.occam.wsbridge.dtos.JobDto;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;

@Path("/api/jobs")
@Service
public class JobsController {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Collection<JobDto> getAllJobs() {
        return List.of(JobDto.builder()
                .title("Senior developer wanted")
                .description("Looking for a senior developer who can write a bridge " +
                        "to integrate Spring MVC and Apache CXF frameworks")
                .build());
    }
}