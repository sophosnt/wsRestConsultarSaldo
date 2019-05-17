package com.sophos.poc.wsrestconsultarsaldo.model;

import java.util.Date;
import org.springframework.validation.annotation.Validated;
import com.fasterxml.jackson.annotation.JsonProperty;

@Validated
public class ConsultaSaldoRes{

	@JsonProperty("id_trn")
	private String id_trn = null;

	@JsonProperty("auth_code")
	private String auth_code = null;

	@JsonProperty("date")
	private Date date = null;

	@JsonProperty("server_date")
	private Date server_date = null;

	@JsonProperty("status")
	private StatusType status = null;

	@JsonProperty("account")
	private AccountType account = null;

	public String getId_trn() {
		return id_trn;
	}

	public void setId_trn(String id_trn) {
		this.id_trn = id_trn;
	}

	public String getAuth_code() {
		return auth_code;
	}

	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getServer_date() {
		return server_date;
	}

	public void setServer_date(Date server_date) {
		this.server_date = server_date;
	}

	public StatusType getStatus() {
		return status;
	}

	public void setStatus(StatusType status) {
		this.status = status;
	}

	public AccountType getAccount() {
		return account;
	}

	public void setAccount(AccountType account) {
		this.account = account;
	}

	
}
