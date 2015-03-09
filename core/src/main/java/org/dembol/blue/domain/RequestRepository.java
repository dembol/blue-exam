package org.dembol.blue.domain;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * Represents {@link org.dembol.blue.domain.Request} repository.
 *
 * Based on Repository building block from Domain-Driven Design approach.
 * Based on Spring Repositories.
 */
public interface RequestRepository extends CrudRepository<Request, Integer>, PagingAndSortingRepository<Request, Integer>, JpaSpecificationExecutor {
}
