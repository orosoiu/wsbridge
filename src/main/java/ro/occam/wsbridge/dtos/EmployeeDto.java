package ro.occam.wsbridge.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDto {

    private String firstName;
    private String lastName;
    private String jobTitle;
    private String email;
}
