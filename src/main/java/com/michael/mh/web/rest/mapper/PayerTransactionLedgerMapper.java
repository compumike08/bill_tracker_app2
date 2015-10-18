package com.michael.mh.web.rest.mapper;

import com.michael.mh.domain.*;
import com.michael.mh.web.rest.dto.PayerTransactionLedgerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PayerTransactionLedger and its DTO PayerTransactionLedgerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PayerTransactionLedgerMapper {

    @Mapping(source = "payerAccount.id", target = "payerAccountId")
    PayerTransactionLedgerDTO payerTransactionLedgerToPayerTransactionLedgerDTO(PayerTransactionLedger payerTransactionLedger);

    @Mapping(source = "payerAccountId", target = "payerAccount")
    PayerTransactionLedger payerTransactionLedgerDTOToPayerTransactionLedger(PayerTransactionLedgerDTO payerTransactionLedgerDTO);

    default PayerAccount payerAccountFromId(Long id) {
        if (id == null) {
            return null;
        }
        PayerAccount payerAccount = new PayerAccount();
        payerAccount.setId(id);
        return payerAccount;
    }
}
