package com.rakeshv.hackathon.orders.repository;
import com.rakeshv.hackathon.orders.domain.OrderCsrp;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrderCsrp entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderCsrpRepository extends JpaRepository<OrderCsrp, Long> {

}
