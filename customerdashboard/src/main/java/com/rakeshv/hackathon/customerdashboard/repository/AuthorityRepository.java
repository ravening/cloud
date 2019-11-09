package com.rakeshv.hackathon.customerdashboard.repository;

import com.rakeshv.hackathon.customerdashboard.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
