package com.michael.mh.web.rest.dto;

import org.joda.time.DateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

import com.michael.mh.domain.enumeration.TransactionTypes;

/**
 * A DTO for the PayerTransactionLedger entity.
 */
public class PayerTransactionLedgerDTO implements Serializable {

    private Long id;

    @NotNull
    private DateTime payer_trsctn_datetime;

    @NotNull
    private BigDecimal payer_trsctn_amount;

    @NotNull
    private TransactionTypes payer_trsctn_type;

    @NotNull
    private Integer trsctn_pair_id;

    private Long payerAccountId;

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

        PayerTransactionLedgerDTO payerTransactionLedgerDTO = (PayerTransactionLedgerDTO) o;

        if ( ! Objects.equals(id, payerTransactionLedgerDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PayerTransactionLedgerDTO{" +
                "id=" + id +
                ", payer_trsctn_datetime='" + payer_trsctn_datetime + "'" +
                ", payer_trsctn_amount='" + payer_trsctn_amount + "'" +
                ", payer_trsctn_type='" + payer_trsctn_type + "'" +
                ", trsctn_pair_id='" + trsctn_pair_id + "'" +
                '}';
    }
}
