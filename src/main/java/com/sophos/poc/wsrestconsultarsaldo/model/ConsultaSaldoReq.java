package com.sophos.poc.wsrestconsultarsaldo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

import org.springframework.validation.annotation.Validated;

@Validated
public class ConsultaSaldoReq {

	@JsonProperty("id_trn")
	private String id_trn = null;

	@JsonProperty("account_id")
	private String account_id = null;

	@JsonProperty("account_type")
	private String account_type = null;

	@JsonProperty("card_id")
	private Double card_id = null;

	@JsonProperty("pin")
	private String pin = null;

	@JsonProperty("date")
	private Date date = null;

	public String getId_trn() {
		return id_trn;
	}

	public void setId_trn(String id_trn) {
		this.id_trn = id_trn;
	}

	public String getAccount_id() {
		return account_id;
	}

	public void setAccount_id(String account_id) {
		this.account_id = account_id;
	}

	public String getAccount_type() {
		return account_type;
	}

	public void setAccount_type(String account_type) {
		this.account_type = account_type;
	}

	public Double getCard_id() {
		return card_id;
	}

	public void setCard_id(Double card_id) {
		this.card_id = card_id;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
