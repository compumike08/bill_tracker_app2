package com.michael.mh.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.michael.mh.domain.util.CustomDateTimeDeserializer;
import com.michael.mh.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.michael.mh.domain.enumeration.TransactionTypes;

/**
 * A BillTransactionLedger.
 */
@Entity
@Table(name = "BILLTRANSACTIONLEDGER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class BillTransactionLedger implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    

    @NotNull        
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "bill_trsctn_datetime", nullable = false)
    private DateTime bill_trsctn_datetime;

    @NotNull        
    @Column(name = "bill_trsctn_amount", precision=10, scale=2, nullable = false)
    private BigDecimal bill_trsctn_amount;

    @NotNull        
    @Enumerated(EnumType.STRING)
    @Column(name = "bill_trsctn_type", nullable = false)
    private TransactionTypes bill_trsctn_type;

    @NotNull        
    @Column(name = "trsctn_pair_id", nullable = false)
    private Integer trsctn_pair_id;

    @ManyToOne
    private Bill bill;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getBill_trsctn_datetime() {
        return bill_trsctn_datetime;
    }

    public void setBill_trsctn_datetime(DateTime bill_trsctn_datetime) {
        this.bill_trsctn_datetime = bill_trsctn_datetime;
    }

    public BigDecimal getBill_trsctn_amount() {
        return bill_trsctn_amount;
    }

    public void setBill_trsctn_amount(BigDecimal bill_trsctn_amount) {
        this.bill_trsctn_amount = bill_trsctn_amount;
    }

    public TransactionTypes getBill_trsctn_type() {
        return bill_trsctn_type;
    }

    public void setBill_trsctn_type(TransactionTypes bill_trsctn_type) {
        this.bill_trsctn_type = bill_trsctn_type;
    }

    public Integer getTrsctn_pair_id() {
        return trsctn_pair_id;
    }

    public void setTrsctn_pair_id(Integer trsctn_pair_id) {
        this.trsctn_pair_id = trsctn_pair_id;
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

        BillTransactionLedger billTransactionLedger = (BillTransactionLedger) o;

        if ( ! Objects.equals(id, billTransactionLedger.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BillTransactionLedger{" +
                "id=" + id +
                ", bill_trsctn_datetime='" + bill_trsctn_datetime + "'" +
                ", bill_trsctn_amount='" + bill_trsctn_amount + "'" +
                ", bill_trsctn_type='" + bill_trsctn_type + "'" +
                ", trsctn_pair_id='" + trsctn_pair_id + "'" +
                '}';
    }
}
