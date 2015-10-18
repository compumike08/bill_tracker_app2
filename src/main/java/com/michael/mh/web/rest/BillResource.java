package com.michael.mh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.michael.mh.domain.Bill;
import com.michael.mh.repository.BillRepository;
import com.michael.mh.web.rest.util.HeaderUtil;
import com.michael.mh.web.rest.dto.BillDTO;
import com.michael.mh.web.rest.mapper.BillMapper;
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
 * REST controller for managing Bill.
 */
@RestController
@RequestMapping("/api")
public class BillResource {

    private final Logger log = LoggerFactory.getLogger(BillResource.class);

    @Inject
    private BillRepository billRepository;

    @Inject
    private BillMapper billMapper;

    /**
     * POST  /bills -> Create a new bill.
     */
    @RequestMapping(value = "/bills",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BillDTO> create(@Valid @RequestBody BillDTO billDTO) throws URISyntaxException {
        log.debug("REST request to save Bill : {}", billDTO);
        if (billDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new bill cannot already have an ID").body(null);
        }
        Bill bill = billMapper.billDTOToBill(billDTO);
        Bill result = billRepository.save(bill);
        return ResponseEntity.created(new URI("/api/bills/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("bill", result.getId().toString()))
                .body(billMapper.billToBillDTO(result));
    }

    /**
     * PUT  /bills -> Updates an existing bill.
     */
    @RequestMapping(value = "/bills",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BillDTO> update(@Valid @RequestBody BillDTO billDTO) throws URISyntaxException {
        log.debug("REST request to update Bill : {}", billDTO);
        if (billDTO.getId() == null) {
            return create(billDTO);
        }
        Bill bill = billMapper.billDTOToBill(billDTO);
        Bill result = billRepository.save(bill);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("bill", billDTO.getId().toString()))
                .body(billMapper.billToBillDTO(result));
    }

    /**
     * GET  /bills -> get all the bills.
     */
    @RequestMapping(value = "/bills",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<BillDTO> getAll() {
        log.debug("REST request to get all Bills");
        return billRepository.findAll().stream()
            .map(bill -> billMapper.billToBillDTO(bill))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /bills/:id -> get the "id" bill.
     */
    @RequestMapping(value = "/bills/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BillDTO> get(@PathVariable Long id) {
        log.debug("REST request to get Bill : {}", id);
        return Optional.ofNullable(billRepository.findOne(id))
            .map(billMapper::billToBillDTO)
            .map(billDTO -> new ResponseEntity<>(
                billDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bills/:id -> delete the "id" bill.
     */
    @RequestMapping(value = "/bills/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete Bill : {}", id);
        billRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bill", id.toString())).build();
    }
}
