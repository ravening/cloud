package com.rakeshv.hackathon.admindashboard.web.rest;

import com.rakeshv.hackathon.admindashboard.AdmindashboardApp;
import com.rakeshv.hackathon.admindashboard.domain.Csrp;
import com.rakeshv.hackathon.admindashboard.repository.CsrpRepository;
import com.rakeshv.hackathon.admindashboard.web.rest.errors.ExceptionTranslator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;

import static com.rakeshv.hackathon.admindashboard.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link CsrpResource} REST controller.
 */
@SpringBootTest(classes = AdmindashboardApp.class)
public class CsrpResourceIT {

    private static final String DEFAULT_DOMAIN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CPU = new BigDecimal(1);
    private static final BigDecimal UPDATED_CPU = new BigDecimal(2);

    private static final BigDecimal DEFAULT_RAM = new BigDecimal(1);
    private static final BigDecimal UPDATED_RAM = new BigDecimal(2);

    private static final BigDecimal DEFAULT_STORAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_STORAGE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TRAFFIC = new BigDecimal(1);
    private static final BigDecimal UPDATED_TRAFFIC = new BigDecimal(2);

    @Autowired
    private CsrpRepository csrpRepository;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restCsrpMockMvc;

    private Csrp csrp;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final CsrpResource csrpResource = new CsrpResource(csrpRepository);
        this.restCsrpMockMvc = MockMvcBuilders.standaloneSetup(csrpResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Csrp createEntity(EntityManager em) {
        Csrp csrp = new Csrp()
            .domainName(DEFAULT_DOMAIN_NAME)
            .cpu(DEFAULT_CPU)
            .ram(DEFAULT_RAM)
            .storage(DEFAULT_STORAGE)
            .traffic(DEFAULT_TRAFFIC);
        return csrp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Csrp createUpdatedEntity(EntityManager em) {
        Csrp csrp = new Csrp()
            .domainName(UPDATED_DOMAIN_NAME)
            .cpu(UPDATED_CPU)
            .ram(UPDATED_RAM)
            .storage(UPDATED_STORAGE)
            .traffic(UPDATED_TRAFFIC);
        return csrp;
    }

    @BeforeEach
    public void initTest() {
        csrp = createEntity(em);
    }

    @Test
    @Transactional
    public void createCsrp() throws Exception {
        int databaseSizeBeforeCreate = csrpRepository.findAll().size();

        // Create the Csrp
        restCsrpMockMvc.perform(post("/api/csrps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(csrp)))
            .andExpect(status().isCreated());

        // Validate the Csrp in the database
        List<Csrp> csrpList = csrpRepository.findAll();
        assertThat(csrpList).hasSize(databaseSizeBeforeCreate + 1);
        Csrp testCsrp = csrpList.get(csrpList.size() - 1);
        assertThat(testCsrp.getDomainName()).isEqualTo(DEFAULT_DOMAIN_NAME);
        assertThat(testCsrp.getCpu()).isEqualTo(DEFAULT_CPU);
        assertThat(testCsrp.getRam()).isEqualTo(DEFAULT_RAM);
        assertThat(testCsrp.getStorage()).isEqualTo(DEFAULT_STORAGE);
        assertThat(testCsrp.getTraffic()).isEqualTo(DEFAULT_TRAFFIC);
    }

    @Test
    @Transactional
    public void createCsrpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = csrpRepository.findAll().size();

        // Create the Csrp with an existing ID
        csrp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCsrpMockMvc.perform(post("/api/csrps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(csrp)))
            .andExpect(status().isBadRequest());

        // Validate the Csrp in the database
        List<Csrp> csrpList = csrpRepository.findAll();
        assertThat(csrpList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllCsrps() throws Exception {
        // Initialize the database
        csrpRepository.saveAndFlush(csrp);

        // Get all the csrpList
        restCsrpMockMvc.perform(get("/api/csrps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(csrp.getId().intValue())))
            .andExpect(jsonPath("$.[*].domainName").value(hasItem(DEFAULT_DOMAIN_NAME)))
            .andExpect(jsonPath("$.[*].cpu").value(hasItem(DEFAULT_CPU.intValue())))
            .andExpect(jsonPath("$.[*].ram").value(hasItem(DEFAULT_RAM.intValue())))
            .andExpect(jsonPath("$.[*].storage").value(hasItem(DEFAULT_STORAGE.intValue())))
            .andExpect(jsonPath("$.[*].traffic").value(hasItem(DEFAULT_TRAFFIC.intValue())));
    }
    
    @Test
    @Transactional
    public void getCsrp() throws Exception {
        // Initialize the database
        csrpRepository.saveAndFlush(csrp);

        // Get the csrp
        restCsrpMockMvc.perform(get("/api/csrps/{id}", csrp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(csrp.getId().intValue()))
            .andExpect(jsonPath("$.domainName").value(DEFAULT_DOMAIN_NAME))
            .andExpect(jsonPath("$.cpu").value(DEFAULT_CPU.intValue()))
            .andExpect(jsonPath("$.ram").value(DEFAULT_RAM.intValue()))
            .andExpect(jsonPath("$.storage").value(DEFAULT_STORAGE.intValue()))
            .andExpect(jsonPath("$.traffic").value(DEFAULT_TRAFFIC.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingCsrp() throws Exception {
        // Get the csrp
        restCsrpMockMvc.perform(get("/api/csrps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCsrp() throws Exception {
        // Initialize the database
        csrpRepository.saveAndFlush(csrp);

        int databaseSizeBeforeUpdate = csrpRepository.findAll().size();

        // Update the csrp
        Csrp updatedCsrp = csrpRepository.findById(csrp.getId()).get();
        // Disconnect from session so that the updates on updatedCsrp are not directly saved in db
        em.detach(updatedCsrp);
        updatedCsrp
            .domainName(UPDATED_DOMAIN_NAME)
            .cpu(UPDATED_CPU)
            .ram(UPDATED_RAM)
            .storage(UPDATED_STORAGE)
            .traffic(UPDATED_TRAFFIC);

        restCsrpMockMvc.perform(put("/api/csrps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedCsrp)))
            .andExpect(status().isOk());

        // Validate the Csrp in the database
        List<Csrp> csrpList = csrpRepository.findAll();
        assertThat(csrpList).hasSize(databaseSizeBeforeUpdate);
        Csrp testCsrp = csrpList.get(csrpList.size() - 1);
        assertThat(testCsrp.getDomainName()).isEqualTo(UPDATED_DOMAIN_NAME);
        assertThat(testCsrp.getCpu()).isEqualTo(UPDATED_CPU);
        assertThat(testCsrp.getRam()).isEqualTo(UPDATED_RAM);
        assertThat(testCsrp.getStorage()).isEqualTo(UPDATED_STORAGE);
        assertThat(testCsrp.getTraffic()).isEqualTo(UPDATED_TRAFFIC);
    }

    @Test
    @Transactional
    public void updateNonExistingCsrp() throws Exception {
        int databaseSizeBeforeUpdate = csrpRepository.findAll().size();

        // Create the Csrp

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restCsrpMockMvc.perform(put("/api/csrps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(csrp)))
            .andExpect(status().isBadRequest());

        // Validate the Csrp in the database
        List<Csrp> csrpList = csrpRepository.findAll();
        assertThat(csrpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteCsrp() throws Exception {
        // Initialize the database
        csrpRepository.saveAndFlush(csrp);

        int databaseSizeBeforeDelete = csrpRepository.findAll().size();

        // Delete the csrp
        restCsrpMockMvc.perform(delete("/api/csrps/{id}", csrp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Csrp> csrpList = csrpRepository.findAll();
        assertThat(csrpList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Csrp.class);
        Csrp csrp1 = new Csrp();
        csrp1.setId(1L);
        Csrp csrp2 = new Csrp();
        csrp2.setId(csrp1.getId());
        assertThat(csrp1).isEqualTo(csrp2);
        csrp2.setId(2L);
        assertThat(csrp1).isNotEqualTo(csrp2);
        csrp1.setId(null);
        assertThat(csrp1).isNotEqualTo(csrp2);
    }
}
