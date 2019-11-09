package com.rakeshv.hackathon.admindashboard.web.rest;

import com.rakeshv.hackathon.admindashboard.AdmindashboardApp;
import com.rakeshv.hackathon.admindashboard.domain.Vps;
import com.rakeshv.hackathon.admindashboard.repository.VpsRepository;
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
 * Integration tests for the {@link VpsResource} REST controller.
 */
@SpringBootTest(classes = AdmindashboardApp.class)
public class VpsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CPU = new BigDecimal(1);
    private static final BigDecimal UPDATED_CPU = new BigDecimal(2);

    private static final BigDecimal DEFAULT_RAM = new BigDecimal(1);
    private static final BigDecimal UPDATED_RAM = new BigDecimal(2);

    private static final BigDecimal DEFAULT_DISK = new BigDecimal(1);
    private static final BigDecimal UPDATED_DISK = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TRAFFIC = new BigDecimal(1);
    private static final BigDecimal UPDATED_TRAFFIC = new BigDecimal(2);

    private static final String DEFAULT_TEMPLATE = "AAAAAAAAAA";
    private static final String UPDATED_TEMPLATE = "BBBBBBBBBB";

    @Autowired
    private VpsRepository vpsRepository;

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

    private MockMvc restVpsMockMvc;

    private Vps vps;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VpsResource vpsResource = new VpsResource(vpsRepository);
        this.restVpsMockMvc = MockMvcBuilders.standaloneSetup(vpsResource)
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
    public static Vps createEntity(EntityManager em) {
        Vps vps = new Vps()
            .name(DEFAULT_NAME)
            .cpu(DEFAULT_CPU)
            .ram(DEFAULT_RAM)
            .disk(DEFAULT_DISK)
            .traffic(DEFAULT_TRAFFIC)
            .template(DEFAULT_TEMPLATE);
        return vps;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Vps createUpdatedEntity(EntityManager em) {
        Vps vps = new Vps()
            .name(UPDATED_NAME)
            .cpu(UPDATED_CPU)
            .ram(UPDATED_RAM)
            .disk(UPDATED_DISK)
            .traffic(UPDATED_TRAFFIC)
            .template(UPDATED_TEMPLATE);
        return vps;
    }

    @BeforeEach
    public void initTest() {
        vps = createEntity(em);
    }

    @Test
    @Transactional
    public void createVps() throws Exception {
        int databaseSizeBeforeCreate = vpsRepository.findAll().size();

        // Create the Vps
        restVpsMockMvc.perform(post("/api/vps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vps)))
            .andExpect(status().isCreated());

        // Validate the Vps in the database
        List<Vps> vpsList = vpsRepository.findAll();
        assertThat(vpsList).hasSize(databaseSizeBeforeCreate + 1);
        Vps testVps = vpsList.get(vpsList.size() - 1);
        assertThat(testVps.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVps.getCpu()).isEqualTo(DEFAULT_CPU);
        assertThat(testVps.getRam()).isEqualTo(DEFAULT_RAM);
        assertThat(testVps.getDisk()).isEqualTo(DEFAULT_DISK);
        assertThat(testVps.getTraffic()).isEqualTo(DEFAULT_TRAFFIC);
        assertThat(testVps.getTemplate()).isEqualTo(DEFAULT_TEMPLATE);
    }

    @Test
    @Transactional
    public void createVpsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = vpsRepository.findAll().size();

        // Create the Vps with an existing ID
        vps.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVpsMockMvc.perform(post("/api/vps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vps)))
            .andExpect(status().isBadRequest());

        // Validate the Vps in the database
        List<Vps> vpsList = vpsRepository.findAll();
        assertThat(vpsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = vpsRepository.findAll().size();
        // set the field null
        vps.setName(null);

        // Create the Vps, which fails.

        restVpsMockMvc.perform(post("/api/vps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vps)))
            .andExpect(status().isBadRequest());

        List<Vps> vpsList = vpsRepository.findAll();
        assertThat(vpsList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllVps() throws Exception {
        // Initialize the database
        vpsRepository.saveAndFlush(vps);

        // Get all the vpsList
        restVpsMockMvc.perform(get("/api/vps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(vps.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].cpu").value(hasItem(DEFAULT_CPU.intValue())))
            .andExpect(jsonPath("$.[*].ram").value(hasItem(DEFAULT_RAM.intValue())))
            .andExpect(jsonPath("$.[*].disk").value(hasItem(DEFAULT_DISK.intValue())))
            .andExpect(jsonPath("$.[*].traffic").value(hasItem(DEFAULT_TRAFFIC.intValue())))
            .andExpect(jsonPath("$.[*].template").value(hasItem(DEFAULT_TEMPLATE)));
    }
    
    @Test
    @Transactional
    public void getVps() throws Exception {
        // Initialize the database
        vpsRepository.saveAndFlush(vps);

        // Get the vps
        restVpsMockMvc.perform(get("/api/vps/{id}", vps.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(vps.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.cpu").value(DEFAULT_CPU.intValue()))
            .andExpect(jsonPath("$.ram").value(DEFAULT_RAM.intValue()))
            .andExpect(jsonPath("$.disk").value(DEFAULT_DISK.intValue()))
            .andExpect(jsonPath("$.traffic").value(DEFAULT_TRAFFIC.intValue()))
            .andExpect(jsonPath("$.template").value(DEFAULT_TEMPLATE));
    }

    @Test
    @Transactional
    public void getNonExistingVps() throws Exception {
        // Get the vps
        restVpsMockMvc.perform(get("/api/vps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVps() throws Exception {
        // Initialize the database
        vpsRepository.saveAndFlush(vps);

        int databaseSizeBeforeUpdate = vpsRepository.findAll().size();

        // Update the vps
        Vps updatedVps = vpsRepository.findById(vps.getId()).get();
        // Disconnect from session so that the updates on updatedVps are not directly saved in db
        em.detach(updatedVps);
        updatedVps
            .name(UPDATED_NAME)
            .cpu(UPDATED_CPU)
            .ram(UPDATED_RAM)
            .disk(UPDATED_DISK)
            .traffic(UPDATED_TRAFFIC)
            .template(UPDATED_TEMPLATE);

        restVpsMockMvc.perform(put("/api/vps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVps)))
            .andExpect(status().isOk());

        // Validate the Vps in the database
        List<Vps> vpsList = vpsRepository.findAll();
        assertThat(vpsList).hasSize(databaseSizeBeforeUpdate);
        Vps testVps = vpsList.get(vpsList.size() - 1);
        assertThat(testVps.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVps.getCpu()).isEqualTo(UPDATED_CPU);
        assertThat(testVps.getRam()).isEqualTo(UPDATED_RAM);
        assertThat(testVps.getDisk()).isEqualTo(UPDATED_DISK);
        assertThat(testVps.getTraffic()).isEqualTo(UPDATED_TRAFFIC);
        assertThat(testVps.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void updateNonExistingVps() throws Exception {
        int databaseSizeBeforeUpdate = vpsRepository.findAll().size();

        // Create the Vps

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVpsMockMvc.perform(put("/api/vps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(vps)))
            .andExpect(status().isBadRequest());

        // Validate the Vps in the database
        List<Vps> vpsList = vpsRepository.findAll();
        assertThat(vpsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVps() throws Exception {
        // Initialize the database
        vpsRepository.saveAndFlush(vps);

        int databaseSizeBeforeDelete = vpsRepository.findAll().size();

        // Delete the vps
        restVpsMockMvc.perform(delete("/api/vps/{id}", vps.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Vps> vpsList = vpsRepository.findAll();
        assertThat(vpsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Vps.class);
        Vps vps1 = new Vps();
        vps1.setId(1L);
        Vps vps2 = new Vps();
        vps2.setId(vps1.getId());
        assertThat(vps1).isEqualTo(vps2);
        vps2.setId(2L);
        assertThat(vps1).isNotEqualTo(vps2);
        vps1.setId(null);
        assertThat(vps1).isNotEqualTo(vps2);
    }
}
