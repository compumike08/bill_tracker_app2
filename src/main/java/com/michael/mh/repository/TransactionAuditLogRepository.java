package com.michael.mh.repository;

import com.michael.mh.domain.TransactionAuditLog;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TransactionAuditLog entity.
 */
public interface TransactionAuditLogRepository extends JpaRepository<TransactionAuditLog,Long> {

}
