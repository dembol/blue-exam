package org.dembol.blue.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.dembol.blue.shared.RequestNotFoundException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Maps {@link org.dembol.blue.shared.RequestNotFoundException} into 404 Not Found Response.
 */
@Slf4j
public class RequestNotFoundExceptionMapper implements ExceptionMapper<RequestNotFoundException> {

	@Override
	public Response toResponse(RequestNotFoundException exception) {
		log.info("request not found '{}'", exception.getId());
		return Response.ok("404").status(Response.Status.NOT_FOUND).build();
	}
}
