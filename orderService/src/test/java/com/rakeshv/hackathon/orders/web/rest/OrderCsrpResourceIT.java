package com.rakeshv.hackathon.orders.web.rest;

import com.rakeshv.hackathon.orders.OrderServiceApp;
import com.rakeshv.hackathon.orders.domain.OrderCsrp;
import com.rakeshv.hackathon.orders.repository.OrderCsrpRepository;
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
 * Integration tests for the {@link OrderCsrpResource} REST controller.
 */
@SpringBootTest(classes = OrderServiceApp.class)
public class OrderCsrpResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final BigDecimal DEFAULT_CSRPPACK = new BigDecimal(1);
    private static final BigDecimal UPDATED_CSRPPACK = new BigDecimal(2);

    @Autowired
    private OrderCsrpRepository orderCsrpRepository;

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

    private MockMvc restOrderCsrpMockMvc;

    private OrderCsrp orderCsrp;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final OrderCsrpResource orderCsrpResource = new OrderCsrpResource(orderCsrpRepository);
        this.restOrderCsrpMockMvc = MockMvcBuilders.standaloneSetup(orderCsrpResource)
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
    public static OrderCsrp createEntity(EntityManager em) {
        OrderCsrp orderCsrp = new OrderCsrp()
            .name(DEFAULT_NAME)
            .csrppack(DEFAULT_CSRPPACK);
        return orderCsrp;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static OrderCsrp createUpdatedEntity(EntityManager em) {
        OrderCsrp orderCsrp = new OrderCsrp()
            .name(UPDATED_NAME)
            .csrppack(UPDATED_CSRPPACK);
        return orderCsrp;
    }

    @BeforeEach
    public void initTest() {
        orderCsrp = createEntity(em);
    }

    @Test
    @Transactional
    public void createOrderCsrp() throws Exception {
        int databaseSizeBeforeCreate = orderCsrpRepository.findAll().size();

        // Create the OrderCsrp
        restOrderCsrpMockMvc.perform(post("/api/order-csrps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCsrp)))
            .andExpect(status().isCreated());

        // Validate the OrderCsrp in the database
        List<OrderCsrp> orderCsrpList = orderCsrpRepository.findAll();
        assertThat(orderCsrpList).hasSize(databaseSizeBeforeCreate + 1);
        OrderCsrp testOrderCsrp = orderCsrpList.get(orderCsrpList.size() - 1);
        assertThat(testOrderCsrp.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testOrderCsrp.getCsrppack()).isEqualTo(DEFAULT_CSRPPACK);
    }

    @Test
    @Transactional
    public void createOrderCsrpWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = orderCsrpRepository.findAll().size();

        // Create the OrderCsrp with an existing ID
        orderCsrp.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restOrderCsrpMockMvc.perform(post("/api/order-csrps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCsrp)))
            .andExpect(status().isBadRequest());

        // Validate the OrderCsrp in the database
        List<OrderCsrp> orderCsrpList = orderCsrpRepository.findAll();
        assertThat(orderCsrpList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllOrderCsrps() throws Exception {
        // Initialize the database
        orderCsrpRepository.saveAndFlush(orderCsrp);

        // Get all the orderCsrpList
        restOrderCsrpMockMvc.perform(get("/api/order-csrps?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(orderCsrp.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].csrppack").value(hasItem(DEFAULT_CSRPPACK.intValue())));
    }
    
    @Test
    @Transactional
    public void getOrderCsrp() throws Exception {
        // Initialize the database
        orderCsrpRepository.saveAndFlush(orderCsrp);

        // Get the orderCsrp
        restOrderCsrpMockMvc.perform(get("/api/order-csrps/{id}", orderCsrp.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(orderCsrp.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.csrppack").value(DEFAULT_CSRPPACK.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingOrderCsrp() throws Exception {
        // Get the orderCsrp
        restOrderCsrpMockMvc.perform(get("/api/order-csrps/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateOrderCsrp() throws Exception {
        // Initialize the database
        orderCsrpRepository.saveAndFlush(orderCsrp);

        int databaseSizeBeforeUpdate = orderCsrpRepository.findAll().size();

        // Update the orderCsrp
        OrderCsrp updatedOrderCsrp = orderCsrpRepository.findById(orderCsrp.getId()).get();
        // Disconnect from session so that the updates on updatedOrderCsrp are not directly saved in db
        em.detach(updatedOrderCsrp);
        updatedOrderCsrp
            .name(UPDATED_NAME)
            .csrppack(UPDATED_CSRPPACK);

        restOrderCsrpMockMvc.perform(put("/api/order-csrps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedOrderCsrp)))
            .andExpect(status().isOk());

        // Validate the OrderCsrp in the database
        List<OrderCsrp> orderCsrpList = orderCsrpRepository.findAll();
        assertThat(orderCsrpList).hasSize(databaseSizeBeforeUpdate);
        OrderCsrp testOrderCsrp = orderCsrpList.get(orderCsrpList.size() - 1);
        assertThat(testOrderCsrp.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testOrderCsrp.getCsrppack()).isEqualTo(UPDATED_CSRPPACK);
    }

    @Test
    @Transactional
    public void updateNonExistingOrderCsrp() throws Exception {
        int databaseSizeBeforeUpdate = orderCsrpRepository.findAll().size();

        // Create the OrderCsrp

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restOrderCsrpMockMvc.perform(put("/api/order-csrps")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(orderCsrp)))
            .andExpect(status().isBadRequest());

        // Validate the OrderCsrp in the database
        List<OrderCsrp> orderCsrpList = orderCsrpRepository.findAll();
        assertThat(orderCsrpList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteOrderCsrp() throws Exception {
        // Initialize the database
        orderCsrpRepository.saveAndFlush(orderCsrp);

        int databaseSizeBeforeDelete = orderCsrpRepository.findAll().size();

        // Delete the orderCsrp
        restOrderCsrpMockMvc.perform(delete("/api/order-csrps/{id}", orderCsrp.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<OrderCsrp> orderCsrpList = orderCsrpRepository.findAll();
        assertThat(orderCsrpList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(OrderCsrp.class);
        OrderCsrp orderCsrp1 = new OrderCsrp();
        orderCsrp1.setId(1L);
        OrderCsrp orderCsrp2 = new OrderCsrp();
        orderCsrp2.setId(orderCsrp1.getId());
        assertThat(orderCsrp1).isEqualTo(orderCsrp2);
        orderCsrp2.setId(2L);
        assertThat(orderCsrp1).isNotEqualTo(orderCsrp2);
        orderCsrp1.setId(null);
        assertThat(orderCsrp1).isNotEqualTo(orderCsrp2);
    }
}
