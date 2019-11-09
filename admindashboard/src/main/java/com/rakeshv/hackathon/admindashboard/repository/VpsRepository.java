package com.rakeshv.hackathon.admindashboard.repository;
import com.rakeshv.hackathon.admindashboard.domain.Vps;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Vps entity.
 */
@SuppressWarnings("unused")
@Repository
public interface VpsRepository extends JpaRepository<Vps, Long> {

}
