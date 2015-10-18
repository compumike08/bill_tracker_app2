package com.michael.mh.web.rest;

import com.michael.mh.Application;
import com.michael.mh.domain.Bill;
import com.michael.mh.repository.BillRepository;
import com.michael.mh.web.rest.dto.BillDTO;
import com.michael.mh.web.rest.mapper.BillMapper;

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
import org.joda.time.LocalDate;
import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the BillResource REST controller.
 *
 * @see BillResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest
public class BillResourceTest {

    private static final String DEFAULT_BILL_NAME = "SAMPLE_TEXT";
    private static final String UPDATED_BILL_NAME = "UPDATED_TEXT";

    private static final BigDecimal DEFAULT_BILL_AMOUNT = new BigDecimal(1);
    private static final BigDecimal UPDATED_BILL_AMOUNT = new BigDecimal(2);

    private static final LocalDate DEFAULT_BILL_STATEMENT_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_BILL_STATEMENT_DATE = new LocalDate();

    private static final LocalDate DEFAULT_BILL_DUE_DATE = new LocalDate(0L);
    private static final LocalDate UPDATED_BILL_DUE_DATE = new LocalDate();

    private static final Boolean DEFAULT_IS_BILL_PAID = false;
    private static final Boolean UPDATED_IS_BILL_PAID = true;

    private static final Boolean DEFAULT_IS_BILL_REIMBURSED = false;
    private static final Boolean UPDATED_IS_BILL_REIMBURSED = true;

    @Inject
    private BillRepository billRepository;

    @Inject
    private BillMapper billMapper;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    private MockMvc restBillMockMvc;

    private Bill bill;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        BillResource billResource = new BillResource();
        ReflectionTestUtils.setField(billResource, "billRepository", billRepository);
        ReflectionTestUtils.setField(billResource, "billMapper", billMapper);
        this.restBillMockMvc = MockMvcBuilders.standaloneSetup(billResource).setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        bill = new Bill();
        bill.setBill_name(DEFAULT_BILL_NAME);
        bill.setBill_amount(DEFAULT_BILL_AMOUNT);
        bill.setBill_statement_date(DEFAULT_BILL_STATEMENT_DATE);
        bill.setBill_due_date(DEFAULT_BILL_DUE_DATE);
        bill.setIs_bill_paid(DEFAULT_IS_BILL_PAID);
        bill.setIs_bill_reimbursed(DEFAULT_IS_BILL_REIMBURSED);
    }

    @Test
    @Transactional
    public void createBill() throws Exception {
        int databaseSizeBeforeCreate = billRepository.findAll().size();

        // Create the Bill
        BillDTO billDTO = billMapper.billToBillDTO(bill);

        restBillMockMvc.perform(post("/api/bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billDTO)))
                .andExpect(status().isCreated());

        // Validate the Bill in the database
        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeCreate + 1);
        Bill testBill = bills.get(bills.size() - 1);
        assertThat(testBill.getBill_name()).isEqualTo(DEFAULT_BILL_NAME);
        assertThat(testBill.getBill_amount()).isEqualTo(DEFAULT_BILL_AMOUNT);
        assertThat(testBill.getBill_statement_date()).isEqualTo(DEFAULT_BILL_STATEMENT_DATE);
        assertThat(testBill.getBill_due_date()).isEqualTo(DEFAULT_BILL_DUE_DATE);
        assertThat(testBill.getIs_bill_paid()).isEqualTo(DEFAULT_IS_BILL_PAID);
        assertThat(testBill.getIs_bill_reimbursed()).isEqualTo(DEFAULT_IS_BILL_REIMBURSED);
    }

    @Test
    @Transactional
    public void checkBill_nameIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setBill_name(null);

        // Create the Bill, which fails.
        BillDTO billDTO = billMapper.billToBillDTO(bill);

        restBillMockMvc.perform(post("/api/bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billDTO)))
                .andExpect(status().isBadRequest());

        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBill_amountIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setBill_amount(null);

        // Create the Bill, which fails.
        BillDTO billDTO = billMapper.billToBillDTO(bill);

        restBillMockMvc.perform(post("/api/bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billDTO)))
                .andExpect(status().isBadRequest());

        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBill_statement_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setBill_statement_date(null);

        // Create the Bill, which fails.
        BillDTO billDTO = billMapper.billToBillDTO(bill);

        restBillMockMvc.perform(post("/api/bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billDTO)))
                .andExpect(status().isBadRequest());

        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkBill_due_dateIsRequired() throws Exception {
        int databaseSizeBeforeTest = billRepository.findAll().size();
        // set the field null
        bill.setBill_due_date(null);

        // Create the Bill, which fails.
        BillDTO billDTO = billMapper.billToBillDTO(bill);

        restBillMockMvc.perform(post("/api/bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billDTO)))
                .andExpect(status().isBadRequest());

        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllBills() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get all the bills
        restBillMockMvc.perform(get("/api/bills"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(bill.getId().intValue())))
                .andExpect(jsonPath("$.[*].bill_name").value(hasItem(DEFAULT_BILL_NAME.toString())))
                .andExpect(jsonPath("$.[*].bill_amount").value(hasItem(DEFAULT_BILL_AMOUNT.intValue())))
                .andExpect(jsonPath("$.[*].bill_statement_date").value(hasItem(DEFAULT_BILL_STATEMENT_DATE.toString())))
                .andExpect(jsonPath("$.[*].bill_due_date").value(hasItem(DEFAULT_BILL_DUE_DATE.toString())))
                .andExpect(jsonPath("$.[*].is_bill_paid").value(hasItem(DEFAULT_IS_BILL_PAID.booleanValue())))
                .andExpect(jsonPath("$.[*].is_bill_reimbursed").value(hasItem(DEFAULT_IS_BILL_REIMBURSED.booleanValue())));
    }

    @Test
    @Transactional
    public void getBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

        // Get the bill
        restBillMockMvc.perform(get("/api/bills/{id}", bill.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(bill.getId().intValue()))
            .andExpect(jsonPath("$.bill_name").value(DEFAULT_BILL_NAME.toString()))
            .andExpect(jsonPath("$.bill_amount").value(DEFAULT_BILL_AMOUNT.intValue()))
            .andExpect(jsonPath("$.bill_statement_date").value(DEFAULT_BILL_STATEMENT_DATE.toString()))
            .andExpect(jsonPath("$.bill_due_date").value(DEFAULT_BILL_DUE_DATE.toString()))
            .andExpect(jsonPath("$.is_bill_paid").value(DEFAULT_IS_BILL_PAID.booleanValue()))
            .andExpect(jsonPath("$.is_bill_reimbursed").value(DEFAULT_IS_BILL_REIMBURSED.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingBill() throws Exception {
        // Get the bill
        restBillMockMvc.perform(get("/api/bills/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

		int databaseSizeBeforeUpdate = billRepository.findAll().size();

        // Update the bill
        bill.setBill_name(UPDATED_BILL_NAME);
        bill.setBill_amount(UPDATED_BILL_AMOUNT);
        bill.setBill_statement_date(UPDATED_BILL_STATEMENT_DATE);
        bill.setBill_due_date(UPDATED_BILL_DUE_DATE);
        bill.setIs_bill_paid(UPDATED_IS_BILL_PAID);
        bill.setIs_bill_reimbursed(UPDATED_IS_BILL_REIMBURSED);
        
        BillDTO billDTO = billMapper.billToBillDTO(bill);

        restBillMockMvc.perform(put("/api/bills")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(billDTO)))
                .andExpect(status().isOk());

        // Validate the Bill in the database
        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeUpdate);
        Bill testBill = bills.get(bills.size() - 1);
        assertThat(testBill.getBill_name()).isEqualTo(UPDATED_BILL_NAME);
        assertThat(testBill.getBill_amount()).isEqualTo(UPDATED_BILL_AMOUNT);
        assertThat(testBill.getBill_statement_date()).isEqualTo(UPDATED_BILL_STATEMENT_DATE);
        assertThat(testBill.getBill_due_date()).isEqualTo(UPDATED_BILL_DUE_DATE);
        assertThat(testBill.getIs_bill_paid()).isEqualTo(UPDATED_IS_BILL_PAID);
        assertThat(testBill.getIs_bill_reimbursed()).isEqualTo(UPDATED_IS_BILL_REIMBURSED);
    }

    @Test
    @Transactional
    public void deleteBill() throws Exception {
        // Initialize the database
        billRepository.saveAndFlush(bill);

		int databaseSizeBeforeDelete = billRepository.findAll().size();

        // Get the bill
        restBillMockMvc.perform(delete("/api/bills/{id}", bill.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Bill> bills = billRepository.findAll();
        assertThat(bills).hasSize(databaseSizeBeforeDelete - 1);
    }
}
