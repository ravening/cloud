package com.rakeshv.hackathon.admindashboard.repository;
import com.rakeshv.hackathon.admindashboard.domain.Csrp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Csrp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CsrpRepository extends JpaRepository<Csrp, Long> {

}
