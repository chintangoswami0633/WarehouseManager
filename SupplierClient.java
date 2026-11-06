import java.io.*;
import java.net.*;

public class SupplierClient {
    
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
        
        System.out.println("Initialised WarehouseSupplier and I/O connections");
        
        // Connect to user input from keyboard
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        String response;
        
        System.out.println("Supplier ready. Type commands (check_stock, add_apples <amount>, add_oranges <amount>, quit):");
        
        while (true) {
            System.out.print("Supplier> ");
            userInput = stdIn.readLine();
            
            if (userInput == null || userInput.equals("quit")) {
                break;
            }
            
            String[] parts = userInput.trim().split(" ");
            String command = parts[0];
            
            if (command.equals("check_stock")) {
                System.out.println("WarehouseSupplier sending \"check_stock\" to WarehouseServer");
                out.println("CHECK_STOCK");
                response = in.readLine();
                System.out.println("WarehouseSupplier received \"Stock: " + response + "\" from WarehouseServer");
                
            } else if (command.equals("add_apples") && parts.length > 1) {
                int amount = Integer.parseInt(parts[1]);
                System.out.println("add_apples " + amount);
                System.out.println("WarehouseSupplier sending \"add_apples " + amount + "\" to WarehouseServer");
                out.println("ADD_APPLES:" + amount);
                response = in.readLine();
                System.out.println("WarehouseSupplier received \"" + response + "\" from WarehouseServer");
                
            } else if (command.equals("add_oranges") && parts.length > 1) {
                int amount = Integer.parseInt(parts[1]);
                System.out.println("add_oranges " + amount);
                System.out.println("WarehouseSupplier sending \"add_oranges " + amount + "\" to WarehouseServer");
                out.println("ADD_ORANGES:" + amount);
                response = in.readLine();
                System.out.println("WarehouseSupplier received \"" + response + "\" from WarehouseServer");
                
            } else {
                System.out.println("Unknown command. Use: check_stock, add_apples <amount>, add_oranges <amount>, quit");
            }
        }
        
        socket.close();
    }
}
