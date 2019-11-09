package com.rakeshv.hackathon.vps.web.rest;

import com.rakeshv.hackathon.vps.domain.VirtualMachine;
import com.rakeshv.hackathon.vps.repository.VirtualMachineRepository;
import com.rakeshv.hackathon.vps.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link com.rakeshv.hackathon.vps.domain.VirtualMachine}.
 */
@RestController
@RequestMapping("/api")
public class VirtualMachineResource {

    private final Logger log = LoggerFactory.getLogger(VirtualMachineResource.class);

    private static final String ENTITY_NAME = "virtualMachineVirtualMachine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VirtualMachineRepository virtualMachineRepository;

    public VirtualMachineResource(VirtualMachineRepository virtualMachineRepository) {
        this.virtualMachineRepository = virtualMachineRepository;
    }

    /**
     * {@code POST  /virtual-machines} : Create a new virtualMachine.
     *
     * @param virtualMachine the virtualMachine to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new virtualMachine, or with status {@code 400 (Bad Request)} if the virtualMachine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/virtual-machines")
    public ResponseEntity<VirtualMachine> createVirtualMachine(@RequestBody VirtualMachine virtualMachine) throws URISyntaxException {
        log.debug("REST request to save VirtualMachine : {}", virtualMachine);
        if (virtualMachine.getId() != null) {
            throw new BadRequestAlertException("A new virtualMachine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        VirtualMachine result = virtualMachineRepository.save(virtualMachine);
        return ResponseEntity.created(new URI("/api/virtual-machines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /virtual-machines} : Updates an existing virtualMachine.
     *
     * @param virtualMachine the virtualMachine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated virtualMachine,
     * or with status {@code 400 (Bad Request)} if the virtualMachine is not valid,
     * or with status {@code 500 (Internal Server Error)} if the virtualMachine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/virtual-machines")
    public ResponseEntity<VirtualMachine> updateVirtualMachine(@RequestBody VirtualMachine virtualMachine) throws URISyntaxException {
        log.debug("REST request to update VirtualMachine : {}", virtualMachine);
        if (virtualMachine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        VirtualMachine result = virtualMachineRepository.save(virtualMachine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, virtualMachine.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /virtual-machines} : get all the virtualMachines.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of virtualMachines in body.
     */
    @GetMapping("/virtual-machines")
    public ResponseEntity<List<VirtualMachine>> getAllVirtualMachines(Pageable pageable) {
        log.debug("REST request to get a page of VirtualMachines");
        Page<VirtualMachine> page = virtualMachineRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /virtual-machines/:id} : get the "id" virtualMachine.
     *
     * @param id the id of the virtualMachine to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the virtualMachine, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/virtual-machines/{id}")
    public ResponseEntity<VirtualMachine> getVirtualMachine(@PathVariable Long id) {
        log.debug("REST request to get VirtualMachine : {}", id);
        Optional<VirtualMachine> virtualMachine = virtualMachineRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(virtualMachine);
    }

    /**
     * {@code DELETE  /virtual-machines/:id} : delete the "id" virtualMachine.
     *
     * @param id the id of the virtualMachine to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/virtual-machines/{id}")
    public ResponseEntity<Void> deleteVirtualMachine(@PathVariable Long id) {
        log.debug("REST request to delete VirtualMachine : {}", id);
        virtualMachineRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
