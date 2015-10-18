package com.michael.mh.repository;

import com.michael.mh.domain.PayerAccount;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PayerAccount entity.
 */
public interface PayerAccountRepository extends JpaRepository<PayerAccount,Long> {

}
