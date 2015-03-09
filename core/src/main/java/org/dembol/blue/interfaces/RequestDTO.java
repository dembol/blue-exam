package org.dembol.blue.interfaces;

import com.google.common.collect.Lists;
import lombok.Data;

import java.util.List;
import java.util.Optional;

/**
 * Represents Data Access Object for the {@link org.dembol.blue.domain.Request}.
 */
@Data
public class RequestDTO {

	private Integer id;

	private String title;

	private String description;

	private String reason;

	private String state;

	private List<String> stateHistory = Lists.newArrayList();

	public void setReason(Optional<String> reasonOptional) {
		if (reasonOptional.isPresent()) {
			reason = reasonOptional.get();
		}
	}
}
