import java.net.*;
import java.io.*;

public class WarehouseServer {
    private int apples = 1000;
    private int oranges = 1000;
    
    public synchronized String checkStock() {
        Thread currentThread = Thread.currentThread();
        System.out.println(currentThread.getName() + " is attempting to acquire a lock!");
        System.out.println(currentThread.getName() + " got a lock!");
        System.out.println("WarehouseServer checked the stock!");
        System.out.println("Stock: " + apples + " apples and " + oranges + " oranges!");
        System.out.println(currentThread.getName() + " released a lock!");
        return apples + " apples and " + oranges + " oranges";
    }
    
    public synchronized String buyApples(int amount) {
        Thread currentThread = Thread.currentThread();
        System.out.println(currentThread.getName() + " is attempting to acquire a lock!");
        System.out.println(currentThread.getName() + " got a lock!");
        
        if (apples >= amount) {
            System.out.println("WarehouseCustomer bought " + amount + " apples!");
            apples -= amount;
            System.out.println(amount + " apples sold! Apples: " + apples);
            System.out.println(currentThread.getName() + " released a lock!");
            return amount + " apples bought! Remaining stock: " + apples + " apples and " + oranges + " oranges";
        }
        System.out.println(currentThread.getName() + " released a lock!");
        return "Not enough apples in stock!";
    }
    
    public synchronized String buyOranges(int amount) {
        Thread currentThread = Thread.currentThread();
        System.out.println(currentThread.getName() + " is attempting to acquire a lock!");
        System.out.println(currentThread.getName() + " got a lock!");
        
        if (oranges >= amount) {
            System.out.println("WarehouseCustomer bought " + amount + " oranges!");
            oranges -= amount;
            System.out.println(amount + " oranges sold! Oranges: " + oranges);
            System.out.println(currentThread.getName() + " released a lock!");
            return amount + " oranges bought! Remaining stock: " + apples + " apples and " + oranges + " oranges";
        }
        System.out.println(currentThread.getName() + " released a lock!");
        return "Not enough oranges in stock!";
    }
    
    public synchronized String addApples(int amount) {
        Thread currentThread = Thread.currentThread();
        System.out.println(currentThread.getName() + " is attempting to acquire a lock!");
        System.out.println(currentThread.getName() + " got a lock!");
        System.out.println("WarehouseSupplier added " + amount + " apples!");
        apples += amount;
        System.out.println(amount + " apples added to stock! Apples: " + apples);
        System.out.println(currentThread.getName() + " released a lock!");
        return amount + " apples added to stock! Apples: " + apples;
    }
    
    public synchronized String addOranges(int amount) {
        Thread currentThread = Thread.currentThread();
        System.out.println(currentThread.getName() + " is attempting to acquire a lock!");
        System.out.println(currentThread.getName() + " got a lock!");
        System.out.println("WarehouseSupplier added " + amount + " oranges!");
        oranges += amount;
        System.out.println(amount + " oranges added to stock! Oranges: " + oranges);
        System.out.println(currentThread.getName() + " released a lock!");
        return amount + " oranges added to stock! Oranges: " + oranges;
    }
    
    public static void main(String[] args) throws IOException {
        WarehouseServer warehouse = new WarehouseServer();
        
     
        InetAddress computerAddr = null;
        try {
            computerAddr = InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            System.out.println(e);
        }
        System.out.println("The address of this computer is... " + computerAddr.getHostName());
        
 
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(5000);
        } catch (IOException e) {
            System.err.println("Could not listen on port: 5000.");
            System.exit(1);
        }
        
        System.out.println("Warehouse server up and waiting");
        System.out.println("Starting stock - Apples: 1000, Oranges: 1000");
        
      
        while (true) {
            Socket clientSocket = null;
            try {
                clientSocket = serverSocket.accept();
                System.out.println("Client connected");
            } catch (IOException e) {
                System.err.println("Server socket failed.");
                System.exit(1);
            }
            
     
            ClientHandler handler = new ClientHandler(clientSocket, warehouse);
            handler.start();
        }
    }
}
