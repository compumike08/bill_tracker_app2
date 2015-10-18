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


/**
 * A TransactionAuditLog.
 */
@Entity
@Table(name = "TRANSACTIONAUDITLOG")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class TransactionAuditLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    

    @NotNull        
    @Column(name = "debit_amount", precision=10, scale=2, nullable = false)
    private BigDecimal debit_amount;

    @NotNull        
    @Column(name = "credit_amount", precision=10, scale=2, nullable = false)
    private BigDecimal credit_amount;

    @NotNull        
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "trsctn_datetime", nullable = false)
    private DateTime trsctn_datetime;

    @ManyToOne
    private PayerAccount payerAccount;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getDebit_amount() {
        return debit_amount;
    }

    public void setDebit_amount(BigDecimal debit_amount) {
        this.debit_amount = debit_amount;
    }

    public BigDecimal getCredit_amount() {
        return credit_amount;
    }

    public void setCredit_amount(BigDecimal credit_amount) {
        this.credit_amount = credit_amount;
    }

    public DateTime getTrsctn_datetime() {
        return trsctn_datetime;
    }

    public void setTrsctn_datetime(DateTime trsctn_datetime) {
        this.trsctn_datetime = trsctn_datetime;
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

        TransactionAuditLog transactionAuditLog = (TransactionAuditLog) o;

        if ( ! Objects.equals(id, transactionAuditLog.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TransactionAuditLog{" +
                "id=" + id +
                ", debit_amount='" + debit_amount + "'" +
                ", credit_amount='" + credit_amount + "'" +
                ", trsctn_datetime='" + trsctn_datetime + "'" +
                '}';
    }
}
