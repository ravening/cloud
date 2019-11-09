package com.rakeshv.hackathon.csrp.repository;
import com.rakeshv.hackathon.csrp.domain.PrivateCloud;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the PrivateCloud entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PrivateCloudRepository extends JpaRepository<PrivateCloud, Long> {

}
