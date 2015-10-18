package com.michael.mh.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the PayerAccount entity.
 */
public class PayerAccountDTO implements Serializable {

    private Long id;

    private String payer_acct_name;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PayerAccountDTO payerAccountDTO = (PayerAccountDTO) o;

        if ( ! Objects.equals(id, payerAccountDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PayerAccountDTO{" +
                "id=" + id +
                ", payer_acct_name='" + payer_acct_name + "'" +
                '}';
    }
}
