package com.rakeshv.hackathon.admindashboard.web.rest;

import com.rakeshv.hackathon.admindashboard.domain.Csrp;
import com.rakeshv.hackathon.admindashboard.repository.CsrpRepository;
import com.rakeshv.hackathon.admindashboard.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.rakeshv.hackathon.admindashboard.domain.Csrp}.
 */
@RestController
@RequestMapping("/api")
public class CsrpResource {

    private final Logger log = LoggerFactory.getLogger(CsrpResource.class);

    private static final String ENTITY_NAME = "csrp";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CsrpRepository csrpRepository;

    public CsrpResource(CsrpRepository csrpRepository) {
        this.csrpRepository = csrpRepository;
    }

    /**
     * {@code POST  /csrps} : Create a new csrp.
     *
     * @param csrp the csrp to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new csrp, or with status {@code 400 (Bad Request)} if the csrp has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/csrps")
    public ResponseEntity<Csrp> createCsrp(@RequestBody Csrp csrp) throws URISyntaxException {
        log.debug("REST request to save Csrp : {}", csrp);
        if (csrp.getId() != null) {
            throw new BadRequestAlertException("A new csrp cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Csrp result = csrpRepository.save(csrp);
        return ResponseEntity.created(new URI("/api/csrps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /csrps} : Updates an existing csrp.
     *
     * @param csrp the csrp to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated csrp,
     * or with status {@code 400 (Bad Request)} if the csrp is not valid,
     * or with status {@code 500 (Internal Server Error)} if the csrp couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/csrps")
    public ResponseEntity<Csrp> updateCsrp(@RequestBody Csrp csrp) throws URISyntaxException {
        log.debug("REST request to update Csrp : {}", csrp);
        if (csrp.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Csrp result = csrpRepository.save(csrp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, csrp.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /csrps} : get all the csrps.
     *

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of csrps in body.
     */
    @GetMapping("/csrps")
    public List<Csrp> getAllCsrps() {
        log.debug("REST request to get all Csrps");
        return csrpRepository.findAll();
    }

    /**
     * {@code GET  /csrps/:id} : get the "id" csrp.
     *
     * @param id the id of the csrp to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the csrp, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/csrps/{id}")
    public ResponseEntity<Csrp> getCsrp(@PathVariable Long id) {
        log.debug("REST request to get Csrp : {}", id);
        Optional<Csrp> csrp = csrpRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(csrp);
    }

    /**
     * {@code DELETE  /csrps/:id} : delete the "id" csrp.
     *
     * @param id the id of the csrp to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/csrps/{id}")
    public ResponseEntity<Void> deleteCsrp(@PathVariable Long id) {
        log.debug("REST request to delete Csrp : {}", id);
        csrpRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
