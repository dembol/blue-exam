package org.dembol.blue.interfaces;

import org.dembol.blue.shared.ContractException;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class ContractExceptionMapperTest {

	@Mock
	private ContractException exceptionMock;

	private ContractExceptionMapper sut;

	@BeforeMethod
	public void beforeMethod() {
		initMocks(this);
		sut = new ContractExceptionMapper();
	}

	@Test
	public void toResponseShouldReturnHttpBadRequest() throws Exception {
		// given

		// when
		Response response = sut.toResponse(exceptionMock);

		// then
		assertThat(response.getStatus()).isEqualTo(Response.Status.BAD_REQUEST.getStatusCode());
	}
}