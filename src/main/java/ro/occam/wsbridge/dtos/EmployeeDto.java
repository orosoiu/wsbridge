package ro.occam.wsbridge.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EmployeeDto {

    private String firstName;
    private String lastName;
    private String jobTitle;
    private String email;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String badgeNo;
}
