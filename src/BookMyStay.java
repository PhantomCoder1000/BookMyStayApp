import java.util.LinkedList;
import java.util.Map;
import java.util.HashMap;
import java.util.Queue;

/**
 * ====================================================================
 * ABSTRACT CLASS - Room
 * ====================================================================
 * * Use Case 2: Basic Room Types & Static Availability
 * * Description:
 * This abstract class represents a generic hotel room.
 * * It models attributes that are intrinsic to a room type
 * and remain constant regardless of availability.
 * * Inventory-related concerns are intentionally excluded.
 * * @version 2.0
 */
abstract class Room {

    /** Number of beds available in the room. */
    protected int numberOfBeds;

    /** Total size of the room in square feet. */
    protected int squareFeet;

    /** Price charged per night for this room type. */
    protected double pricePerNight;

    /**
     * Constructor used by child classes to
     * initialize common room attributes.
     * * @param numberOfBeds number of beds in the room
     * @param squareFeet total room size
     * @param pricePerNight cost per night
     */
    public Room(int numberOfBeds, int squareFeet, double pricePerNight) {
        this.numberOfBeds = numberOfBeds;
        this.squareFeet = squareFeet;
        this.pricePerNight = pricePerNight;
    }

    /** Displays room details. */
    public void displayRoomDetails() {
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + squareFeet + " sqft");
        System.out.println("Price per night: " + pricePerNight);
    }
}

/**
 * ====================================================================
 * CLASS - SingleRoom
 * ====================================================================
 * * Represents a single room in the hotel.
 * * @version 2.0
 */
class SingleRoom extends Room {

    /**
     * Initializes a SingleRoom with
     * predefined attributes.
     */
    public SingleRoom() {
        super(1, 250, 1500.0);
    }
}

/**
 * ====================================================================
 * CLASS - DoubleRoom
 * ====================================================================
 * * Represents a double room in the hotel.
 * * @version 2.0
 */
class DoubleRoom extends Room {

    /**
     * Initializes a DoubleRoom with
     * predefined attributes.
     */
    public DoubleRoom() {
        super(2, 400, 2500.0);
    }
}

/**
 * ====================================================================
 * CLASS - SuiteRoom
 * ====================================================================
 * * Represents a suite room in the hotel.
 * * @version 2.0
 */
class SuiteRoom extends Room {

    /**
     * Initializes a SuiteRoom with
     * predefined attributes.
     */
    public SuiteRoom() {
        super(3, 750, 5000.0);
    }
}





/**
 * ====================================================================
 * CLASS - RoomInventory
 * ====================================================================
 *
 * Use Case 3: Centralized Room Inventory Management
 *
 * Description:
 * This class acts as the single source of truth
 * for room availability in the hotel.
 *
 * Room pricing and characteristics are obtained
 * from Room objects, not duplicated here.
 *
 * This avoids multiple sources of truth and
 * keeps responsibilities clearly separated.
 *
 * @version 3.0
 */
class RoomInventory {

    /**
     * Stores available room count for each room type.
     *
     * Key   -> Room type name
     * Value -> Available room count
     */
    private Map<String, Integer> roomAvailability;

    /**
     * Constructor initializes the inventory
     * with default availability values.
     */
    public RoomInventory() {
        this.roomAvailability = new HashMap<>();
        initializeInventory();
    }

    /**
     * Initializes room availability data.
     *
     * This method centralizes inventory setup
     * instead of using scattered variables.
     */
    private void initializeInventory() {
        roomAvailability.put("Single Room", 5);
        roomAvailability.put("Double Room", 3);
        roomAvailability.put("Suite Room", 2);
    }

    /**
     * Returns the current availability map.
     *
     * @return map of room type to available count
     */
    public Map<String, Integer> getRoomAvailability() {
        return roomAvailability;
    }

    /**
     * Updates availability for a specific room type.
     *
     * @param roomType the room type to update
     * @param count new availability count
     */
    public void updateAvailability(String roomType, int count) {
        roomAvailability.put(roomType, count);
    }
}


/**
 * ====================================================================
 * CLASS - RoomSearchService
 * ====================================================================
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Description:
 * This class provides search functionality
 * for guests to view available rooms.
 *
 * It reads room availability from inventory
 * and room details from Room objects.
 *
 * No inventory mutation or booking logic
 * is performed in this class.
 *
 * @version 4.0
 */
class RoomSearchService {

    /**
     * Displays available rooms along with
     * their details and pricing.
     *
     * This method performs read-only access
     * to inventory and room data.
     *
     * @param inventory centralized room inventory
     * @param singleRoom single room definition
     * @param doubleRoom double room definition
     * @param suiteRoom suite room definition
     */
    public void searchAvailableRooms(
            RoomInventory inventory,
            Room singleRoom,
            Room doubleRoom,
            Room suiteRoom) {

        Map<String, Integer> availability = inventory.getRoomAvailability();

        // Check and display Single Room availability
        if (availability.get("Single Room") != null && availability.get("Single Room") > 0) {
            System.out.println("Single Room:");
            singleRoom.displayRoomDetails();
            System.out.println("Available: " + availability.get("Single Room") + "\n");
        }

        // Check and display Double Room availability
        if (availability.get("Double Room") != null && availability.get("Double Room") > 0) {
            System.out.println("Double Room:");
            doubleRoom.displayRoomDetails();
            System.out.println("Available: " + availability.get("Double Room") + "\n");
        }

        // Check and display Suite Room availability
        if (availability.get("Suite Room") != null && availability.get("Suite Room") > 0) {
            System.out.println("Suite Room:");
            suiteRoom.displayRoomDetails();
            System.out.println("Available: " + availability.get("Suite Room"));
        }
    }
}

/**
 * ====================================================================
 * MAIN CLASS - UseCase4RoomSearch
 * ====================================================================
 *
 * Use Case 4: Room Search & Availability Check
 *
 * Description:
 * This class demonstrates how guests
 * can view available rooms without
 * modifying inventory data.
 *
 * The system enforces read-only access
 * by design and usage discipline.
 *
 * @version 4.0
 */

/**
 * ====================================================================
 * CLASS - Reservation
 * ====================================================================
 *
 * Use Case 5: Booking Request (FIFO)
 *
 * Description:
 * This class represents a booking request
 * made by a guest.
 *
 * At this stage, a reservation only captures
 * intent, not confirmation or room allocation.
 *
 * @version 5.0
 */
class Reservation {

    /** Name of the guest making the booking. */
    private String guestName;

    /** Requested room type. */
    private String roomType;

    /**
     * Creates a new booking request.
     *
     * @param guestName name of the guest
     * @param roomType requested room type
     */
    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    /** @return guest name */
    public String getGuestName() {
        return guestName;
    }

    /** @return requested room type */
    public String getRoomType() {
        return roomType;
    }
}


/**
 * ====================================================================
 * CLASS - BookingRequestQueue
 * ====================================================================
 *
 * Use Case 5: Booking Request (FIFO)
 *
 * Description:
 * This class manages booking requests
 * using a queue to ensure fair allocation.
 *
 * Requests are processed strictly
 * in the order they are received.
 *
 * @version 5.0
 */
class BookingRequestQueue {

    /** Queue that stores booking requests. */
    private Queue<Reservation> requestQueue;

    /** Initializes an empty booking queue. */
    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    /**
     * Adds a booking request to the queue.
     *
     * @param reservation booking request
     */
    public void addRequest(Reservation reservation) {
        requestQueue.offer(reservation);
    }

    /**
     * Retrieves and removes the next
     * booking request from the queue.
     *
     * @return next reservation request
     */
    public Reservation getNextRequest() {
        return requestQueue.poll();
    }

    /**
     * Checks whether there are
     * pending booking requests.
     *
     * @return true if queue is not empty
     */
    public boolean hasPendingRequests() {
        return !requestQueue.isEmpty();
    }
}


/**
 * ====================================================================
 * MAIN CLASS - UseCase5BookingRequestQueue
 * ====================================================================
 *
 * Use Case 5: Booking Request (First-Come-First-Served)
 *
 * Description:
 * This class demonstrates how booking
 * requests are accepted and queued
 * in a fair and predictable order.
 *
 * No room allocation or inventory
 * update is performed here.
 *
 * @version 5.0
 */


/**
 * ====================================================================
 * CLASS - BookingService
 * ====================================================================
 *
 * Use Case 6: Room Booking Process
 *
 * Description:
 * This class handles the actual room reservation logic.
 * It verifies room availability and updates the centralized
 * inventory upon successful booking.
 *
 * @version 6.0
 */
class BookingService {

    /**
     * Processes a single booking request by checking availability
     * and decrementing the inventory if a room is available.
     *
     * @param inventory the centralized room inventory
     * @param reservation the pending booking request
     */
    public void processBooking(RoomInventory inventory, Reservation reservation) {
        String roomType = reservation.getRoomType();
        String guestName = reservation.getGuestName();
        Map<String, Integer> availabilityMap = inventory.getRoomAvailability();

        System.out.println("Processing booking for Guest: " + guestName + ", Room Type: " + roomType);

        // Check if the requested room type exists and has available inventory
        if (availabilityMap.containsKey(roomType) && availabilityMap.get(roomType) > 0) {

            // Decrement the available room count by 1
            int currentAvailability = availabilityMap.get(roomType);
            inventory.updateAvailability(roomType, currentAvailability - 1);

            System.out.println("Booking confirmed for " + guestName + " in a " + roomType);
        } else {
            // Handle scenario where room is unavailable
            System.out.println("Booking failed for " + guestName + ". " + roomType + " is fully booked.");
        }
    }
}

/**
 * ====================================================================
 * MAIN CLASS - UseCase6BookingProcess
 * ====================================================================
 *
 * Use Case 6: Room Booking Process
 *
 * Description:
 * This class demonstrates the complete reservation lifecycle.
 * It queues booking requests, processes them in FIFO order,
 * and actively updates the inventory.
 *
 * @version 6.0
 */
public class BookMyStay {

    /**
     * Application entry point.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("Booking Process\n");

        // 1. Initialize central inventory
        RoomInventory inventory = new RoomInventory();

        // 2. Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // 3. Create and enqueue booking requests
        // Note: Room types must perfectly match the keys in RoomInventory
        bookingQueue.addRequest(new Reservation("Abhi", "Single Room"));
        bookingQueue.addRequest(new Reservation("Subha", "Double Room"));
        bookingQueue.addRequest(new Reservation("Vanmathi", "Suite Room"));

        // 4. Initialize booking service
        BookingService bookingService = new BookingService();

        // 5. Process all pending requests in FIFO order
        while (bookingQueue.hasPendingRequests()) {
            Reservation currentRequest = bookingQueue.getNextRequest();
            bookingService.processBooking(inventory, currentRequest);
        }
    }
}