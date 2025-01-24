import java.util.List;

public class Admin extends User {

    public Admin(String username, String password) {
        super(username, password);
    }

    public void addEvent(Event event, List<Event> eventList) {
        eventList.add(event);
    }

    public void editEvent(Event event, String name, String category, int tickets, String status) {
        event.name = name;
        event.category = category;
        event.tickets = tickets;
        event.status = status;
    }

    public void viewOrders(List<Order> orders) {
        for (Order order : orders) {
            System.out.println(order);
        }
    }
}
