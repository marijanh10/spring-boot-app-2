package hranj.marijan.springbootapp.bean;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.sql.Timestamp;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private Timestamp timestamp;
    private HttpStatus status;
    private String error;
    private String exception;

}
