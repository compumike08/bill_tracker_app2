package com.michael.mh.web.rest;

import com.michael.mh.Application;
import com.michael.mh.domain.TransactionAuditLog;
import com.michael.mh.repository.TransactionAuditLogRepository;
import com.michael.mh.web.rest.dto.TransactionAuditLogDTO;
import com.michael.mh.web.rest.mapper.TransactionAuditLogMapper;

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


/**
 * Test class for the TransactionAuditLogResource REST controller.
 *
 * @see TransactionAuditLogResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class TransactionAuditLogResourceTest {

    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");


    private static final BigDecimal DEFAULT_DEBIT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_DEBIT_AMOUNT = new BigDecimal(2);

    private static final BigDecimal DEFAULT_CREDIT_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_CREDIT_AMOUNT = new BigDecimal(2);

    private static final DateTime DEFAULT_TRSCTN_DATETIME = new DateTime(0L, DateTimeZone.UTC);
    private static final DateTime UPDATED_TRSCTN_DATETIME = new DateTime(DateTimeZone.UTC).withMillisOfSecond(0);
    private static final String DEFAULT_TRSCTN_DATETIME_STR = dateTimeFormatter.print(DEFAULT_TRSCTN_DATETIME);

    @Inject
    private TransactionAuditLogRepository transactionAuditLogRepository;

    @Inject
    private TransactionAuditLogMapper transactionAuditLogMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restTransactionAuditLogMockMvc;

    private TransactionAuditLog transactionAuditLog;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TransactionAuditLogResource transactionAuditLogResource = new TransactionAuditLogResource();
        ReflectionTestUtils.setField(transactionAuditLogResource, "transactionAuditLogRepository", transactionAuditLogRepository);
        ReflectionTestUtils.setField(transactionAuditLogResource, "transactionAuditLogMapper", transactionAuditLogMapper);
        this.restTransactionAuditLogMockMvc = MockMvcBuilders.standaloneSetup(transactionAuditLogResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        transactionAuditLog = new TransactionAuditLog();
        transactionAuditLog.setDebit_amount(DEFAULT_DEBIT_AMOUNT);
        transactionAuditLog.setCredit_amount(DEFAULT_CREDIT_AMOUNT);
        transactionAuditLog.setTrsctn_datetime(DEFAULT_TRSCTN_DATETIME);
    }

    @Test
    @Transactional
    public void createTransactionAuditLog() throws Exception {
        int databaseSizeBeforeCreate = transactionAuditLogRepository.findAll().size();

        // Create the TransactionAuditLog
        TransactionAuditLogDTO transactionAuditLogDTO = transactionAuditLogMapper.transactionAuditLogToTransactionAuditLogDTO(transactionAuditLog);

        restTransactionAuditLogMockMvc.perform(post("/api/transactionAuditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transactionAuditLogDTO)))
                .andExpect(status().isCreated());

        // Validate the TransactionAuditLog in the database
        List<TransactionAuditLog> transactionAuditLogs = transactionAuditLogRepository.findAll();
        assertThat(transactionAuditLogs).hasSize(databaseSizeBeforeCreate + 1);
        TransactionAuditLog testTransactionAuditLog = transactionAuditLogs.get(transactionAuditLogs.size() - 1);
        assertThat(testTransactionAuditLog.getDebit_amount()).isEqualTo(DEFAULT_DEBIT_AMOUNT);
        assertThat(testTransactionAuditLog.getCredit_amount()).isEqualTo(DEFAULT_CREDIT_AMOUNT);
        assertThat(testTransactionAuditLog.getTrsctn_datetime().toDateTime(DateTimeZone.UTC)).isEqualTo(DEFAULT_TRSCTN_DATETIME);
    }

    @Test
    @Transactional
    public void checkDebit_amountIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionAuditLogRepository.findAll().size();
        // set the field null
        transactionAuditLog.setDebit_amount(null);

        // Create the TransactionAuditLog, which fails.
        TransactionAuditLogDTO transactionAuditLogDTO = transactionAuditLogMapper.transactionAuditLogToTransactionAuditLogDTO(transactionAuditLog);

        restTransactionAuditLogMockMvc.perform(post("/api/transactionAuditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transactionAuditLogDTO)))
                .andExpect(status().isBadRequest());

        List<TransactionAuditLog> transactionAuditLogs = transactionAuditLogRepository.findAll();
        assertThat(transactionAuditLogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkCredit_amountIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionAuditLogRepository.findAll().size();
        // set the field null
        transactionAuditLog.setCredit_amount(null);

        // Create the TransactionAuditLog, which fails.
        TransactionAuditLogDTO transactionAuditLogDTO = transactionAuditLogMapper.transactionAuditLogToTransactionAuditLogDTO(transactionAuditLog);

        restTransactionAuditLogMockMvc.perform(post("/api/transactionAuditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transactionAuditLogDTO)))
                .andExpect(status().isBadRequest());

        List<TransactionAuditLog> transactionAuditLogs = transactionAuditLogRepository.findAll();
        assertThat(transactionAuditLogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkTrsctn_datetimeIsRequired() throws Exception {
        int databaseSizeBeforeTest = transactionAuditLogRepository.findAll().size();
        // set the field null
        transactionAuditLog.setTrsctn_datetime(null);

        // Create the TransactionAuditLog, which fails.
        TransactionAuditLogDTO transactionAuditLogDTO = transactionAuditLogMapper.transactionAuditLogToTransactionAuditLogDTO(transactionAuditLog);

        restTransactionAuditLogMockMvc.perform(post("/api/transactionAuditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transactionAuditLogDTO)))
                .andExpect(status().isBadRequest());

        List<TransactionAuditLog> transactionAuditLogs = transactionAuditLogRepository.findAll();
        assertThat(transactionAuditLogs).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllTransactionAuditLogs() throws Exception {
        // Initialize the database
        transactionAuditLogRepository.saveAndFlush(transactionAuditLog);

        // Get all the transactionAuditLogs
        restTransactionAuditLogMockMvc.perform(get("/api/transactionAuditLogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(transactionAuditLog.getId().intValue())))
                .andExpect(jsonPath("$.[*].debit_amount").value(hasItem(DEFAULT_DEBIT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].credit_amount").value(hasItem(DEFAULT_CREDIT_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].trsctn_datetime").value(hasItem(DEFAULT_TRSCTN_DATETIME_STR)));
    }

    @Test
    @Transactional
    public void getTransactionAuditLog() throws Exception {
        // Initialize the database
        transactionAuditLogRepository.saveAndFlush(transactionAuditLog);

        // Get the transactionAuditLog
        restTransactionAuditLogMockMvc.perform(get("/api/transactionAuditLogs/{id}", transactionAuditLog.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(transactionAuditLog.getId().intValue()))
            .andExpect(jsonPath("$.debit_amount").value(DEFAULT_DEBIT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.credit_amount").value(DEFAULT_CREDIT_AMOUNT.intValue()))
            .andExpect(jsonPath("$.trsctn_datetime").value(DEFAULT_TRSCTN_DATETIME_STR));
    }

    @Test
    @Transactional
    public void getNonExistingTransactionAuditLog() throws Exception {
        // Get the transactionAuditLog
        restTransactionAuditLogMockMvc.perform(get("/api/transactionAuditLogs/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTransactionAuditLog() throws Exception {
        // Initialize the database
        transactionAuditLogRepository.saveAndFlush(transactionAuditLog);

		int databaseSizeBeforeUpdate = transactionAuditLogRepository.findAll().size();

        // Update the transactionAuditLog
        transactionAuditLog.setDebit_amount(UPDATED_DEBIT_AMOUNT);
        transactionAuditLog.setCredit_amount(UPDATED_CREDIT_AMOUNT);
        transactionAuditLog.setTrsctn_datetime(UPDATED_TRSCTN_DATETIME);
        
        TransactionAuditLogDTO transactionAuditLogDTO = transactionAuditLogMapper.transactionAuditLogToTransactionAuditLogDTO(transactionAuditLog);

        restTransactionAuditLogMockMvc.perform(put("/api/transactionAuditLogs")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(transactionAuditLogDTO)))
                .andExpect(status().isOk());

        // Validate the TransactionAuditLog in the database
        List<TransactionAuditLog> transactionAuditLogs = transactionAuditLogRepository.findAll();
        assertThat(transactionAuditLogs).hasSize(databaseSizeBeforeUpdate);
        TransactionAuditLog testTransactionAuditLog = transactionAuditLogs.get(transactionAuditLogs.size() - 1);
        assertThat(testTransactionAuditLog.getDebit_amount()).isEqualTo(UPDATED_DEBIT_AMOUNT);
        assertThat(testTransactionAuditLog.getCredit_amount()).isEqualTo(UPDATED_CREDIT_AMOUNT);
        assertThat(testTransactionAuditLog.getTrsctn_datetime().toDateTime(DateTimeZone.UTC)).isEqualTo(UPDATED_TRSCTN_DATETIME);
    }

    @Test
    @Transactional
    public void deleteTransactionAuditLog() throws Exception {
        // Initialize the database
        transactionAuditLogRepository.saveAndFlush(transactionAuditLog);

		int databaseSizeBeforeDelete = transactionAuditLogRepository.findAll().size();

        // Get the transactionAuditLog
        restTransactionAuditLogMockMvc.perform(delete("/api/transactionAuditLogs/{id}", transactionAuditLog.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<TransactionAuditLog> transactionAuditLogs = transactionAuditLogRepository.findAll();
        assertThat(transactionAuditLogs).hasSize(databaseSizeBeforeDelete - 1);
    }
}
