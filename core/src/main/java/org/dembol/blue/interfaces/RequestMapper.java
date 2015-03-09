package org.dembol.blue.interfaces;

import org.dembol.blue.domain.Request;
import org.dembol.blue.domain.State;

import java.util.List;
import java.util.stream.Collectors;

class RequestMapper {

	List<RequestDTO> mapRequestsToDTO(List<Request> requests) {
		return requests.stream().map(this::mapRequestToDTO).collect(Collectors.toList());
	}

	RequestDTO mapRequestToDTO(Request request) {
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setId(request.getId());
		requestDTO.setState(request.getState().toString());
		requestDTO.setReason(request.getReason());
		requestDTO.setTitle(request.getTitle());
		requestDTO.setDescription(request.getDescription());

		List<String> simpleStateHistory = request.getStateHistory().stream().map(State::toString).collect(Collectors.toList());
		requestDTO.setStateHistory(simpleStateHistory);

		return requestDTO;
	}
}
