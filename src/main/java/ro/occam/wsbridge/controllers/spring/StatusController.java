package ro.occam.wsbridge.controllers.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("springMvcStatusController")
@RequestMapping(("/api/spring/status"))
public class StatusController {

    @GetMapping
    public String getStatus() {
        return "Running";
    }
}
