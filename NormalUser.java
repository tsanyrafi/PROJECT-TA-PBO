import java.util.ArrayList;
import java.util.List;

public class NormalUser extends User {
    public List<Event> registeredEvents = new ArrayList<>();

    public NormalUser(String username, String password) {
        super(username, password);
    }

    public void registerEvent(Event event) {
        if (event.tickets > 0 && event.status.equalsIgnoreCase("active")) {
            registeredEvents.add(event);
            event.tickets -= 1;
        } else {
            System.out.println("Unable to register. Tickets unavailable or event inactive.");
        }
    }
}
