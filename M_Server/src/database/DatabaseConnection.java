package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DatabaseConnection {

    private static Logger logger = LogManager.getLogger(DatabaseConnection.class);

    private static Connection conn = null;

    DatabaseConnection() {

    }

    public static Connection getConnection() {

        if (conn == null) {

            String url = "jdbc:mysql://localhost:3307/rentalproject";
            try {
                conn = DriverManager.getConnection(url, "root", "usbw");
            } catch (SQLException e) {
                logger.error("Error occurred while establishing db connection {}", e.getMessage());
            }
        }

        return conn;

    }
    
    
    public static void closeConnection(Connection conn) {
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			logger.error("Error Closing Connection {}", e.getMessage());
		}
	}
	
	public static void closeStatement(PreparedStatement stmt) {
		try {
			if (stmt != null) {
				stmt.close();
			}
		} catch (SQLException e) {
			logger.error("Error Closing Statement {}", e.getMessage());
		}
	}
	
	public static void closeResultSet(ResultSet result) {
		try {
			if (result != null) {
				result.close();
			}
		} catch (SQLException e) {
			logger.error("Error Closing ResultSet {}", e.getMessage());
		}
	}

}
