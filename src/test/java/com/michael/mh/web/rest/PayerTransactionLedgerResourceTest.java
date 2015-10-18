package com.michael.mh.web.rest;

import com.michael.mh.Application;
import com.michael.mh.domain.PayerTransactionLedger;
import com.michael.mh.repository.PayerTransactionLedgerRepository;
import com.michael.mh.web.rest.dto.PayerTransactionLedgerDTO;
import com.michael.mh.web.rest.mapper.PayerTransactionLedgerMapper;

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
 * Test class for the PayerTransactionLedgerResource REST controller.
 *
 * @see PayerTransactionLedgerResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PayerTransactionLedgerResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final DateTime DEFAULT_PAYER_TRSCTN_DATETIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_PAYER_TRSCTN_DATETIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_PAYER_TRSCTN_DATETIME_STR = dateTimeFormatter.print(DEFAULT_PAYER_TRSCTN_DATETIME);

    private static final BigDecimal DEFAULT_PAYER_TRSCTN_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_PAYER_TRSCTN_AMOUNT = new BigDecimal(2);

    private static final TransactionTypes DEFAULT_PAYER_TRSCTN_TYPE = TransactionTypes.DEBIT;
    private static final TransactionTypes UPDATED_PAYER_TRSCTN_TYPE = TransactionTypes.CREDIT;

    private static final Integer DEFAULT_TRSCTN_PAIR_ID = 1;
    private static final Integer UPDATED_TRSCTN_PAIR_ID = 2;

    @Inject
    private PayerTransactionLedgerRepository payerTransactionLedgerRepository;

    @Inject
    private PayerTransactionLedgerMapper payerTransactionLedgerMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restPayerTransactionLedgerMockMvc;

    private PayerTransactionLedger payerTransactionLedger;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PayerTransactionLedgerResource payerTransactionLedgerResource = new PayerTransactionLedgerResource();
        ReflectionTestUtils.setField(payerTransactionLedgerResource, "payerTransactionLedgerRepository", payerTransactionLedgerRepository);
        ReflectionTestUtils.setField(payerTransactionLedgerResource, "payerTransactionLedgerMapper", payerTransactionLedgerMapper);
        this.restPayerTransactionLedgerMockMvc = MockMvcBuilders.standaloneSetup(payerTransactionLedgerResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        payerTransactionLedger = new PayerTransactionLedger();
        payerTransactionLedger.setPayer_trsctn_datetime(DEFAULT_PAYER_TRSCTN_DATETIME);
        payerTransactionLedger.setPayer_trsctn_amount(DEFAULT_PAYER_TRSCTN_AMOUNT);
        payerTransactionLedger.setPayer_trsctn_type(DEFAULT_PAYER_TRSCTN_TYPE);
        payerTransactionLedger.setTrsctn_pair_id(DEFAULT_TRSCTN_PAIR_ID);
    }

    @Test
    @Transactional
    public void createPayerTransactionLedger() throws Exception {
        int databaseSizeBeforeCreate = payerTransactionLedgerRepository.findAll().size();

        // Create the PayerTransactionLedger
        PayerTransactionLedgerDTO payerTransactionLedgerDTO = payerTransactionLedgerMapper.payerTransactionLedgerToPayerTransactionLedgerDTO(payerTransactionLedger);

        restPayerTransactionLedgerMockMvc.perform(post("/api/payerTransactionLedgers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payerTransactionLedgerDTO)))
                .andExpect(status().isCreated());

        // Validate the PayerTransactionLedger in the database
        List<PayerTransactionLedger> payerTransactionLedgers = payerTransactionLedgerRepository.findAll();
        assertThat(payerTransactionLedgers).hasSize(databaseSizeBeforeCreate + 1);
        PayerTransactionLedger testPayerTransactionLedger = payerTransactionLedgers.get(payerTransactionLedgers.size() - 1);
        assertThat(testPayerTransactionLedger.getPayer_trsctn_datetime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_PAYER_TRSCTN_DATETIME);
        assertThat(testPayerTransactionLedger.getPayer_trsctn_amount()).isEqualTo(DEFAULT_PAYER_TRSCTN_AMOUNT);
        assertThat(testPayerTransactionLedger.getPayer_trsctn_type()).isEqualTo(DEFAULT_PAYER_TRSCTN_TYPE);
        assertThat(testPayerTransactionLedger.getTrsctn_pair_id()).isEqualTo(DEFAULT_TRSCTN_PAIR_ID);
    }

    @Test
    @Transactional
    public void checkPayer_trsctn_datetimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = payerTransactionLedgerRepository.findAll().size();
        // set the field null
        payerTransactionLedger.setPayer_trsctn_datetime(null);

        // Create the PayerTransactionLedger, which fails.
        PayerTransactionLedgerDTO payerTransactionLedgerDTO = payerTransactionLedgerMapper.payerTransactionLedgerToPayerTransactionLedgerDTO(payerTransactionLedger);

        restPayerTransactionLedgerMockMvc.perform(post("/api/payerTransactionLedgers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payerTransactionLedgerDTO)))
                .andExpect(status().isBadRequest());

        List<PayerTransactionLedger> payerTransactionLedgers = payerTransactionLedgerRepository.findAll();
        assertThat(payerTransactionLedgers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPayer_trsctn_amountIsRequired() throws Exception {
        int databaseSizeBeforeTest = payerTransactionLedgerRepository.findAll().size();
        // set the field null
        payerTransactionLedger.setPayer_trsctn_amount(null);

        // Create the PayerTransactionLedger, which fails.
        PayerTransactionLedgerDTO payerTransactionLedgerDTO = payerTransactionLedgerMapper.payerTransactionLedgerToPayerTransactionLedgerDTO(payerTransactionLedger);

        restPayerTransactionLedgerMockMvc.perform(post("/api/payerTransactionLedgers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payerTransactionLedgerDTO)))
                .andExpect(status().isBadRequest());

        List<PayerTransactionLedger> payerTransactionLedgers = payerTransactionLedgerRepository.findAll();
        assertThat(payerTransactionLedgers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPayer_trsctn_typeIsRequired() throws Exception {
        int databaseSizeBeforeTest = payerTransactionLedgerRepository.findAll().size();
        // set the field null
        payerTransactionLedger.setPayer_trsctn_type(null);

        // Create the PayerTransactionLedger, which fails.
        PayerTransactionLedgerDTO payerTransactionLedgerDTO = payerTransactionLedgerMapper.payerTransactionLedgerToPayerTransactionLedgerDTO(payerTransactionLedger);

        restPayerTransactionLedgerMockMvc.perform(post("/api/payerTransactionLedgers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payerTransactionLedgerDTO)))
                .andExpect(status().isBadRequest());

        List<PayerTransactionLedger> payerTransactionLedgers = payerTransactionLedgerRepository.findAll();
        assertThat(payerTransactionLedgers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrsctn_pair_idIsRequired() throws Exception {
        int databaseSizeBeforeTest = payerTransactionLedgerRepository.findAll().size();
        // set the field null
        payerTransactionLedger.setTrsctn_pair_id(null);

        // Create the PayerTransactionLedger, which fails.
        PayerTransactionLedgerDTO payerTransactionLedgerDTO = payerTransactionLedgerMapper.payerTransactionLedgerToPayerTransactionLedgerDTO(payerTransactionLedger);

        restPayerTransactionLedgerMockMvc.perform(post("/api/payerTransactionLedgers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payerTransactionLedgerDTO)))
                .andExpect(status().isBadRequest());

        List<PayerTransactionLedger> payerTransactionLedgers = payerTransactionLedgerRepository.findAll();
        assertThat(payerTransactionLedgers).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPayerTransactionLedgers() throws Exception {
        // Initialize the database
        payerTransactionLedgerRepository.saveAndFlush(payerTransactionLedger);

        // Get all the payerTransactionLedgers
        restPayerTransactionLedgerMockMvc.perform(get("/api/payerTransactionLedgers"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(payerTransactionLedger.getId().intValue())))
                .andExpect(jsonPath("$.[*].payer_trsctn_datetime").value(hasItem(DEFAULT_PAYER_TRSCTN_DATETIME_STR)))
                .andExpect(jsonPath("$.[*].payer_trsctn_amount").value(hasItem(DEFAULT_PAYER_TRSCTN_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].payer_trsctn_type").value(hasItem(DEFAULT_PAYER_TRSCTN_TYPE.toString())))
                .andExpect(jsonPath("$.[*].trsctn_pair_id").value(hasItem(DEFAULT_TRSCTN_PAIR_ID)));
    }

    @Test
    @Transactional
    public void getPayerTransactionLedger() throws Exception {
        // Initialize the database
        payerTransactionLedgerRepository.saveAndFlush(payerTransactionLedger);

        // Get the payerTransactionLedger
        restPayerTransactionLedgerMockMvc.perform(get("/api/payerTransactionLedgers/{id}", payerTransactionLedger.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(payerTransactionLedger.getId().intValue()))
            .andExpect(jsonPath("$.payer_trsctn_datetime").value(DEFAULT_PAYER_TRSCTN_DATETIME_STR))
            .andExpect(jsonPath("$.payer_trsctn_amount").value(DEFAULT_PAYER_TRSCTN_AMOUNT.intValue()))
            .andExpect(jsonPath("$.payer_trsctn_type").value(DEFAULT_PAYER_TRSCTN_TYPE.toString()))
            .andExpect(jsonPath("$.trsctn_pair_id").value(DEFAULT_TRSCTN_PAIR_ID));
    }

    @Test
    @Transactional
    public void getNonExistingPayerTransactionLedger() throws Exception {
        // Get the payerTransactionLedger
        restPayerTransactionLedgerMockMvc.perform(get("/api/payerTransactionLedgers/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayerTransactionLedger() throws Exception {
        // Initialize the database
        payerTransactionLedgerRepository.saveAndFlush(payerTransactionLedger);

		int databaseSizeBeforeUpdate = payerTransactionLedgerRepository.findAll().size();

        // Update the payerTransactionLedger
        payerTransactionLedger.setPayer_trsctn_datetime(UPDATED_PAYER_TRSCTN_DATETIME);
        payerTransactionLedger.setPayer_trsctn_amount(UPDATED_PAYER_TRSCTN_AMOUNT);
        payerTransactionLedger.setPayer_trsctn_type(UPDATED_PAYER_TRSCTN_TYPE);
        payerTransactionLedger.setTrsctn_pair_id(UPDATED_TRSCTN_PAIR_ID);
        
        PayerTransactionLedgerDTO payerTransactionLedgerDTO = payerTransactionLedgerMapper.payerTransactionLedgerToPayerTransactionLedgerDTO(payerTransactionLedger);

        restPayerTransactionLedgerMockMvc.perform(put("/api/payerTransactionLedgers")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payerTransactionLedgerDTO)))
                .andExpect(status().isOk());

        // Validate the PayerTransactionLedger in the database
        List<PayerTransactionLedger> payerTransactionLedgers = payerTransactionLedgerRepository.findAll();
        assertThat(payerTransactionLedgers).hasSize(databaseSizeBeforeUpdate);
        PayerTransactionLedger testPayerTransactionLedger = payerTransactionLedgers.get(payerTransactionLedgers.size() - 1);
        assertThat(testPayerTransactionLedger.getPayer_trsctn_datetime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_PAYER_TRSCTN_DATETIME);
        assertThat(testPayerTransactionLedger.getPayer_trsctn_amount()).isEqualTo(UPDATED_PAYER_TRSCTN_AMOUNT);
        assertThat(testPayerTransactionLedger.getPayer_trsctn_type()).isEqualTo(UPDATED_PAYER_TRSCTN_TYPE);
        assertThat(testPayerTransactionLedger.getTrsctn_pair_id()).isEqualTo(UPDATED_TRSCTN_PAIR_ID);
    }

    @Test
    @Transactional
    public void deletePayerTransactionLedger() throws Exception {
        // Initialize the database
        payerTransactionLedgerRepository.saveAndFlush(payerTransactionLedger);

		int databaseSizeBeforeDelete = payerTransactionLedgerRepository.findAll().size();

        // Get the payerTransactionLedger
        restPayerTransactionLedgerMockMvc.perform(delete("/api/payerTransactionLedgers/{id}", payerTransactionLedger.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PayerTransactionLedger> payerTransactionLedgers = payerTransactionLedgerRepository.findAll();
        assertThat(payerTransactionLedgers).hasSize(databaseSizeBeforeDelete - 1);
    }
}
