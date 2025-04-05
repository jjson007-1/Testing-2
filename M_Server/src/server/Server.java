package server;

import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import database.Authentication;
import database.EquipmentDB;
import model.Equipment;

public class Server {

    private static final Logger logger = LogManager.getLogger(Server.class);
    ServerSocket serverSocket;

    Server() {
    

        try {
            serverSocket = new ServerSocket(2023);
            logger.info("Server Running now");

            while (!serverSocket.isClosed()) {

                Socket accept = serverSocket.accept();
                logger.info("Client Connected....");

                ClientHandler handler = new ClientHandler(accept);
                new Thread(handler).start();

            }

        } catch (IOException e) {
            logger.error("Error: Socket Connection {}", e.getMessage());
            try {
                serverSocket.close();
            } catch (IOException e1) {
                logger.error("Error while closing socket {}", e1.getMessage());
            }
        }

    }

    class ClientHandler implements Runnable {

        Socket soc;
        ObjectOutputStream oStream;
        ObjectInputStream iStream;

        ClientHandler(Socket socket) {
            this.soc = socket;
            this.initObjectStreams();

        }

        private void initObjectStreams() {

            try {
                oStream = new ObjectOutputStream(soc.getOutputStream());
                iStream = new ObjectInputStream(soc.getInputStream());
            } catch (IOException e) {
                logger.error("Error initializing streams {}", e.getMessage());
            }

        }

        @Override
        public void run() {
            try {
                String command;
                while (!Thread.currentThread().isInterrupted()) {
                    command = (String) iStream.readObject();

                    if (command == null) {
                        logger.warn("Received null command, terminating connection for client: {}", soc.getInetAddress());
                        break;
                    }
                    this.handleClient(command);
                }
            } catch (ClassNotFoundException e) {
                logger.error("Received an unknown command object", e);
            } catch (IOException e) {
                logger.info("Client disconnected: {}", soc.getInetAddress());
                logger.debug("I/O error details:", e);
            } finally {
                closeResources();
            }
        }

        private void closeResources() {

            try {
                this.oStream.flush();
                this.iStream.close();
                this.oStream.close();
            } catch (IOException e) {
                logger.error("Error while closing resources..!");
            }

        }

        private void handleClient(String command) {

            try {

                switch (command) {
                    case "login":

                        String username = (String) iStream.readObject();
                        String password = (String) iStream.readObject();

                        boolean found = new Authentication().login(username, password);

                        oStream.writeObject(found);

                        break;
                        
                    case "All Equipments":
                    	ArrayList<Equipment> equipments = new EquipmentDB().getAllEquipment();
                        oStream.writeObject(equipments);
                        break;
                        
                    case "Add Equipment":
                    	Equipment equipment = (Equipment) iStream.readObject();
                    	new EquipmentDB().insertEquipment(equipment);
                        break;
                    
                    case "Update Equipment":
                    	Equipment updateEquipment = (Equipment) iStream.readObject();
                    	new EquipmentDB().updateEquipment(updateEquipment);
                        break;

                    case "Delete Equiptment":
                    	 int id = (int) iStream.readObject();
                    	 new EquipmentDB().deleteEquipment(id);
                        break;
                    default:
                    	logger.info("Unsuported Operation: {}", command);
                        break;
                }

            } catch (ClassNotFoundException | IOException e) {
                logger.error("Error while performing {}: reason {}", command, e.getMessage());
            }

        }
    }

    public static void main(String[] args) {
        new Server();
        
    }
}
