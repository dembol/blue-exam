package org.dembol.blue.domain;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.EnumSet;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class StateTest {

	private State sut;

	@BeforeMethod
	public void beforeMethod() {
		initMocks(this);
	}

	@Test
	public void stateCreatedShouldAllowModifications() throws Exception {
		// given
		sut = State.CREATED;
		testStateAllowModifications();
	}

	@Test
	public void stateVerifiedShouldAllowModifications() throws Exception {
		// given
		sut = State.VERIFIED;
		testStateAllowModifications();
	}

	@Test
	public void stateDeletedShouldNotAllowModifications() throws Exception {
		// given
		sut = State.DELETED;
		testStateDoesNotAllowModifications();
	}

	@Test
	public void stateAcceptedShouldNotAllowModifications() throws Exception {
		// given
		sut = State.ACCEPTED;
		testStateDoesNotAllowModifications();
	}

	@Test
	public void stateRejectedShouldNotAllowModifications() throws Exception {
		// given
		sut = State.REJECTED;
		testStateDoesNotAllowModifications();
	}

	@Test
	public void statePublishedShouldNotAllowModifications() throws Exception {
		// given
		sut = State.PUBLISHED;
		testStateDoesNotAllowModifications();
	}

	@Test
	public void stateCreatedShouldNotRequireReason() throws Exception {
		// given
		sut = State.CREATED;
		testStateDoesNotRequireReason();
	}

	@Test
	public void stateVerifyShouldNotRequireReason() throws Exception {
		// given
		sut = State.VERIFIED;
		testStateDoesNotRequireReason();
	}

	@Test
	public void stateAcceptedShouldNotRequireReason() throws Exception {
		// given
		sut = State.ACCEPTED;
		testStateDoesNotRequireReason();
	}

	@Test
	public void statePublishedShouldNotRequireReason() throws Exception {
		// given
		sut = State.PUBLISHED;
		testStateDoesNotRequireReason();
	}

	@Test
	public void stateDeletedShouldNotRequireReason() throws Exception {
		// given
		sut = State.DELETED;
		testStateRequiresReason();
	}

	@Test
	public void stateRejectedShouldNotRequireReason() throws Exception {
		// given
		sut = State.REJECTED;
		testStateRequiresReason();
	}

	private void testStateAllowModifications() {
		testStateHasPermission(StatePermission.CONTENT_MODIFIABLE);
	}

	private void testStateDoesNotAllowModifications() {
		testStateHasNotPermission(StatePermission.CONTENT_MODIFIABLE);
	}

	private void testStateRequiresReason() {
		testStateHasPermission(StatePermission.REASON_REQUIRED);
	}

	private void testStateDoesNotRequireReason() {
		testStateHasNotPermission(StatePermission.REASON_REQUIRED);
	}

	private void testStateHasPermission(StatePermission expectedPermission) {

		// when
		EnumSet<StatePermission> permissions = sut.getPermissions();

		// then
		assertThat(permissions).contains(expectedPermission);
	}

	private void testStateHasNotPermission(StatePermission expectedPermission) {

		// when
		EnumSet<StatePermission> permissions = sut.getPermissions();

		// then
		assertThat(permissions).excludes(expectedPermission);
	}
}