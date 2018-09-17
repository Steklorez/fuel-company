package com.fuelcompany.infrastructure.repository;


import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.io.Serializable;

@NoRepositoryBean
public interface DefaultRepository<T, ID extends Serializable> extends PagingAndSortingRepository<T, ID> {
}