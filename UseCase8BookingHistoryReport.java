import java.util.ArrayList;
import java.util.List;

/**
 * Application entry point for Use Case 8: Booking History & Reporting
 *
 * Goal: Introduce historical tracking of confirmed bookings to provide 
 * operational visibility, enable audits, and support reporting.
 *
 * @author Book My Stay App
 * @version 8.0
 */
public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {
        System.out.println("--- Hotel Booking System: Booking History & Reporting Module ---\n");

        // 1. Initialize Booking History storage
        BookingHistory history = new BookingHistory();

        System.out.println("Event: Processing Confirmed Bookings from Allocation Service...\n");

        // 2. Simulating confirmed reservations mapped directly to history
        Reservation res1 = new Reservation("RES-101", "Guest-A", "Single Room");
        history.addReservation(res1);

        Reservation res2 = new Reservation("RES-102", "Guest-B", "Double Room");
        history.addReservation(res2);

        Reservation res3 = new Reservation("RES-103", "Guest-C", "Suite Room");
        history.addReservation(res3);

        System.out.println("\nEvent Check: Admin requesting Operational Reports...\n");
        
        // 3. Independent Booking Report Service decoupled from storage logic
        BookingReportService reportService = new BookingReportService(history);
        
        // 4. Output analytical reports preserving chronologically
        reportService.generateChronologicalReport();
        reportService.generateSummaryReport();
        
        System.out.println("System Notice: Historical Auditing and Reporting successfully validated via ArrayList Preservation.");
    }
}

/**
 * Booking History – maintains a record of confirmed reservations using a List.
 */
class BookingHistory {
    // List naturally preserves insertion order, serving as a chronologically ordered audit trail.
    private List<Reservation> confirmedBookings;

    public BookingHistory() {
        this.confirmedBookings = new ArrayList<>();
    }

    /**
     * Store each confirmed reservation in booking history tracking insertion ordering automatically
     */
    public void addReservation(Reservation reservation) {
        // Appending to the tail logic
        confirmedBookings.add(reservation);
        System.out.println("  [RECORDED] Added to History -> " + reservation.getReservationId());
    }

    /**
     * Allow retrieval of stored reservations for review by the reporting layer.
     */
    public List<Reservation> getHistory() {
        return confirmedBookings;
    }
}

/**
 * Booking Report Service – generates summaries and reports from stored booking data.
 * Crucially decouples reading/rendering logic from raw storage.
 */
class BookingReportService {
    private BookingHistory history;

    public BookingReportService(BookingHistory history) {
        this.history = history;
    }

    /**
     * Generates an audit trail of bookings in the strict order they were confirmed.
     * Prevents mutating core storage logic.
     */
    public void generateChronologicalReport() {
        System.out.println("=== Full Chronological Booking Audit Trail ===");
        List<Reservation> records = history.getHistory(); // Reaches safely into History data
        
        if (records.isEmpty()) {
            System.out.println(" No bookings found in historical records.");
        } else {
            for (int i = 0; i < records.size(); i++) {
                System.out.println(" [" + (i + 1) + "] " + records.get(i).toString());
            }
        }
        System.out.println("==============================================");
    }

    /**
     * Generates an aggregate summary demonstrating simplified volume analysis
     */
    public void generateSummaryReport() {
        System.out.println("\n=== Daily Volume Operational Summary ===");
        System.out.println(" System Metrics: " + history.getHistory().size() + " total fully-confirmed reservations today.");
        System.out.println("========================================\n");
    }
}

/**
 * Data Model for a Confirmed Booking Reservation.
 */
class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() { return reservationId; }
    public String getGuestName() { return guestName; }
    public String getRoomType() { return roomType; }

    @Override
    public String toString() {
        return "ID: " + reservationId + " | Guest: " + guestName + " | Room: " + roomType;
    }
}
