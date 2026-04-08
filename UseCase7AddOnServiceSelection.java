import java.util.*;

/**
 * Application entry point for Use Case 7: Add-On Service Selection
 *
 * Goal: Extend the booking model to support optional services, demonstrating 
 * how real-world business features can be added without modifying core logic.
 *
 * @author Book My Stay App
 * @version 7.0
 */
public class UseCase7AddOnServiceSelection {
    public static void main(String[] args) {
        System.out.println("--- Hotel Booking System: Add-On Services Module ---\n");

        // 1. Existing Confirmed Reservations (Simulated State after Allocation)
        String reservationId1 = "RES-Guest-101"; 
        String reservationId2 = "RES-Guest-102";

        // 2. Available Add-On Services – represents individual optional offerings.
        Service breakfast = new Service("Breakfast Buffet", 15.00);
        Service lateCheckout = new Service("Late Checkout", 30.00);
        Service spaAccess = new Service("Daily Spa Access", 50.00);

        // 3. Add-On Service Manager initializes the association structures
        AddOnServiceManager serviceManager = new AddOnServiceManager();

        System.out.println("Event: Guests selecting Add-On Services for Reservations...\n");

        // Guest 1 selects Breakfast and Spa demonstrating a One-to-Many Relationship
        serviceManager.addServiceToReservation(reservationId1, breakfast);
        serviceManager.addServiceToReservation(reservationId1, spaAccess);

        // Guest 2 selects Late Checkout
        serviceManager.addServiceToReservation(reservationId2, lateCheckout);

        System.out.println("\n--- Generating Add-On Cost Breakdown Summaries ---");
        
        // 4. Cost Aggregation Execution
        serviceManager.displayServicesForReservation(reservationId1);
        serviceManager.displayServicesForReservation(reservationId2);
        
        System.out.println("\nSystem Notice: Core booking logic and inventory state remain completely unaffected by Add-Ons!");
    }
}

/**
 * Add-On Service – represents an individual optional offering configured by the hotel.
 */
class Service {
    private String serviceName;
    private double price;

    public Service(String serviceName, double price) {
        this.serviceName = serviceName;
        this.price = price;
    }

    public String getServiceName() {
        return serviceName;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return serviceName + " ($" + price + ")";
    }
}

/**
 * Add-On Service Manager – Manages the Map and List combination.
 * Decouples add-on tracking completely from Room Inventory and Allocation engines.
 */
class AddOnServiceManager {
    // Map<String, List<Service>> efficiently groups multiple services to a singular logical key
    private Map<String, List<Service>> reservationServices;

    public AddOnServiceManager() {
        // Initializes the relationship mapping natively 
        this.reservationServices = new HashMap<>();
    }

    /**
     * Selected services are attached to a list mapped to the corresponding reservation ID.
     * Preserves list insertion order naturally.
     */
    public void addServiceToReservation(String reservationId, Service service) {
        // Defensive creation of the nested List structure 
        reservationServices.putIfAbsent(reservationId, new ArrayList<>());
        
        // Push the service into the mapped list
        reservationServices.get(reservationId).add(service);
        
        System.out.println("  -> [MAPPED] Added '" + service.getServiceName() + "' to Reservation -> " + reservationId);
    }

    /**
     * Calculates total additional cost by looping the list and combining service prices.
     */
    public void displayServicesForReservation(String reservationId) {
        System.out.println("=== Add-On Invoice for " + reservationId + " ===");
        
        // Validation Logic if no services exist
        if (!reservationServices.containsKey(reservationId) || reservationServices.get(reservationId).isEmpty()) {
            System.out.println(" (No add-on services selected)");
            System.out.println(" Additional Cost: $0.0");
            System.out.println("=============================================");
            return;
        }

        List<Service> services = reservationServices.get(reservationId);
        double totalCost = 0.0;
        
        // Iterating over the One-to-Many Map-List combination 
        for (Service s : services) {
            System.out.println(" + " + s.toString());
            totalCost += s.getPrice();
        }
        
        System.out.println("---------------------------------------------");
        System.out.println(" Total Additional Cost calculation: $" + totalCost);
        System.out.println("=============================================\n");
    }
}
