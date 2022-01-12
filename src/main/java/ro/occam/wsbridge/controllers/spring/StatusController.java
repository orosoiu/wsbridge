package ro.occam.wsbridge.controllers.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("springMvcStatusController")
@RequestMapping(("api/spring/"))
public class StatusController {

    @GetMapping("/status")
    public String getStatus() {
        return "Running";
    }
}
