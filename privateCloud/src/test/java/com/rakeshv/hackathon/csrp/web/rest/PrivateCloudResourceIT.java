package com.rakeshv.hackathon.csrp.web.rest;

import com.rakeshv.hackathon.csrp.PrivateCloudApp;
import com.rakeshv.hackathon.csrp.domain.PrivateCloud;
import com.rakeshv.hackathon.csrp.repository.PrivateCloudRepository;
import com.rakeshv.hackathon.csrp.web.rest.errors.ExceptionTranslator;

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

import static com.rakeshv.hackathon.csrp.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PrivateCloudResource} REST controller.
 */
@SpringBootTest(classes = PrivateCloudApp.class)
public class PrivateCloudResourceIT {

    private static final String DEFAULT_DOMAIN_NAME = "AAAAAAAAAA";
    private static final String UPDATED_DOMAIN_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ACCOUNTID = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACCOUNTID = new BigDecimal(2);

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CPU = new BigDecimal(1);
    private static final BigDecimal UPDATED_CPU = new BigDecimal(2);

    private static final BigDecimal DEFAULT_RAM = new BigDecimal(1);
    private static final BigDecimal UPDATED_RAM = new BigDecimal(2);

    private static final BigDecimal DEFAULT_STORAGE = new BigDecimal(1);
    private static final BigDecimal UPDATED_STORAGE = new BigDecimal(2);

    private static final BigDecimal DEFAULT_TRAFFIC = new BigDecimal(1);
    private static final BigDecimal UPDATED_TRAFFIC = new BigDecimal(2);

    @Autowired
    private PrivateCloudRepository privateCloudRepository;

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

    private MockMvc restPrivateCloudMockMvc;

    private PrivateCloud privateCloud;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PrivateCloudResource privateCloudResource = new PrivateCloudResource(privateCloudRepository);
        this.restPrivateCloudMockMvc = MockMvcBuilders.standaloneSetup(privateCloudResource)
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
    public static PrivateCloud createEntity(EntityManager em) {
        PrivateCloud privateCloud = new PrivateCloud()
            .domainName(DEFAULT_DOMAIN_NAME)
            .accountid(DEFAULT_ACCOUNTID)
            .accountName(DEFAULT_ACCOUNT_NAME)
            .cpu(DEFAULT_CPU)
            .ram(DEFAULT_RAM)
            .storage(DEFAULT_STORAGE)
            .traffic(DEFAULT_TRAFFIC);
        return privateCloud;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PrivateCloud createUpdatedEntity(EntityManager em) {
        PrivateCloud privateCloud = new PrivateCloud()
            .domainName(UPDATED_DOMAIN_NAME)
            .accountid(UPDATED_ACCOUNTID)
            .accountName(UPDATED_ACCOUNT_NAME)
            .cpu(UPDATED_CPU)
            .ram(UPDATED_RAM)
            .storage(UPDATED_STORAGE)
            .traffic(UPDATED_TRAFFIC);
        return privateCloud;
    }

    @BeforeEach
    public void initTest() {
        privateCloud = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrivateCloud() throws Exception {
        int databaseSizeBeforeCreate = privateCloudRepository.findAll().size();

        // Create the PrivateCloud
        restPrivateCloudMockMvc.perform(post("/api/private-clouds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(privateCloud)))
            .andExpect(status().isCreated());

        // Validate the PrivateCloud in the database
        List<PrivateCloud> privateCloudList = privateCloudRepository.findAll();
        assertThat(privateCloudList).hasSize(databaseSizeBeforeCreate + 1);
        PrivateCloud testPrivateCloud = privateCloudList.get(privateCloudList.size() - 1);
        assertThat(testPrivateCloud.getDomainName()).isEqualTo(DEFAULT_DOMAIN_NAME);
        assertThat(testPrivateCloud.getAccountid()).isEqualTo(DEFAULT_ACCOUNTID);
        assertThat(testPrivateCloud.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testPrivateCloud.getCpu()).isEqualTo(DEFAULT_CPU);
        assertThat(testPrivateCloud.getRam()).isEqualTo(DEFAULT_RAM);
        assertThat(testPrivateCloud.getStorage()).isEqualTo(DEFAULT_STORAGE);
        assertThat(testPrivateCloud.getTraffic()).isEqualTo(DEFAULT_TRAFFIC);
    }

    @Test
    @Transactional
    public void createPrivateCloudWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = privateCloudRepository.findAll().size();

        // Create the PrivateCloud with an existing ID
        privateCloud.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrivateCloudMockMvc.perform(post("/api/private-clouds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(privateCloud)))
            .andExpect(status().isBadRequest());

        // Validate the PrivateCloud in the database
        List<PrivateCloud> privateCloudList = privateCloudRepository.findAll();
        assertThat(privateCloudList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPrivateClouds() throws Exception {
        // Initialize the database
        privateCloudRepository.saveAndFlush(privateCloud);

        // Get all the privateCloudList
        restPrivateCloudMockMvc.perform(get("/api/private-clouds?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(privateCloud.getId().intValue())))
            .andExpect(jsonPath("$.[*].domainName").value(hasItem(DEFAULT_DOMAIN_NAME)))
            .andExpect(jsonPath("$.[*].accountid").value(hasItem(DEFAULT_ACCOUNTID.intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].cpu").value(hasItem(DEFAULT_CPU.intValue())))
            .andExpect(jsonPath("$.[*].ram").value(hasItem(DEFAULT_RAM.intValue())))
            .andExpect(jsonPath("$.[*].storage").value(hasItem(DEFAULT_STORAGE.intValue())))
            .andExpect(jsonPath("$.[*].traffic").value(hasItem(DEFAULT_TRAFFIC.intValue())));
    }
    
    @Test
    @Transactional
    public void getPrivateCloud() throws Exception {
        // Initialize the database
        privateCloudRepository.saveAndFlush(privateCloud);

        // Get the privateCloud
        restPrivateCloudMockMvc.perform(get("/api/private-clouds/{id}", privateCloud.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(privateCloud.getId().intValue()))
            .andExpect(jsonPath("$.domainName").value(DEFAULT_DOMAIN_NAME))
            .andExpect(jsonPath("$.accountid").value(DEFAULT_ACCOUNTID.intValue()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME))
            .andExpect(jsonPath("$.cpu").value(DEFAULT_CPU.intValue()))
            .andExpect(jsonPath("$.ram").value(DEFAULT_RAM.intValue()))
            .andExpect(jsonPath("$.storage").value(DEFAULT_STORAGE.intValue()))
            .andExpect(jsonPath("$.traffic").value(DEFAULT_TRAFFIC.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingPrivateCloud() throws Exception {
        // Get the privateCloud
        restPrivateCloudMockMvc.perform(get("/api/private-clouds/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrivateCloud() throws Exception {
        // Initialize the database
        privateCloudRepository.saveAndFlush(privateCloud);

        int databaseSizeBeforeUpdate = privateCloudRepository.findAll().size();

        // Update the privateCloud
        PrivateCloud updatedPrivateCloud = privateCloudRepository.findById(privateCloud.getId()).get();
        // Disconnect from session so that the updates on updatedPrivateCloud are not directly saved in db
        em.detach(updatedPrivateCloud);
        updatedPrivateCloud
            .domainName(UPDATED_DOMAIN_NAME)
            .accountid(UPDATED_ACCOUNTID)
            .accountName(UPDATED_ACCOUNT_NAME)
            .cpu(UPDATED_CPU)
            .ram(UPDATED_RAM)
            .storage(UPDATED_STORAGE)
            .traffic(UPDATED_TRAFFIC);

        restPrivateCloudMockMvc.perform(put("/api/private-clouds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrivateCloud)))
            .andExpect(status().isOk());

        // Validate the PrivateCloud in the database
        List<PrivateCloud> privateCloudList = privateCloudRepository.findAll();
        assertThat(privateCloudList).hasSize(databaseSizeBeforeUpdate);
        PrivateCloud testPrivateCloud = privateCloudList.get(privateCloudList.size() - 1);
        assertThat(testPrivateCloud.getDomainName()).isEqualTo(UPDATED_DOMAIN_NAME);
        assertThat(testPrivateCloud.getAccountid()).isEqualTo(UPDATED_ACCOUNTID);
        assertThat(testPrivateCloud.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testPrivateCloud.getCpu()).isEqualTo(UPDATED_CPU);
        assertThat(testPrivateCloud.getRam()).isEqualTo(UPDATED_RAM);
        assertThat(testPrivateCloud.getStorage()).isEqualTo(UPDATED_STORAGE);
        assertThat(testPrivateCloud.getTraffic()).isEqualTo(UPDATED_TRAFFIC);
    }

    @Test
    @Transactional
    public void updateNonExistingPrivateCloud() throws Exception {
        int databaseSizeBeforeUpdate = privateCloudRepository.findAll().size();

        // Create the PrivateCloud

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrivateCloudMockMvc.perform(put("/api/private-clouds")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(privateCloud)))
            .andExpect(status().isBadRequest());

        // Validate the PrivateCloud in the database
        List<PrivateCloud> privateCloudList = privateCloudRepository.findAll();
        assertThat(privateCloudList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrivateCloud() throws Exception {
        // Initialize the database
        privateCloudRepository.saveAndFlush(privateCloud);

        int databaseSizeBeforeDelete = privateCloudRepository.findAll().size();

        // Delete the privateCloud
        restPrivateCloudMockMvc.perform(delete("/api/private-clouds/{id}", privateCloud.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PrivateCloud> privateCloudList = privateCloudRepository.findAll();
        assertThat(privateCloudList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PrivateCloud.class);
        PrivateCloud privateCloud1 = new PrivateCloud();
        privateCloud1.setId(1L);
        PrivateCloud privateCloud2 = new PrivateCloud();
        privateCloud2.setId(privateCloud1.getId());
        assertThat(privateCloud1).isEqualTo(privateCloud2);
        privateCloud2.setId(2L);
        assertThat(privateCloud1).isNotEqualTo(privateCloud2);
        privateCloud1.setId(null);
        assertThat(privateCloud1).isNotEqualTo(privateCloud2);
    }
}
