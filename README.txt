### Scheduling application

PURPOSE : This is an appointment scheduling application built in Java. Users can create and manage appointments and customers. A MySQL database is used to store data. An access log file is also used to keep track of successsful and unsuccessful user login attempts.

IDE : Intellij IDEA 2021.2.1 (Community Edition)
JAVA JDK : 17.0.3.1
JAVAFX SDK : 18.0.1
MYSQL CONNECTOR : Java 8.0.291

DIRECTIONS : 
Extract file and open in Intellij. The first screen is the Login screen. User can log in with the following credentials: username = test, password = test. In case the user is located in a French speaking country this screen will be translated.

After logging in the user will be in the Home screen with three different options available - "Customers", "Appointments", and "Reports".

The Customers screen will show a table with a list of customers and associated information. The user has the options to create a new customer, and modify or delete an existing customer.

The Appointments screen will show a table with a list of appointments. The user can filter the appointments by all appointments, current month appointments, or current week appointments. The user has also the options to create a new appointment, and modify or delete an existing appointment.

The Reports screen provides three different reports. The first allows the user to see how many of a specific appointment type exist for the selected month. The second allows the user to see all the appointments an existing contact has. The third allows the user to see how many customers from each country exist.

ADDITIONAL REPORT DESCRIPTION : The third report provides the total number of customers per country.
