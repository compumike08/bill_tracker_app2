package com.michael.mh.repository;

import com.michael.mh.domain.PayerTransactionLedger;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PayerTransactionLedger entity.
 */
public interface PayerTransactionLedgerRepository extends JpaRepository<PayerTransactionLedger,Long> {

}
