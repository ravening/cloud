package com.rakeshv.hackathon.admindashboard.web.rest;

import com.rakeshv.hackathon.admindashboard.domain.Vps;
import com.rakeshv.hackathon.admindashboard.repository.VpsRepository;
import com.rakeshv.hackathon.admindashboard.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.rakeshv.hackathon.admindashboard.domain.Vps}.
 */
@RestController
@RequestMapping("/api")
public class VpsResource {

    private final Logger log = LoggerFactory.getLogger(VpsResource.class);

    private static final String ENTITY_NAME = "vps";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final VpsRepository vpsRepository;

    public VpsResource(VpsRepository vpsRepository) {
        this.vpsRepository = vpsRepository;
    }

    /**
     * {@code POST  /vps} : Create a new vps.
     *
     * @param vps the vps to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new vps, or with status {@code 400 (Bad Request)} if the vps has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/vps")
    public ResponseEntity<Vps> createVps(@Valid @RequestBody Vps vps) throws URISyntaxException {
        log.debug("REST request to save Vps : {}", vps);
        if (vps.getId() != null) {
            throw new BadRequestAlertException("A new vps cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Vps result = vpsRepository.save(vps);
        return ResponseEntity.created(new URI("/api/vps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /vps} : Updates an existing vps.
     *
     * @param vps the vps to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated vps,
     * or with status {@code 400 (Bad Request)} if the vps is not valid,
     * or with status {@code 500 (Internal Server Error)} if the vps couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/vps")
    public ResponseEntity<Vps> updateVps(@Valid @RequestBody Vps vps) throws URISyntaxException {
        log.debug("REST request to update Vps : {}", vps);
        if (vps.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Vps result = vpsRepository.save(vps);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, vps.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /vps} : get all the vps.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of vps in body.
     */
    @GetMapping("/vps")
    public List<Vps> getAllVps() {
        log.debug("REST request to get all Vps");
        log.error("=====getting the list of all the vps packs");
        log.error("=====vps packs are {}", vpsRepository.findAll());
        return vpsRepository.findAll();
    }

    /**
     * {@code GET  /vps/:id} : get the "id" vps.
     *
     * @param id the id of the vps to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the vps, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/vps/{id}")
    public ResponseEntity<Vps> getVps(@PathVariable Long id) {
        log.debug("REST request to get Vps : {}", id);
        Optional<Vps> vps = vpsRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(vps);
    }

    /**
     * {@code DELETE  /vps/:id} : delete the "id" vps.
     *
     * @param id the id of the vps to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/vps/{id}")
    public ResponseEntity<Void> deleteVps(@PathVariable Long id) {
        log.debug("REST request to delete Vps : {}", id);
        vpsRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
