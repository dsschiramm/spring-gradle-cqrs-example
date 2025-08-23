package company.system.query;

import company.system.utils.models.output.ErrorDTO;
import jakarta.persistence.NoResultException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleMethodNotValidException(HttpRequestMethodNotSupportedException ex) {

        String message = "HTTP method not supported for this endpoint.";

        if (ex.getSupportedMethods() != null && ex.getSupportedMethods().length > 0) {
            message += " Supported methods: " + String.join(", ", ex.getSupportedMethods());
        }

        ErrorDTO errorDTO = new ErrorDTO("METHOD_NOT_ALLOWED", message);

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorDTO);
    }

    @ExceptionHandler(NoResultException.class)
    public ResponseEntity<ErrorDTO> handleNoResultException(NoResultException ignoredException) {
        ErrorDTO errorDTO = new ErrorDTO("NO_RESULT", "Resource not found.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDTO);
    }

    /*
        There could be an audit history of requests and unhandled exceptions
            and tracking could be done to facilitate analysis and maintainability.
    */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDTO> handleGlobalException(Exception ex, HttpServletRequest request) {

        LOGGER.error("UNHANDLED_EXCEPTION ON_REQUEST=[{} {}] EXCEPTION={} MESSAGE={}",
                request.getMethod(), request.getRequestURI(), ex.getClass().getSimpleName(), ex.getMessage());

        ErrorDTO errorDTO = new ErrorDTO(
                "INTERNAL_SERVER_ERROR",
                "An unexpected error occurred. Please contact support."
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDTO);
    }
}