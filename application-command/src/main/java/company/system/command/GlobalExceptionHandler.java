package company.system.command;

import company.system.command.exceptions.DomainException;
import company.system.utils.models.output.ErrorDTO;
import company.system.utils.models.output.FieldValidationDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorDTO> handleMethodNotValidException(HttpRequestMethodNotSupportedException ex) {

        String message = "HTTP method not supported for this endpoint.";

        if (ex.getSupportedMethods() != null && ex.getSupportedMethods().length > 0) {
            message += " Supported methods: " + String.join(", ", ex.getSupportedMethods());
        }

        ErrorDTO errorDTO = new ErrorDTO("METHOD_NOT_ALLOWED", message);

        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED).body(errorDTO);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDTO> handleArgumentNotValidException(MethodArgumentNotValidException ex) {

        List<FieldValidationDTO> fieldErrors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fe -> new FieldValidationDTO(
                        fe.getField(),
                        fe.getRejectedValue(),
                        fe.getDefaultMessage(),
                        fe.getCode()
                ))
                .toList();

        ErrorDTO error = new ErrorDTO("VALIDATION_ERROR",
                "Validation failed for one or more fields",
                fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorDTO> handleException(ConstraintViolationException ex) {

        List<FieldValidationDTO> fieldErrors = ex.getConstraintViolations()
                .stream()
                .map(cv -> new FieldValidationDTO(
                        cv.getPropertyPath().toString(),
                        cv.getInvalidValue(),
                        cv.getMessage(),
                        cv.getConstraintDescriptor().getAnnotation().annotationType().getSimpleName()
                ))
                .toList();

        ErrorDTO error = new ErrorDTO("VALIDATION_ERROR",
                "Validation failed for one or more fields",
                fieldErrors);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ErrorDTO> handleDomainException(DomainException ex) {

        return ResponseEntity.
                status(ex.getStatus()).
                body(new ErrorDTO(ex.getCode(), ex.getMessage()));
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