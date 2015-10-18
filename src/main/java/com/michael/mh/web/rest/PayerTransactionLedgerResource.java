package com.michael.mh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.michael.mh.domain.PayerTransactionLedger;
import com.michael.mh.repository.PayerTransactionLedgerRepository;
import com.michael.mh.web.rest.util.HeaderUtil;
import com.michael.mh.web.rest.dto.PayerTransactionLedgerDTO;
import com.michael.mh.web.rest.mapper.PayerTransactionLedgerMapper;
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
 * REST controller for managing PayerTransactionLedger.
 */
@RestController
@RequestMapping("/api")
public class PayerTransactionLedgerResource {

    private final Logger log = LoggerFactory.getLogger(PayerTransactionLedgerResource.class);

    @Inject
    private PayerTransactionLedgerRepository payerTransactionLedgerRepository;

    @Inject
    private PayerTransactionLedgerMapper payerTransactionLedgerMapper;

    /**
     * POST  /payerTransactionLedgers -> Create a new payerTransactionLedger.
     */
    @RequestMapping(value = "/payerTransactionLedgers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PayerTransactionLedgerDTO> create(@Valid @RequestBody PayerTransactionLedgerDTO payerTransactionLedgerDTO) throws URISyntaxException {
        log.debug("REST request to save PayerTransactionLedger : {}", payerTransactionLedgerDTO);
        if (payerTransactionLedgerDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new payerTransactionLedger cannot already have an ID").body(null);
        }
        PayerTransactionLedger payerTransactionLedger = payerTransactionLedgerMapper.payerTransactionLedgerDTOToPayerTransactionLedger(payerTransactionLedgerDTO);
        PayerTransactionLedger result = payerTransactionLedgerRepository.save(payerTransactionLedger);
        return ResponseEntity.created(new URI("/api/payerTransactionLedgers/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("payerTransactionLedger", result.getId().toString()))
                .body(payerTransactionLedgerMapper.payerTransactionLedgerToPayerTransactionLedgerDTO(result));
    }

    /**
     * PUT  /payerTransactionLedgers -> Updates an existing payerTransactionLedger.
     */
    @RequestMapping(value = "/payerTransactionLedgers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PayerTransactionLedgerDTO> update(@Valid @RequestBody PayerTransactionLedgerDTO payerTransactionLedgerDTO) throws URISyntaxException {
        log.debug("REST request to update PayerTransactionLedger : {}", payerTransactionLedgerDTO);
        if (payerTransactionLedgerDTO.getId() == null) {
            return create(payerTransactionLedgerDTO);
        }
        PayerTransactionLedger payerTransactionLedger = payerTransactionLedgerMapper.payerTransactionLedgerDTOToPayerTransactionLedger(payerTransactionLedgerDTO);
        PayerTransactionLedger result = payerTransactionLedgerRepository.save(payerTransactionLedger);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("payerTransactionLedger", payerTransactionLedgerDTO.getId().toString()))
                .body(payerTransactionLedgerMapper.payerTransactionLedgerToPayerTransactionLedgerDTO(result));
    }

    /**
     * GET  /payerTransactionLedgers -> get all the payerTransactionLedgers.
     */
    @RequestMapping(value = "/payerTransactionLedgers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<PayerTransactionLedgerDTO> getAll() {
        log.debug("REST request to get all PayerTransactionLedgers");
        return payerTransactionLedgerRepository.findAll().stream()
            .map(payerTransactionLedger -> payerTransactionLedgerMapper.payerTransactionLedgerToPayerTransactionLedgerDTO(payerTransactionLedger))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /payerTransactionLedgers/:id -> get the "id" payerTransactionLedger.
     */
    @RequestMapping(value = "/payerTransactionLedgers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PayerTransactionLedgerDTO> get(@PathVariable Long id) {
        log.debug("REST request to get PayerTransactionLedger : {}", id);
        return Optional.ofNullable(payerTransactionLedgerRepository.findOne(id))
            .map(payerTransactionLedgerMapper::payerTransactionLedgerToPayerTransactionLedgerDTO)
            .map(payerTransactionLedgerDTO -> new ResponseEntity<>(
                payerTransactionLedgerDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /payerTransactionLedgers/:id -> delete the "id" payerTransactionLedger.
     */
    @RequestMapping(value = "/payerTransactionLedgers/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete PayerTransactionLedger : {}", id);
        payerTransactionLedgerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("payerTransactionLedger", id.toString())).build();
    }
}
