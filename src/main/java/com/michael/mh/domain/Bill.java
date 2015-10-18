package com.michael.mh.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.michael.mh.domain.util.CustomLocalDateSerializer;
import com.michael.mh.domain.util.ISO8601LocalDateDeserializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Bill.
 */
@Entity
@Table(name = "BILL")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Bill implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    

    @NotNull        
    @Column(name = "bill_name", nullable = false)
    private String bill_name;

    @NotNull        
    @Column(name = "bill_amount", precision=10, scale=2, nullable = false)
    private BigDecimal bill_amount;

    @NotNull        
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "bill_statement_date", nullable = false)
    private LocalDate bill_statement_date;

    @NotNull        
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "bill_due_date", nullable = false)
    private LocalDate bill_due_date;
    
    @Column(name = "is_bill_paid")
    private Boolean is_bill_paid;
    
    @Column(name = "is_bill_reimbursed")
    private Boolean is_bill_reimbursed;

    @OneToMany(mappedBy = "bill")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BillAmountsOwed> billAmountsOweds = new HashSet<>();

    @OneToMany(mappedBy = "bill")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BillTransactionLedger> billTransactionLedgers = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBill_name() {
        return bill_name;
    }

    public void setBill_name(String bill_name) {
        this.bill_name = bill_name;
    }

    public BigDecimal getBill_amount() {
        return bill_amount;
    }

    public void setBill_amount(BigDecimal bill_amount) {
        this.bill_amount = bill_amount;
    }

    public LocalDate getBill_statement_date() {
        return bill_statement_date;
    }

    public void setBill_statement_date(LocalDate bill_statement_date) {
        this.bill_statement_date = bill_statement_date;
    }

    public LocalDate getBill_due_date() {
        return bill_due_date;
    }

    public void setBill_due_date(LocalDate bill_due_date) {
        this.bill_due_date = bill_due_date;
    }

    public Boolean getIs_bill_paid() {
        return is_bill_paid;
    }

    public void setIs_bill_paid(Boolean is_bill_paid) {
        this.is_bill_paid = is_bill_paid;
    }

    public Boolean getIs_bill_reimbursed() {
        return is_bill_reimbursed;
    }

    public void setIs_bill_reimbursed(Boolean is_bill_reimbursed) {
        this.is_bill_reimbursed = is_bill_reimbursed;
    }

    public Set<BillAmountsOwed> getBillAmountsOweds() {
        return billAmountsOweds;
    }

    public void setBillAmountsOweds(Set<BillAmountsOwed> billAmountsOweds) {
        this.billAmountsOweds = billAmountsOweds;
    }

    public Set<BillTransactionLedger> getBillTransactionLedgers() {
        return billTransactionLedgers;
    }

    public void setBillTransactionLedgers(Set<BillTransactionLedger> billTransactionLedgers) {
        this.billTransactionLedgers = billTransactionLedgers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Bill bill = (Bill) o;

        if ( ! Objects.equals(id, bill.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Bill{" +
                "id=" + id +
                ", bill_name='" + bill_name + "'" +
                ", bill_amount='" + bill_amount + "'" +
                ", bill_statement_date='" + bill_statement_date + "'" +
                ", bill_due_date='" + bill_due_date + "'" +
                ", is_bill_paid='" + is_bill_paid + "'" +
                ", is_bill_reimbursed='" + is_bill_reimbursed + "'" +
                '}';
    }
}
