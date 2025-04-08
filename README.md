### ✅ `README.md` – Document & Q&A Management System

# 📄 Document and Q&A Management System

A Spring Boot backend application for uploading documents (PDF, DOCX, Text), extracting content, storing in a MySQL database, and providing a simple Q&A API using keyword-based search.

---

## 🚀 Features

- ✅ Upload documents (`.pdf`, `.docx`, `.txt`) via REST API
- ✅ Extracts and stores full text and metadata (author, type, date)
- ✅ Search content using simple keyword-based Q&A
- ✅ Filter documents by metadata (author, type, date range)
- ✅ Asynchronous document processing for performance
- ✅ In-memory caching using Spring Cache (`@Cacheable`)
- ✅ Auto-generated Swagger UI for API testing and documentation

---

## 🛠️ Tech Stack

- **Java 17**
- **Spring Boot**
- **Spring Data JPA**
- **MySQL**
- **Swagger / OpenAPI**
- **Spring Cache (Simple/In-Memory)**
- **Apache PDFBox**, **Apache POI** for text extraction

---

## 📦 Project Structure

```
src/
├── controller/       # REST APIs (upload, search, filter)
├── service/          # Business logic & caching
├── model/            # JPA entities
├── repository/       # Spring Data JPA interfaces
├── config/           # Swagger, caching configs
└── resources/
    └── application.properties
```

---

## 🔄 API Overview

### 🔹 Upload Document

`POST /api/documents/upload`

- Uploads PDF, DOCX, or TXT file
- Accepts author metadata
- Stores content + metadata in DB

### 🔹 Keyword Q&A

`POST /api/qa?question=your+search+term`

- Returns documents matching keyword (full-text or partial)
- Uses caching for faster repeated queries

### 🔹 Filter Documents

`GET /api/documents/filter`

- Filter by author, type, or date range
- Supports pagination & sorting (if extended)

---

## 🧪 Try It Out: Swagger UI

Start the app and open:

```
http://localhost:5001/swagger-ui/index.html
```

Use Swagger to test all endpoints (upload files, filter, search, etc.)

---

## ⚙️ How to Run

### Prerequisites

- Java 17+
- Maven
- MySQL running on `localhost:3306` with a `document_db` database

### Clone and Run

```bash
git clone https://github.com/sakshi9611/DocumentManagementApp
./mvnw spring-boot:run
```

---

## 🧠 Architecture Diagram

```
User --> REST Controller --> Service Layer --> DB (MySQL)
                         ↘        ↘
                          Swagger   Cache (In-Memory)
```
## 🧑‍💻 Author

Sakshi Sharma 
Backend Java Developer
