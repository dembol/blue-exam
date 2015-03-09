package org.dembol.blue.domain;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RequestRepository extends CrudRepository<Request, Integer>, PagingAndSortingRepository<Request, Integer>, JpaSpecificationExecutor {
}
