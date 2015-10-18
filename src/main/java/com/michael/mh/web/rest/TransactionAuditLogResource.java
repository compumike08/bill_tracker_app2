package com.michael.mh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.michael.mh.domain.TransactionAuditLog;
import com.michael.mh.repository.TransactionAuditLogRepository;
import com.michael.mh.web.rest.util.HeaderUtil;
import com.michael.mh.web.rest.dto.TransactionAuditLogDTO;
import com.michael.mh.web.rest.mapper.TransactionAuditLogMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing TransactionAuditLog.
 */
@RestController
@RequestMapping("/api")
public class TransactionAuditLogResource {

    private final Logger log = LoggerFactory.getLogger(TransactionAuditLogResource.class);

    @Inject
    private TransactionAuditLogRepository transactionAuditLogRepository;

    @Inject
    private TransactionAuditLogMapper transactionAuditLogMapper;

    /**
     * POST  /transactionAuditLogs -> Create a new transactionAuditLog.
     */
    @RequestMapping(value = "/transactionAuditLogs",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TransactionAuditLogDTO> create(@Valid @RequestBody TransactionAuditLogDTO transactionAuditLogDTO) throws URISyntaxException {
        log.debug("REST request to save TransactionAuditLog : {}", transactionAuditLogDTO);
        if (transactionAuditLogDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new transactionAuditLog cannot already have an ID").body(null);
        }
        TransactionAuditLog transactionAuditLog = transactionAuditLogMapper.transactionAuditLogDTOToTransactionAuditLog(transactionAuditLogDTO);
        TransactionAuditLog result = transactionAuditLogRepository.save(transactionAuditLog);
        return ResponseEntity.created(new URI("/api/transactionAuditLogs/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("transactionAuditLog", result.getId().toString()))
                .body(transactionAuditLogMapper.transactionAuditLogToTransactionAuditLogDTO(result));
    }

    /**
     * PUT  /transactionAuditLogs -> Updates an existing transactionAuditLog.
     */
    @RequestMapping(value = "/transactionAuditLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TransactionAuditLogDTO> update(@Valid @RequestBody TransactionAuditLogDTO transactionAuditLogDTO) throws URISyntaxException {
        log.debug("REST request to update TransactionAuditLog : {}", transactionAuditLogDTO);
        if (transactionAuditLogDTO.getId() == null) {
            return create(transactionAuditLogDTO);
        }
        TransactionAuditLog transactionAuditLog = transactionAuditLogMapper.transactionAuditLogDTOToTransactionAuditLog(transactionAuditLogDTO);
        TransactionAuditLog result = transactionAuditLogRepository.save(transactionAuditLog);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("transactionAuditLog", transactionAuditLogDTO.getId().toString()))
                .body(transactionAuditLogMapper.transactionAuditLogToTransactionAuditLogDTO(result));
    }

    /**
     * GET  /transactionAuditLogs -> get all the transactionAuditLogs.
     */
    @RequestMapping(value = "/transactionAuditLogs",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<TransactionAuditLogDTO> getAll() {
        log.debug("REST request to get all TransactionAuditLogs");
        return transactionAuditLogRepository.findAll().stream()
            .map(transactionAuditLog -> transactionAuditLogMapper.transactionAuditLogToTransactionAuditLogDTO(transactionAuditLog))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /transactionAuditLogs/:id -> get the "id" transactionAuditLog.
     */
    @RequestMapping(value = "/transactionAuditLogs/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TransactionAuditLogDTO> get(@PathVariable Long id) {
        log.debug("REST request to get TransactionAuditLog : {}", id);
        return Optional.ofNullable(transactionAuditLogRepository.findOne(id))
            .map(transactionAuditLogMapper::transactionAuditLogToTransactionAuditLogDTO)
            .map(transactionAuditLogDTO -> new ResponseEntity<>(
                transactionAuditLogDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /transactionAuditLogs/:id -> delete the "id" transactionAuditLog.
     */
    @RequestMapping(value = "/transactionAuditLogs/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete TransactionAuditLog : {}", id);
        transactionAuditLogRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("transactionAuditLog", id.toString())).build();
    }
}
