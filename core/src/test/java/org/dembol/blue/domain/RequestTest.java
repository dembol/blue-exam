package org.dembol.blue.domain;

import com.google.common.collect.Lists;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

public class RequestTest {

	private Request sut;

	@BeforeMethod
	public void beforeMethod() {
		initMocks(this);

		sut = new Request();
	}

	@Test
	public void getIdShouldReturnSetId() throws Exception {
		// given
		Integer expectedId = Math.abs(ThreadLocalRandom.current().nextInt());

		// when
		sut.setId(expectedId);

		// then
		assertThat(sut.getId()).isEqualTo(expectedId);
	}

	@Test
	public void getTitleShouldReturnSetTitle() throws Exception {
		// given
		String expectedTitle = "test_title";

		// when
		sut.setTitle(expectedTitle);

		// then
		assertThat(sut.getTitle()).isEqualTo(expectedTitle);
	}

	@Test
	public void getTitleShouldReturnNonEmptyTitle() throws Exception {
		// given
		String expectedTitle = "";

		// when
		sut.setTitle(null);

		// then
		assertThat(sut.getTitle()).isEqualTo(expectedTitle);
	}

    @Test
	public void getDescriptionShouldReturnSetDescription() throws Exception {
		// given
		String expectedDescription = "test_description";

		// when
		sut.setDescription(expectedDescription);

		// then
		assertThat(sut.getDescription()).isEqualTo(expectedDescription);
	}

	@Test
	public void getDescriptionShouldReturnNonEmptyDescription() throws Exception {
		// given
		String expectedDescription = "";

		// when
		sut.setDescription(null);

		// then
		assertThat(sut.getDescription()).isEqualTo(expectedDescription);
	}

	@Test
	public void changeStateWithoutReasonShouldThrowExceptionWhenTransitionNotAvailable() throws Exception {
		// given
		Exception caughtException = null;

		// when
		try {
			sut.changeStateWithoutReason(State.REJECTED);
		} catch (Exception exception) {
			caughtException = exception;
		}

		// then
		assertThat(caughtException).isNotNull().isExactlyInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void changeStateWithReasonShouldThrowExceptionWhenTransitionNotAvailable() throws Exception {
		// given
		Exception caughtException = null;

		// when
		try {
			sut.changeStateWithReason(State.REJECTED, "reason");
		} catch (Exception exception) {
			caughtException = exception;
		}

		// then
		assertThat(caughtException).isNotNull().isExactlyInstanceOf(IllegalArgumentException.class);
	}

	@Test
	public void changeStateWithReasonShouldSetReason() throws Exception {
		// given
		String expectedReason = "reason";

		// when
		sut.changeStateWithReason(State.DELETED, expectedReason);

		// then
		assertThat(sut.getReason().get()).isEqualTo(expectedReason);
	}

	@Test
	public void getReasonShouldReturnEmptyReasonWhenStateNotChanged() throws Exception {
		// given

		// when

		// then
		assertThat(sut.getReason().isPresent()).isFalse();
	}

	@Test
	public void getStateForNewRequestShouldReturnCreated() throws Exception {
		// given

		// when

		// then
		assertThat(sut.getState()).isEqualTo(State.CREATED);
	}

	@Test
	public void getStateShouldReturnCurrentState() throws Exception {
		// given

		// when
		sut.changeStateWithoutReason(State.VERIFIED);

		// then
		assertThat(sut.getState()).isEqualTo(State.VERIFIED);
	}

	@Test
	public void getStateHistoryShouldReturnChangedStatesInOrder() throws Exception {
		// given
		List<State> expectedStateHistory = Lists.newArrayList(State.CREATED, State.VERIFIED, State.ACCEPTED);

		// when
		sut.changeStateWithoutReason(State.VERIFIED);
		sut.changeStateWithoutReason(State.ACCEPTED);
		sut.changeStateWithoutReason(State.PUBLISHED);

		// then
		assertThat(sut.getStateHistory()).isEqualTo(expectedStateHistory);
	}
}