package ro.occam.wsbridge.controllers.spring;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.occam.wsbridge.dtos.EmployeeDto;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping(("api/spring/employees"))
public class EmployeeController {

    @GetMapping
    public Collection<EmployeeDto> getEmployees() {
        return Collections.singleton(EmployeeDto.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .jobTitle("Principal software engineer")
                        .email("john.doe@occam.ro")
                        .build());
    }
}
