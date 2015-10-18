package com.michael.mh.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A BillAmountsOwed.
 */
@Entity
@Table(name = "BILLAMOUNTSOWED")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BillAmountsOwed implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    

    @NotNull        
    @Column(name = "amount_owed", precision=10, scale=2, nullable = false)
    private BigDecimal amount_owed;

    @ManyToOne
    private PayerAccount payerAccount;

    @ManyToOne
    private Bill bill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount_owed() {
        return amount_owed;
    }

    public void setAmount_owed(BigDecimal amount_owed) {
        this.amount_owed = amount_owed;
    }

    public PayerAccount getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(PayerAccount payerAccount) {
        this.payerAccount = payerAccount;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BillAmountsOwed billAmountsOwed = (BillAmountsOwed) o;

        if ( ! Objects.equals(id, billAmountsOwed.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BillAmountsOwed{" +
                "id=" + id +
                ", amount_owed='" + amount_owed + "'" +
                '}';
    }
}
