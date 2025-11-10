# Time Forge Microservices

## 🛠 Introduction
Time Forge is a **microservices-based** application developed by **Deep Tech Team**. It efficiently manages workspaces, collaborations, projects, and their objectives.

## 🏗 Architecture
The application is based on a **microservices architecture** with the following components:

- 🚪 **Gateway Service**: API Gateway to manage incoming requests.
- 🔍 **Discovery Service**: Microservices discovery service using Eureka.
- ⚙️ **Config Server**: Centralized configuration management.
- 👤 **User Service**: User management.
- 🏢 **Workspace Service**: Workspace management.
- 🤝 **Collaborations Service**: Managing user collaborations.
- 📂 **Projects Service**: Project management.
- 🎯 **Goals Service**: Managing project objectives.
- 🔐 **Keycloak Integration**: Authentication and authorization management via Keycloak.

## 🚀 Technologies Used
- **Backend**: `Spring Boot`, `Spring Cloud (Gateway, Eureka, Config Server)`, `Feign Clients`
- **Security**: `Keycloak`, `Spring Security`
- **Database**: `PostgreSQL`, `MongoDB`
- **Interservice Communication**: `Feign Clients`, `OpenFeign`
- **Deployment**: `Docker`, `Kubernetes (planned)`

## ⚙️ Installation and Configuration
### 📌 Prerequisites
- ✅ **Java 17+**
- ✅ **Spring Boot**
- ✅ **Docker & Docker Compose**
- ✅ **MongoDB**
- ✅ **Keycloak** configured with the correct realms and clients

### 🏗 Installation
1. **Clone the repository**
   ```bash
   git clone https://github.com/deep-tech-team/time-forge-microservices.git
   cd time-forge-microservices
   ```

2. **Start services with Docker Compose**
   ```bash
   docker-compose up -d
   ```

3. **Manually start the microservices** (if needed)
   ```bash
   mvn spring-boot:run -pl config-server
   mvn spring-boot:run -pl discovery-service
   mvn spring-boot:run -pl gateway-service
   mvn spring-boot:run -pl user-service
   # Repeat for other services
   ```

## 📖 API Documentation
The API is documented using **Swagger**.

- 🔗 Swagger URL: `http://localhost:8080/swagger-ui.html`

## 🤝 Contributing
Contributions are welcome!

## 👥 Contributors
👤 **Houssem Ellouze** -
👤 **Hassene Mansouri** -
👤 **Med Mansour Taleb** -
👤 **Mohamed Wassim Bahriya Lasghar** -
👤 **Mahdi Mzoughi**  
-
💡 Contributions, ideas, or improvements? Open an **issue** or submit a **pull request**!

---

1. 🍴 **Fork** the project
2. 🌱 Create a new branch (`feature/my-feature`)
3. 💾 Commit your changes (`git commit -m 'Added my feature'`)
4. 📤 Push to the branch (`git push origin feature/my-feature`)
5. 🔄 Open a **Pull Request**

## 📬 Contact
📢 **Deep Tech Team**  
📧 Email: support@deeptechteam.com  
🔗 GitHub: [https://github.com/deep-tech-team](https://github.com/deep-tech-team)

