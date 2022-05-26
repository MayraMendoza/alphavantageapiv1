package com.careerdevs.alphavantageapiv1.repositories;

import com.careerdevs.alphavantageapiv1.models.Overview;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OverviewRepository extends CrudRepository<Overview, Long> {
// List<Laptop> findByName(String name);
     Overview findBySymbol(String symbol);

     List<Overview> findByExchange(String exchange);

}
