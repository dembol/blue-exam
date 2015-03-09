package org.dembol.blue.domain;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import static org.dembol.blue.domain.StatePermission.CONTENT_MODIFIABLE;
import static org.dembol.blue.domain.StatePermission.REASON_REQUIRED;

/**
 * Represents single Request.
 *
 * Implements Aggregate Root building block from Domain-Driven Design approach. It's responsible for checking consistency and invariants.
 */
@ToString
@EqualsAndHashCode
@Entity
@Validated
public class Request {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	@Column(name = "title")
	@NotNull
	@Size(min = 1)
	private String title;

	@Column(name = "description")
	@NotNull
	@Size(min = 1)
	private String description;

	@Column(name = "reason")
	private String reason;

	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private StateChangeHistory history = new StateChangeHistory();

	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	@NotNull
	private State state = State.CREATED;

	void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public String getTitle() {
		return Strings.nullToEmpty(title);
	}

	public void setTitle(String title) {
		checkStateModifiable();
		this.title = title;
	}

	public String getDescription() {
		return Strings.nullToEmpty(description);
	}

	public void setDescription(String description) {
		checkStateModifiable();
		this.description = description;
	}

	public Optional<String> getReason() {
		return Optional.fromNullable(reason);
	}

	public State getState() {
		return state;
	}

	public List<State> getStateHistory() {
		return history.getStateHistory();
	}

	public void changeStateWithoutReason(State newState) {
		checkTransitionAvailable(newState);
		checkReasonNotRequired(newState);
		history.addNewStateChange(state);
		state = newState;
	}

	public void changeStateWithReason(State newState, String withReason) {
		checkTransitionAvailable(newState);
		checkReasonRequired(newState);
		history.addNewStateChange(state);
		state = newState;
		reason = withReason;
	}

	private void checkStateModifiable() {
		Preconditions.checkArgument(state.checkPermission(CONTENT_MODIFIABLE));
	}

	private void checkReasonRequired(State newState) {
		Preconditions.checkArgument(newState.checkPermission(REASON_REQUIRED));
	}

	private void checkReasonNotRequired(State newState) {
		Preconditions.checkArgument(!newState.checkPermission(REASON_REQUIRED));
	}

	private void checkTransitionAvailable(State newState) {
		Preconditions.checkArgument(state.checkTransition(newState));
	}
}
