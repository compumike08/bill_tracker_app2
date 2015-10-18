package com.michael.mh.repository;

import com.michael.mh.domain.BillAmountsOwed;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BillAmountsOwed entity.
 */
public interface BillAmountsOwedRepository extends JpaRepository<BillAmountsOwed,Long> {

}
