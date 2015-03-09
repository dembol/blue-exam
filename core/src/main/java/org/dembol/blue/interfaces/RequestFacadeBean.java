package org.dembol.blue.interfaces;

import lombok.extern.slf4j.Slf4j;
import org.dembol.blue.application.RequestService;
import org.dembol.blue.domain.Request;
import org.dembol.blue.domain.RequestSpecification;
import org.dembol.blue.domain.State;

import java.util.List;
import java.util.Optional;
import javax.ws.rs.core.Response;

@Slf4j
public class RequestFacadeBean implements RequestFacade {

	private final RequestService requestService;

	public RequestFacadeBean(RequestService requestService) {
		this.requestService = requestService;
	}

	@Override
	public Response getRequest(Integer requestId) {
		Optional<Request> requestOptional = requestService.findRequestById(requestId);
		if (!requestOptional.isPresent()) {
			return Response.status(Response.Status.NOT_FOUND).build();
		}

		Request request = requestOptional.get();
		RequestMapper requestMapper = new RequestMapper();
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

		RequestMapper requestMapper = new RequestMapper();
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
	public void setRequestAccept(Integer requestId) {
		requestService.changeRequestState(requestId, State.ACCEPTED);
	}

	@Override
	public void setRequestVerify(Integer requestId) {
		requestService.changeRequestState(requestId, State.VERIFIED);
	}

	@Override
	public void setRequestPublish(Integer requestId) {
		requestService.changeRequestState(requestId, State.PUBLISHED);
	}

	@Override
	public void setRequestReject(Integer requestId, String reason) {
		requestService.changeRequestStateWithReason(requestId, State.REJECTED, reason);
	}

	@Override
	public void setRequestDelete(Integer requestId, String reason) {
		requestService.changeRequestStateWithReason(requestId, State.DELETED, reason);
	}

	@Override
	public void setRequestContent(Integer requestId, String title, String description) {
		requestService.changeRequestContent(requestId, title, description);
	}
}
