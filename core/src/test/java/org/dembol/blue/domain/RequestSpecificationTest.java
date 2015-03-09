package org.dembol.blue.domain;

import org.mockito.Mock;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;

import static org.dembol.blue.domain.State.CREATED;
import static org.fest.assertions.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class RequestSpecificationTest {

	private static final String TEST_TITLE = "test_title";
	private static final String TEST_DESCRIPTION = "test_description";
	private static final State TEST_STATE = CREATED;
	private static final String TEST_STATE_NAME = TEST_STATE.name();

	@Mock
	private Root<Request> rootMock;

	@Mock
	private Path<String> titlePathMock;

	@Mock
	private Path<String> descriptionPathMock;

	@Mock
	private Path<State> statePathMock;

	@Mock
	private Predicate titlePredicateMock;

	@Mock
	private Predicate descriptionPredicateMock;

	@Mock
	private Predicate statePredicateMock;

	@Mock
	private Predicate andPredicateMock;

	@Mock
	private CriteriaQuery criteriaQueryMock;

	@Mock
	private CriteriaBuilder criteriaBuilderMock;

	@Mock
	private SingularAttribute<Request, String> titleFieldMock;

	@Mock
	private SingularAttribute<Request, String> descriptionFieldMock;

	@Mock
	private SingularAttribute<Request, State> stateFieldMock;

	private RequestSpecification sut;
	
	@BeforeMethod
	public void beforeMethod() {
		initMocks(this);

		recordRootMockBehaviour();
		returnCriteriaBuilderBehaviour();

		sut = new RequestSpecification();
	}

	private void recordRootMockBehaviour() {
		Request_.title = titleFieldMock;
		Request_.description = descriptionFieldMock;
		Request_.state = stateFieldMock;

		when(rootMock.get(Request_.title)).thenReturn(titlePathMock);
		when(rootMock.get(Request_.description)).thenReturn(descriptionPathMock);
		when(rootMock.get(Request_.state)).thenReturn(statePathMock);
	}

	private void returnCriteriaBuilderBehaviour() {
		when(criteriaBuilderMock.equal(titlePathMock, TEST_TITLE)).thenReturn(titlePredicateMock);
		when(criteriaBuilderMock.equal(descriptionPathMock, TEST_DESCRIPTION)).thenReturn(descriptionPredicateMock);
		when(criteriaBuilderMock.equal(statePathMock, TEST_STATE)).thenReturn(statePredicateMock);

		Predicate[] predicates = {titlePredicateMock, descriptionPredicateMock, statePredicateMock};
		when(criteriaBuilderMock.and(predicates)).thenReturn(andPredicateMock);
	}

	@Test
	public void toPredicateShouldConsiderNonNullFields() throws Exception {
		// given
		sut.setTitle(TEST_TITLE);
		sut.setDescription(TEST_DESCRIPTION);
		sut.setState(TEST_STATE_NAME);

		// when
		Predicate predicate = sut.toPredicate(rootMock, criteriaQueryMock, criteriaBuilderMock);

		// then
		assertThat(predicate).isEqualTo(andPredicateMock);
	}
}