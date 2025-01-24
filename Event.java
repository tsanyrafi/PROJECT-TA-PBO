import java.util.Date;

public class Event {
    public String name;
    public String category;
    public int tickets;
    public String status;
    public Date eventDate;

    public Event(String name, String category, int tickets, String status, Date eventDate) {
        this.name = name;
        this.category = category;
        this.tickets = tickets;
        this.status = status;
        this.eventDate = eventDate;
    }

    @Override
    public String toString() {
        return "Event Name: " + name + ", Category: " + category + ", Tickets: " + tickets + ", Status: " + status;
    }
}
