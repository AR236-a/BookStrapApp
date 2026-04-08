import java.util.List;

/**
 * Handles read-only access to inventory and room information.
 * Goal: Enable guests to view available rooms without modifying system state.
 *
 * @author Book My Stay App
 * @version 4.0
 */
public class SearchService {
    private RoomInventory inventory;

    /**
     * Constructs SearchService with access to the read-only view of inventory.
     * @param inventory The central room inventory
     */
    public SearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Searches and displays available room types and their details.
     * Unavailable room types are filtered out.
     *
     * @param rooms List of all possible room domains to search against.
     */
    public void searchAvailableRooms(List<Room> rooms) {
        System.out.println("--- Room Availability Search Results ---");
        boolean found = false;

        for (Room room : rooms) {
            String roomType = room.getRoomType();
            
            // The system retrieves availability data from the inventory (Read-Only)
            int availableCount = inventory.getAvailableRooms(roomType);

            // Validation Logic - Display only room types with availability > 0
            if (availableCount > 0) {
                found = true;
                System.out.println("\nAVAILABLE [" + availableCount + " left] ->");
                // Room details and pricing are obtained from room objects
                room.displayDetails();
            }
        }

        if (!found) {
            System.out.println("No rooms are currently available.");
        }
        System.out.println("----------------------------------------");
    }
}
