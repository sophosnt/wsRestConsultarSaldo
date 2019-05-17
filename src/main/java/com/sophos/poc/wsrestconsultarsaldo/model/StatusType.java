package com.sophos.poc.wsrestconsultarsaldo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.validation.annotation.Validated;

@Validated
public class StatusType {
	@JsonProperty("status_code")
	private String status_code = null;

	@JsonProperty("status_desc")
	private String status_desc = null;

	@JsonProperty("status_info")
	private String status_info = null;

	@JsonProperty("additional_status_code")
	private String additional_status_code = null;

	@JsonProperty("additional_status_desc")
	private String additional_status_desc = null;

	public String getStatus_code() {
		return status_code;
	}

	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}

	public String getStatus_desc() {
		return status_desc;
	}

	public void setStatus_desc(String status_desc) {
		this.status_desc = status_desc;
	}

	public String getStatus_info() {
		return status_info;
	}

	public void setStatus_info(String status_info) {
		this.status_info = status_info;
	}

	public String getAdditional_status_code() {
		return additional_status_code;
	}

	public void setAdditional_status_code(String additional_status_code) {
		this.additional_status_code = additional_status_code;
	}

	public String getAdditional_status_desc() {
		return additional_status_desc;
	}

	public void setAdditional_status_desc(String additional_status_desc) {
		this.additional_status_desc = additional_status_desc;
	}

}
