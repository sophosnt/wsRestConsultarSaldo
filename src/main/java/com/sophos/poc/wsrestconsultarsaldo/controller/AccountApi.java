package com.sophos.poc.wsrestconsultarsaldo.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.sophos.poc.wsrestconsultarsaldo.model.ConsultaSaldoReq;
import com.sophos.poc.wsrestconsultarsaldo.model.ConsultaSaldoRes;


public interface AccountApi {

    @RequestMapping(value = "/balance",
        produces = { "application/json" }, 
        consumes = { "application/json" },
        method = RequestMethod.GET)
    ResponseEntity<ConsultaSaldoRes> balanceGet(@RequestHeader(value="X-RqUID", required=false) String xRqUID,
    		@RequestHeader(value="X-Channel", required=false) String xChannel,
    		@RequestHeader(value="X-IPAddr", required=false) String xIPAddr,
    		@RequestHeader(value="X-Session", required=false) String xSession,
    		@RequestHeader(value="X-haveToken", required=false) String xHaveToken,
    		@Valid @RequestBody ConsultaSaldoReq getBalanceAccount);

}
