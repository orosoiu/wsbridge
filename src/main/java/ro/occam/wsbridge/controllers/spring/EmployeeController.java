package ro.occam.wsbridge.controllers.spring;

import org.springframework.web.bind.annotation.*;
import ro.occam.wsbridge.dtos.EmployeeDto;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(("/api/employees"))
public class EmployeeController {

    @GetMapping
    public Collection<EmployeeDto> getEmployees() {
        return List.of(EmployeeDto.builder()
                        .firstName("John")
                        .lastName("Doe")
                        .jobTitle("Principal software engineer")
                        .email("john.doe@occam.ro")
                        .build(),
                EmployeeDto.builder()
                        .firstName("Jane")
                        .lastName("Doe")
                        .jobTitle("Principal software engineer")
                        .email("jane.doe@occam.ro")
                        .build());
    }

    @GetMapping("/{employeeBadgeNo}")
    public EmployeeDto getEmployeeByPathBadgeNo(@PathVariable String employeeBadgeNo) {
        return EmployeeDto.builder()
                .firstName("John")
                .lastName("Doe")
                .jobTitle("Principal software engineer")
                .email("john.doe@occam.ro")
                .badgeNo(employeeBadgeNo)
                .build();
    }
}
