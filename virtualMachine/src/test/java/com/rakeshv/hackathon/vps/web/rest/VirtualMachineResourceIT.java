package com.rakeshv.hackathon.vps.web.rest;

import com.rakeshv.hackathon.vps.VirtualMachineApp;
import com.rakeshv.hackathon.vps.domain.VirtualMachine;
import com.rakeshv.hackathon.vps.repository.VirtualMachineRepository;
import com.rakeshv.hackathon.vps.web.rest.errors.ExceptionTranslator;

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

import static com.rakeshv.hackathon.vps.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link VirtualMachineResource} REST controller.
 */
@SpringBootTest(classes = VirtualMachineApp.class)
public class VirtualMachineResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_ACCOUNTID = new BigDecimal(1);
    private static final BigDecimal UPDATED_ACCOUNTID = new BigDecimal(2);

    private static final String DEFAULT_ACCOUNT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_ACCOUNT_NAME = "BBBBBBBBBB";

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
    private VirtualMachineRepository virtualMachineRepository;

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

    private MockMvc restVirtualMachineMockMvc;

    private VirtualMachine virtualMachine;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final VirtualMachineResource virtualMachineResource = new VirtualMachineResource(virtualMachineRepository);
        this.restVirtualMachineMockMvc = MockMvcBuilders.standaloneSetup(virtualMachineResource)
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
    public static VirtualMachine createEntity(EntityManager em) {
        VirtualMachine virtualMachine = new VirtualMachine()
            .name(DEFAULT_NAME)
            .accountid(DEFAULT_ACCOUNTID)
            .accountName(DEFAULT_ACCOUNT_NAME)
            .cpu(DEFAULT_CPU)
            .ram(DEFAULT_RAM)
            .disk(DEFAULT_DISK)
            .traffic(DEFAULT_TRAFFIC)
            .template(DEFAULT_TEMPLATE);
        return virtualMachine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static VirtualMachine createUpdatedEntity(EntityManager em) {
        VirtualMachine virtualMachine = new VirtualMachine()
            .name(UPDATED_NAME)
            .accountid(UPDATED_ACCOUNTID)
            .accountName(UPDATED_ACCOUNT_NAME)
            .cpu(UPDATED_CPU)
            .ram(UPDATED_RAM)
            .disk(UPDATED_DISK)
            .traffic(UPDATED_TRAFFIC)
            .template(UPDATED_TEMPLATE);
        return virtualMachine;
    }

    @BeforeEach
    public void initTest() {
        virtualMachine = createEntity(em);
    }

    @Test
    @Transactional
    public void createVirtualMachine() throws Exception {
        int databaseSizeBeforeCreate = virtualMachineRepository.findAll().size();

        // Create the VirtualMachine
        restVirtualMachineMockMvc.perform(post("/api/virtual-machines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(virtualMachine)))
            .andExpect(status().isCreated());

        // Validate the VirtualMachine in the database
        List<VirtualMachine> virtualMachineList = virtualMachineRepository.findAll();
        assertThat(virtualMachineList).hasSize(databaseSizeBeforeCreate + 1);
        VirtualMachine testVirtualMachine = virtualMachineList.get(virtualMachineList.size() - 1);
        assertThat(testVirtualMachine.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testVirtualMachine.getAccountid()).isEqualTo(DEFAULT_ACCOUNTID);
        assertThat(testVirtualMachine.getAccountName()).isEqualTo(DEFAULT_ACCOUNT_NAME);
        assertThat(testVirtualMachine.getCpu()).isEqualTo(DEFAULT_CPU);
        assertThat(testVirtualMachine.getRam()).isEqualTo(DEFAULT_RAM);
        assertThat(testVirtualMachine.getDisk()).isEqualTo(DEFAULT_DISK);
        assertThat(testVirtualMachine.getTraffic()).isEqualTo(DEFAULT_TRAFFIC);
        assertThat(testVirtualMachine.getTemplate()).isEqualTo(DEFAULT_TEMPLATE);
    }

    @Test
    @Transactional
    public void createVirtualMachineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = virtualMachineRepository.findAll().size();

        // Create the VirtualMachine with an existing ID
        virtualMachine.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restVirtualMachineMockMvc.perform(post("/api/virtual-machines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(virtualMachine)))
            .andExpect(status().isBadRequest());

        // Validate the VirtualMachine in the database
        List<VirtualMachine> virtualMachineList = virtualMachineRepository.findAll();
        assertThat(virtualMachineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllVirtualMachines() throws Exception {
        // Initialize the database
        virtualMachineRepository.saveAndFlush(virtualMachine);

        // Get all the virtualMachineList
        restVirtualMachineMockMvc.perform(get("/api/virtual-machines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(virtualMachine.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].accountid").value(hasItem(DEFAULT_ACCOUNTID.intValue())))
            .andExpect(jsonPath("$.[*].accountName").value(hasItem(DEFAULT_ACCOUNT_NAME)))
            .andExpect(jsonPath("$.[*].cpu").value(hasItem(DEFAULT_CPU.intValue())))
            .andExpect(jsonPath("$.[*].ram").value(hasItem(DEFAULT_RAM.intValue())))
            .andExpect(jsonPath("$.[*].disk").value(hasItem(DEFAULT_DISK.intValue())))
            .andExpect(jsonPath("$.[*].traffic").value(hasItem(DEFAULT_TRAFFIC.intValue())))
            .andExpect(jsonPath("$.[*].template").value(hasItem(DEFAULT_TEMPLATE)));
    }
    
    @Test
    @Transactional
    public void getVirtualMachine() throws Exception {
        // Initialize the database
        virtualMachineRepository.saveAndFlush(virtualMachine);

        // Get the virtualMachine
        restVirtualMachineMockMvc.perform(get("/api/virtual-machines/{id}", virtualMachine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(virtualMachine.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.accountid").value(DEFAULT_ACCOUNTID.intValue()))
            .andExpect(jsonPath("$.accountName").value(DEFAULT_ACCOUNT_NAME))
            .andExpect(jsonPath("$.cpu").value(DEFAULT_CPU.intValue()))
            .andExpect(jsonPath("$.ram").value(DEFAULT_RAM.intValue()))
            .andExpect(jsonPath("$.disk").value(DEFAULT_DISK.intValue()))
            .andExpect(jsonPath("$.traffic").value(DEFAULT_TRAFFIC.intValue()))
            .andExpect(jsonPath("$.template").value(DEFAULT_TEMPLATE));
    }

    @Test
    @Transactional
    public void getNonExistingVirtualMachine() throws Exception {
        // Get the virtualMachine
        restVirtualMachineMockMvc.perform(get("/api/virtual-machines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVirtualMachine() throws Exception {
        // Initialize the database
        virtualMachineRepository.saveAndFlush(virtualMachine);

        int databaseSizeBeforeUpdate = virtualMachineRepository.findAll().size();

        // Update the virtualMachine
        VirtualMachine updatedVirtualMachine = virtualMachineRepository.findById(virtualMachine.getId()).get();
        // Disconnect from session so that the updates on updatedVirtualMachine are not directly saved in db
        em.detach(updatedVirtualMachine);
        updatedVirtualMachine
            .name(UPDATED_NAME)
            .accountid(UPDATED_ACCOUNTID)
            .accountName(UPDATED_ACCOUNT_NAME)
            .cpu(UPDATED_CPU)
            .ram(UPDATED_RAM)
            .disk(UPDATED_DISK)
            .traffic(UPDATED_TRAFFIC)
            .template(UPDATED_TEMPLATE);

        restVirtualMachineMockMvc.perform(put("/api/virtual-machines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedVirtualMachine)))
            .andExpect(status().isOk());

        // Validate the VirtualMachine in the database
        List<VirtualMachine> virtualMachineList = virtualMachineRepository.findAll();
        assertThat(virtualMachineList).hasSize(databaseSizeBeforeUpdate);
        VirtualMachine testVirtualMachine = virtualMachineList.get(virtualMachineList.size() - 1);
        assertThat(testVirtualMachine.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testVirtualMachine.getAccountid()).isEqualTo(UPDATED_ACCOUNTID);
        assertThat(testVirtualMachine.getAccountName()).isEqualTo(UPDATED_ACCOUNT_NAME);
        assertThat(testVirtualMachine.getCpu()).isEqualTo(UPDATED_CPU);
        assertThat(testVirtualMachine.getRam()).isEqualTo(UPDATED_RAM);
        assertThat(testVirtualMachine.getDisk()).isEqualTo(UPDATED_DISK);
        assertThat(testVirtualMachine.getTraffic()).isEqualTo(UPDATED_TRAFFIC);
        assertThat(testVirtualMachine.getTemplate()).isEqualTo(UPDATED_TEMPLATE);
    }

    @Test
    @Transactional
    public void updateNonExistingVirtualMachine() throws Exception {
        int databaseSizeBeforeUpdate = virtualMachineRepository.findAll().size();

        // Create the VirtualMachine

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restVirtualMachineMockMvc.perform(put("/api/virtual-machines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(virtualMachine)))
            .andExpect(status().isBadRequest());

        // Validate the VirtualMachine in the database
        List<VirtualMachine> virtualMachineList = virtualMachineRepository.findAll();
        assertThat(virtualMachineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteVirtualMachine() throws Exception {
        // Initialize the database
        virtualMachineRepository.saveAndFlush(virtualMachine);

        int databaseSizeBeforeDelete = virtualMachineRepository.findAll().size();

        // Delete the virtualMachine
        restVirtualMachineMockMvc.perform(delete("/api/virtual-machines/{id}", virtualMachine.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<VirtualMachine> virtualMachineList = virtualMachineRepository.findAll();
        assertThat(virtualMachineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(VirtualMachine.class);
        VirtualMachine virtualMachine1 = new VirtualMachine();
        virtualMachine1.setId(1L);
        VirtualMachine virtualMachine2 = new VirtualMachine();
        virtualMachine2.setId(virtualMachine1.getId());
        assertThat(virtualMachine1).isEqualTo(virtualMachine2);
        virtualMachine2.setId(2L);
        assertThat(virtualMachine1).isNotEqualTo(virtualMachine2);
        virtualMachine1.setId(null);
        assertThat(virtualMachine1).isNotEqualTo(virtualMachine2);
    }
}
