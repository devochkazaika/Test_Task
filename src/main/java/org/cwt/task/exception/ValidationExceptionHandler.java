package org.cwt.task.exception;


import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Provider
public class ValidationExceptionHandler implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(ConstraintViolationException exception) {
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        Map<String, Object> response = new HashMap<>();

        response.put("message", "Ошибка валидации. Проверьте введенные данные.");
        Map<String, String> errors = new HashMap<>();

        for (ConstraintViolation<?> violation : violations) {
            errors.put(violation.getPropertyPath().toString(), violation.getMessage());
        }

        response.put("errors", errors);

        return Response.status(Response.Status.BAD_REQUEST).entity(response).build();
    }
}

