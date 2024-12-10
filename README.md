# Ticketing System Simulation

Table of Contents
 1. Overview
 2. Features
 3. Technologies Used
 4. Prerequisites
 5. Installation and Setup
    Backend Setup
    Frontend Setup
 6. Running the Application
 7. Usage
    Configuration
    Starting the Simulation
    Stopping the Simulation
    Resetting the Simulation
 8. Project Structure
    CLI Sturture
    Backend Structure
    Frontend Structure
 9. License
 10. Contact


Overview

The Ticketing System Simulation is a robust application designed to emulate the process of ticket distribution and purchasing. It leverages a Java Spring Boot backend and a React.js frontend to provide a seamless experience for configuring simulations, monitoring real-time ticket statuses, and managing system logs. The system is capable of handling multiple vendors releasing tickets and multiple customers purchasing them concurrently, ensuring accurate tracking and logging throughout the simulation lifecycle.

Features

Configuration Management: Define simulation parameters such as the number of vendors and customers, ticket release rates, retrieval intervals, maximum ticket capacities, and total tickets.
Real-Time Monitoring: View live updates on ticket statuses, including current tickets available, total tickets released, and total tickets purchased.
Logging: Monitor detailed logs of vendor activities (ticket releases) and customer actions (ticket purchases) in real-time.
Control Panel: Start, stop, and reset the simulation with ease.
State Preservation: Maintain the state of ticket counts and logs across simulation runs without unintended resets.
WebSocket Integration: Utilize WebSockets (STOMP over SockJS) for efficient real-time communication between backend and frontend.
Security: Configure security settings to protect endpoints, especially the H2 console during development.

Technologies Used

Backend

Java Spring Boot: Framework for building the backend services.
Spring Data JPA: For data persistence and repository management.
Spring WebSocket: Facilitates real-time communication using STOMP over SockJS.
H2 Database: In-memory database for development and testing purposes.
Maven: Dependency and build management.

Frontend

React.js: Library for building the user interface.
STOMP.js & SockJS-client: For handling WebSocket connections.
React Router: Manages routing within the application.
CSS-in-JS: Styling components using JavaScript.
Prerequisites

git clone https://github.com/yourusername/ticketing-system-simulation.git
cd ticketing-system-simulation/backend
Install Dependencies and Build:


mvn clean install
Configure Application Properties:


Server port
server.port=8080

H2 Console Configuration
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

Database Configuration
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

JPA Configuration
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
Run the Backend Application:


mvn spring-boot:run
The backend server will start on http://localhost:8080.

Frontend Setup
Navigate to Frontend Directory:

cd ../frontend
Install Dependencies:

Using npm:

npm install



Run the Frontend Application:

Using npm:

npm start


The frontend will start on http://localhost:3000 and should automatically open in your default browser.

Running the Application
Ensure Both Backend and Frontend Are Running:

Backend: http://localhost:8080
Frontend: http://localhost:3000
Access the Dashboard:

Open your browser and navigate to http://localhost:3000/dashboard to access the simulation dashboard.

#Usage

Configuration

Navigate to Configuration Page:

Use the Navbar to go to the Configuration page.

Define Simulation Parameters:

Number of Vendors: Specify how many vendors will release tickets.
Number of Customers: Specify how many customers will purchase tickets.
Ticket Release Rate (ms): Interval at which each vendor releases a ticket.
Customer Retrieval Interval (ms): Interval at which each customer attempts to purchase a ticket.
Maximum Ticket Capacity: Maximum number of tickets that can be held in the pool at any given time.
Total Tickets: Total number of tickets to be released during the simulation.
Save Configuration:

After entering all parameters, save the configuration to persist it in the database.

Starting the Simulation
Navigate to Dashboard:

Go to the Dashboard page via the Navbar.

Start the Simulation:

Click the Start button in the Control Panel.
Vendors will begin releasing tickets at the defined intervals.
Customers will start attempting to purchase tickets based on their retrieval intervals.
Monitor Real-Time Status:

Ticket Status: View the number of current tickets available, total tickets released, and total tickets purchased.
Logs: Observe real-time logs detailing vendor releases and customer purchases.
Stopping the Simulation
Stop the Simulation:

Click the Stop button in the Control Panel.
This will interrupt all running vendor and customer threads.
Ticket counts and logs up to the point of stopping will remain preserved.
Restarting the Simulation:

Click the Start button again.
The simulation will resume from where it was stopped, continuing to release and purchase tickets without resetting counts.
Resetting the Simulation
Reset the Simulation:

Click the Reset button in the Control Panel.
This will perform the following actions:
Stop all running vendor and customer threads.
Clear all logs.
Reset ticket counts to their initial state (e.g., currentTickets: 0, totalTicketsReleased: 0, totalTicketsPurchased: 0).
Verify Reset:

Ensure that logs are cleared from the frontend.
Ticket status displays are reset to initial values.
No ongoing processes are running until the simulation is started again.

#Project Structure

CLI Structure

Configuration.java
Logger.java
Participant.java
Vendor.java
Customer.java
TicketPool.java
TicketingSystem.java


Backend Structure


 Config
  CorsConfig.java
  ModelMapperConfig.java
  WebSocketConfig.java
 
 Controllers
  SystemController.java
  LogController.java
  TicketController.java
  ConfigurationController.java
  CustomerController.java
  VendorCOntroller.java
  WebSocketController.java
  
 DTO
  ConfigurationDTO.java
  TicketStatusDTO.java
  VendorDTO.java
  CustomerDTO.java
 
 Events
  TicketsSoldOutEvent.java
  
 Model
  Configuration.java
  Customer.java
  Ticket.java
  Vendor.java
  
 Repositories
  
  ConfigurationRepository.java
  CustomerRepository.java
  TicketRepository.java
  VendorRepository.java
  
 Runnables
 
  CustomerRunnable.java
  VendorRunnable.java
  
 Security
 
  SecurityConfig.java
  
 Services
 
  ConfigurationService.java
  CustomerService.java
  LogService.java
  TicketPoolService.java
  TicketService.java
  VendorService.java
  
 resources
  application.properties
  
 pom.xml
 

Frontend Structure

 components
  
  ControlPanel.jsx
  LogDisplay.jsx
  Navbar.jsx
  
 pages
 
 ConfigPage.jsx
 DashboardPage.jsx
 
 App.jsx
 ApiClient.js
 index.js
 package.json


git clone https://github.com/yourusername/ticketing-system-simulation.git
cd ticketing-system-simulation

Create a New Branch:


git checkout -b feature/your-feature-name

Commit Changes:

git commit -m "Add your descriptive commit message here"

git push origin feature/your-feature-name

License
This project is licensed under the MIT License. You are free to use, modify, and distribute this software as per the license terms.

Contact
For any questions, issues, or contributions, please contact:

Mihin Thenuja Abeywickrama

Email: mihintabeywicmrama55@gmail.com
LinkedIn: https://www.linkedin.com/in/mihin-abeywickrama-9324372ab/
GitHub: Mihin91
