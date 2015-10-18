package com.michael.mh.web.rest.mapper;

import com.michael.mh.domain.*;
import com.michael.mh.web.rest.dto.BillDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Bill and its DTO BillDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface BillMapper {

    BillDTO billToBillDTO(Bill bill);

    @Mapping(target = "billAmountsOweds", ignore = true)
    @Mapping(target = "billTransactionLedgers", ignore = true)
    Bill billDTOToBill(BillDTO billDTO);
}
