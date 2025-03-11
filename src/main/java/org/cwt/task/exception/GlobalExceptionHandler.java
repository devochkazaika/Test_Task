package org.cwt.task.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.HashMap;
import java.util.Map;

@Provider
public class GlobalExceptionHandler implements ExceptionMapper<Throwable> {

    @Override
    public Response toResponse(Throwable exception) {
        Map<String, String> errorResponse = new HashMap<>();

        if (exception instanceof IllegalArgumentException) {
            errorResponse.put("message", "Некорректный аргумент: " + exception.getMessage());
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .build();
        }
        else if (exception instanceof NotFoundException) {
            errorResponse.put("message", "Не найдено: " + exception.getMessage());
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(errorResponse)
                    .build();
        }
        else if (exception instanceof ConstraintViolationException) {
            for (ConstraintViolation<?> violation : ((ConstraintViolationException) exception).getConstraintViolations()) {
                errorResponse.put(violation.getPropertyPath().toString(), violation.getMessage());
            }
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity(errorResponse)
                    .build();
        } else {
            errorResponse.put("message", exception.getMessage());
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(errorResponse)
                    .build();
        }
    }
}