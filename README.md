# 📚 Library Management System - Server Side

Welcome to the **Server Side** repository of the Library Management System! This backend is built using **Java** and **Spring Boot**, providing robust APIs to manage library operations efficiently.

## 🚀 Important Links

- **🌐 Live Website:** [Library Management App](https://library-management-client-side-three.vercel.app/)
- **💻 Client-Side Repository:** [GitHub - Client Side](https://github.com/NAFEEJ007/LibraryManagement-Client-side)
- **⚙️ Server-Side Repository:** [GitHub - Server Side](https://github.com/NAFEEJ007/LibraryManagement-Server-side)

---

## 🛠️ Technology Stack

- **Language:** Java 21 ☕
- **Framework:** Spring Boot 3.5.x 🍃
- **Database:** MySQL 🐬
- **ORM:** Spring Data JPA 🗄️
- **Utilities:** Lombok 🧩

---

## ✨ Key Features

- **Robust RESTful APIs** for managing books and borrowing operations.
- **Database Integration** using MySQL and Spring Data JPA for seamless data persistence.
- **Clean Architecture** utilizing standard Spring Boot practices.
- **CORS Configuration** to communicate flawlessly with the deployed Vercel frontend.

---

## ⚙️ Getting Started (Local Setup)

To run this server locally on your machine, follow these steps:

### 📋 Prerequisites

Ensure you have the following installed:
- [Java Development Kit (JDK) 21](https://www.oracle.com/java/technologies/downloads/#java21)
- [Maven](https://maven.apache.org/download.cgi) (Optional, as the wrapper `mvnw` is included)
- [MySQL Server](https://dev.mysql.com/downloads/mysql/)

### 🚀 Installation & Running

1. **Clone the repository:**
   ```bash
   git clone https://github.com/NAFEEJ007/LibraryManagement-Server-side.git
   ```

2. **Navigate to the project directory:**
   ```bash
   cd LibraryManagement-Server-side
   ```

3. **Configure the Database:**
   - Create a new MySQL database (e.g., `library_db`).
   - Open `src/main/resources/application.properties` (or `application-example.properties`) and configure your database credentials:
     ```properties
     spring.datasource.url=jdbc:mysql://localhost:3306/your_database_name
     spring.datasource.username=your_mysql_username
     spring.datasource.password=your_mysql_password
     ```

4. **Run the Application:**
   Using the Maven wrapper, you can run the app directly:
   - On **Windows**:
     ```cmd
     mvnw.cmd spring-boot:run
     ```
   - On **Mac/Linux**:
     ```bash
     ./mvnw spring-boot:run
     ```

The server should now be running locally on `http://localhost:8080` (or the port defined in your properties). 🎉

---

## 🤝 Contributing

Contributions, issues, and feature requests are always welcome! Feel free to check the issues page if you want to contribute.

## 👨‍💻 Author

**NAFEEJ007**
- GitHub: [@NAFEEJ007](https://github.com/NAFEEJ007)
