import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        List<Event> eventList = new ArrayList<>();
        List<Order> orderList = new ArrayList<>();
        List<Admin> adminList = new ArrayList<>();
        List<NormalUser> userList = new ArrayList<>();

        // Tambahkan admin dan user ke dalam sistem
        adminList.add(new Admin("admin1", "admin1"));
        adminList.add(new Admin("admin2", "admin2"));
        userList.add(new NormalUser("rafi", "rafi"));
        userList.add(new NormalUser("tsany", "tsany"));

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Event System - Konser Musik");
            frame.setSize(500, 500);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 100, 100));
            JButton loginButton = new JButton("Login");

            buttonPanel.add(loginButton);
            frame.add(buttonPanel, BorderLayout.CENTER);

            loginButton.addActionListener(e -> loginDialog(adminList, userList, eventList, orderList));

            frame.setVisible(true);
        });
    }

    private static void loginDialog(List<Admin> adminList, List<NormalUser> userList, List<Event> eventList, List<Order> orderList) {
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();

        Object[] message = {
            "Username:", usernameField,
            "Password:", passwordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            Admin admin = adminList.stream().filter(a -> a.username.equals(username) && a.authenticate(password)).findFirst().orElse(null);
            NormalUser user = userList.stream().filter(u -> u.username.equals(username) && u.authenticate(password)).findFirst().orElse(null);

            if (admin != null) {
                showAdminPanel(admin, eventList, orderList);
            } else if (user != null) {
                showUserPanel(user, eventList, orderList);
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password.");
            }
        }
    }

    private static void showAdminPanel(Admin admin, List<Event> eventList, List<Order> orderList) {
        JFrame adminFrame = new JFrame("Admin Panel - Konser Musik");
        adminFrame.setSize(500, 400);
        adminFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        adminFrame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton addEventButton = new JButton("Add Event");
        JButton viewEventsButton = new JButton("View Events");
        JButton viewOrdersButton = new JButton("View Orders");

        buttonPanel.add(addEventButton);
        buttonPanel.add(viewEventsButton);
        buttonPanel.add(viewOrdersButton);

        adminFrame.add(buttonPanel, BorderLayout.CENTER);

        addEventButton.addActionListener(e -> addEvent(admin, eventList));
        viewEventsButton.addActionListener(e -> viewEvents(eventList));
        viewOrdersButton.addActionListener(e -> viewOrders(orderList));

        adminFrame.setVisible(true);
    }

    private static void showUserPanel(NormalUser user, List<Event> eventList, List<Order> orderList) {
        JFrame userFrame = new JFrame("User Panel - Konser Musik");
        userFrame.setSize(500, 400);
        userFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        userFrame.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton viewEventsButton = new JButton("View Events");
        JButton registerEventButton = new JButton("Register for Event");

        buttonPanel.add(viewEventsButton);
        buttonPanel.add(registerEventButton);

        userFrame.add(buttonPanel, BorderLayout.CENTER);

        viewEventsButton.addActionListener(e -> viewEvents(eventList));
        registerEventButton.addActionListener(e -> registerEvent(user, eventList, orderList));

        userFrame.setVisible(true);
    }

    private static void addEvent(Admin admin, List<Event> eventList) {
        JTextField nameField = new JTextField();
        JTextField categoryField = new JTextField();
        JTextField ticketsField = new JTextField();
        JTextField statusField = new JTextField();
        JTextField dateField = new JTextField();

        Object[] message = {
            "Nama Event (Konser Musik):", nameField,
            "Kategori:", categoryField,
            "Tiket", ticketsField,
            "Status (Order/Pre-Order):", statusField,
            "Event Date (yyyy-MM-dd):", dateField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Tambah Event", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                String name = nameField.getText();
                String category = categoryField.getText();
                int tickets = Integer.parseInt(ticketsField.getText());
                String status = statusField.getText();
                Date eventDate = java.sql.Date.valueOf(dateField.getText());

                Event event = new Event(name, category, tickets, status, eventDate);
                admin.addEvent(event, eventList);
                JOptionPane.showMessageDialog(null, "Sukses Menambah Event.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Invalid input.");
            }
        }
    }

    private static void viewEvents(List<Event> eventList) {
        StringBuilder eventInfo = new StringBuilder("Events:\n");
        for (Event event : eventList) {
            eventInfo.append(event.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, eventInfo.toString());
    }

    private static void viewOrders(List<Order> orderList) {
        StringBuilder orderInfo = new StringBuilder("Order:\n");
        for (Order order : orderList) {
            orderInfo.append(order.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, orderInfo.toString());
    }

    private static void registerEvent(NormalUser user, List<Event> eventList, List<Order> orderList) {
        String eventName = JOptionPane.showInputDialog("Enter the name of the event to register:");
        Event eventToRegister = null;
        for (Event event : eventList) {
            if (event.name.equalsIgnoreCase(eventName)) {
                eventToRegister = event;
                break;
            }
        }

        if (eventToRegister != null) {
            user.registerEvent(eventToRegister);
            orderList.add(new Order(user.username, eventToRegister));
            JOptionPane.showMessageDialog(null, "Successfully registered for the event.");
        } else {
            JOptionPane.showMessageDialog(null, "Event not found.");
        }
    }
}
