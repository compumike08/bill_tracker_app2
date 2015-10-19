package com.michael.mh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.michael.mh.domain.BillAmountsOwed;
import com.michael.mh.repository.BillAmountsOwedRepository;
import com.michael.mh.service.BillAmountsOwedByBillService;
import com.michael.mh.web.rest.util.HeaderUtil;
import com.michael.mh.web.rest.dto.BillAmountsOwedDTO;
import com.michael.mh.web.rest.mapper.BillAmountsOwedMapper;
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
 * REST controller for managing BillAmountsOwed.
 */
@RestController
@RequestMapping("/api")
public class BillAmountsOwedResource {

    private final Logger log = LoggerFactory.getLogger(BillAmountsOwedResource.class);

    @Inject
    private BillAmountsOwedRepository billAmountsOwedRepository;

    @Inject
    private BillAmountsOwedMapper billAmountsOwedMapper;

    @Inject
    private BillAmountsOwedByBillService billAmountsOwedByBillService;

    /**
     * POST  /billAmountsOweds -> Create a new billAmountsOwed.
     */
    @RequestMapping(value = "/billAmountsOweds",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BillAmountsOwedDTO> create(@Valid @RequestBody BillAmountsOwedDTO billAmountsOwedDTO) throws URISyntaxException {
        log.debug("REST request to save BillAmountsOwed : {}", billAmountsOwedDTO);
        if (billAmountsOwedDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new billAmountsOwed cannot already have an ID").body(null);
        }
        BillAmountsOwed billAmountsOwed = billAmountsOwedMapper.billAmountsOwedDTOToBillAmountsOwed(billAmountsOwedDTO);
        BillAmountsOwed result = billAmountsOwedRepository.save(billAmountsOwed);
        return ResponseEntity.created(new URI("/api/billAmountsOweds/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("billAmountsOwed", result.getId().toString()))
                .body(billAmountsOwedMapper.billAmountsOwedToBillAmountsOwedDTO(result));
    }

    /**
     * PUT  /billAmountsOweds -> Updates an existing billAmountsOwed.
     */
    @RequestMapping(value = "/billAmountsOweds",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BillAmountsOwedDTO> update(@Valid @RequestBody BillAmountsOwedDTO billAmountsOwedDTO) throws URISyntaxException {
        log.debug("REST request to update BillAmountsOwed : {}", billAmountsOwedDTO);
        if (billAmountsOwedDTO.getId() == null) {
            return create(billAmountsOwedDTO);
        }
        BillAmountsOwed billAmountsOwed = billAmountsOwedMapper.billAmountsOwedDTOToBillAmountsOwed(billAmountsOwedDTO);
        BillAmountsOwed result = billAmountsOwedRepository.save(billAmountsOwed);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("billAmountsOwed", billAmountsOwedDTO.getId().toString()))
                .body(billAmountsOwedMapper.billAmountsOwedToBillAmountsOwedDTO(result));
    }

    /**
     * GET  /billAmountsOweds -> get all the billAmountsOweds.
     */
    @RequestMapping(value = "/billAmountsOweds",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<BillAmountsOwedDTO> getAll() {
        log.debug("REST request to get all BillAmountsOweds");
        return billAmountsOwedRepository.findAll().stream()
            .map(billAmountsOwed -> billAmountsOwedMapper.billAmountsOwedToBillAmountsOwedDTO(billAmountsOwed))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /billAmountsOweds/:id -> get the "id" billAmountsOwed.
     */
    @RequestMapping(value = "/billAmountsOweds/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BillAmountsOwedDTO> get(@PathVariable Long id) {
        log.debug("REST request to get BillAmountsOwed : {}", id);
        return Optional.ofNullable(billAmountsOwedRepository.findOne(id))
            .map(billAmountsOwedMapper::billAmountsOwedToBillAmountsOwedDTO)
            .map(billAmountsOwedDTO -> new ResponseEntity<>(
                billAmountsOwedDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /billAmountsOweds -> get all the billAmountsOweds.
     */
    @RequestMapping(value = "/billAmountsOweds/bills/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<BillAmountsOwedDTO> getBillAmountsOwedByBillId(@PathVariable Long id) {
        log.debug("REST request to get all BillAmountsOweds associated with bill : {}", id);
        return billAmountsOwedByBillService.findBillAmountsOwedByBillId(id).stream()
            .map(billAmountsOwed -> billAmountsOwedMapper.billAmountsOwedToBillAmountsOwedDTO(billAmountsOwed))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * DELETE  /billAmountsOweds/:id -> delete the "id" billAmountsOwed.
     */
    @RequestMapping(value = "/billAmountsOweds/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete BillAmountsOwed : {}", id);
        billAmountsOwedRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("billAmountsOwed", id.toString())).build();
    }
}
