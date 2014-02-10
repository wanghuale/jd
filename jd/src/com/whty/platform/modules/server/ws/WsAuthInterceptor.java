package com.whty.platform.modules.server.ws;

import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPMessage;

import org.apache.cxf.binding.soap.SoapMessage;
import org.apache.cxf.binding.soap.saaj.SAAJInInterceptor;
import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.NodeList;

public class WsAuthInterceptor extends AbstractPhaseInterceptor<SoapMessage> {
	private static Logger logger = LoggerFactory.getLogger(WsAuthInterceptor.class);

	private SAAJInInterceptor saa = new SAAJInInterceptor();

	public WsAuthInterceptor() {
		super("pre-invoke");
		getAfter().add(SAAJInInterceptor.class.getName());
	}

	public void handleMessage(SoapMessage message) throws Fault {
		SOAPMessage mess = (SOAPMessage) message.getContent(SOAPMessage.class);
		if (mess == null) {
			this.saa.handleMessage(message);
			mess = (SOAPMessage) message.getContent(SOAPMessage.class);
		}
		try {
			SOAPHeader head = mess.getSOAPHeader();
			if (head == null) {
				return;
			}

			NodeList nodes = head.getElementsByTagName("tns:spId");
			NodeList nodepass = head.getElementsByTagName("tns:spPassword");

			String spId = "";// Properties.getProperty("ws.auth.spId");
			String spPassword = ""; // PropertiesHolder.getProperty("ws.auth.spPassword");

			if (nodes.item(0).getTextContent().equals(spId)) {
				if (nodepass.item(0).getTextContent().equals(spPassword))
					logger.debug("Authentication success.");
			} else {
				SOAPException soapExc = new SOAPException("Authentication error.");
				throw new Fault(soapExc);
			}
		} catch (SOAPException e) {
			e.printStackTrace();
		} catch (Exception e) {
			SOAPException soapExc = new SOAPException("Authentication error.");
			throw new Fault(soapExc);
		}
	}
}