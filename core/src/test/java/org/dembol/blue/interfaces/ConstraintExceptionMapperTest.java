package org.dembol.blue.interfaces;

import org.hibernate.validator.method.MethodConstraintViolationException;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.ws.rs.core.Response;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class ConstraintExceptionMapperTest {

	@Mock
	private MethodConstraintViolationException exceptionMock;

	private ConstraintExceptionMapper sut;

	@BeforeMethod
	public void beforeMethod() {
		initMocks(this);
		sut = new ConstraintExceptionMapper();
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