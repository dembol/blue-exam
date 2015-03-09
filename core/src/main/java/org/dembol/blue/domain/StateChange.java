package org.dembol.blue.domain;

import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * Represents single change of {@link org.dembol.blue.domain.Request}.
 */
@Validated
public class StateChange {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private String id;

	@Column(name = "state")
	@NotNull
	private State state;
}
