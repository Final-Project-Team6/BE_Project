package com.fastcampus.aptner.apartment.repository;

import com.fastcampus.aptner.apartment.domain.Home;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HomeRepository extends JpaRepository<Home,Long> {
}
