package org.dembol.blue.domain;

import com.google.common.collect.Lists;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * Represents history of {@link org.dembol.blue.domain.Request} changes.

 * Implements the Event Sourcing pattern based on {@link StateChange}.
 */
@ToString
@EqualsAndHashCode
@Entity
class StateChangeHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@ElementCollection(targetClass = State.class, fetch = FetchType.EAGER)
	@Enumerated(EnumType.STRING)
	@CollectionTable(name = "request_history")
	@Column(name = "stateHistory")
	private List<State> stateHistory = Lists.newArrayList();

	void addNewStateChange(State state) {
		stateHistory.add(state);
	}

	List<State> getStateHistory() {
		return stateHistory;
	}
}

