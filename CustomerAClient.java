import java.io.*;
import java.net.*;

public class CustomerAClient {
    
    public static void main(String[] args) throws IOException {
     
        Socket socket = null;
        PrintWriter out = null;
        BufferedReader in = null;
        
        try {
            socket = new Socket("localhost", 5000);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("Can't find host");
            System.exit(1);
        } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to: 5000.");
            System.exit(1);
        }
        
        System.out.println("Initialised WarehouseCustomerA and I/O connections");
        
      
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        String response;
        
        System.out.println("Customer A ready. Type commands (check_stock, buy_apples <amount>, buy_oranges <amount>, quit):");
        

        while (true) {
            System.out.print("CustomerA> ");
            userInput = stdIn.readLine();
            
            if (userInput == null || userInput.equals("quit")) {
                break;
            }
            
     
            String[] parts = userInput.trim().split(" ");
            String command = parts[0];
            
            if (command.equals("check_stock")) {
                System.out.println("WarehouseCustomerA sending \"check_stock\" to WarehouseServer");
                out.println("CHECK_STOCK");
                response = in.readLine();
                System.out.println("WarehouseCustomerA received \"Stock: " + response + "\" from WarehouseServer");
                
            } else if (command.equals("buy_apples") && parts.length > 1) {
                int amount = Integer.parseInt(parts[1]);
                System.out.println("buy_apples " + amount);
                System.out.println("WarehouseCustomerA sending \"buy_apples " + amount + "\" to WarehouseServer");
                out.println("BUY_APPLES:" + amount);
                response = in.readLine();
                System.out.println("WarehouseCustomerA received \"" + response + "\" from WarehouseServer");
                
            } else if (command.equals("buy_oranges") && parts.length > 1) {
                int amount = Integer.parseInt(parts[1]);
                System.out.println("buy_oranges " + amount);
                System.out.println("WarehouseCustomerA sending \"buy_oranges " + amount + "\" to WarehouseServer");
                out.println("BUY_ORANGES:" + amount);
                response = in.readLine();
                System.out.println("WarehouseCustomerA received \"" + response + "\" from WarehouseServer");
                
            } else {
                System.out.println("Unknown command. Use: check_stock, buy_apples <amount>, buy_oranges <amount>, quit");
            }
        }
        
        socket.close();
    }
}
