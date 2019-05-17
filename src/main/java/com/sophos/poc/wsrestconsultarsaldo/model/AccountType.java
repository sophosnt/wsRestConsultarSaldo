package com.sophos.poc.wsrestconsultarsaldo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

@Validated
public class AccountType {

	@JsonProperty("account_id")
	private String account_id = null;

	@JsonProperty("account_type")
	private String account_type = null;

	@JsonProperty("card_id")
	private Double card_id = null;

	@JsonProperty("pin")
	private String pin = null;

	@JsonProperty("account_bal")
	private Double account_bal = null;

	@JsonProperty("currency")
	private String currency = null;

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

	public Double getAccount_bal() {
		return account_bal;
	}

	public void setAccount_bal(Double account_bal) {
		this.account_bal = account_bal;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@Override
	public String toString() {
		return "{\"account_id\":" + account_id + ", \"account_type\":" + "\""+ account_type + "\""+ ", \"card_id\":" + card_id
				+ ", \"pin\":" + pin + ", \"account_bal\":" + account_bal + ", \"currency\":" + "\""+ currency + "\""+ "}";
	}
	
}
