# 🚀 Career Connect

A full-stack job portal application connecting job seekers with recruiters. Built with Spring Boot and PostgreSQL.

## ✨ Features

### 👤 Authentication & Authorization
- User registration and login (JWT-based)
- Role-based access (Seeker/Recruiter)
- Secure password encryption

### 💼 Job Management
- Post, update, and manage job listings
- Search and filter jobs by keyword, type, and location
- Track applicant counts per job
- Job status management (Active/Paused/Closed)

### 📄 Applications
- Apply to jobs with resume and cover letter
- Track application status (Applied/Reviewed/Interview/Rejected/Accepted)
- View applicant details and history
- Filter applications by job or recruiter

### 👨‍💼 Profiles
- **Job Seeker Profile**: Bio, skills, resume, avatar, AI feedback
- **Recruiter Profile**: Company details, hiring preferences
- Profile creation and updates

### 🎯 Smart Recommendations
- AI-powered job recommendations based on skills matching
- Match scoring algorithm
- Supports both HTML and plain-text job requirements

### 📁 File Uploads
- Resume and avatar upload support
- Integration with cloud storage (Supabase)

## 🛠️ Tech Stack

- **Backend**: Spring Boot 3.2.2
- **Database**: PostgreSQL (Supabase)
- **Security**: Spring Security + JWT
- **ORM**: Hibernate/JPA
- **API Documentation**: Swagger/OpenAPI 3.0
- **Build Tool**: Maven
- **Java Version**: 21

## 📋 Prerequisites

- Java 21 or higher
- Maven 3.6+
- PostgreSQL database

## ⚙️ Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Career-Connect
   ```

2. **Configure database**
   
   Update `src/main/resources/application.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://your-host:5432/your-database
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   
   jwt.secret=your-secret-key
   jwt.expiration=86400000
   ```

3. **Build the project**
   ```bash
   mvn clean install
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

   The application will start on `http://localhost:9090`

## 📚 API Documentation

Once the application is running, access Swagger UI at:

```
http://localhost:9090/swagger-ui.html
```

### Key Endpoints

#### Authentication
- `POST /api/auth/signup` - Register new user
- `POST /api/auth/login` - User login

#### Jobs
- `GET /api/jobs` - List all jobs (with filters)
- `POST /api/jobs/recruiter/{recruiterId}` - Post a job
- `GET /api/jobs/{id}` - Get job details
- `PUT /api/jobs/{id}` - Update job
- `GET /api/jobs/recruiter/{recruiterId}` - Get recruiter's jobs

#### Applications
- `POST /api/applications/{userId}/{jobId}` - Apply to job
- `GET /api/applications/user/{userId}` - User's applications
- `GET /api/applications/recruiter/{recruiterId}` - Applicants for recruiter
- `PUT /api/applications/{id}/status` - Update application status

#### Profiles
- `GET /api/profile/{userId}` - Get seeker profile
- `POST /api/profile/{userId}` - Create/Update seeker profile
- `GET /api/recruiter/profile/{userId}` - Get recruiter profile
- `POST /api/recruiter/profile/{userId}` - Create/Update recruiter profile

#### Recommendations
- `GET /api/recommendations/{userId}` - Get job recommendations

#### File Upload
- `POST /api/upload/resume` - Upload resume
- `POST /api/upload/avatar` - Upload avatar

## 🔐 Authentication

The API uses JWT tokens. After login, include the token in requests:

```
Authorization: Bearer <your-jwt-token>
```

## 🗂️ Project Structure

```
src/main/java/com/careerconnect/
├── config/          # Security, JWT, Swagger configuration
├── controller/      # REST API endpoints
├── dto/            # Data Transfer Objects
├── exception/      # Global exception handling
├── model/          # JPA entities
├── repository/     # Database repositories
└── service/        # Business logic
```

## 👥 User Roles

- **SEEKER**: Job seekers who can browse jobs, apply, and manage their profile
- **RECRUITER**: Employers who can post jobs, review applications, and manage company profile

## 📝 License

Apache 2.0

---

Built with ❤️ by Career Connect Team
