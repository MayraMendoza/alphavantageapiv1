package com.careerdevs.alphavantageapiv1.repositories;

import com.careerdevs.alphavantageapiv1.models.Overview;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OverviewRepository extends CrudRepository<Overview, Long> {
// List<Laptop> findByName(String name);
     Overview findBySymbol(String symbol);

     List<Overview> findByExchange(String exchange);
     List<Overview> findByCountry(String country);
     List<Overview> findByCurrency(String currency);
     List<Overview> findBySector(String sector);
     List<Overview> findByAssetType(String assetType);

     List<Overview> deleteByExchange(String exchange);
     List<Overview> deleteByCurrency(String currency);
     List<Overview> deleteByCountry(String country);
     List<Overview> deleteByAssetType(String assetType);
     List<Overview> deleteBySector(String sector);


}
