package com.michael.mh.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A PayerAccount.
 */
@Entity
@Table(name = "PAYERACCOUNT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PayerAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Column(name = "payer_acct_name")
    private String payer_acct_name;

    @OneToMany(mappedBy = "payerAccount")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<TransactionAuditLog> transactionAuditLogs = new HashSet<>();

    @OneToMany(mappedBy = "payerAccount")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<PayerTransactionLedger> payerTransactionLedgers = new HashSet<>();

    @OneToMany(mappedBy = "payerAccount")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<BillAmountsOwed> billAmountsOweds = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPayer_acct_name() {
        return payer_acct_name;
    }

    public void setPayer_acct_name(String payer_acct_name) {
        this.payer_acct_name = payer_acct_name;
    }

    public Set<TransactionAuditLog> getTransactionAuditLogs() {
        return transactionAuditLogs;
    }

    public void setTransactionAuditLogs(Set<TransactionAuditLog> transactionAuditLogs) {
        this.transactionAuditLogs = transactionAuditLogs;
    }

    public Set<PayerTransactionLedger> getPayerTransactionLedgers() {
        return payerTransactionLedgers;
    }

    public void setPayerTransactionLedgers(Set<PayerTransactionLedger> payerTransactionLedgers) {
        this.payerTransactionLedgers = payerTransactionLedgers;
    }

    public Set<BillAmountsOwed> getBillAmountsOweds() {
        return billAmountsOweds;
    }

    public void setBillAmountsOweds(Set<BillAmountsOwed> billAmountsOweds) {
        this.billAmountsOweds = billAmountsOweds;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PayerAccount payerAccount = (PayerAccount) o;

        if ( ! Objects.equals(id, payerAccount.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PayerAccount{" +
                "id=" + id +
                ", payer_acct_name='" + payer_acct_name + "'" +
                '}';
    }
}
