package org.dembol.blue.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.dembol.blue.shared.ContractException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * Maps {@link org.dembol.blue.shared.ContractException} into 400 Bad Request Response.
 */
@Slf4j
public class ContractExceptionMapper implements ExceptionMapper<ContractException> {

	@Override
	public Response toResponse(ContractException exception) {
		log.info("contract exception", exception.getMessage());
		return Response.ok("400").status(Response.Status.BAD_REQUEST).build();
	}
}
