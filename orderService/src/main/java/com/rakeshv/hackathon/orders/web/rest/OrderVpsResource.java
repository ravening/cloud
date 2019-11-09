package com.rakeshv.hackathon.orders.web.rest;

import com.rakeshv.hackathon.orders.domain.OrderVps;
import com.rakeshv.hackathon.orders.repository.OrderVpsRepository;
import com.rakeshv.hackathon.orders.service.CreateVpsService;
import com.rakeshv.hackathon.orders.service.VpsService;
import com.rakeshv.hackathon.orders.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * REST controller for managing {@link com.rakeshv.hackathon.orders.domain.OrderVps}.
 */
@RestController
@RequestMapping("/api")
public class OrderVpsResource {

    @Autowired
    VpsService vpsService;
    @Autowired
    CreateVpsService createVpsService;

    private final Logger log = LoggerFactory.getLogger(OrderVpsResource.class);

    private static final String ENTITY_NAME = "orderServiceOrderVps";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final OrderVpsRepository orderVpsRepository;

    public OrderVpsResource(OrderVpsRepository orderVpsRepository) {
        this.orderVpsRepository = orderVpsRepository;
    }

    /**
     * {@code POST  /order-vps} : Create a new orderVps.
     *
     * @param orderVps the orderVps to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new orderVps, or with status {@code 400 (Bad Request)} if the orderVps has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/order-vps")
    public ResponseEntity<OrderVps> createOrderVps(@RequestBody OrderVps orderVps) throws URISyntaxException {
        log.error("recieved post request");
        log.info("REST request to save OrderVps : {}", orderVps);
        if (orderVps.getId() != null) {
            throw new BadRequestAlertException("A new orderVps cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OrderVps result = orderVpsRepository.save(orderVps);
        createVpsService.createVps();
        return ResponseEntity.created(new URI("/api/order-vps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /order-vps} : Updates an existing orderVps.
     *
     * @param orderVps the orderVps to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated orderVps,
     * or with status {@code 400 (Bad Request)} if the orderVps is not valid,
     * or with status {@code 500 (Internal Server Error)} if the orderVps couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/order-vps")
    public ResponseEntity<OrderVps> updateOrderVps(@RequestBody OrderVps orderVps) throws URISyntaxException {
        log.debug("REST request to update OrderVps : {}", orderVps);
        if (orderVps.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        OrderVps result = orderVpsRepository.save(orderVps);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, orderVps.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /order-vps} : get all the orderVps.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of orderVps in body.
     */
    @GetMapping("/order-vps")
    public ResponseEntity<List<OrderVps>> getAllOrderVps(Pageable pageable) {
        log.error("REST request to get a page of OrderVps");
        
        Page<OrderVps> page = orderVpsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(vpsService.getAllVpsPacksList());
    }

    /**
     * 
     */
    @GetMapping("/new-vps")
    public ResponseEntity<String> createNewVps() {
        log.info("Received order to create new VPS");
        // Submit a job to rabbitmq to create vps
        createVpsService.createVps();
        return ResponseEntity.ok("Order submitted");
    }

    /**
     * {@code GET  /order-vps/:id} : get the "id" orderVps.
     *
     * @param id the id of the orderVps to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the orderVps, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/order-vps/{id}")
    public ResponseEntity<OrderVps> getOrderVps(@PathVariable Long id) {
        log.debug("REST request to get OrderVps : {}", id);
        Optional<OrderVps> orderVps = orderVpsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(orderVps);
    }

    /**
     * {@code DELETE  /order-vps/:id} : delete the "id" orderVps.
     *
     * @param id the id of the orderVps to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/order-vps/{id}")
    public ResponseEntity<Void> deleteOrderVps(@PathVariable Long id) {
        log.debug("REST request to delete OrderVps : {}", id);
        orderVpsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
