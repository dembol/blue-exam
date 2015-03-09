package org.dembol.blue.application;

import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;
import com.google.common.collect.Lists;
import org.dembol.blue.domain.Request;
import org.dembol.blue.domain.RequestRepository;
import org.dembol.blue.domain.RequestSpecification;
import org.dembol.blue.domain.State;
import org.dembol.blue.shared.RequestNotFoundException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Represents application service providing common interface for all Open-Host Service from interfaces layer.
 *
 * Based on Domain-Driven Design Layers.
 */
public class RequestServiceBean implements RequestService {

	private static final int DEFAULT_PAGE_SIZE = 10;
	private static final int DEFAULT_PAGE = 0;

	private final RequestRepository requestRepository;

	public RequestServiceBean(RequestRepository requestRepository) {
		this.requestRepository = requestRepository;
	}

	@Override
	public Integer addRequest(Request request) {
		requestRepository.save(request);
		return request.getId();
	}

	@Override
	public void changeRequestContent(Integer id, String title, String description) {
		Request request = findRequestByIdMandatory(id);
		request.setTitle(title);
		request.setDescription(description);
		requestRepository.save(request);
	}

	@Override
	public void changeRequestState(Integer id, State state) {
		Request request = findRequestByIdMandatory(id);
		request.changeStateWithoutReason(state);
	}

	@Override
	public void changeRequestStateWithReason(Integer id, State state, String reason) {
		Request request = findRequestByIdMandatory(id);
		request.changeStateWithReason(state, reason);
	}

	private Request findRequestByIdMandatory(Integer id) {
		Optional<Request> requestOptional = findRequestById(id);
		if (!requestOptional.isPresent()) {
			throw new RequestNotFoundException("there is no request with id", id);
		}

		return requestOptional.get();
	}

	@Override
	public Optional<Request> findRequestById(Integer id) {
		Request request = requestRepository.findOne(id);
		return Optional.fromNullable(request);
	}

	@Override
	public List<Request> findRequestsBySpecification(RequestSpecification specification, Integer pageNumber) {
		pageNumber = MoreObjects.firstNonNull(pageNumber, DEFAULT_PAGE);
		Pageable pageable = new PageRequest(pageNumber, DEFAULT_PAGE_SIZE);

		@SuppressWarnings("unchecked")
		Iterable<Request> requests = (Iterable<Request>) requestRepository.findAll(specification, pageable);

		return Lists.newArrayList(requests);
	}
}
