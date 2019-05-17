package com.sophos.poc.wsrestconsultarsaldo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sophos.poc.wsrestconsultarsaldo.util.SwitchConfiguration;

@Component
public class SwitchClientSocket {

	private static final Logger logger = LogManager.getLogger(SwitchClientSocket.class);
	private String switchHost;
	private int switchPort;
	@Autowired
	private SwitchConfiguration switchConf;

	@Autowired
	public SwitchClientSocket(SwitchConfiguration switchConf) {
		this.switchConf = switchConf;
		this.switchPort = this.switchConf.getPort();
		this.switchHost = this.switchConf.getHost();
	}

	public String callSwitch(String isoMessage) throws IOException {
		Socket socket = null;
		PrintWriter toServer = null;
		BufferedReader fromServer = null;
		try {
			int serverPort = switchPort;
			InetAddress host = InetAddress.getByName(switchHost);
			logger.info("Connecting to server on port " + serverPort);
			socket = new Socket(host, serverPort);
			logger.info("Just connected to " + socket.getRemoteSocketAddress());
			toServer = new PrintWriter(socket.getOutputStream(), true);
			toServer.println(isoMessage);
			toServer.flush();
			fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = fromServer.readLine();
			logger.info("Client received: " + line + " from Server");
			toServer.close();
			fromServer.close();
			socket.close();
			return line;
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
			throw ex;
		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}

	public static void main(String[] args) {
		SwitchConfiguration conf = new SwitchConfiguration();
		SwitchClientSocket client = new SwitchClientSocket(conf);
		client.run();
	}

	public void run() {
		try {
			int serverPort = 9881;
			InetAddress host = InetAddress.getByName("192.168.99.100");
			System.out.println("Connecting to server on port " + serverPort);

			Socket socket = new Socket(host, serverPort);
			System.out.println("Just connected to " + socket.getRemoteSocketAddress());

			PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
			toServer.println(
					"0200B2200000001000000000000000800000201234000000010000011072218012345606A5DFGR031VETEALDEMONIOISO8583 1234567890");
			toServer.flush();

			BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String line = fromServer.readLine();
			System.out.println("Client received: " + line + " from Server");

			toServer.close();
			fromServer.close();
			socket.close();
		} catch (UnknownHostException ex) {
			ex.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
