package com.michael.mh.web.rest.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the BillAmountsOwed entity.
 */
public class BillAmountsOwedDTO implements Serializable {

    private Long id;

    @NotNull
    private BigDecimal amount_owed;

    private Long payerAccountId;

    private Long billId;

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

    public Long getPayerAccountId() {
        return payerAccountId;
    }

    public void setPayerAccountId(Long payerAccountId) {
        this.payerAccountId = payerAccountId;
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

        BillAmountsOwedDTO billAmountsOwedDTO = (BillAmountsOwedDTO) o;

        if ( ! Objects.equals(id, billAmountsOwedDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BillAmountsOwedDTO{" +
                "id=" + id +
                ", amount_owed='" + amount_owed + "'" +
                '}';
    }
}
