import java.net.*;
import java.io.*;

public class ClientHandler extends Thread {
    private Socket socket;
    private WarehouseServer warehouse;
    
    public ClientHandler(Socket socket, WarehouseServer warehouse) {
        this.socket = socket;
        this.warehouse = warehouse;
    }
    

    public void run() {
        try {

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                String response = processRequest(inputLine);
                out.println(response);
            }
            
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String processRequest(String request) {
        String[] parts = request.split(":");
        
        switch (parts[0]) {
            case "CHECK_STOCK":
                return warehouse.checkStock();
            case "BUY_APPLES":
                return warehouse.buyApples(Integer.parseInt(parts[1]));
            case "BUY_ORANGES":
                return warehouse.buyOranges(Integer.parseInt(parts[1]));
            case "ADD_APPLES":
                return warehouse.addApples(Integer.parseInt(parts[1]));
            case "ADD_ORANGES":
                return warehouse.addOranges(Integer.parseInt(parts[1]));
            default:
                return "ERROR:UNKNOWN_COMMAND";
        }
    }
}
