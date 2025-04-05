package database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import model.Date;
import model.Equipment;


public class EquipmentDB {


    private static Logger logger = LogManager.getLogger(EquipmentDB.class);

    private Connection conn = null;
    private PreparedStatement pstmt;
    private ResultSet result;

    public EquipmentDB() {
        conn = DatabaseConnection.getConnection();
    }
    
    public void closeResourses(boolean isResult) {
    	
    	if (isResult) {
    		DatabaseConnection.closeResultSet(result);
    	}
		DatabaseConnection.closeStatement(pstmt);
		DatabaseConnection.closeConnection(conn);
    	
    }
    
    public ArrayList<Equipment> getAllEquipment() {
    	ArrayList<Equipment> equipments = new ArrayList<>();
		String sql = "SELECT * FROM rentalproject.equipment";
		try {
			pstmt = conn.prepareStatement(sql);
			result = pstmt.executeQuery();
			while(result.next()) {
				
				Date date = new Date();
				date.toDate(result.getString("date_added"));
				
				Equipment e = new Equipment(
	                result.getInt("id"),
	                result.getString("name"),
	                result.getString("description"),
	                result.getFloat("price"),
	                date,
	                result.getString("avaliability"),
	                result.getString("condition")
	            );
				equipments.add(e);
			}
		} catch (SQLException e) {
			this.closeResourses(true);
			logger.error("Error getting equiptments {}", e.getMessage());
		}
		return equipments;
	}
    
    
    
    public void insertEquipment(Equipment equipment) {
		String sqlinsert = "INSERT INTO rentalproject.equipment "
				+ "(`id`, `name`, `price`, `description`, `date_added`, `avaliability`, `condition`) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (PreparedStatement pstmt = conn.prepareStatement(sqlinsert)) {
			pstmt.setInt(1, equipment.getEquipmentId());
			pstmt.setString(2, equipment.getName());
			pstmt.setDouble(3, equipment.getRentalPrice());
			pstmt.setString(4, equipment.getDescription());
			pstmt.setString(5, equipment.getDate_added().toString());
			pstmt.setString(6, equipment.getAvailabilityStatus());
			pstmt.setString(7, equipment.getConditionStatus());

			int insert = pstmt.executeUpdate();
			if(insert == 1) {
				JOptionPane.showMessageDialog(null, "Record inserted", "Record Status", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			this.closeResourses(false);
			logger.error("Error Saving Equipment: {}", e.getMessage());
		} catch (Exception e) {
			this.closeResourses(false);
			logger.error("Error Saving Equipment: {}", e.getMessage());
		} 
	}
    
    public void updateEquipment(Equipment equipment) {
		String sqlUpdate = "UPDATE rentalproject.equipment SET "
				+ "`name` = ?, "
				+ "`price` = ?, "
				+ "`description` = ?, "
				+ "`date_added` = ?, "
				+ "`avaliability` = ?, "
				+ "`condition` = ? "
				+ "WHERE `id` = ?";

		try{
			pstmt = conn.prepareStatement(sqlUpdate);
			pstmt.setString(1, equipment.getName());
			pstmt.setDouble(2, equipment.getRentalPrice());
			pstmt.setString(3, equipment.getDescription());
			pstmt.setString(4, equipment.getDate_added().toString());
			pstmt.setString(5, equipment.getAvailabilityStatus());
			pstmt.setString(6, equipment.getConditionStatus());
			pstmt.setInt(7, equipment.getEquipmentId());

			int updated = pstmt.executeUpdate();
			if (updated == 0) {
				JOptionPane.showMessageDialog(null, "Record not updated!", "Update Status", JOptionPane.ERROR_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null, "Record updated Successfully", "Update Status", JOptionPane.INFORMATION_MESSAGE);
			}
		} catch (SQLException e) {
			this.closeResourses(false);
			logger.error("Error Updating Equipment: {}", e.getMessage());
		} catch (Exception e) {
			this.closeResourses(false);
			logger.error("Error Updating Equipment: {}", e.getMessage());
		} 
	}
    
    
    public void deleteEquipment(int id) {
        String deleteSql = "DELETE FROM rentalproject.equipment WHERE id = ?";

        try {
        	pstmt = conn.prepareStatement(deleteSql);
            pstmt.setInt(1, id);
            int deleted = pstmt.executeUpdate();
            if (deleted == 0) {
                JOptionPane.showMessageDialog(null, "Record not deleted!", "Deletion Status", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Record has Successfully been Deleted", "Deletion Status", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
        	this.closeResourses(false);
            logger.error("Deletion Failed: {}", e.getMessage());
        } catch (Exception e) {
        	this.closeResourses(false);
            logger.error("Deletion Failed: {}", e.getMessage());
        }
    }
    

}
