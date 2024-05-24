package com.fastcampus.aptner.apartment.repository;

import com.fastcampus.aptner.apartment.domain.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface ApartmentRepository extends JpaRepository<Apartment,Long> {
    Optional<Apartment> findApartmentByName(String name);
    Optional<Apartment> findApartmentByEngName(String name);
    Optional<Apartment> findApartmentByApartmentId(Long apartmentId);

    @Modifying
    @Transactional
    @Query("update Apartment a set a.name = :name where a.apartmentId = :apartmentId")
    int updateApartmentNameByApartmentId(@Param("apartmentId") Long apartmentId, @Param("name") String name);

    List<Apartment> findAllByNameContainsOrEngNameContains(String keyword,String keyword2);


}
