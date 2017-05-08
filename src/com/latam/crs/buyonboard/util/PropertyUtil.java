package com.latam.crs.buyonboard.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author jrdiaz
 *
 */
public final class PropertyUtil {

	private static final PropertyUtil INSTANCE = new PropertyUtil();
	private static final Logger logger = LoggerFactory.getLogger(PropertyUtil.class);

	private Properties propConfig;

	private PropertyUtil() {
		if(propConfig==null){
			propConfig=new Properties();
		}
	}

	public static PropertyUtil getInstance() {
		return INSTANCE;
	}

	/**
	 * This method load the different files of configuration 
	 * 
	 * @param property
	 * @return
	 * @throws IOException
	 */
	private Properties getProperty(String property) throws IOException {
		FileInputStream fis = new FileInputStream(property);
		propConfig.load(fis);
		return propConfig;
	}

	/**
	 * Read properties from configuration files
	 * config.properties, db.properties, log4j.properties
	 *
	 * @throws IOException
	 */
	public void readProperties() throws IOException {
		try {
			propConfig = getProperty(Constants.FRAMEWORK_PROPERTIES);
			if (logger.isDebugEnabled()) {
				logger.debug(Constants.LOG_FILE_READ + Constants.FRAMEWORK_PROPERTIES + Constants.SQUARE_CLOSE);
			}

		} catch (IOException ioException) {
			logger.error(Constants.LOG_ERROR_LOAD_FILE + Constants.FRAMEWORK_PROPERTIES, ioException);
			throw ioException;
		}
		
		try {
			propConfig = getProperty(Constants.LOG_PROPERTIES);
			if (logger.isDebugEnabled()) {
				logger.debug(Constants.LOG_FILE_READ + Constants.LOG_PROPERTIES + Constants.SQUARE_CLOSE);
			}

		} catch (IOException ioException) {
			logger.error(Constants.LOG_ERROR_LOAD_FILE + Constants.LOG_PROPERTIES, ioException);
			throw ioException;
		}
		
		try {
			propConfig = getProperty(Constants.BUY_ON_BOARD_PROPERTIES);
			if (logger.isDebugEnabled()) {
				logger.debug(Constants.LOG_FILE_READ + Constants.BUY_ON_BOARD_PROPERTIES + Constants.SQUARE_CLOSE);
			}

		} catch (IOException ioException) {
			logger.error(Constants.LOG_ERROR_LOAD_FILE + Constants.BUY_ON_BOARD_PROPERTIES, ioException);
			throw ioException;
		}
	}

	/**
	 * 
	 * 
	 * @return
	 */
	public Properties getPropConfig() {
		return propConfig;
	}
}
