package com.michael.mh.web.rest.mapper;

import com.michael.mh.domain.*;
import com.michael.mh.web.rest.dto.BillAmountsOwedDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity BillAmountsOwed and its DTO BillAmountsOwedDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BillAmountsOwedMapper {

    @Mapping(source = "payerAccount.id", target = "payerAccountId")
    @Mapping(source = "bill.id", target = "billId")
    BillAmountsOwedDTO billAmountsOwedToBillAmountsOwedDTO(BillAmountsOwed billAmountsOwed);

    @Mapping(source = "payerAccountId", target = "payerAccount")
    @Mapping(source = "billId", target = "bill")
    BillAmountsOwed billAmountsOwedDTOToBillAmountsOwed(BillAmountsOwedDTO billAmountsOwedDTO);

    default PayerAccount payerAccountFromId(Long id) {
        if (id == null) {
            return null;
        }
        PayerAccount payerAccount = new PayerAccount();
        payerAccount.setId(id);
        return payerAccount;
    }

    default Bill billFromId(Long id) {
        if (id == null) {
            return null;
        }
        Bill bill = new Bill();
        bill.setId(id);
        return bill;
    }
}
