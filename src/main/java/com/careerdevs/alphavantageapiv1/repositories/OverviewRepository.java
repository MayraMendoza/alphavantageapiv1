package com.careerdevs.alphavantageapiv1.repositories;

import com.careerdevs.alphavantageapiv1.models.Overview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;


@Transactional
public interface OverviewRepository extends JpaRepository<Overview, Long> {
// List<Laptop> findByName(String name);
    public List<Overview> findBySymbol(String symbol);



     List<Overview> findByExchange(String exchange);
     List<Overview> findById(long id);
     List<Overview> findByCountry(String country);
     List<Overview> findByName(String name);
     List<Overview> findByCurrency(String currency);
     List<Overview> findBySector(String sector);
     List<Overview> findByAssetType(String assetType);
     List<Overview> findByMarketCapGreaterThanEqual(long marketCap);
     List<Overview> findByMarketCapLessThanEqual(long marketCap);
     List<Overview> findByYearHighGreaterThanEqual(float yearHigh);
     List<Overview> findByYearHighLessThanEqual(float yearHigh);
     List<Overview> findByYearLowGreaterThanEqual(float yearLow);
     List<Overview> findByYearLowLessThanEqual(float yearLow);
     List<Overview> findByDividendDateLessThanEqual(String dividendDate);
     List<Overview> findByDividendDateGreaterThanEqual(String dividendDate);

     List<Overview> deleteByExchange(String exchange);
     List<Overview> deleteByCurrency(String currency);
     List<Overview> deleteByCountry(String country);
     List<Overview> deleteByAssetType(String assetType);
     List<Overview> deleteBySector(String sector);
     List<Overview> deleteBySymbol(String symbol);
     List<Overview> deleteByName(String name);
     List<Overview> deleteById(long id);
     List<Overview> deleteByMarketCapGreaterThanEqual(long marketCap);
     List<Overview> deleteByMarketCapLessThanEqual(long marketCap);
     List<Overview> deleteByYearHighGreaterThanEqual(float yearHigh);
     List<Overview> deleteByYearHighLessThanEqual(float yearHigh);
     List<Overview> deleteByYearLowGreaterThanEqual(float yearLow);
     List<Overview> deleteByYearLowLessThanEqual(float yearLow);
     List<Overview> deleteByDividendDateLessThanEqual(String dividendDate);
     List<Overview> deleteByDividendDateGreaterThanEqual(String dividendDate);

     List<Overview> findByMarketCap(long marketCap);






}
