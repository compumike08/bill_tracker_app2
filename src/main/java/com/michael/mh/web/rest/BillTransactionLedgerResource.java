package com.michael.mh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.michael.mh.domain.BillTransactionLedger;
import com.michael.mh.repository.BillTransactionLedgerRepository;
import com.michael.mh.web.rest.util.HeaderUtil;
import com.michael.mh.web.rest.dto.BillTransactionLedgerDTO;
import com.michael.mh.web.rest.mapper.BillTransactionLedgerMapper;
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
 * REST controller for managing BillTransactionLedger.
 */
@RestController
@RequestMapping("/api")
public class BillTransactionLedgerResource {

    private final Logger log = LoggerFactory.getLogger(BillTransactionLedgerResource.class);

    @Inject
    private BillTransactionLedgerRepository billTransactionLedgerRepository;

    @Inject
    private BillTransactionLedgerMapper billTransactionLedgerMapper;

    /**
     * POST  /billTransactionLedgers -> Create a new billTransactionLedger.
     */
    @RequestMapping(value = "/billTransactionLedgers",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BillTransactionLedgerDTO> create(@Valid @RequestBody BillTransactionLedgerDTO billTransactionLedgerDTO) throws URISyntaxException {
        log.debug("REST request to save BillTransactionLedger : {}", billTransactionLedgerDTO);
        if (billTransactionLedgerDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new billTransactionLedger cannot already have an ID").body(null);
        }
        BillTransactionLedger billTransactionLedger = billTransactionLedgerMapper.billTransactionLedgerDTOToBillTransactionLedger(billTransactionLedgerDTO);
        BillTransactionLedger result = billTransactionLedgerRepository.save(billTransactionLedger);
        return ResponseEntity.created(new URI("/api/billTransactionLedgers/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("billTransactionLedger", result.getId().toString()))
                .body(billTransactionLedgerMapper.billTransactionLedgerToBillTransactionLedgerDTO(result));
    }

    /**
     * PUT  /billTransactionLedgers -> Updates an existing billTransactionLedger.
     */
    @RequestMapping(value = "/billTransactionLedgers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BillTransactionLedgerDTO> update(@Valid @RequestBody BillTransactionLedgerDTO billTransactionLedgerDTO) throws URISyntaxException {
        log.debug("REST request to update BillTransactionLedger : {}", billTransactionLedgerDTO);
        if (billTransactionLedgerDTO.getId() == null) {
            return create(billTransactionLedgerDTO);
        }
        BillTransactionLedger billTransactionLedger = billTransactionLedgerMapper.billTransactionLedgerDTOToBillTransactionLedger(billTransactionLedgerDTO);
        BillTransactionLedger result = billTransactionLedgerRepository.save(billTransactionLedger);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("billTransactionLedger", billTransactionLedgerDTO.getId().toString()))
                .body(billTransactionLedgerMapper.billTransactionLedgerToBillTransactionLedgerDTO(result));
    }

    /**
     * GET  /billTransactionLedgers -> get all the billTransactionLedgers.
     */
    @RequestMapping(value = "/billTransactionLedgers",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<BillTransactionLedgerDTO> getAll() {
        log.debug("REST request to get all BillTransactionLedgers");
        return billTransactionLedgerRepository.findAll().stream()
            .map(billTransactionLedger -> billTransactionLedgerMapper.billTransactionLedgerToBillTransactionLedgerDTO(billTransactionLedger))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /billTransactionLedgers/:id -> get the "id" billTransactionLedger.
     */
    @RequestMapping(value = "/billTransactionLedgers/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BillTransactionLedgerDTO> get(@PathVariable Long id) {
        log.debug("REST request to get BillTransactionLedger : {}", id);
        return Optional.ofNullable(billTransactionLedgerRepository.findOne(id))
            .map(billTransactionLedgerMapper::billTransactionLedgerToBillTransactionLedgerDTO)
            .map(billTransactionLedgerDTO -> new ResponseEntity<>(
                billTransactionLedgerDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /billTransactionLedgers/:id -> delete the "id" billTransactionLedger.
     */
    @RequestMapping(value = "/billTransactionLedgers/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete BillTransactionLedger : {}", id);
        billTransactionLedgerRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("billTransactionLedger", id.toString())).build();
    }
}
