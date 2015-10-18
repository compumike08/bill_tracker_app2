package com.michael.mh.web.rest.mapper;

import com.michael.mh.domain.*;
import com.michael.mh.web.rest.dto.PayerAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity PayerAccount and its DTO PayerAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PayerAccountMapper {

    PayerAccountDTO payerAccountToPayerAccountDTO(PayerAccount payerAccount);

    @Mapping(target = "transactionAuditLogs", ignore = true)
    @Mapping(target = "payerTransactionLedgers", ignore = true)
    @Mapping(target = "billAmountsOweds", ignore = true)
    PayerAccount payerAccountDTOToPayerAccount(PayerAccountDTO payerAccountDTO);
}
