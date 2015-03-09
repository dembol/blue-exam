package org.dembol.blue.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.method.MethodConstraintViolationException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Maps {@link org.hibernate.validator.method.MethodConstraintViolationException} into 400 Bad Request Response.
 */
@Slf4j
public class ConstraintExceptionMapper implements ExceptionMapper<MethodConstraintViolationException> {

	@Override
	public Response toResponse(MethodConstraintViolationException exception) {
		log.info("constraint exception", exception);
		return Response.ok("400").status(Response.Status.BAD_REQUEST).build();
	}
}
