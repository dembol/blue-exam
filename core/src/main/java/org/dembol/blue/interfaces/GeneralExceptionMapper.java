package org.dembol.blue.interfaces;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

@Slf4j
public class GeneralExceptionMapper implements ExceptionMapper<Exception> {

	@Override
	public Response toResponse(Exception exception) {
		log.error("internal exception", exception);
		return Response.ok("503").status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}
}
