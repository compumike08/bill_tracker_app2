package com.michael.mh.web.rest.dto;

import org.joda.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Bill entity.
 */
public class BillDTO implements Serializable {

    private Long id;

    @NotNull
    private String bill_name;

    @NotNull
    private BigDecimal bill_amount;

    @NotNull
    private LocalDate bill_statement_date;

    @NotNull
    private LocalDate bill_due_date;

    private Boolean is_bill_paid;

    private Boolean is_bill_reimbursed;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        BillDTO billDTO = (BillDTO) o;

        if ( ! Objects.equals(id, billDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "BillDTO{" +
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
