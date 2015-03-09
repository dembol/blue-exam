package org.dembol.blue.interfaces;

import com.google.common.collect.Lists;
import org.dembol.blue.domain.Request;
import org.dembol.blue.domain.State;

import java.util.List;

/**
 * Represents mapper between {@link org.dembol.blue.domain.Request} and {@link org.dembol.blue.interfaces.RequestDTO}.
 */
class RequestMapper {

	List<RequestDTO> mapRequestsToDTO(List<Request> requests) {
		List<RequestDTO> requestsDTO = Lists.newArrayList();
		for (Request request : requests) {
			RequestDTO requestDTO = mapRequestToDTO(request);
			requestsDTO.add(requestDTO);
		}

		return requestsDTO;
	}

	RequestDTO mapRequestToDTO(Request request) {
		RequestDTO requestDTO = new RequestDTO();
		requestDTO.setId(request.getId());
		requestDTO.setState(request.getState().toString());
		requestDTO.setReason(request.getReason());
		requestDTO.setTitle(request.getTitle());
		requestDTO.setDescription(request.getDescription());

		List<String> simpleHistoryStates = Lists.newArrayList();
		for (State historyState : request.getStateHistory()) {
			simpleHistoryStates.add(historyState.toString());
		}

		requestDTO.setStateHistory(simpleHistoryStates);

		return requestDTO;
	}
}
