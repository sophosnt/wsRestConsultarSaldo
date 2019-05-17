package com.sophos.poc.wsrestconsultarsaldo.controller;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sophos.poc.wsrestconsultarsaldo.model.ConsultaSaldoReq;
import com.sophos.poc.wsrestconsultarsaldo.model.ConsultaSaldoRes;
import com.sophos.poc.wsrestconsultarsaldo.service.AccountService;


@RestController
@RequestMapping("/accounts")
public class AccountApiController implements AccountApi {

    private static final Logger log = LoggerFactory.getLogger(AccountApiController.class);

    private final ObjectMapper objectMapper;
    private final HttpServletRequest request;
    @Autowired
    private AccountService service;

    @org.springframework.beans.factory.annotation.Autowired
    public AccountApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<ConsultaSaldoRes> balanceGet(
    		@RequestHeader(value="X-RqUID", required=false) String xRqUID,
    		@RequestHeader(value="X-Channel", required=false) String xChannel,
    		@RequestHeader(value="X-IPAddr", required=false) String xIPAddr,
    		@RequestHeader(value="X-Session", required=false) String xSession,
    		@RequestHeader(value="X-haveToken", required=false) String xHaveToken,
    		@Valid @RequestBody ConsultaSaldoReq getBalanceAccount) {
    	
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
            	ConsultaSaldoRes response = service.getBalance(getBalanceAccount);
            	if(response!=null) {
            		return new ResponseEntity<ConsultaSaldoRes>(response, HttpStatus.OK);
            	}
                return new ResponseEntity<ConsultaSaldoRes>(objectMapper.readValue("{  \"date\" : \"2000-01-23T04:56:07.000+00:00\",  \"id_trn\" : \"id_trn\",  \"server_date\" : \"2000-01-23T04:56:07.000+00:00\",  \"account\" : {    \"account_type\" : \"account_type\",    \"account_id\" : 0,    \"pin\" : \"pin\",    \"currency\" : \"currency\",    \"card_id\" : 6,    \"account_bal\" : 1  },  \"auth_code\" : \"auth_code\",  \"status\" : {    \"status_code\" : \"status_code\",    \"status_desc\" : \"status_desc\",    \"status_info\" : \"status_info\",    \"additional_status_code\" : \"additional_status_code\",    \"additional_status_desc\" : \"additional_status_desc\"  }}", ConsultaSaldoRes.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<ConsultaSaldoRes>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<ConsultaSaldoRes>(HttpStatus.NOT_IMPLEMENTED);
    }

}
