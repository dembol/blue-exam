package org.dembol.blue.shared;

import lombok.Getter;

public class RequestNotFoundException extends ContractException {

	@Getter
	private Integer id;

	public RequestNotFoundException(String message, Integer id) {
		super(message);
		this.id = id;
	}
}
