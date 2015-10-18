package com.michael.mh.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.michael.mh.domain.PayerAccount;
import com.michael.mh.repository.PayerAccountRepository;
import com.michael.mh.web.rest.util.HeaderUtil;
import com.michael.mh.web.rest.dto.PayerAccountDTO;
import com.michael.mh.web.rest.mapper.PayerAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * REST controller for managing PayerAccount.
 */
@RestController
@RequestMapping("/api")
public class PayerAccountResource {

    private final Logger log = LoggerFactory.getLogger(PayerAccountResource.class);

    @Inject
    private PayerAccountRepository payerAccountRepository;

    @Inject
    private PayerAccountMapper payerAccountMapper;

    /**
     * POST  /payerAccounts -> Create a new payerAccount.
     */
    @RequestMapping(value = "/payerAccounts",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PayerAccountDTO> create(@RequestBody PayerAccountDTO payerAccountDTO) throws URISyntaxException {
        log.debug("REST request to save PayerAccount : {}", payerAccountDTO);
        if (payerAccountDTO.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new payerAccount cannot already have an ID").body(null);
        }
        PayerAccount payerAccount = payerAccountMapper.payerAccountDTOToPayerAccount(payerAccountDTO);
        PayerAccount result = payerAccountRepository.save(payerAccount);
        return ResponseEntity.created(new URI("/api/payerAccounts/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("payerAccount", result.getId().toString()))
                .body(payerAccountMapper.payerAccountToPayerAccountDTO(result));
    }

    /**
     * PUT  /payerAccounts -> Updates an existing payerAccount.
     */
    @RequestMapping(value = "/payerAccounts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PayerAccountDTO> update(@RequestBody PayerAccountDTO payerAccountDTO) throws URISyntaxException {
        log.debug("REST request to update PayerAccount : {}", payerAccountDTO);
        if (payerAccountDTO.getId() == null) {
            return create(payerAccountDTO);
        }
        PayerAccount payerAccount = payerAccountMapper.payerAccountDTOToPayerAccount(payerAccountDTO);
        PayerAccount result = payerAccountRepository.save(payerAccount);
        return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("payerAccount", payerAccountDTO.getId().toString()))
                .body(payerAccountMapper.payerAccountToPayerAccountDTO(result));
    }

    /**
     * GET  /payerAccounts -> get all the payerAccounts.
     */
    @RequestMapping(value = "/payerAccounts",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    @Transactional(readOnly = true)
    public List<PayerAccountDTO> getAll() {
        log.debug("REST request to get all PayerAccounts");
        return payerAccountRepository.findAll().stream()
            .map(payerAccount -> payerAccountMapper.payerAccountToPayerAccountDTO(payerAccount))
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * GET  /payerAccounts/:id -> get the "id" payerAccount.
     */
    @RequestMapping(value = "/payerAccounts/{id}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PayerAccountDTO> get(@PathVariable Long id) {
        log.debug("REST request to get PayerAccount : {}", id);
        return Optional.ofNullable(payerAccountRepository.findOne(id))
            .map(payerAccountMapper::payerAccountToPayerAccountDTO)
            .map(payerAccountDTO -> new ResponseEntity<>(
                payerAccountDTO,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /payerAccounts/:id -> delete the "id" payerAccount.
     */
    @RequestMapping(value = "/payerAccounts/{id}",
            method = RequestMethod.DELETE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        log.debug("REST request to delete PayerAccount : {}", id);
        payerAccountRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("payerAccount", id.toString())).build();
    }
}
