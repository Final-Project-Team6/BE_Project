package com.fastcampus.aptner.apartment.repository;

import com.fastcampus.aptner.apartment.domain.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment,Long> {
}
