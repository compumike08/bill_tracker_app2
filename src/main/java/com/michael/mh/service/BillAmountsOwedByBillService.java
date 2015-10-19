package com.michael.mh.service;

import com.michael.mh.domain.Bill;
import com.michael.mh.domain.BillAmountsOwed;
import com.michael.mh.repository.BillAmountsOwedRepository;
import com.michael.mh.repository.BillRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

@Service
@Transactional
public class BillAmountsOwedByBillService {

    private final Logger log = LoggerFactory.getLogger(BillAmountsOwedByBillService.class);

    @Inject
    private BillRepository billRepository;

    @Inject
    private BillAmountsOwedRepository billAmountsOwedRepository;

    public List<BillAmountsOwed> findBillAmountsOwedByBillId(Long findBillId){
        Bill findBill = billRepository.findOne(findBillId);

        List<BillAmountsOwed> foundBillAmountsOwed = new LinkedList<>();

        if(!(findBill == null)) {
            foundBillAmountsOwed = billAmountsOwedRepository.findAllByBill(findBill);
        }

        return foundBillAmountsOwed;
    }

}
