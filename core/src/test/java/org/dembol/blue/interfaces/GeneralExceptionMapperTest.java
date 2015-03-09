package org.dembol.blue.interfaces;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class GeneralExceptionMapperTest {

	@Mock
	private Exception exceptionMock;

	private GeneralExceptionMapper sut;

	@BeforeMethod
	public void beforeMethod() {
		initMocks(this);
		sut = new GeneralExceptionMapper();
	}

	@Test
	public void toResponseShouldReturnHttpInternalServerError() throws Exception {
		// given

		// when
		Response response = sut.toResponse(exceptionMock);

		// then
		assertThat(response.getStatus()).isEqualTo(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode());
	}

}