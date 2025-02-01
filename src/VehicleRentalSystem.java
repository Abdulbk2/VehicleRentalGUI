import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

// Base class: Vehicle
abstract class Vehicle {
    protected String brand;
    protected String model;
    protected double rentalRatePerDay;

    public Vehicle(String brand, String model, double rentalRatePerDay) {
        this.brand = brand;
        this.model = model;
        this.rentalRatePerDay = rentalRatePerDay;
    }

    public abstract double calculateRentalCost(int days);

    @Override
    public String toString() {
        return brand + " " + model + " ($" + rentalRatePerDay + "/day)";
    }
}

// Car subclass
class Car extends Vehicle {
    private boolean luxury;

    public Car(String brand, String model, double rentalRatePerDay, boolean luxury) {
        super(brand, model, rentalRatePerDay);
        this.luxury = luxury;
    }

    @Override
    public double calculateRentalCost(int days) {
        return luxury ? rentalRatePerDay * days * 1.2 : rentalRatePerDay * days; // Luxury cars cost 20% more
    }
}

// Bike subclass
class Bike extends Vehicle {
    private boolean helmetIncluded;

    public Bike(String brand, String model, double rentalRatePerDay, boolean helmetIncluded) {
        super(brand, model, rentalRatePerDay);
        this.helmetIncluded = helmetIncluded;
    }

    @Override
    public double calculateRentalCost(int days) {
        return rentalRatePerDay * days;
    }
}

// Main GUI Application
public class VehicleRentalSystem extends JFrame {
    private ArrayList<Vehicle> vehicles = new ArrayList<>();
    private JTextArea receiptArea;
    private JComboBox<String> vehicleComboBox;
    private JTextField daysField;
    private JTextField nameField;
    private JTextField dateField;

    // Database connection details
    final String DB_URL = "jdbc:mysql://127.0.0.1:3306/vehiclerental_schema";
    final String DB_USER = "root";
    final String DB_PASSWORD = "Abdul@4293"; //  MySQL password

    public VehicleRentalSystem() {
        // Initialize vehicles
        vehicles.add(new Car("Toyota", "Camry", 50, false));
        vehicles.add(new Car("Mercedes", "C-Class", 120, true));
        vehicles.add(new Bike("Yamaha", "MT-07", 30, true));
        vehicles.add(new Car("Honda", "CBR500R", 35, false));
        vehicles.add(new Car("PROTON", "PR41", 45, false));
        vehicles.add(new Car("FERARRI", "HURICAN", 150, false));
        vehicles.add(new Bike("SYM", "SPORT 110E", 35, true));
        vehicles.add(new Car("PROTON", "SAGA", 35, false));
        vehicles.add(new Car("PERODUA", "AXIA", 37, false));
        vehicles.add(new Car("PERODUA", "MYVI", 33, false));

        // Set up the GUI
        setTitle("Vehicle Rental System");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        ImageIcon image = new ImageIcon("background.jpg");
        setIconImage(image.getImage());

        // Tabbed Pane with custom color
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(new Color(30, 144, 255)); // Set menu bar color (light blue)

        // Home Tab with Background Image
        JPanel homePanel = new JPanel() {
            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundImage = new ImageIcon("background.jpg"); // Add your image file
                g.drawImage(backgroundImage.getImage(), 400
                        , 500, getWidth(), getHeight(), this);

            }
        };
        homePanel.setLayout(new BorderLayout());
        JLabel welcomeLabel = new JLabel("<html><h1 style='color:blacks;'>Welcome to ABDUl $ CO  Vehicle Rental System</h1></html>", JLabel.CENTER);
        welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
        welcomeLabel.setVerticalAlignment(JLabel.TOP);
        homePanel.add(welcomeLabel, BorderLayout.NORTH);

        JTextArea infoArea = new JTextArea("Choose from a variety of cars and bikes to rent.\n\nFeatures:\n- View available vehicles\n- Calculate rental costs\n- Generate a receipt");
        infoArea.setEditable(false);
        infoArea.setBackground(new Color(191, 95, 95, 200)); // Semi-transparent background
        infoArea.setMargin(new Insets(10, 10, 10, 10));
        homePanel.add(new JScrollPane(infoArea), BorderLayout.CENTER);
        tabbedPane.addTab("Home", homePanel);

        // Form Tab
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridLayout(5, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel vehicleLabel = new JLabel("Select Vehicle:");
        vehicleComboBox = new JComboBox<>();
        for (Vehicle v : vehicles) {
            vehicleComboBox.addItem(v.toString());
        }

        JLabel nameLabel = new JLabel("Full Name:");
        nameField = new JTextField();


        JLabel daysLabel = new JLabel("Number of Days:");
        daysField = new JTextField();

        JLabel dateLabel = new JLabel("Rental Date:");
        dateField = new JTextField();


        JButton calculateButton = new JButton("Calculate Cost");
        calculateButton.addActionListener(new CalculateCostListener());

        formPanel.add(vehicleLabel);
        formPanel.add(vehicleComboBox);
        formPanel.add(nameLabel);
        formPanel.add(daysLabel);
        formPanel.add(nameField);
        formPanel.add(daysField);
        formPanel.add(dateLabel);
        formPanel.add(dateField);
        formPanel.add(new JLabel());
        formPanel.add(calculateButton);
        tabbedPane.addTab("Form", formPanel);

        // Receipt Tab
        JPanel receiptPanel = new JPanel();
        receiptPanel.setLayout(new BorderLayout());
        receiptArea = new JTextArea(10, 20);
        receiptArea.setEditable(false);
        receiptPanel.add(new JScrollPane(receiptArea), BorderLayout.CENTER);
        tabbedPane.addTab("Receipt", receiptPanel);

        add(tabbedPane);
    }

    // Action Listener for Calculate Cost Button
    private class CalculateCostListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
                int selectedIndex = vehicleComboBox.getSelectedIndex();
                int days = Integer.parseInt(daysField.getText());
                String fullName = nameField.getText().trim();
                String date = dateField.getText().trim() ;

                if (fullName.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please enter your full name.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (days <= 0) {
                    JOptionPane.showMessageDialog(null, "Number of days must be positive!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                   if (date.isEmpty() ) {
                       JOptionPane.showMessageDialog(null, "Please enter the date.", "Error", JOptionPane.ERROR_MESSAGE);
                       return;
                   }

                Vehicle selectedVehicle = vehicles.get(selectedIndex);
                double cost = selectedVehicle.calculateRentalCost(days);

                // Save to database
                String insertSQL = "INSERT INTO rentals (full_name, vehicle, number_of_days, total_cost, rental_date) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement stmt = conn.prepareStatement(insertSQL)) {
                    stmt.setString(1, fullName);
                    stmt.setString(2, selectedVehicle.toString());
                    stmt.setInt(3, days);
                    stmt.setDouble(4, cost);
                    stmt.setString(5,date);
                    stmt.executeUpdate();
                }

                receiptArea.setText("Receipt\n-----------------------------\n" +
                        "Full Name: " + fullName + "\n" + // Include Full Name in Receipt
                        "Vehicle Rented: " + selectedVehicle + "\n" +
                        "Number of Days: " + days + "\n" +
                        "Rental date: " + date + "\n" +
                        "Total Rental Cost: $" + cost + "\n-----------------------------\n");
                JOptionPane.showMessageDialog(null, "Rental Cost Calculated! Check Receipt Tab.", "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number of days.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

        }
    }

    // Main Method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VehicleRentalSystem gui = new VehicleRentalSystem();
            gui.setVisible(true);
        });

        // For Our Database Connection Mysql Server

    }
}
