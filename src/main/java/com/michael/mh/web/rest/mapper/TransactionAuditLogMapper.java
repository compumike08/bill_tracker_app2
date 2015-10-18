package com.michael.mh.web.rest.mapper;

import com.michael.mh.domain.*;
import com.michael.mh.web.rest.dto.TransactionAuditLogDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity TransactionAuditLog and its DTO TransactionAuditLogDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface TransactionAuditLogMapper {

    @Mapping(source = "payerAccount.id", target = "payerAccountId")
    TransactionAuditLogDTO transactionAuditLogToTransactionAuditLogDTO(TransactionAuditLog transactionAuditLog);

    @Mapping(source = "payerAccountId", target = "payerAccount")
    TransactionAuditLog transactionAuditLogDTOToTransactionAuditLog(TransactionAuditLogDTO transactionAuditLogDTO);

    default PayerAccount payerAccountFromId(Long id) {
        if (id == null) {
            return null;
        }
        PayerAccount payerAccount = new PayerAccount();
        payerAccount.setId(id);
        return payerAccount;
    }
}
