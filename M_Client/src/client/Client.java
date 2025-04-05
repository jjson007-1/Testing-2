package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import model.Equipment;

public class Client {

    private static Logger logger = LogManager.getLogger(Client.class);

    private Socket socket;
    private ObjectInputStream iStream;
    private ObjectOutputStream oStream;

    public Client() {

        try {
            socket = new Socket("localhost", 2023);
            logger.info("Socket is configured at port {}", socket.getPort());
            initObjectStreams();
        } catch (IOException e) {
            logger.error("Error while creating socket {}", e.getMessage());
        }
    }

    private void initObjectStreams() {

        try {
            oStream = new ObjectOutputStream(socket.getOutputStream());
            iStream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            logger.error("Error initializing streams {}", e.getMessage());
        }

    }

    public boolean login(String username, String password) {

        try {

            oStream.writeObject("login");

            oStream.writeObject(username);
            oStream.writeObject(password);

            boolean found = (boolean) iStream.readObject();

            return found;

        } catch (IOException e) {
            logger.error("Stream Error: 'Login' {}", e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {

            logger.error("Error in stream connection", e.getMessage());
            return false;
        }

    }
    
    public ArrayList<Equipment> getEquipments() {

        try {
            oStream.writeObject("All Equipments");
            
            @SuppressWarnings("unchecked")
            ArrayList<Equipment> equipments = (ArrayList<Equipment>) iStream.readObject();

            return equipments;

        } catch (IOException e) {
            logger.error("Stream Error: 'get equiptment' {}", e.getMessage());
            return new ArrayList<Equipment>();
        } catch (ClassNotFoundException e) {

            logger.error("Error in stream connection", e.getMessage());
            return new ArrayList<Equipment>();
        }

    }
    
    public void deleteEquipment(int id) {

        try {
            oStream.writeObject("Delete Equiptment");
            oStream.writeObject(id);

        } catch (IOException e) {
            logger.error("Stream Error: 'deleting equipment' {}", e.getMessage());
           
        }
    }
    
    public void insertEquipment(Equipment equipment) {

        try {
        	oStream.writeObject("Add Equipment");
            oStream.writeObject(equipment);

        } catch (IOException e) {
            logger.error("Stream Error: 'Add equipment' {}", e.getMessage());
           
        }
    }
    
    public void updateEquipment(Equipment equipment) {

        try {
        	oStream.writeObject("Update Equipment");
            oStream.writeObject(equipment);

        } catch (IOException e) {
            logger.error("Stream Error: 'Update equipment' {}", e.getMessage());
           
        }

    }

}
