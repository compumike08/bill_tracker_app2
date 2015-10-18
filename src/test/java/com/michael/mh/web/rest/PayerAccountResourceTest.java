package com.michael.mh.web.rest;

import com.michael.mh.Application;
import com.michael.mh.domain.PayerAccount;
import com.michael.mh.repository.PayerAccountRepository;
import com.michael.mh.web.rest.dto.PayerAccountDTO;
import com.michael.mh.web.rest.mapper.PayerAccountMapper;

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
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the PayerAccountResource REST controller.
 *
 * @see PayerAccountResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class PayerAccountResourceTest {

    private static final String DEFAULT_PAYER_ACCT_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_PAYER_ACCT_NAME = "UPDATED_TEXT";

    @Inject
    private PayerAccountRepository payerAccountRepository;

    @Inject
    private PayerAccountMapper payerAccountMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restPayerAccountMockMvc;

    private PayerAccount payerAccount;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PayerAccountResource payerAccountResource = new PayerAccountResource();
        ReflectionTestUtils.setField(payerAccountResource, "payerAccountRepository", payerAccountRepository);
        ReflectionTestUtils.setField(payerAccountResource, "payerAccountMapper", payerAccountMapper);
        this.restPayerAccountMockMvc = MockMvcBuilders.standaloneSetup(payerAccountResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        payerAccount = new PayerAccount();
        payerAccount.setPayer_acct_name(DEFAULT_PAYER_ACCT_NAME);
    }

    @Test
    @Transactional
    public void createPayerAccount() throws Exception {
        int databaseSizeBeforeCreate = payerAccountRepository.findAll().size();

        // Create the PayerAccount
        PayerAccountDTO payerAccountDTO = payerAccountMapper.payerAccountToPayerAccountDTO(payerAccount);

        restPayerAccountMockMvc.perform(post("/api/payerAccounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payerAccountDTO)))
                .andExpect(status().isCreated());

        // Validate the PayerAccount in the database
        List<PayerAccount> payerAccounts = payerAccountRepository.findAll();
        assertThat(payerAccounts).hasSize(databaseSizeBeforeCreate + 1);
        PayerAccount testPayerAccount = payerAccounts.get(payerAccounts.size() - 1);
        assertThat(testPayerAccount.getPayer_acct_name()).isEqualTo(DEFAULT_PAYER_ACCT_NAME);
    }

    @Test
    @Transactional
    public void getAllPayerAccounts() throws Exception {
        // Initialize the database
        payerAccountRepository.saveAndFlush(payerAccount);

        // Get all the payerAccounts
        restPayerAccountMockMvc.perform(get("/api/payerAccounts"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(payerAccount.getId().intValue())))
                .andExpect(jsonPath("$.[*].payer_acct_name").value(hasItem(DEFAULT_PAYER_ACCT_NAME.toString())));
    }

    @Test
    @Transactional
    public void getPayerAccount() throws Exception {
        // Initialize the database
        payerAccountRepository.saveAndFlush(payerAccount);

        // Get the payerAccount
        restPayerAccountMockMvc.perform(get("/api/payerAccounts/{id}", payerAccount.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(payerAccount.getId().intValue()))
            .andExpect(jsonPath("$.payer_acct_name").value(DEFAULT_PAYER_ACCT_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPayerAccount() throws Exception {
        // Get the payerAccount
        restPayerAccountMockMvc.perform(get("/api/payerAccounts/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePayerAccount() throws Exception {
        // Initialize the database
        payerAccountRepository.saveAndFlush(payerAccount);

		int databaseSizeBeforeUpdate = payerAccountRepository.findAll().size();

        // Update the payerAccount
        payerAccount.setPayer_acct_name(UPDATED_PAYER_ACCT_NAME);
        
        PayerAccountDTO payerAccountDTO = payerAccountMapper.payerAccountToPayerAccountDTO(payerAccount);

        restPayerAccountMockMvc.perform(put("/api/payerAccounts")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(payerAccountDTO)))
                .andExpect(status().isOk());

        // Validate the PayerAccount in the database
        List<PayerAccount> payerAccounts = payerAccountRepository.findAll();
        assertThat(payerAccounts).hasSize(databaseSizeBeforeUpdate);
        PayerAccount testPayerAccount = payerAccounts.get(payerAccounts.size() - 1);
        assertThat(testPayerAccount.getPayer_acct_name()).isEqualTo(UPDATED_PAYER_ACCT_NAME);
    }

    @Test
    @Transactional
    public void deletePayerAccount() throws Exception {
        // Initialize the database
        payerAccountRepository.saveAndFlush(payerAccount);

		int databaseSizeBeforeDelete = payerAccountRepository.findAll().size();

        // Get the payerAccount
        restPayerAccountMockMvc.perform(delete("/api/payerAccounts/{id}", payerAccount.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PayerAccount> payerAccounts = payerAccountRepository.findAll();
        assertThat(payerAccounts).hasSize(databaseSizeBeforeDelete - 1);
    }
}
