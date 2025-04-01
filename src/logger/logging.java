package logger;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class logging {

		private static final Logger logger = LogManager.getLogger(logging.class);
		
		public static void main(String[] args) {
			
			logger.info("Test Info message");
			logger.debug("Test Debug Message");
			logger.error("Test Error Message");
			logger.trace("Test Trace Message");
			logger.fatal("Test Fatal Message");
			logger.warn("Test Warning Message");
		}
}


