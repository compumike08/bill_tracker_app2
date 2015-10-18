package com.michael.mh.web.rest.mapper;

import com.michael.mh.domain.*;
import com.michael.mh.web.rest.dto.BillTransactionLedgerDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BillTransactionLedger and its DTO BillTransactionLedgerDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BillTransactionLedgerMapper {

    @Mapping(source = "bill.id", target = "billId")
    BillTransactionLedgerDTO billTransactionLedgerToBillTransactionLedgerDTO(BillTransactionLedger billTransactionLedger);

    @Mapping(source = "billId", target = "bill")
    BillTransactionLedger billTransactionLedgerDTOToBillTransactionLedger(BillTransactionLedgerDTO billTransactionLedgerDTO);

    default Bill billFromId(Long id) {
        if (id == null) {
            return null;
        }
        Bill bill = new Bill();
        bill.setId(id);
        return bill;
    }
}
