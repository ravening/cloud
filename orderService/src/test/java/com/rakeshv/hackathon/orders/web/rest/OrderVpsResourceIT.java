package com.rakeshv.hackathon.orders.web.rest;

import com.rakeshv.hackathon.orders.OrderServiceApp;
import com.rakeshv.hackathon.orders.domain.OrderVps;
import com.rakeshv.hackathon.orders.repository.OrderVpsRepository;
import com.rakeshv.hackathon.orders.web.rest.errors.ExceptionTranslator;

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

import static com.rakeshv.hackathon.orders.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link OrderVpsResource} REST controller.
 */
@SpringBootTest(classes = OrderServiceApp.class)
public class OrderVpsResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_VPSPACK = new BigDecimal(1);
    private static final BigDecimal UPDATED_VPSPACK = new BigDecimal(2);

    @Autowired
    private OrderVpsRepository orderVpsRepository;

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

    private MockMvc restOrderVpsMockMvc;

    private OrderVps orderVps;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderVpsResource orderVpsResource = new OrderVpsResource(orderVpsRepository);
        this.restOrderVpsMockMvc = MockMvcBuilders.standaloneSetup(orderVpsResource)
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
    public static OrderVps createEntity(EntityManager em) {
        OrderVps orderVps = new OrderVps()
            .name(DEFAULT_NAME)
            .vpspack(DEFAULT_VPSPACK);
        return orderVps;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderVps createUpdatedEntity(EntityManager em) {
        OrderVps orderVps = new OrderVps()
            .name(UPDATED_NAME)
            .vpspack(UPDATED_VPSPACK);
        return orderVps;
    }

    @BeforeEach
    public void initTest() {
        orderVps = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderVps() throws Exception {
        int databaseSizeBeforeCreate = orderVpsRepository.findAll().size();

        // Create the OrderVps
        restOrderVpsMockMvc.perform(post("/api/order-vps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderVps)))
            .andExpect(status().isCreated());

        // Validate the OrderVps in the database
        List<OrderVps> orderVpsList = orderVpsRepository.findAll();
        assertThat(orderVpsList).hasSize(databaseSizeBeforeCreate + 1);
        OrderVps testOrderVps = orderVpsList.get(orderVpsList.size() - 1);
        assertThat(testOrderVps.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrderVps.getVpspack()).isEqualTo(DEFAULT_VPSPACK);
    }

    @Test
    @Transactional
    public void createOrderVpsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderVpsRepository.findAll().size();

        // Create the OrderVps with an existing ID
        orderVps.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderVpsMockMvc.perform(post("/api/order-vps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderVps)))
            .andExpect(status().isBadRequest());

        // Validate the OrderVps in the database
        List<OrderVps> orderVpsList = orderVpsRepository.findAll();
        assertThat(orderVpsList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderVps() throws Exception {
        // Initialize the database
        orderVpsRepository.saveAndFlush(orderVps);

        // Get all the orderVpsList
        restOrderVpsMockMvc.perform(get("/api/order-vps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderVps.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].vpspack").value(hasItem(DEFAULT_VPSPACK.intValue())));
    }
    
    @Test
    @Transactional
    public void getOrderVps() throws Exception {
        // Initialize the database
        orderVpsRepository.saveAndFlush(orderVps);

        // Get the orderVps
        restOrderVpsMockMvc.perform(get("/api/order-vps/{id}", orderVps.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderVps.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.vpspack").value(DEFAULT_VPSPACK.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderVps() throws Exception {
        // Get the orderVps
        restOrderVpsMockMvc.perform(get("/api/order-vps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderVps() throws Exception {
        // Initialize the database
        orderVpsRepository.saveAndFlush(orderVps);

        int databaseSizeBeforeUpdate = orderVpsRepository.findAll().size();

        // Update the orderVps
        OrderVps updatedOrderVps = orderVpsRepository.findById(orderVps.getId()).get();
        // Disconnect from session so that the updates on updatedOrderVps are not directly saved in db
        em.detach(updatedOrderVps);
        updatedOrderVps
            .name(UPDATED_NAME)
            .vpspack(UPDATED_VPSPACK);

        restOrderVpsMockMvc.perform(put("/api/order-vps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderVps)))
            .andExpect(status().isOk());

        // Validate the OrderVps in the database
        List<OrderVps> orderVpsList = orderVpsRepository.findAll();
        assertThat(orderVpsList).hasSize(databaseSizeBeforeUpdate);
        OrderVps testOrderVps = orderVpsList.get(orderVpsList.size() - 1);
        assertThat(testOrderVps.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrderVps.getVpspack()).isEqualTo(UPDATED_VPSPACK);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderVps() throws Exception {
        int databaseSizeBeforeUpdate = orderVpsRepository.findAll().size();

        // Create the OrderVps

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderVpsMockMvc.perform(put("/api/order-vps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderVps)))
            .andExpect(status().isBadRequest());

        // Validate the OrderVps in the database
        List<OrderVps> orderVpsList = orderVpsRepository.findAll();
        assertThat(orderVpsList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderVps() throws Exception {
        // Initialize the database
        orderVpsRepository.saveAndFlush(orderVps);

        int databaseSizeBeforeDelete = orderVpsRepository.findAll().size();

        // Delete the orderVps
        restOrderVpsMockMvc.perform(delete("/api/order-vps/{id}", orderVps.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderVps> orderVpsList = orderVpsRepository.findAll();
        assertThat(orderVpsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderVps.class);
        OrderVps orderVps1 = new OrderVps();
        orderVps1.setId(1L);
        OrderVps orderVps2 = new OrderVps();
        orderVps2.setId(orderVps1.getId());
        assertThat(orderVps1).isEqualTo(orderVps2);
        orderVps2.setId(2L);
        assertThat(orderVps1).isNotEqualTo(orderVps2);
        orderVps1.setId(null);
        assertThat(orderVps1).isNotEqualTo(orderVps2);
    }
}
