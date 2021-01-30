package hranj.marijan.springbootapp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hranj.marijan.springbootapp.bean.ErrorResponse;
import hranj.marijan.springbootapp.bean.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public final class ResponseMessageSender {

    private ResponseMessageSender() {}

    public static void sendErrorResponseMessage(HttpServletResponse res, ErrorResponse errorResponse) {
        try {
            res.setStatus(errorResponse.getStatus().value());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.getWriter().write(convertObjectToJson(errorResponse));
            res.getWriter().flush();
            log.error(errorResponse.getException() + ": " + errorResponse.getError());
        } catch (IOException e) {
            log.error("IOException ", e);
        }
    }

    public static void sendResponseMessage(HttpServletResponse res, Response response) {
        try {
            res.setStatus(response.getStatus().value());
            res.setContentType(MediaType.APPLICATION_JSON_VALUE);
            res.getWriter().write(convertObjectToJson(response));
            res.getWriter().flush();
        } catch (IOException e) {
            log.error("IOException ", e);
        }
    }

    private static String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

}
