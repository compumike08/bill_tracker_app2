package com.michael.mh.web.rest;

import com.michael.mh.Application;
import com.michael.mh.domain.BillTransactionLedger;
import com.michael.mh.repository.BillTransactionLedgerRepository;
import com.michael.mh.web.rest.dto.BillTransactionLedgerDTO;
import com.michael.mh.web.rest.mapper.BillTransactionLedgerMapper;

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
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.michael.mh.domain.enumeration.TransactionTypes;

/**
 * Test class for the BillTransactionLedgerResource REST controller.
 *
 * @see BillTransactionLedgerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BillTransactionLedgerResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final DateTime DEFAULT_BILL_TRSCTN_DATETIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_BILL_TRSCTN_DATETIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_BILL_TRSCTN_DATETIME_STR = dateTimeFormatter.print(DEFAULT_BILL_TRSCTN_DATETIME);

    private static final BigDecimal DEFAULT_BILL_TRSCTN_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_BILL_TRSCTN_AMOUNT = new BigDecimal(2);

    private static final TransactionTypes DEFAULT_BILL_TRSCTN_TYPE = TransactionTypes.DEBIT;
    private static final TransactionTypes UPDATED_BILL_TRSCTN_TYPE = TransactionTypes.CREDIT;

    private static final Integer DEFAULT_TRSCTN_PAIR_ID = 1;
    private static final Integer UPDATED_TRSCTN_PAIR_ID = 2;

    @Inject
    private BillTransactionLedgerRepository billTransactionLedgerRepository;

    @Inject
    private BillTransactionLedgerMapper billTransactionLedgerMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restBillTransactionLedgerMockMvc;

    private BillTransactionLedger billTransactionLedger;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BillTransactionLedgerResource billTransactionLedgerResource = new BillTransactionLedgerResource();
        ReflectionTestUtils.setField(billTransactionLedgerResource, "billTransactionLedgerRepository", billTransactionLedgerRepository);
        ReflectionTestUtils.setField(billTransactionLedgerResource, "billTransactionLedgerMapper", billTransactionLedgerMapper);
        this.restBillTransactionLedgerMockMvc = MockMvcBuilders.standaloneSetup(billTransactionLedgerResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        billTransactionLedger = new BillTransactionLedger();
        billTransactionLedger.setBill_trsctn_datetime(DEFAULT_BILL_TRSCTN_DATETIME);
        billTransactionLedger.setBill_trsctn_amount(DEFAULT_BILL_TRSCTN_AMOUNT);
        billTransactionLedger.setBill_trsctn_type(DEFAULT_BILL_TRSCTN_TYPE);
        billTransactionLedger.setTrsctn_pair_id(DEFAULT_TRSCTN_PAIR_ID);
    }

    @Test
    @Transactional
    public void createBillTransactionLedger() throws Exception {
        int databaseSizeBeforeCreate = billTransactionLedgerRepository.findAll().size();

        // Create the BillTransactionLedger
        BillTransactionLedgerDTO billTransactionLedgerDTO = billTransactionLedgerMapper.billTransactionLedgerToBillTransactionLedgerDTO(billTransactionLedger);

        restBillTransactionLedgerMockMvc.perform(post("/api/billTransactionLedgers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billTransactionLedgerDTO)))
                .andExpect(status().isCreated());

        // Validate the BillTransactionLedger in the database
        List<BillTransactionLedger> billTransactionLedgers = billTransactionLedgerRepository.findAll();
        assertThat(billTransactionLedgers).hasSize(databaseSizeBeforeCreate + 1);
        BillTransactionLedger testBillTransactionLedger = billTransactionLedgers.get(billTransactionLedgers.size() - 1);
        assertThat(testBillTransactionLedger.getBill_trsctn_datetime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_BILL_TRSCTN_DATETIME);
        assertThat(testBillTransactionLedger.getBill_trsctn_amount()).isEqualTo(DEFAULT_BILL_TRSCTN_AMOUNT);
        assertThat(testBillTransactionLedger.getBill_trsctn_type()).isEqualTo(DEFAULT_BILL_TRSCTN_TYPE);
        assertThat(testBillTransactionLedger.getTrsctn_pair_id()).isEqualTo(DEFAULT_TRSCTN_PAIR_ID);
    }

    @Test
    @Transactional
    public void checkBill_trsctn_datetimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = billTransactionLedgerRepository.findAll().size();
        // set the field null
        billTransactionLedger.setBill_trsctn_datetime(null);

        // Create the BillTransactionLedger, which fails.
        BillTransactionLedgerDTO billTransactionLedgerDTO = billTransactionLedgerMapper.billTransactionLedgerToBillTransactionLedgerDTO(billTransactionLedger);

        restBillTransactionLedgerMockMvc.perform(post("/api/billTransactionLedgers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billTransactionLedgerDTO)))
                .andExpect(status().isBadRequest());

        List<BillTransactionLedger> billTransactionLedgers = billTransactionLedgerRepository.findAll();
        assertThat(billTransactionLedgers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBill_trsctn_amountIsRequired() throws Exception {
        int databaseSizeBeforeTest = billTransactionLedgerRepository.findAll().size();
        // set the field null
        billTransactionLedger.setBill_trsctn_amount(null);

        // Create the BillTransactionLedger, which fails.
        BillTransactionLedgerDTO billTransactionLedgerDTO = billTransactionLedgerMapper.billTransactionLedgerToBillTransactionLedgerDTO(billTransactionLedger);

        restBillTransactionLedgerMockMvc.perform(post("/api/billTransactionLedgers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billTransactionLedgerDTO)))
                .andExpect(status().isBadRequest());

        List<BillTransactionLedger> billTransactionLedgers = billTransactionLedgerRepository.findAll();
        assertThat(billTransactionLedgers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBill_trsctn_typeIsRequired() throws Exception {
        int databaseSizeBeforeTest = billTransactionLedgerRepository.findAll().size();
        // set the field null
        billTransactionLedger.setBill_trsctn_type(null);

        // Create the BillTransactionLedger, which fails.
        BillTransactionLedgerDTO billTransactionLedgerDTO = billTransactionLedgerMapper.billTransactionLedgerToBillTransactionLedgerDTO(billTransactionLedger);

        restBillTransactionLedgerMockMvc.perform(post("/api/billTransactionLedgers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billTransactionLedgerDTO)))
                .andExpect(status().isBadRequest());

        List<BillTransactionLedger> billTransactionLedgers = billTransactionLedgerRepository.findAll();
        assertThat(billTransactionLedgers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrsctn_pair_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = billTransactionLedgerRepository.findAll().size();
        // set the field null
        billTransactionLedger.setTrsctn_pair_id(null);

        // Create the BillTransactionLedger, which fails.
        BillTransactionLedgerDTO billTransactionLedgerDTO = billTransactionLedgerMapper.billTransactionLedgerToBillTransactionLedgerDTO(billTransactionLedger);

        restBillTransactionLedgerMockMvc.perform(post("/api/billTransactionLedgers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billTransactionLedgerDTO)))
                .andExpect(status().isBadRequest());

        List<BillTransactionLedger> billTransactionLedgers = billTransactionLedgerRepository.findAll();
        assertThat(billTransactionLedgers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBillTransactionLedgers() throws Exception {
        // Initialize the database
        billTransactionLedgerRepository.saveAndFlush(billTransactionLedger);

        // Get all the billTransactionLedgers
        restBillTransactionLedgerMockMvc.perform(get("/api/billTransactionLedgers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(billTransactionLedger.getId().intValue())))
                .andExpect(jsonPath("$.[*].bill_trsctn_datetime").value(hasItem(DEFAULT_BILL_TRSCTN_DATETIME_STR)))
                .andExpect(jsonPath("$.[*].bill_trsctn_amount").value(hasItem(DEFAULT_BILL_TRSCTN_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].bill_trsctn_type").value(hasItem(DEFAULT_BILL_TRSCTN_TYPE.toString())))
                .andExpect(jsonPath("$.[*].trsctn_pair_id").value(hasItem(DEFAULT_TRSCTN_PAIR_ID)));
    }

    @Test
    @Transactional
    public void getBillTransactionLedger() throws Exception {
        // Initialize the database
        billTransactionLedgerRepository.saveAndFlush(billTransactionLedger);

        // Get the billTransactionLedger
        restBillTransactionLedgerMockMvc.perform(get("/api/billTransactionLedgers/{id}", billTransactionLedger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(billTransactionLedger.getId().intValue()))
            .andExpect(jsonPath("$.bill_trsctn_datetime").value(DEFAULT_BILL_TRSCTN_DATETIME_STR))
            .andExpect(jsonPath("$.bill_trsctn_amount").value(DEFAULT_BILL_TRSCTN_AMOUNT.intValue()))
            .andExpect(jsonPath("$.bill_trsctn_type").value(DEFAULT_BILL_TRSCTN_TYPE.toString()))
            .andExpect(jsonPath("$.trsctn_pair_id").value(DEFAULT_TRSCTN_PAIR_ID));
    }

    @Test
    @Transactional
    public void getNonExistingBillTransactionLedger() throws Exception {
        // Get the billTransactionLedger
        restBillTransactionLedgerMockMvc.perform(get("/api/billTransactionLedgers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBillTransactionLedger() throws Exception {
        // Initialize the database
        billTransactionLedgerRepository.saveAndFlush(billTransactionLedger);

		int databaseSizeBeforeUpdate = billTransactionLedgerRepository.findAll().size();

        // Update the billTransactionLedger
        billTransactionLedger.setBill_trsctn_datetime(UPDATED_BILL_TRSCTN_DATETIME);
        billTransactionLedger.setBill_trsctn_amount(UPDATED_BILL_TRSCTN_AMOUNT);
        billTransactionLedger.setBill_trsctn_type(UPDATED_BILL_TRSCTN_TYPE);
        billTransactionLedger.setTrsctn_pair_id(UPDATED_TRSCTN_PAIR_ID);
        
        BillTransactionLedgerDTO billTransactionLedgerDTO = billTransactionLedgerMapper.billTransactionLedgerToBillTransactionLedgerDTO(billTransactionLedger);

        restBillTransactionLedgerMockMvc.perform(put("/api/billTransactionLedgers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billTransactionLedgerDTO)))
                .andExpect(status().isOk());

        // Validate the BillTransactionLedger in the database
        List<BillTransactionLedger> billTransactionLedgers = billTransactionLedgerRepository.findAll();
        assertThat(billTransactionLedgers).hasSize(databaseSizeBeforeUpdate);
        BillTransactionLedger testBillTransactionLedger = billTransactionLedgers.get(billTransactionLedgers.size() - 1);
        assertThat(testBillTransactionLedger.getBill_trsctn_datetime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_BILL_TRSCTN_DATETIME);
        assertThat(testBillTransactionLedger.getBill_trsctn_amount()).isEqualTo(UPDATED_BILL_TRSCTN_AMOUNT);
        assertThat(testBillTransactionLedger.getBill_trsctn_type()).isEqualTo(UPDATED_BILL_TRSCTN_TYPE);
        assertThat(testBillTransactionLedger.getTrsctn_pair_id()).isEqualTo(UPDATED_TRSCTN_PAIR_ID);
    }

    @Test
    @Transactional
    public void deleteBillTransactionLedger() throws Exception {
        // Initialize the database
        billTransactionLedgerRepository.saveAndFlush(billTransactionLedger);

		int databaseSizeBeforeDelete = billTransactionLedgerRepository.findAll().size();

        // Get the billTransactionLedger
        restBillTransactionLedgerMockMvc.perform(delete("/api/billTransactionLedgers/{id}", billTransactionLedger.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<BillTransactionLedger> billTransactionLedgers = billTransactionLedgerRepository.findAll();
        assertThat(billTransactionLedgers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
