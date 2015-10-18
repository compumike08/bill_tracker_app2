package com.michael.mh.web.rest;

import com.michael.mh.Application;
import com.michael.mh.domain.BillAmountsOwed;
import com.michael.mh.repository.BillAmountsOwedRepository;
import com.michael.mh.web.rest.dto.BillAmountsOwedDTO;
import com.michael.mh.web.rest.mapper.BillAmountsOwedMapper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the BillAmountsOwedResource REST controller.
 *
 * @see BillAmountsOwedResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BillAmountsOwedResourceTest {


    private static final BigDecimal DEFAULT_AMOUNT_OWED = new BigDecimal(1);
    private static final BigDecimal UPDATED_AMOUNT_OWED = new BigDecimal(2);

    @Inject
    private BillAmountsOwedRepository billAmountsOwedRepository;

    @Inject
    private BillAmountsOwedMapper billAmountsOwedMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restBillAmountsOwedMockMvc;

    private BillAmountsOwed billAmountsOwed;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BillAmountsOwedResource billAmountsOwedResource = new BillAmountsOwedResource();
        ReflectionTestUtils.setField(billAmountsOwedResource, "billAmountsOwedRepository", billAmountsOwedRepository);
        ReflectionTestUtils.setField(billAmountsOwedResource, "billAmountsOwedMapper", billAmountsOwedMapper);
        this.restBillAmountsOwedMockMvc = MockMvcBuilders.standaloneSetup(billAmountsOwedResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        billAmountsOwed = new BillAmountsOwed();
        billAmountsOwed.setAmount_owed(DEFAULT_AMOUNT_OWED);
    }

    @Test
    @Transactional
    public void createBillAmountsOwed() throws Exception {
        int databaseSizeBeforeCreate = billAmountsOwedRepository.findAll().size();

        // Create the BillAmountsOwed
        BillAmountsOwedDTO billAmountsOwedDTO = billAmountsOwedMapper.billAmountsOwedToBillAmountsOwedDTO(billAmountsOwed);

        restBillAmountsOwedMockMvc.perform(post("/api/billAmountsOweds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billAmountsOwedDTO)))
                .andExpect(status().isCreated());

        // Validate the BillAmountsOwed in the database
        List<BillAmountsOwed> billAmountsOweds = billAmountsOwedRepository.findAll();
        assertThat(billAmountsOweds).hasSize(databaseSizeBeforeCreate + 1);
        BillAmountsOwed testBillAmountsOwed = billAmountsOweds.get(billAmountsOweds.size() - 1);
        assertThat(testBillAmountsOwed.getAmount_owed()).isEqualTo(DEFAULT_AMOUNT_OWED);
    }

    @Test
    @Transactional
    public void checkAmount_owedIsRequired() throws Exception {
        int databaseSizeBeforeTest = billAmountsOwedRepository.findAll().size();
        // set the field null
        billAmountsOwed.setAmount_owed(null);

        // Create the BillAmountsOwed, which fails.
        BillAmountsOwedDTO billAmountsOwedDTO = billAmountsOwedMapper.billAmountsOwedToBillAmountsOwedDTO(billAmountsOwed);

        restBillAmountsOwedMockMvc.perform(post("/api/billAmountsOweds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billAmountsOwedDTO)))
                .andExpect(status().isBadRequest());

        List<BillAmountsOwed> billAmountsOweds = billAmountsOwedRepository.findAll();
        assertThat(billAmountsOweds).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBillAmountsOweds() throws Exception {
        // Initialize the database
        billAmountsOwedRepository.saveAndFlush(billAmountsOwed);

        // Get all the billAmountsOweds
        restBillAmountsOwedMockMvc.perform(get("/api/billAmountsOweds"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(billAmountsOwed.getId().intValue())))
                .andExpect(jsonPath("$.[*].amount_owed").value(hasItem(DEFAULT_AMOUNT_OWED.intValue())));
    }

    @Test
    @Transactional
    public void getBillAmountsOwed() throws Exception {
        // Initialize the database
        billAmountsOwedRepository.saveAndFlush(billAmountsOwed);

        // Get the billAmountsOwed
        restBillAmountsOwedMockMvc.perform(get("/api/billAmountsOweds/{id}", billAmountsOwed.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(billAmountsOwed.getId().intValue()))
            .andExpect(jsonPath("$.amount_owed").value(DEFAULT_AMOUNT_OWED.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBillAmountsOwed() throws Exception {
        // Get the billAmountsOwed
        restBillAmountsOwedMockMvc.perform(get("/api/billAmountsOweds/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillAmountsOwed() throws Exception {
        // Initialize the database
        billAmountsOwedRepository.saveAndFlush(billAmountsOwed);

		int databaseSizeBeforeUpdate = billAmountsOwedRepository.findAll().size();

        // Update the billAmountsOwed
        billAmountsOwed.setAmount_owed(UPDATED_AMOUNT_OWED);
        
        BillAmountsOwedDTO billAmountsOwedDTO = billAmountsOwedMapper.billAmountsOwedToBillAmountsOwedDTO(billAmountsOwed);

        restBillAmountsOwedMockMvc.perform(put("/api/billAmountsOweds")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billAmountsOwedDTO)))
                .andExpect(status().isOk());

        // Validate the BillAmountsOwed in the database
        List<BillAmountsOwed> billAmountsOweds = billAmountsOwedRepository.findAll();
        assertThat(billAmountsOweds).hasSize(databaseSizeBeforeUpdate);
        BillAmountsOwed testBillAmountsOwed = billAmountsOweds.get(billAmountsOweds.size() - 1);
        assertThat(testBillAmountsOwed.getAmount_owed()).isEqualTo(UPDATED_AMOUNT_OWED);
    }

    @Test
    @Transactional
    public void deleteBillAmountsOwed() throws Exception {
        // Initialize the database
        billAmountsOwedRepository.saveAndFlush(billAmountsOwed);

		int databaseSizeBeforeDelete = billAmountsOwedRepository.findAll().size();

        // Get the billAmountsOwed
        restBillAmountsOwedMockMvc.perform(delete("/api/billAmountsOweds/{id}", billAmountsOwed.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BillAmountsOwed> billAmountsOweds = billAmountsOwedRepository.findAll();
        assertThat(billAmountsOweds).hasSize(databaseSizeBeforeDelete - 1);
    }
}
