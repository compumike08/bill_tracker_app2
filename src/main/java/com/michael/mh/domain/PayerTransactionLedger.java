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
 * A PayerTransactionLedger.
 */
@Entity
@Table(name = "PAYERTRANSACTIONLEDGER")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class PayerTransactionLedger implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    

    @NotNull        
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "payer_trsctn_datetime", nullable = false)
    private DateTime payer_trsctn_datetime;

    @NotNull        
    @Column(name = "payer_trsctn_amount", precision=10, scale=2, nullable = false)
    private BigDecimal payer_trsctn_amount;

    @NotNull        
    @Enumerated(EnumType.STRING)
    @Column(name = "payer_trsctn_type", nullable = false)
    private TransactionTypes payer_trsctn_type;

    @NotNull        
    @Column(name = "trsctn_pair_id", nullable = false)
    private Integer trsctn_pair_id;

    @ManyToOne
    private PayerAccount payerAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public DateTime getPayer_trsctn_datetime() {
        return payer_trsctn_datetime;
    }

    public void setPayer_trsctn_datetime(DateTime payer_trsctn_datetime) {
        this.payer_trsctn_datetime = payer_trsctn_datetime;
    }

    public BigDecimal getPayer_trsctn_amount() {
        return payer_trsctn_amount;
    }

    public void setPayer_trsctn_amount(BigDecimal payer_trsctn_amount) {
        this.payer_trsctn_amount = payer_trsctn_amount;
    }

    public TransactionTypes getPayer_trsctn_type() {
        return payer_trsctn_type;
    }

    public void setPayer_trsctn_type(TransactionTypes payer_trsctn_type) {
        this.payer_trsctn_type = payer_trsctn_type;
    }

    public Integer getTrsctn_pair_id() {
        return trsctn_pair_id;
    }

    public void setTrsctn_pair_id(Integer trsctn_pair_id) {
        this.trsctn_pair_id = trsctn_pair_id;
    }

    public PayerAccount getPayerAccount() {
        return payerAccount;
    }

    public void setPayerAccount(PayerAccount payerAccount) {
        this.payerAccount = payerAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PayerTransactionLedger payerTransactionLedger = (PayerTransactionLedger) o;

        if ( ! Objects.equals(id, payerTransactionLedger.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PayerTransactionLedger{" +
                "id=" + id +
                ", payer_trsctn_datetime='" + payer_trsctn_datetime + "'" +
                ", payer_trsctn_amount='" + payer_trsctn_amount + "'" +
                ", payer_trsctn_type='" + payer_trsctn_type + "'" +
                ", trsctn_pair_id='" + trsctn_pair_id + "'" +
                '}';
    }
}
