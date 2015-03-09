package org.dembol.blue.interfaces;

import com.google.common.base.Optional;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.dembol.blue.application.RequestService;
import org.dembol.blue.domain.Request;
import org.dembol.blue.domain.RequestSpecification;
import org.dembol.blue.domain.State;

import java.util.List;
import javax.ws.rs.core.Response;

/**
 * Represents main REST system's facade.
 */
@Slf4j
public class RequestFacadeBean implements RequestFacade {

	@Getter(AccessLevel.PACKAGE)
	private final RequestService requestService;

	@Getter(AccessLevel.PACKAGE)
	private final RequestMapper requestMapper;

	public RequestFacadeBean(RequestService requestService) {
		this.requestService = requestService;
		this.requestMapper = new RequestMapper();
	}

	RequestFacadeBean(RequestService requestService, RequestMapper requestMapper) {
		this.requestService = requestService;
		this.requestMapper = requestMapper;
	}

	@Override
	public Response getRequest(Integer requestId) {
		Optional<Request> requestOptional = requestService.findRequestById(requestId);
		if (!requestOptional.isPresent()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		Request request = requestOptional.get();
		RequestDTO requestDTO = requestMapper.mapRequestToDTO(request);
		return Response.ok(requestDTO).build();
	}

	@Override
	public List<RequestDTO> findRequests(String title, String description, String state, Integer pageNumber) {
		RequestSpecification specification = new RequestSpecification();
		specification.setTitle(title);
		specification.setDescription(description);
		specification.setState(state);
		List<Request> requests = requestService.findRequestsBySpecification(specification, pageNumber);

		return requestMapper.mapRequestsToDTO(requests);
	}

	@Override
	public Integer addRequest(String title, String description) {
		Request request = new Request();
		request.setTitle(title);
		request.setDescription(description);
		return requestService.addRequest(request);
	}

	@Override
	public void setRequestAccepted(Integer requestId) {
		requestService.changeRequestState(requestId, State.ACCEPTED);
	}

	@Override
	public void setRequestVerified(Integer requestId) {
		requestService.changeRequestState(requestId, State.VERIFIED);
	}

	@Override
	public void setRequestPublished(Integer requestId) {
		requestService.changeRequestState(requestId, State.PUBLISHED);
	}

	@Override
	public void setRequestRejected(Integer requestId, String reason) {
		requestService.changeRequestStateWithReason(requestId, State.REJECTED, reason);
	}

	@Override
	public void setRequestDeleted(Integer requestId, String reason) {
		requestService.changeRequestStateWithReason(requestId, State.DELETED, reason);
	}

	@Override
	public void setRequestContent(Integer requestId, String title, String description) {
		requestService.changeRequestContent(requestId, title, description);
	}
}
