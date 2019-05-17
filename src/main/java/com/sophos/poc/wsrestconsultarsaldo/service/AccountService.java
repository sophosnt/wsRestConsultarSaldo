package com.sophos.poc.wsrestconsultarsaldo.service;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpos.iso.ISOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sophos.poc.wsrestconsultarsaldo.model.AccountType;
import com.sophos.poc.wsrestconsultarsaldo.model.ConsultaSaldoReq;
import com.sophos.poc.wsrestconsultarsaldo.model.ConsultaSaldoRes;
import com.sophos.poc.wsrestconsultarsaldo.model.StatusType;
import com.sophos.poc.wsrestconsultarsaldo.util.BuildISOMessage;
import com.sophos.poc.wsrestconsultarsaldo.util.ParseISOMessage;

@Service
public class AccountService {
	
	@Autowired
	private BuildISOMessage isoBuilder;
	@Autowired
	private ParseISOMessage parseIsoMessage;
	@Autowired
	private SwitchClientSocket switchClient;
	private final String balanceTrn = "0200";
	private static final Logger logger = LogManager.getLogger(AccountService.class);

	public ConsultaSaldoRes getBalance(@Valid ConsultaSaldoReq getBalanceAccount) {
		ConsultaSaldoRes response = new ConsultaSaldoRes();
		StatusType status = new StatusType();
		response.setId_trn(getBalanceAccount.getId_trn());
		response.setDate(getBalanceAccount.getDate());
		Date serverDate = Calendar.getInstance().getTime();
		response.setServer_date(serverDate);
		
		isoBuilder.getInstance();
		HashMap<Integer, String> campos = new  HashMap<>();
		campos.put(3, "00"+getBalanceAccount.getAccount_type()+"00");
		campos.put(7, getFormatDate("MMddHHmmss", getBalanceAccount.getDate()));
		campos.put(11, getRandomNumberString());
		//campos.put(52, getBalanceAccount.getPin().getBytes().toString());
		campos.put(102, getBalanceAccount.getAccount_id().toString());
		campos.put(104, "CONSULTA DE SALDOS");
		String isoMessage = isoBuilder.createIsoMessagge(campos, balanceTrn);
		logger.info("ISO-Message:" +isoMessage);
		if(isoMessage!=null) {
			try {
				String rs = switchClient.callSwitch(isoMessage);
				parseIsoMessage.getInstance();
				HashMap<Integer, String> rsFields = parseIsoMessage.getMapValues(rs);
				response.setAuth_code(rsFields.get(38));
				status.setStatus_code(rsFields.get(39));
				status.setStatus_desc(rsFields.get(44));
				status.setAdditional_status_code(rsFields.get(11));
				status.setAdditional_status_desc(rsFields.get(105));	
				if(status.getStatus_code().equals("00")) {
					status.setStatus_info("Info");
					AccountType account = new AccountType();
					account.setAccount_id(getBalanceAccount.getAccount_id());
					account.setAccount_type(getBalanceAccount.getAccount_type());
					Double account_bal = 0D;
					account_bal = Double.parseDouble(rsFields.get(4));
					account.setAccount_bal(account_bal );
					account.setCurrency(rsFields.get(49)!= null ?rsFields.get(49) : "COP");
					response.setAccount(account);
				}else {
					status.setStatus_info("Warn");
				}							
				response.setStatus(status);					
				return response;
			}catch (ISOException e) {
				logger.error("Error al interpretar respuesta del Switch:");
//				status.setStatus_code("996");
//				status.setStatus_desc("Error al interpretar respuesta del Switch");
//				status.setStatus_info("Error");
//				status.setAdditional_status_code("999");
//				status.setAdditional_status_desc("Comuhnicarse con el administrador");
//				response.setStatus(status);
//				return response;
			}catch (UnknownHostException ex) {
				logger.error("Error UnknownHostException el Host no es valido o accesible");
//				status.setStatus_code("997");
//				status.setStatus_desc("Error UnknownHostException el Host destino no es valido o accesible");
//				status.setStatus_info("Error");
//				status.setAdditional_status_code("999");
//				status.setAdditional_status_desc("Comunicarse con el administrador");
//				response.setStatus(status);
//				return response;
			}catch (IOException e) {
				logger.error("Error al intentar establecer la comunicacion con el Switch");
//				status.setStatus_code("998");
//				status.setStatus_desc("Error al intentar establecer la comunicacion con el Switch");
//				status.setStatus_info("Error");
//				status.setAdditional_status_code("999");
//				status.setAdditional_status_desc("Comunicarse con el administrador");
//				response.setStatus(status);
//				return response;
			} 
		}else {
			status.setStatus_code("999");
			status.setStatus_desc("Se presento un error al construir el mensaje ISO");
			status.setStatus_info("Error");
			status.setAdditional_status_code("999");
			status.setAdditional_status_desc("Validar los datos enviados");
			response.setStatus(status);
			return response;
		}		
		return null;
	}

	private String getRandomNumberString() {
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999);
	    return String.format("%06d", number);
	}
	
	private String getFormatDate(String formato, Date value) {
		SimpleDateFormat format = new SimpleDateFormat(formato);
		format.format(value);
		return format.format(value);
	}
	
	@SuppressWarnings("unused")
	private String padFormatString(String campo, int length, String sentido) {
		if(sentido.equals("R")) {
			return String.format("%-" + length + "s", campo); 
		}else {
			return String.format("%" + length + "s", campo);
		}		
	}
}
