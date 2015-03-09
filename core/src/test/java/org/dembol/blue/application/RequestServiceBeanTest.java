package org.dembol.blue.application;

import com.google.common.base.Optional;
import org.dembol.blue.domain.Request;
import org.dembol.blue.domain.RequestRepository;
import org.dembol.blue.domain.RequestSpecification;
import org.dembol.blue.domain.State;
import org.dembol.blue.shared.RequestNotFoundException;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Matchers.refEq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class RequestServiceBeanTest {

	private static final int TEST_REQUEST_ID = 1234;
	private static final int TEST_REQUEST_NON_EXISTS_ID = 12345;
	private static final String TEST_TITLE = "test_title";
	private static final String TEST_DESCRIPTION = "test_description";
	private static final String TEST_REASON = "test_reason";

	@Mock
	private RequestRepository requestRepositoryMock;

	@Mock
	private RequestSpecification requestSpecificationMock;

	private Request request;

	private RequestService sut;

	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);

		request = new Request();
		when(requestRepositoryMock.findOne(TEST_REQUEST_ID)).thenReturn(request);

		sut = new RequestServiceBean(requestRepositoryMock);
	}

	@Test
	public void addRequestShouldAddRequestToRepository() throws Exception {
		// given

		// when
		sut.addRequest(request);

		// then
		verify(requestRepositoryMock).save(request);
	}

	@Test
	public void addRequestShouldReturnNewRequestId() throws Exception {
		// given
		Request requestMock = Mockito.mock(Request.class);
		when(requestMock.getId()).thenReturn(TEST_REQUEST_ID);
		when(requestRepositoryMock.save(requestMock)).thenReturn(requestMock);

		// when
		Integer newId = sut.addRequest(requestMock);

		// then
		assertThat(newId).isEqualTo(TEST_REQUEST_ID);
	}

	@Test
	public void changeRequestContentShouldSetTitleAndDescription() throws Exception {
		// given

		// when
		sut.changeRequestContent(TEST_REQUEST_ID, TEST_TITLE, TEST_DESCRIPTION);

		// then
		assertThat(request.getTitle()).isEqualTo(TEST_TITLE);
		assertThat(request.getDescription()).isEqualTo(TEST_DESCRIPTION);
	}

	@Test
	public void changeRequestContentShouldSaveRequestInRepository() throws Exception {
		// given
		Request expectedRequest = new Request();
		expectedRequest.setTitle(TEST_TITLE);
		expectedRequest.setDescription(TEST_DESCRIPTION);

		// when
		sut.changeRequestContent(TEST_REQUEST_ID, TEST_TITLE, TEST_DESCRIPTION);

		// then

		verify(requestRepositoryMock).save(refEq(expectedRequest));
	}

	@Test
	public void changeRequestStateShouldChangeStateInRequest() throws Exception {
		// given
		State expectedState = State.VERIFIED;

		// when
		sut.changeRequestState(TEST_REQUEST_ID, expectedState);

		// then
		assertThat(request.getState()).isEqualTo(expectedState);
	}

	@Test
	public void changeRequestStateShouldThrowExceptionWhenRequestDoesNotExists() throws Exception {
		// given
		State expectedState = State.VERIFIED;
		Exception caughtException = null;

		// when
		try {
			sut.changeRequestState(TEST_REQUEST_NON_EXISTS_ID, expectedState);
		} catch (Exception exception) {
			caughtException = exception;
		}

		// then
		assertThat(caughtException).isInstanceOf(RequestNotFoundException.class);
		assertThat(((RequestNotFoundException) caughtException).getId()).isEqualTo(TEST_REQUEST_NON_EXISTS_ID);
	}

	@Test
	public void changeRequestStateWithReasonShouldChangeStateInRequest() throws Exception {
		// given
		State expectedState = State.DELETED;

		// when
		sut.changeRequestStateWithReason(TEST_REQUEST_ID, expectedState, TEST_REASON);

		// then
		assertThat(request.getState()).isEqualTo(expectedState);
	}

	@Test
	public void changeRequestStateWithReasonShouldChangeReasonInRequest() throws Exception {
		// given
		State expectedState = State.DELETED;

		// when
		sut.changeRequestStateWithReason(TEST_REQUEST_ID, expectedState, TEST_REASON);

		// then
		assertThat(request.getReason().get()).isEqualTo(TEST_REASON);
	}

	@Test
	public void findRequestShouldReturnRequestFromRepositoryWhenExists() throws Exception {
		// given

		// when
		Optional<Request> foundRequest = sut.findRequestById(TEST_REQUEST_ID);

		// then
		assertThat(foundRequest.get()).isEqualTo(request);
	}

	@Test
	public void findRequestShouldReturnAbsentWhenRequestDoesNotExists() throws Exception {
		// given

		// when
		Optional<Request> foundRequest = sut.findRequestById(TEST_REQUEST_NON_EXISTS_ID);

		// then
		assertThat(foundRequest.isPresent()).isFalse();
	}
}