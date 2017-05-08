package com.latam.crs.buyonboard.logic;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.xml.ws.BindingProvider;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.handler.MessageContext;

import org.apache.log4j.Logger;

import com.lan.webservices.xml.ServiceStatusType;
import com.latam.crs.buyonboard.process.wrapper.WrapperBuyOnBoard;
import com.latam.crs.buyonboard.util.Constants;
import com.latam.crs.buyonboard.util.PropertyUtil;
import com.latam.ws.v1_0.BuyOnBoardBrasil100;
import com.latam.ws.v1_0.BuyOnBoardBrasilPortType;
import com.latam.ws.v1_0.BuyOnBoardBrasilRQ;
import com.latam.ws.v1_0.BuyOnBoardBrasilRS;

public class BuyOnBoardLogic {
	private static Logger logger = Logger.getLogger(WrapperBuyOnBoard.class);
	private PropertyUtil propertyUtil;
	private Properties config;
	private static final int CODE_ERROR = 1;
	
	
	public BuyOnBoardLogic() {
		propertyUtil = PropertyUtil.getInstance();
		try {
			propertyUtil.readProperties();
		} catch (IOException e) {
			logger.error(e.getMessage(),e);
		}
		config = propertyUtil.getPropConfig();
	}
	
	/**
	 * This class execute the call of Web Service. Since the version 1.8 the Web Service throw an WebServiceException 
	 * when something wrong happen and the system doesn't find the WSDL.
	 * 
	 * @param airPort
	 * @return
	 */
	public BuyOnBoardBrasilRS execute(String airPort) {

		BuyOnBoardBrasilRS response = new BuyOnBoardBrasilRS();
		BuyOnBoardBrasilRQ request = createRQBody(airPort);
		
		try {
			BuyOnBoardBrasilPortType portType = getBuyOnBoardBrasilPortType();	
			response = portType.buyOnBoardBrasil(request);
		} catch (WebServiceException e) {
			logger.error(e.getMessage(), e);
			ServiceStatusType serviceStatus = new ServiceStatusType();  
			serviceStatus.setCode(CODE_ERROR);
			serviceStatus.setNativeMessage(e.getMessage());
			response.setServiceStatus(serviceStatus);
		}
		return response;
	}
	
    /**
     * This class create an instance of the port. This class is the responsible to provide 
     * the endpoint, user, pass and the information of header of application.  
     * 
     * @return
     */
	private BuyOnBoardBrasilPortType getBuyOnBoardBrasilPortType(){
		
		BuyOnBoardBrasilPortType portType = new BuyOnBoardBrasil100().getBuyOnBoardBrasilPortType();
		((BindingProvider)portType).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, config.getProperty(Constants.ENDPOINT_LOCAL));
		
		((BindingProvider)portType).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, config.getProperty(Constants.WS_USER));
		((BindingProvider)portType).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, config.getProperty(Constants.WS_PASSWORD));
		
		Map<String, List<String>> requestHeaders = new HashMap<>();
		requestHeaders.put("LAN-ApplicationName", Arrays.asList(""));
		((BindingProvider)portType).getRequestContext().put(MessageContext.HTTP_REQUEST_HEADERS, requestHeaders);
		
		return portType;
	}
	
	/**
	 * This class create the request that we will use like parameter for the service
	 * 
	 * @param airPort
	 * @return
	 */
	private BuyOnBoardBrasilRQ createRQBody(String airPort) {
		BuyOnBoardBrasilRQ request = new BuyOnBoardBrasilRQ();
		request.setAirPort(airPort);
		
		return request;
	}

}
