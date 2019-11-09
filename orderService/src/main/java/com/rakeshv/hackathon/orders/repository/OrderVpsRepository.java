package com.rakeshv.hackathon.orders.repository;
import com.rakeshv.hackathon.orders.domain.OrderVps;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrderVps entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderVpsRepository extends JpaRepository<OrderVps, Long> {

}
