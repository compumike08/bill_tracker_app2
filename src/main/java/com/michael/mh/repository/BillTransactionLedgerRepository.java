package com.michael.mh.repository;

import com.michael.mh.domain.BillTransactionLedger;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BillTransactionLedger entity.
 */
public interface BillTransactionLedgerRepository extends JpaRepository<BillTransactionLedger,Long> {

}
