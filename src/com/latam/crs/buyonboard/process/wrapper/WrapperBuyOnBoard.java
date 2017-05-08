package com.latam.crs.buyonboard.process.wrapper;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import com.latam.crs.buyonboard.logic.BuyOnBoardLogic;
import com.latam.ws.v1_0.BuyOnBoardBrasilRS;

public class WrapperBuyOnBoard {
	private static Logger logger = Logger.getLogger(WrapperBuyOnBoard.class);
	
	public static void main(String[] args) {
		String airPort = "123";	
		BasicConfigurator.configure();
		
		BuyOnBoardLogic logic = new BuyOnBoardLogic();
		BuyOnBoardBrasilRS response = logic.execute(airPort);
		
		if (response.getServiceStatus()!= null && response.getServiceStatus().getCode() != 0){
			logger.error(response.getServiceStatus().getNativeMessage()); 
			logger.error(response.getServiceStatus().getCode()); 
			System.exit(1);
		}
	}

}
