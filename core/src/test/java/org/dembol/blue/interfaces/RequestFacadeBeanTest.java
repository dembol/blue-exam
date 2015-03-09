package org.dembol.blue.interfaces;

import com.google.common.base.Optional;
import org.dembol.blue.application.RequestService;
import org.dembol.blue.domain.Request;
import org.dembol.blue.domain.RequestSpecification;
import org.dembol.blue.domain.State;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import javax.ws.rs.core.Response;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class RequestFacadeBeanTest {

	private static final int TEST_REQUEST_ID = 1234;
	private static final int TEST_REQUEST_NON_EXISTS_ID = 12345;
	private static final String TEST_TITLE = "test_title";
	private static final String TEST_DESCRIPTION = "test_description";
	private static final String TEST_REASON = "test_reason";
	private static final String TEST_STATE = "CREATED";

	@Mock
	private RequestService requestServiceMock;

	@Mock
	private RequestMapper requestMapperMock;

	@Mock
	private Request requestMock;

	@Mock
	private RequestDTO requestDTOMock;

	@Mock
	private List<Request> requestsMock;

	@Mock
	private List<RequestDTO> requestsDTOMock;

	private RequestFacade sut;

	@BeforeMethod
	public void beforeMethod() {
		initMocks(this);

		recordRequestMockBehaviour();
		recordRequestMapperMockBehaviour();
		recordRequestServiceMockBehaviour();

		sut = new RequestFacadeBean(requestServiceMock, requestMapperMock);
	}

	private void recordRequestMockBehaviour() {
		when(requestMock.getId()).thenReturn(TEST_REQUEST_ID);
		when(requestMock.getTitle()).thenReturn(TEST_TITLE);
		when(requestMock.getDescription()).thenReturn(TEST_DESCRIPTION);
		when(requestMock.getState()).thenReturn(State.CREATED);
	}

	private void recordRequestMapperMockBehaviour() {
		when(requestMapperMock.mapRequestToDTO(requestMock)).thenReturn(requestDTOMock);
		when(requestMapperMock.mapRequestsToDTO(requestsMock)).thenReturn(requestsDTOMock);
	}

	private void recordRequestServiceMockBehaviour() {
		when(requestServiceMock.findRequestById(TEST_REQUEST_NON_EXISTS_ID)).thenReturn(Optional.<Request>absent());
		when(requestServiceMock.findRequestById(TEST_REQUEST_ID)).thenReturn(Optional.of(requestMock));

		RequestSpecification requestSpecification = new RequestSpecification();
		requestSpecification.setTitle(TEST_TITLE);
		requestSpecification.setDescription(TEST_DESCRIPTION);
		requestSpecification.setState(TEST_STATE);
		when(requestServiceMock.findRequestsBySpecification(requestSpecification, 0)).thenReturn(requestsMock);

		Request expectedRequest = new Request();
		expectedRequest.setTitle(TEST_TITLE);
		expectedRequest.setDescription(TEST_DESCRIPTION);
		when(requestServiceMock.addRequest(expectedRequest)).thenReturn(TEST_REQUEST_ID);
	}

	@Test
	public void oneArgConstructorShouldSetProperFields() throws Exception {
		// given

		// when
		RequestFacadeBean sutBean = new RequestFacadeBean(requestServiceMock);

		// then
		assertThat(sutBean.getRequestService()).isEqualTo(requestServiceMock);
		assertThat(sutBean.getRequestMapper()).isNotNull();
	}

	@Test
	public void twoArgConstructorShouldSetProperFields() throws Exception {
		// given

		// when
		RequestFacadeBean sutBean = new RequestFacadeBean(requestServiceMock, requestMapperMock);

		// then
		assertThat(sutBean.getRequestService()).isEqualTo(requestServiceMock);
		assertThat(sutBean.getRequestMapper()).isEqualTo(requestMapperMock);
	}

	@Test
	public void getRequestShouldReturnHttpNotFoundWhenRequestDoesNotExists() throws Exception {
		// given

		// when
		Response response = sut.getRequest(TEST_REQUEST_NON_EXISTS_ID);

		// then
		assertThat(response.getStatus()).isEqualTo(Response.Status.NOT_FOUND.getStatusCode());
	}

	@Test
	public void getRequestShouldReturnRequestDTOWhenRequestExists() throws Exception {
		// given

		// when
		Response response = sut.getRequest(TEST_REQUEST_ID);

		// then
		RequestDTO requestDTO = (RequestDTO) response.getEntity();
		assertThat(requestDTO).isEqualTo(requestDTOMock);
	}

	@Test
	public void fndRequestsShouldReturnRequestsListSatisfyingSpecification() throws Exception {
		// given

		// when
		List<RequestDTO> requestsDTO = sut.findRequests(TEST_TITLE, TEST_DESCRIPTION, TEST_STATE, 0);

		// then
		assertThat(requestsDTO).isEqualTo(requestsDTOMock);
	}

	@Test
	public void addRequestShouldAddNewRequestToService() throws Exception {
		// given
		Request expectedRequest = new Request();
		expectedRequest.setTitle(TEST_TITLE);
		expectedRequest.setDescription(TEST_DESCRIPTION);

		// when
		sut.addRequest(TEST_TITLE, TEST_DESCRIPTION);

		// then

		verify(requestServiceMock).addRequest(expectedRequest);
	}

	@Test
	public void addRequestShouldReturnNewRequestId() throws Exception {
		// given
		Request expectedRequest = new Request();
		expectedRequest.setTitle(TEST_TITLE);
		expectedRequest.setDescription(TEST_DESCRIPTION);

		// when
		Integer newId = sut.addRequest(TEST_TITLE, TEST_DESCRIPTION);

		// then

		assertThat(newId).isEqualTo(TEST_REQUEST_ID);
	}

	@Test
	public void setRequestAcceptedShouldDelegateToService() throws Exception {
		// given

		// when
		sut.setRequestAccepted(TEST_REQUEST_ID);

		// then
		verify(requestServiceMock).changeRequestState(TEST_REQUEST_ID, State.ACCEPTED);
	}

	@Test
	public void setRequestVerifiedShouldDelegateToService() throws Exception {
		// given

		// when
		sut.setRequestVerified(TEST_REQUEST_ID);

		// then
		verify(requestServiceMock).changeRequestState(TEST_REQUEST_ID, State.VERIFIED);
	}

	@Test
	public void setRequestPublishedShouldDelegateToService() throws Exception {
		// given

		// when
		sut.setRequestPublished(TEST_REQUEST_ID);

		// then
		verify(requestServiceMock).changeRequestState(TEST_REQUEST_ID, State.PUBLISHED);
	}

	@Test
	public void setRequestRejectedShouldDelegateToService() throws Exception {
		// given

		// when
		sut.setRequestRejected(TEST_REQUEST_ID, TEST_REASON);

		// then
		verify(requestServiceMock).changeRequestStateWithReason(TEST_REQUEST_ID, State.REJECTED, TEST_REASON);
	}

	@Test
	public void setRequestDeletedShouldDelegateToService() throws Exception {
		// given

		// when
		sut.setRequestDeleted(TEST_REQUEST_ID, TEST_REASON);

		// then
		verify(requestServiceMock).changeRequestStateWithReason(TEST_REQUEST_ID, State.DELETED, TEST_REASON);
	}

	@Test
	public void setRequestContentShouldDelegateToService() throws Exception {
		// given

		// when
		sut.setRequestContent(TEST_REQUEST_ID, TEST_TITLE, TEST_DESCRIPTION);

		// then
		verify(requestServiceMock).changeRequestContent(TEST_REQUEST_ID, TEST_TITLE, TEST_DESCRIPTION);
	}
}