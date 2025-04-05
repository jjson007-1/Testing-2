package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Authentication {

    private static Logger logger = LogManager.getLogger(Authentication.class);

    private Connection conn = null;
    private PreparedStatement st;
    private ResultSet set;

    public Authentication() {
        conn = DatabaseConnection.getConnection();
    }

    public boolean login(String username, String password) {

        String sql = """
                SELECT
                        user_id
                FROM user
                where
                    username=?
                and
                    user_password=?
                """;

        try {
            st = conn.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);

            set = st.executeQuery();

            while (set.next()) {
                return true;
            }
            return false;

        } catch (SQLException e) {
        	DatabaseConnection.closeConnection(conn);
        	DatabaseConnection.closeResultSet(set);
        	DatabaseConnection.closeStatement(st);
            logger.error("Error while logging in {}: Error {}", username, e.getMessage());
            return false;
        }
    }

}
