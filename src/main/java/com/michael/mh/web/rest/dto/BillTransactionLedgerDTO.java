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
 * A DTO for the BillTransactionLedger entity.
 */
public class BillTransactionLedgerDTO implements Serializable {

    private Long id;

    @NotNull
    private DateTime bill_trsctn_datetime;

    @NotNull
    private BigDecimal bill_trsctn_amount;

    @NotNull
    private TransactionTypes bill_trsctn_type;

    @NotNull
    private Integer trsctn_pair_id;

    private Long billId;

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

    public Long getBillId() {
        return billId;
    }

    public void setBillId(Long billId) {
        this.billId = billId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BillTransactionLedgerDTO billTransactionLedgerDTO = (BillTransactionLedgerDTO) o;

        if ( ! Objects.equals(id, billTransactionLedgerDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BillTransactionLedgerDTO{" +
                "id=" + id +
                ", bill_trsctn_datetime='" + bill_trsctn_datetime + "'" +
                ", bill_trsctn_amount='" + bill_trsctn_amount + "'" +
                ", bill_trsctn_type='" + bill_trsctn_type + "'" +
                ", trsctn_pair_id='" + trsctn_pair_id + "'" +
                '}';
    }
}
