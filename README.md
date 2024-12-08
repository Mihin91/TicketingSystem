Ticketing System Simulation

Welcome to the Ticketing System Simulation! This project is a comprehensive simulation of a ticketing system, encompassing a Command-Line Interface (CLI), a robust Backend powered by Spring Boot, and a dynamic Frontend built with React.
Whether you're looking to understand multi-threaded applications, RESTful APIs, or modern frontend development, this project has something for you.

1. Table of Contents
2. Ticketing System Simulation

Table of Contents
  Features
  Technologies Used
  Project Structure
  Getting Started
  Prerequisites
  Installation
    1. Clone the Repository
    2. Setup Backend
    3. Setup Frontend
    4. Setup CLI
       
  Usage
    1. Running the Backend
    2. Running the Frontend
    3. Using the CLI
     
  API Documentation
    1. Configuration APIs
    2. System Control APIs
    3. Log APIs
    4. Vendor APIs
    5. Customer APIs
    
  Integration
    1. WebSockets
    2. Database
    
  Contact


Command-Line Interface (CLI):

  Configuration Management: Configure simulation parameters interactively.
  Persistence: Save and load configurations from JSON files.
  Control: Start and stop the simulation seamlessly.
  Validation: Ensures input parameters are valid and consistent.
  
Backend:

  Spring Boot Framework: Robust and scalable backend services.
  RESTful APIs: Manage configurations, vendors, customers, and logs.
  WebSockets (STOMP): Real-time communication for status updates and logs.
  In-Memory H2 Database: Quick setup for development and testing.
  Thread Management: Handles concurrent operations for vendors and customers.
  Event-Driven Architecture: Automatically stops simulation when all tickets are sold.
  
Frontend:

  React Application: Dynamic and responsive user interface.
  Real-Time Updates: Utilizes WebSockets for live status and log updates.
  User-Friendly Dashboard: Monitor and control the simulation with ease.
  Responsive Design: Accessible on various devices and screen sizes.
  Interactive Components: Configuration forms, status displays, and log viewers.
  
🛠️ Technologies Used

CLI:
  Java 11: High-performance programming language.
  Gson: JSON serialization and deserialization.
  
Backend:
  Spring Boot: Framework for building Java applications.
  H2 Database: In-memory database for development and testing.
  WebSockets (STOMP): Real-time communication.
  ModelMapper: Simplifies object mapping.
  JUnit: For unit and integration testing.
  Maven: Dependency and build management.
  
Frontend:
  React: JavaScript library for building user interfaces.
  StompJS & SockJS: For WebSocket communication.
  CSS: Styling the application.
  React Router: Navigation within the application.
  
General:
  Git: Version control.
  GitHub: Repository hosting and collaboration.
  VS Code / IntelliJ IDEA: Recommended IDEs.
  
🔍 Project Structure
  The project is divided into three main components:

  CLI
  Backend
  Frontend
    1. CLI
      Located in the CLI directory, this component allows users to configure and manage the ticketing simulation via the command line.

  Key Components:

  Configuration.java: Handles configuration parameters and JSON file operations.
  Customer.java: Represents customer participants in the simulation.
  Vendor.java: Represents vendor participants in the simulation.
  TicketPool.java: Manages the pool of tickets with thread-safe operations.
  Logger.java: Centralized logging utility.
  Participant.java: Abstract class for participants.
  TicketingSystem.java: Main class to run the simulation.

  2. Backend
    Located in the Backend directory, this is a Spring Boot application that manages the simulation logic, RESTful APIs, WebSockets, and database interactions.

  Key Components:
  
  Controllers: Handle API requests and WebSocket messages.
  ConfigurationController.java
  VendorController.java
  CustomerController.java
  LogController.java
  SystemController.java
  TicketController.java
  Services: Contain business logic and interact with repositories.
  ConfigurationService.java
  VendorService.java
  CustomerService.java
  TicketPoolService.java
  TicketService.java
  LogService.java
  Repositories: Interface with the H2 database.
  ConfigurationRepository.java
  VendorRepository.java
  CustomerRepository.java
  TicketRepository.java
  Models: Define the data structures.
  Configuration.java
  Vendor.java
  Customer.java
  Ticket.java
  Runnables: Handle concurrent operations.
  VendorRunnable.java
  CustomerRunnable.java
  Config: Configuration classes for CORS, WebSockets, and Security.
  CorsConfig.java
  WebSocketConfig.java
  SecurityConfig.java
  ModelMapperConfig.java
  Events: Custom events.
  TicketsSoldOutEvent.java
  
  3. Frontend
    Located in the Frontend directory, this is a React application that provides a user interface to interact with the simulation.

  Key Components:

  Components:
  
  ConfigurationForm.jsx: Form to create and save configurations.
  ConfigurationDisplay.jsx: Displays saved configurations.
  ControlPanel.jsx: Controls to start and stop the simulation.
  TicketStatus.jsx: Displays the current status of tickets.
  LogDisplay.jsx: Shows real-time logs.
  Navbar.jsx: Navigation bar.
  
  Pages:
  ConfigPage.jsx: Page for managing configurations.
  DashboardPage.jsx: Main dashboard for monitoring and control.
  
  Utilities:
  ApiClient.jsx: Handles API requests.
  Styling:
  index.css: Global styles.

Usage
  1. Running the Backend
    Ensure that the backend server is running by following the Setup Backend steps. The backend provides RESTful APIs and WebSocket endpoints for real-time communication.
  
  2. Running the Frontend
    Start the frontend React application as described in the Setup Frontend section. Access the dashboard and configuration pages to interact with the simulation.

  3. Using the CLI
    The CLI allows you to configure and manage the ticketing simulation.

  Load Configuration: Load existing configurations from a JSON file.
  Save Configuration: Save current settings to a JSON file for future use.
  Start Simulation: Initiate the simulation with the configured parameters.
  Stop Simulation: Gracefully terminate the simulation.
  Display Status: View the current status of tickets, vendors, and customers.

API Documentation
  The backend provides several RESTful APIs for managing configurations, vendors, customers, and logs.

  Configuration APIs
  Manage simulation configurations.

  Save Configuration

  Endpoint: POST /api/configurations/save
  Description: Saves a new configuration.
  Request Body: ConfigurationDTO
  Response: Saved ConfigurationDTO

  Get Latest Configuration

  Endpoint: GET /api/configurations/latest
  Description: Retrieves the most recent configuration.
  Response: Latest ConfigurationDTO
  Get All Configurations

  Endpoint: GET /api/configurations/all
  Description: Retrieves all saved configurations.
  Response: Array of ConfigurationDTO
  Delete Configuration

  Endpoint: DELETE /api/configurations/delete/{id}
  Description: Deletes a configuration by ID.
  Response: Success message or 404 if not found.
  System Control APIs
  Control the simulation lifecycle.

  Start Simulation

  Endpoint: POST /api/system/start
  Description: Starts the ticketing simulation based on the latest configuration.
  Response: Success message or error if no configuration is found.
  Stop Simulation

  Endpoint: POST /api/system/stop
  Description: Stops the ticketing simulation.
  Response: Success message.
  Log APIs
  Retrieve simulation logs.
  
  Get Logs
  Endpoint: GET /api/logs
  Description: Retrieves all logs generated during the simulation.
  Response: Array of log strings.
  Vendor APIs
  Manage vendors in the simulation.
  
  Start Vendor
  Endpoint: POST /api/vendors/start
  Description: Starts a vendor with specified parameters.
  Request Body: VendorDTO
  Response: Success message.

  Customer APIs
  Manage customers in the simulation.
  
  Start Customer
  Endpoint: POST /api/customers/start
  Description: Starts a customer with specified parameters.
  Request Body: CustomerDTO
  Response: Success message.


Integration
  WebSockets
    The backend uses WebSockets with STOMP protocol to provide real-time updates to the frontend.

  WebSocket Endpoint: http://localhost:8080/ws
  
  Topics Subscribed:
    /topic/tickets/status: Receives updates on ticket status.
    /topic/logs: Receives real-time log messages.
    
  Frontend Integration:
  
  Utilizes StompJS and SockJS to establish WebSocket connections.
  Subscribes to relevant topics to receive live data.
  
  Database
  An in-memory H2 database is used for development and testing purposes.

  Access H2 Console: http://localhost:8080/h2-console
  JDBC URL: jdbc:h2:mem:Ticketsdb
  Username: sa
  Password: (leave blank)
  
  Entities:
  
  Configuration: Stores simulation parameters.
  Vendor: Represents vendors releasing tickets.
  Customer: Represents customers purchasing tickets.
  Ticket: Represents individual tickets and their statuses.





 Contact
Your Name - @Mihin91 - mihintabeywickrama@gmail.com

Project Link: https://github.com/Mihin91/ticketing-system



