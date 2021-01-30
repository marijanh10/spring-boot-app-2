package hranj.marijan.springbootapp.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class Response {

    private HttpStatus status;
    private Object response;

}
