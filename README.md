# ☁️ Cloud Share

A full-stack SaaS cloud storage and file sharing platform built with **Spring Boot**, **React**, **MongoDB**, **Clerk Authentication**, and **Razorpay**.

Cloud Share enables users to securely upload, organize, and share files using public links while managing storage credits through an integrated payment system.

---

## 🚀 Features

### 🔐 Secure Authentication
- Clerk Authentication
- JWT-based authorization
- Protected REST APIs using Spring Security
- User profile management

### 📁 File Management
- Upload files securely
- View all uploaded files
- Delete files
- Download files
- Storage usage tracking

### 🔗 File Sharing
- Generate secure public sharing links
- Access shared files without authentication
- Share files instantly with anyone

### 💳 Credit System
- Purchase credits using Razorpay
- Secure payment verification
- Automatic credit updates
- Transaction history

### 📊 Dashboard
- User profile
- Available credits
- Uploaded files
- Payment history
- Storage statistics

### 🐳 Deployment Ready
- Dockerized Spring Boot backend
- Environment-based configuration
- Production-ready project structure

---

# 🏗️ Architecture

```
                 +----------------+
                 |     React      |
                 |   (Vite App)   |
                 +-------+--------+
                         |
                         | REST APIs
                         |
                 +-------v--------+
                 | Spring Boot API|
                 | Spring Security|
                 +-------+--------+
                         |
        +----------------+----------------+
        |                                 |
+-------v--------+                 +------v-------+
|    Clerk JWT   |                 |   MongoDB    |
| Authentication |                 | Database     |
+----------------+                 +--------------+

                         |
                 +-------v-------+
                 |  Razorpay API |
                 +---------------+
```

---

# 🛠️ Tech Stack

## Frontend

- React
- Vite
- Clerk Authentication
- Axios
- CSS

## Backend

- Java 17
- Spring Boot
- Spring Security
- Maven
- MongoDB
- Razorpay SDK

## Database

- MongoDB Atlas / MongoDB Community

## Deployment

- Docker
- Render / Railway / AWS compatible

---

# 📂 Project Structure

```
Cloud-Share
│
├── cloudshareapi
│   ├── src
│   ├── Dockerfile
│   ├── pom.xml
│   └── ...
│
├── cloudsharewebapp
│   ├── src
│   ├── public
│   ├── package.json
│   └── ...
│
├── README.md
└── .gitignore
```

---

# ⚙️ Getting Started

## Prerequisites

- Java 17+
- Maven
- Node.js 18+
- npm
- MongoDB Atlas (or local MongoDB)
- Clerk Account
- Razorpay Account

---

# 🔧 Backend Setup

Navigate to backend

```bash
cd cloudshareapi
```

Configure environment variables

```properties
MONGODB_URI=your_mongodb_uri

CLERK_JWKS_URL=your_clerk_jwks_url

RAZORPAY_KEY_ID=your_key_id

RAZORPAY_KEY_SECRET=your_secret
```

Run the backend

```bash
./mvnw spring-boot:run
```

Backend runs on

```
http://localhost:8080
```

---

# 💻 Frontend Setup

Navigate to frontend

```bash
cd cloudsharewebapp
```

Install dependencies

```bash
npm install
```

Create `.env`

```properties
VITE_CLERK_PUBLISHABLE_KEY=your_publishable_key

VITE_RAZORPAY_KEY=your_razorpay_key
```

Run

```bash
npm run dev
```

Frontend runs on

```
http://localhost:5173
```

---

# 🔑 Environment Variables

## Backend

| Variable | Description |
|----------|-------------|
| MONGODB_URI | MongoDB connection string |
| CLERK_JWKS_URL | Clerk JWKS endpoint |
| RAZORPAY_KEY_ID | Razorpay API Key |
| RAZORPAY_KEY_SECRET | Razorpay Secret |

---

## Frontend

| Variable | Description |
|----------|-------------|
| VITE_CLERK_PUBLISHABLE_KEY | Clerk Publishable Key |
| VITE_RAZORPAY_KEY | Razorpay Public Key |

---

# 🔒 Security

- Clerk JWT Authentication
- Spring Security
- Protected APIs
- Secure Payment Verification
- Environment Variables for Secrets

---

# 🐳 Docker

Build

```bash
docker build -t cloudshareapi ./cloudshareapi
```

Run

```bash
docker run -p 8080:8080 cloudshareapi
```

---

# 📡 REST API Modules

- Authentication
- Users
- File Upload
- File Download
- Public Share Links
- Credit Management
- Razorpay Payments
- Transactions

---

# 📈 Future Improvements

- Folder support
- Drag & Drop uploads
- File previews
- Email sharing
- Password-protected links
- Link expiration
- File versioning
- Storage analytics
- Admin dashboard
- Multi-file uploads
- Search & filters

---

# 🤝 Contributing

Contributions are welcome.

1. Fork the repository
2. Create a feature branch

```bash
git checkout -b feature/new-feature
```

3. Commit your changes

```bash
git commit -m "Added new feature"
```

4. Push

```bash
git push origin feature/new-feature
```

5. Open a Pull Request

---

# 👨‍💻 Author

**Adarsh Kumar**

If you found this project useful, consider giving it a ⭐ on GitHub.

---
