package org.dembol.blue.interfaces;

import org.dembol.blue.shared.RequestNotFoundException;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class RequestNotFoundExceptionMapperTest {

	@Mock
	private RequestNotFoundException exceptionMock;

	private RequestNotFoundExceptionMapper sut;

	@BeforeMethod
	public void beforeMethod() {
		initMocks(this);
		sut = new RequestNotFoundExceptionMapper();
	}

	@Test
	public void toResponseShouldReturnHttpNotFound() throws Exception {
		// given

		// when
		Response response = sut.toResponse(exceptionMock);

		// then
		assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
	}

}