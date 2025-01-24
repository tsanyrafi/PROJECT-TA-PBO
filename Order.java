public class Order {
    public String user;
    public Event event;

    public Order(String user, Event event) {
        this.user = user;
        this.event = event;
    }

    @Override
    public String toString() {
        return "User: " + user + ", Event: " + event.name;
    }
}
