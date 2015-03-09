package org.dembol.blue.interfaces;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Maps {@link java.lang.IllegalArgumentException} into 400 Bad Request Response.
 */
@Slf4j
public class IllegalArgumentExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

	@Override
	public Response toResponse(IllegalArgumentException exception) {
		log.info("illegal argument - {}", exception.getMessage());
		return Response.ok("400").status(Response.Status.BAD_REQUEST).build();
	}
}
