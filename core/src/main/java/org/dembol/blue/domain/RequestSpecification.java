package org.dembol.blue.domain;

import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 * Represents Request Specification.
 *
 * Based on Specification building block from Domain-Driven Design approach.
 */
@Data
public class RequestSpecification implements Specification<Request> {

	private String title;

	private String description;

	private State state;

	public void setState(String stateName) {
		if (stateName != null) {
			state = State.valueOf(stateName);
		}
	}

	@Override
	public Predicate toPredicate(Root<Request> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {
		List<Predicate> predicates = Lists.newArrayList();

		if (title != null) {
			Predicate predicate = cb.equal(root.get(Request_.title), title);
			predicates.add(predicate);
		}

		if (description != null) {
			Predicate predicate = cb.equal(root.get(Request_.description), description);
			predicates.add(predicate);
		}

		if (state != null) {
			Predicate predicate = cb.equal(root.get(Request_.state), state);
			predicates.add(predicate);
		}

		Predicate[] predicatesArray = predicates.stream().toArray(Predicate[]::new);
		return cb.and(predicatesArray);
	}
}
