package edu.vt.crest.hr.rest;

import java.util.ArrayList;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionProvider implements ExceptionMapper<ConstraintViolationException>
{

  @Override
  public Response toResponse(final ConstraintViolationException violationException)
  {
    final List<String> messages = new ArrayList();
    violationException.getConstraintViolations().forEach((cv) -> {
      messages.add(getValidationMessage(cv));
    });
    return Response.status(400).entity(messages).build();
  }

  private <T> String getValidationMessage(final ConstraintViolation<T> violation)
  {
    return String.format("%s %s", violation.getPropertyPath().toString(), violation.getMessage());
  }

}
