package org.dembol.blue.application;

import com.google.common.base.Optional;
import org.dembol.blue.domain.Request;
import org.dembol.blue.domain.RequestSpecification;
import org.dembol.blue.domain.State;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Represents application service interface with JSR-303 Constraints.
 *
 * Based on Domain-Driven Design Layers.
 */
@Validated
public interface RequestService {

	Integer addRequest(@NotNull @Valid Request request);

	Optional<Request> findRequestById(@NotNull Integer id);

	List<Request> findRequestsBySpecification(RequestSpecification specification, Integer pageNumber);

	void changeRequestState(@NotNull Integer id, @NotNull State state);

	void changeRequestStateWithReason(@NotNull Integer id, @NotNull State state, @NotNull @Size(min = 1) String reason);

	void changeRequestContent(@NotNull Integer id, @NotNull @Size(min = 1) String title, @NotNull @Size(min = 1) String description);
}
