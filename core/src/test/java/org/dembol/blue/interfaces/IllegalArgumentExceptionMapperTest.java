package org.dembol.blue.interfaces;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class IllegalArgumentExceptionMapperTest {

	@Mock
	private IllegalArgumentException exceptionMock;

	private IllegalArgumentExceptionMapper sut;

	@BeforeMethod
	public void beforeMethod() {
		initMocks(this);
		sut = new IllegalArgumentExceptionMapper();
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