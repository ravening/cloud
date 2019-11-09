package com.rakeshv.hackathon.csrp.web.rest;

import com.rakeshv.hackathon.csrp.domain.PrivateCloud;
import com.rakeshv.hackathon.csrp.repository.PrivateCloudRepository;
import com.rakeshv.hackathon.csrp.web.rest.errors.BadRequestAlertException;

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
 * REST controller for managing {@link com.rakeshv.hackathon.csrp.domain.PrivateCloud}.
 */
@RestController
@RequestMapping("/api")
public class PrivateCloudResource {

    private final Logger log = LoggerFactory.getLogger(PrivateCloudResource.class);

    private static final String ENTITY_NAME = "privateCloudPrivateCloud";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PrivateCloudRepository privateCloudRepository;

    public PrivateCloudResource(PrivateCloudRepository privateCloudRepository) {
        this.privateCloudRepository = privateCloudRepository;
    }

    /**
     * {@code POST  /private-clouds} : Create a new privateCloud.
     *
     * @param privateCloud the privateCloud to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new privateCloud, or with status {@code 400 (Bad Request)} if the privateCloud has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/private-clouds")
    public ResponseEntity<PrivateCloud> createPrivateCloud(@RequestBody PrivateCloud privateCloud) throws URISyntaxException {
        log.debug("REST request to save PrivateCloud : {}", privateCloud);
        if (privateCloud.getId() != null) {
            throw new BadRequestAlertException("A new privateCloud cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PrivateCloud result = privateCloudRepository.save(privateCloud);
        return ResponseEntity.created(new URI("/api/private-clouds/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /private-clouds} : Updates an existing privateCloud.
     *
     * @param privateCloud the privateCloud to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated privateCloud,
     * or with status {@code 400 (Bad Request)} if the privateCloud is not valid,
     * or with status {@code 500 (Internal Server Error)} if the privateCloud couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/private-clouds")
    public ResponseEntity<PrivateCloud> updatePrivateCloud(@RequestBody PrivateCloud privateCloud) throws URISyntaxException {
        log.debug("REST request to update PrivateCloud : {}", privateCloud);
        if (privateCloud.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PrivateCloud result = privateCloudRepository.save(privateCloud);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, privateCloud.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /private-clouds} : get all the privateClouds.
     *

     * @param pageable the pagination information.

     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of privateClouds in body.
     */
    @GetMapping("/private-clouds")
    public ResponseEntity<List<PrivateCloud>> getAllPrivateClouds(Pageable pageable) {
        log.debug("REST request to get a page of PrivateClouds");
        Page<PrivateCloud> page = privateCloudRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /private-clouds/:id} : get the "id" privateCloud.
     *
     * @param id the id of the privateCloud to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the privateCloud, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/private-clouds/{id}")
    public ResponseEntity<PrivateCloud> getPrivateCloud(@PathVariable Long id) {
        log.debug("REST request to get PrivateCloud : {}", id);
        Optional<PrivateCloud> privateCloud = privateCloudRepository.findById(id);
        return ResponseUtil.wrapOrNotFound(privateCloud);
    }

    /**
     * {@code DELETE  /private-clouds/:id} : delete the "id" privateCloud.
     *
     * @param id the id of the privateCloud to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/private-clouds/{id}")
    public ResponseEntity<Void> deletePrivateCloud(@PathVariable Long id) {
        log.debug("REST request to delete PrivateCloud : {}", id);
        privateCloudRepository.deleteById(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
