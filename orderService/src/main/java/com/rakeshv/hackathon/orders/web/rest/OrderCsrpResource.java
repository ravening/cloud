package com.rakeshv.hackathon.orders.web.rest;

import com.rakeshv.hackathon.orders.domain.OrderCsrp;
import com.rakeshv.hackathon.orders.repository.OrderCsrpRepository;
import com.rakeshv.hackathon.orders.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.rakeshv.hackathon.orders.domain.OrderCsrp}.
 */
@RestController
@RequestMapping("/api")
public class OrderCsrpResource {

    private final Logger log = LoggerFactory.getLogger(OrderCsrpResource.class);

    private static final String ENTITY_NAME = "orderServiceOrderCsrp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderCsrpRepository orderCsrpRepository;

    public OrderCsrpResource(OrderCsrpRepository orderCsrpRepository) {
        this.orderCsrpRepository = orderCsrpRepository;
    }

    /**
     * {@code POST  /order-csrps} : Create a new orderCsrp.
     *
     * @param orderCsrp the orderCsrp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderCsrp, or with status {@code 400 (Bad Request)} if the orderCsrp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-csrps")
    public ResponseEntity<OrderCsrp> createOrderCsrp(@RequestBody OrderCsrp orderCsrp) throws URISyntaxException {
        log.debug("REST request to save OrderCsrp : {}", orderCsrp);
        if (orderCsrp.getId() != null) {
            throw new BadRequestAlertException("A new orderCsrp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderCsrp result = orderCsrpRepository.save(orderCsrp);
        return ResponseEntity.created(new URI("/api/order-csrps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-csrps} : Updates an existing orderCsrp.
     *
     * @param orderCsrp the orderCsrp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderCsrp,
     * or with status {@code 400 (Bad Request)} if the orderCsrp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderCsrp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-csrps")
    public ResponseEntity<OrderCsrp> updateOrderCsrp(@RequestBody OrderCsrp orderCsrp) throws URISyntaxException {
        log.debug("REST request to update OrderCsrp : {}", orderCsrp);
        if (orderCsrp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderCsrp result = orderCsrpRepository.save(orderCsrp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderCsrp.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-csrps} : get all the orderCsrps.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderCsrps in body.
     */
    @GetMapping("/order-csrps")
    public ResponseEntity<List<OrderCsrp>> getAllOrderCsrps(Pageable pageable) {
        log.debug("REST request to get a page of OrderCsrps");
        Page<OrderCsrp> page = orderCsrpRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /order-csrps/:id} : get the "id" orderCsrp.
     *
     * @param id the id of the orderCsrp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderCsrp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-csrps/{id}")
    public ResponseEntity<OrderCsrp> getOrderCsrp(@PathVariable Long id) {
        log.debug("REST request to get OrderCsrp : {}", id);
        Optional<OrderCsrp> orderCsrp = orderCsrpRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orderCsrp);
    }

    /**
     * {@code DELETE  /order-csrps/:id} : delete the "id" orderCsrp.
     *
     * @param id the id of the orderCsrp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-csrps/{id}")
    public ResponseEntity<Void> deleteOrderCsrp(@PathVariable Long id) {
        log.debug("REST request to delete OrderCsrp : {}", id);
        orderCsrpRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
