package hranj.marijan.springbootapp.filter;

import hranj.marijan.springbootapp.bean.ErrorResponse;
import hranj.marijan.springbootapp.util.ResponseMessageSender;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (RuntimeException e) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .status(HttpStatus.BAD_REQUEST)
                    .exception(e.getClass().getSimpleName())
                    .error(e.getMessage())
                    .timestamp(new Timestamp(System.currentTimeMillis()))
                    .build();
            ResponseMessageSender.sendErrorResponseMessage(response, errorResponse);
        }
    }
}