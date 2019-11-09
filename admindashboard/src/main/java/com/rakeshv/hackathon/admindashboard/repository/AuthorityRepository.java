package com.rakeshv.hackathon.admindashboard.repository;

import com.rakeshv.hackathon.admindashboard.domain.Authority;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
