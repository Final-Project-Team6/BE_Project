package com.fastcampus.aptner.apartment.repository;

import com.fastcampus.aptner.apartment.domain.Apartment;
import com.fastcampus.aptner.apartment.domain.Home;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HomeRepository extends JpaRepository<Home,Long> {

    Optional<Home> findHomeByApartmentId(Apartment apartmentId);
    Optional<Home> findHomeByHomeId(Long homeId);
    Optional<Home> findHomeByApartmentIdAndHomeId(Apartment apartmentId, Long homeId);
}
