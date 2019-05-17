package com.sophos.poc.wsrestconsultarsaldo.util;

import java.util.HashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.stereotype.Component;

@Component
public class ParseISOMessage {

	private static final Logger logger = LogManager.getLogger(ParseISOMessage.class);
	private ParseISOMessage instance;
	private GenericPackager packager;
	
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

	private void setPackager(GenericPackager packager) {
		this.packager = packager;
	}
	
	public void setInstance(ParseISOMessage instance) {
		this.instance = instance;
	}
	public ParseISOMessage getInstance() {
		if (instance == null) {
			try {
				logger.info("Se crea nueva instancia de BuildIso Message");
				instance = new ParseISOMessage();
			}catch (ISOException e) {
				e.printStackTrace();
			}
		}
		return instance;		
	}
	public ParseISOMessage() throws ISOException {
		setPackager(new GenericPackager(BuildISOMessage.class.getResourceAsStream("/Iso8583.xml")));
	}

	public String getJSON(String data) throws ISOException {
		GenericPackager packager;
		try {
			packager = new GenericPackager(ParseISOMessage.class.getResourceAsStream("/Iso8583.xml"));
			StringBuffer buffer = new StringBuffer();
			ISOMsg isoMsg = new ISOMsg();
			isoMsg.setPackager(packager);
			isoMsg.unpack(data.getBytes());
			buffer.append("{ MTI = " + isoMsg.getMTI() + ",");
			for (int i = 1; i <= isoMsg.getMaxField(); i++) {
				if (isoMsg.hasField(i)) {
					if (i == isoMsg.getMaxField()) {
						buffer.append(" Field-" + i + "=" + isoMsg.getString(i) + " }");
					} else {
						buffer.append(" Field-" + i + "=" + isoMsg.getString(i) + ",");
					}
				}
			}
			logger.info(buffer.toString());
			return buffer.toString();

		} catch (ISOException e) {
			logger.error("Error Construyendo Trama",e);
			throw e;
		}
	}
	
	public HashMap<Integer, String> getMapValues(String data) throws ISOException {
		try {
			HashMap<Integer, String> fields = new HashMap<>();
			ISOMsg isoMsg = new ISOMsg();
			isoMsg.setPackager(packager);
			isoMsg.unpack(data.getBytes());
			fields.put(0, isoMsg.getMTI());
			for (int i = 1; i <= isoMsg.getMaxField(); i++) {
				if (isoMsg.hasField(i)) {
					fields.put(i, isoMsg.getString(i));					
				}
			}
			logger.info(fields.toString());
			return fields;
		} catch (ISOException e) {
			logger.error("Error Construyendo Trama",e);
			throw e;
		}
	}

}
