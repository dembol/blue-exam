package org.dembol.blue.interfaces;

import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.dembol.blue.domain.Request;
import org.dembol.blue.domain.State;
import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.dembol.blue.domain.State.ACCEPTED;
import static org.dembol.blue.domain.State.CREATED;
import static org.dembol.blue.domain.State.REJECTED;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class RequestMapperTest {

	private static final int TEST_REQUEST_ID = 1234;
	private static final String TEST_TITLE = "test_title";
	private static final String TEST_DESCRIPTION = "test_description";
	private static final String TEST_REASON = "test_reason";
	private static final State TEST_STATE = CREATED;

	@Mock
	private Request requestMock;

	private List<State> stateHistory;

	private RequestMapper sut;

	@BeforeMethod
	public void beforeMethod() {
		initMocks(this);

		stateHistory = Lists.newArrayList(CREATED, ACCEPTED, REJECTED);

		when(requestMock.getId()).thenReturn(TEST_REQUEST_ID);
		when(requestMock.getTitle()).thenReturn(TEST_TITLE);
		when(requestMock.getDescription()).thenReturn(TEST_DESCRIPTION);
		when(requestMock.getReason()).thenReturn(Optional.of(TEST_REASON));
		when(requestMock.getState()).thenReturn(TEST_STATE);
		when(requestMock.getStateHistory()).thenReturn(stateHistory);

		sut = new RequestMapper();
	}

	@Test
	public void mapRequestToDTOShouldMapAllProperties() throws Exception {
		// given

		// when
		RequestDTO requestDTO = sut.mapRequestToDTO(requestMock);

		// then
		assertRequestDTOHasProperValues(requestDTO);
	}

	@Test
	public void mapRequestsShouldMapAllRequests() throws Exception {
		// given
		List<Request> requests = Lists.newArrayList(requestMock, requestMock, requestMock);

		// when
		List<RequestDTO> requestsDTO = sut.mapRequestsToDTO(requests);

		// then
		for (RequestDTO requestDTO : requestsDTO) {
			assertRequestDTOHasProperValues(requestDTO);
		}
	}

	private void assertRequestDTOHasProperValues(RequestDTO requestDTO) {
		assertThat(requestDTO.getId()).isEqualTo(TEST_REQUEST_ID);
		assertThat(requestDTO.getTitle()).isEqualTo(TEST_TITLE);
		assertThat(requestDTO.getDescription()).isEqualTo(TEST_DESCRIPTION);
		assertThat(requestDTO.getReason()).isEqualTo(TEST_REASON);
		assertThat(requestDTO.getState()).isEqualTo(TEST_STATE.name());
		assertThat(requestDTO.getStateHistory()).isEqualTo(Lists.newArrayList(CREATED.name(), ACCEPTED.name(), REJECTED.name()));
	}

}