package com.sophos.poc.wsrestconsultarsaldo.util;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.stereotype.Component;

@Component
public class BuildISOMessage {

	private static final Logger logger = LogManager.getLogger(BuildISOMessage.class);
	private GenericPackager packager;
	private BuildISOMessage instance;

	public BuildISOMessage() throws IOException, ISOException {
		setPackager(new GenericPackager(BuildISOMessage.class.getResourceAsStream("/Iso8583.xml")));
	}

	public BuildISOMessage getInstance() {
		if (instance == null) {
			try {
				logger.info("Se crea nueva instancia de BuildIso Message");
				instance = new BuildISOMessage();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ISOException e) {
				e.printStackTrace();
			}
		}
		return instance;
	}

	public void setInstance(BuildISOMessage instance) {
		this.instance = instance;
	}

	public GenericPackager getPackager() {
		try {
			if (packager != null) {
				return packager;
			}
			return new GenericPackager(BuildISOMessage.class.getResourceAsStream("/Iso8583.xml"));
		} catch (ISOException e) {
			logger.error("Error al cargar el Packager");
		}
		return null;
	}

	public void setPackager(GenericPackager packager) {
		this.packager = packager;
	}
	
	public String createIsoMessagge(HashMap<Integer, String> campos, String trn) {
		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager(getPackager());
		String response = null;
		try {
			isoMsg.setMTI(trn);
			for( Map.Entry<Integer, String> campo: campos.entrySet() ) {
				isoMsg.set(campo.getKey(), campo.getValue());
			}
			byte[] data = isoMsg.pack();
			response = new String(data);			
		} catch (ISOException e) {
			logger.error("Error createIsoMessagge:  ", e);
		}
		return response;
	}

	public static void main(String[] args) throws IOException, ISOException {
		// Create Packager based on XML that contain DE type
		GenericPackager packager = new GenericPackager(BuildISOMessage.class.getResourceAsStream("/Iso8583.xml"));
		ISOMsg isoMsg = new ISOMsg();
		isoMsg.setPackager(packager);
		isoMsg.setMTI("0210");
		isoMsg.set(3, "201234");
		isoMsg.set(4, "10000");
		isoMsg.set(7, "110722180");
		isoMsg.set(11, "123456");
		isoMsg.set(38, "888888");
		isoMsg.set(39, "00");
		isoMsg.set(44, "A5DFGR");
		isoMsg.set(105, "VETEALDEMONIOISO8583 1234567890");
		// print the DE list
		logISOMsg(isoMsg);

		byte[] data = isoMsg.pack();
		logger.info("RESULT-1: " + new String(data));

	}

	private static void logISOMsg(ISOMsg msg) {
		try {
			StringBuffer buffer = new StringBuffer();
			buffer.append("{ MTI = " + msg.getMTI() + ",");
			for (int i = 1; i <= msg.getMaxField(); i++) {
				if (msg.hasField(i)) {
					if (i == msg.getMaxField()) {
						buffer.append(" Field-" + i + "=" + msg.getString(i) + " }");
					} else {
						buffer.append(" Field-" + i + "=" + msg.getString(i) + ",");
					}
				}
			}
			logger.info(buffer.toString());
		} catch (ISOException e1) {
			logger.error("Error leyendo ISO", e1);
		}
	}

}
