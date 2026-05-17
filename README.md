# Vehicle Rental GUI

A Java-based graphical user interface application for managing vehicle rental operations. This project demonstrates Object-Oriented Programming (OOP) principles applied to a practical business domain.

## Overview

This Vehicle Rental GUI is a desktop application built with Java that provides a user-friendly interface for managing vehicle rentals, bookings, and customer information. The application showcases key OOP concepts including encapsulation, inheritance, polymorphism, and abstraction.

## Features

- **Vehicle Management**: Add, view, update, and delete vehicle records
- **Customer Management**: Maintain customer information and rental history
- **Rental Operations**: Create and manage vehicle rental bookings
- **User-Friendly Interface**: Intuitive GUI built with Java Swing/AWT
- **Data Persistence**: Store and retrieve rental and customer data

## Technology Stack

- **Language**: Java
- **GUI Framework**: Java Swing / AWT
- **Architecture**: Object-Oriented Programming (OOP)

## Project Structure

```
VehicleRentalGUI/
├── src/
│   ├── models/          # Data models (Vehicle, Customer, Rental, etc.)
│   ├── views/           # GUI components and windows
│   ├── controllers/     # Business logic and event handling
│   └── utils/           # Utility classes and helpers
├── README.md
└── [other project files]
```

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- An IDE such as IntelliJ IDEA, Eclipse, or NetBeans (optional but recommended)

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/Abdulbk2/VehicleRentalGUI.git
   cd VehicleRentalGUI
   ```

2. Compile the Java files:
   ```bash
   javac -d bin src/**/*.java
   ```

3. Run the application:
   ```bash
   java -cp bin [MainClassName]
   ```

   *Note: Replace `[MainClassName]` with the name of your main class that starts the application.*

## Usage

1. Launch the application using the steps above
2. Navigate through the GUI using menu options and buttons
3. Use the provided forms to:
   - Add new vehicles to the rental fleet
   - Register customer information
   - Create and manage rental bookings
   - View rental history and reports

## OOP Principles Demonstrated

- **Encapsulation**: Private attributes with public getter/setter methods
- **Inheritance**: Base classes for vehicles and customers with specialized subclasses
- **Polymorphism**: Method overriding for different vehicle types
- **Abstraction**: Abstract classes and interfaces for common behaviors

## Contributing

Contributions are welcome! If you'd like to improve this project:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is open source and available under the [MIT License](LICENSE) (or specify your preferred license).

## Author & Contributor

**Abdulbk2** - Initial project creation
**Usamnalyt** - Contributor 
**DanMadina** - Contributor
**Dosheero**  - Contributor

## Contact

For questions or feedback about this project, please open an [issue](https://github.com/Abdulbk2/VehicleRentalGUI/issues) on the GitHub repository.

## Acknowledgments

- This project was created as an OOP mini-project to demonstrate core programming concepts
- Thanks to the Java community for excellent documentation and resources

---

**Last Updated**: May 2026
