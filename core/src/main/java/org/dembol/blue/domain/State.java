package org.dembol.blue.domain;

import java.util.EnumSet;

import static org.dembol.blue.domain.StatePermission.CONTENT_MODIFIABLE;
import static org.dembol.blue.domain.StatePermission.REASON_REQUIRED;

/**
 * Represents {@link org.dembol.blue.domain.Request}'s states according to state diagram.
 */
public enum State {

	CREATED {
		@Override
		EnumSet<StatePermission> getPermissions() {
			return EnumSet.of(CONTENT_MODIFIABLE);
		}

		@Override
		EnumSet<State> getTransitions() {
			return EnumSet.of(VERIFIED, DELETED);
		}
	},

	VERIFIED {
		@Override
		EnumSet<StatePermission> getPermissions() {
			return EnumSet.of(CONTENT_MODIFIABLE);
		}

		@Override
		EnumSet<State> getTransitions() {
			return EnumSet.of(REJECTED, ACCEPTED);
		}

	},

	ACCEPTED {
		@Override
		EnumSet<State> getTransitions() {
			return EnumSet.of(PUBLISHED, REJECTED);
		}
	},

	DELETED {
		@Override
		EnumSet<StatePermission> getPermissions() {
			return EnumSet.of(REASON_REQUIRED);
		}
	},

	REJECTED {
		@Override
		EnumSet<StatePermission> getPermissions() {
			return EnumSet.of(REASON_REQUIRED);
		}
	},

	PUBLISHED();

	public boolean checkPermission(StatePermission permission) {
		return getPermissions().contains(permission);
	}

	public boolean checkTransition(State state) {
		return getTransitions().contains(state);
	}

	EnumSet<StatePermission> getPermissions() {
		return EnumSet.noneOf(StatePermission.class);
	}

	EnumSet<State> getTransitions() {
		return EnumSet.noneOf(State.class);
	}
}
