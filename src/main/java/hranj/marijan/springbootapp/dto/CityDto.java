package hranj.marijan.springbootapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
public class CityDto {

    @Size(max = 45, message = "The name needs to have less than 45 characters")
    @NotEmpty(message = "The name can't be empty")
    private String name;

    @Size(max = 255, message = "The description needs to have less than 255 characters")
    @NotEmpty(message = "The description can't be empty")
    private String description;

    @NotNull(message = "Please add a population")
    @Max(value = Long.MAX_VALUE, message = "The number can't exceed " + Long.MAX_VALUE)
    private Long population;

}
