package com.michael.mh.web.rest.dto;

import org.joda.time.DateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the TransactionAuditLog entity.
 */
public class TransactionAuditLogDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal debit_amount;

    @NotNull
    private BigDecimal credit_amount;

    @NotNull
    private DateTime trsctn_datetime;

    private Long payerAccountId;

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

    public Long getPayerAccountId() {
        return payerAccountId;
    }

    public void setPayerAccountId(Long payerAccountId) {
        this.payerAccountId = payerAccountId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        TransactionAuditLogDTO transactionAuditLogDTO = (TransactionAuditLogDTO) o;

        if ( ! Objects.equals(id, transactionAuditLogDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "TransactionAuditLogDTO{" +
                "id=" + id +
                ", debit_amount='" + debit_amount + "'" +
                ", credit_amount='" + credit_amount + "'" +
                ", trsctn_datetime='" + trsctn_datetime + "'" +
                '}';
    }
}
