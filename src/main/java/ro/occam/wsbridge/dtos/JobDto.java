package ro.occam.wsbridge.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JobDto {

    private String title;
    private String description;
}
