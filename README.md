# 🏗️ Project Name - Microservice  

## 🚀 Overview  
Short description of what this microservice does.

## 📌 Features  
- ✅ Feature 1  
- ✅ Feature 2  
- ✅ Feature 3  

---

## 🗒️ Changelog  

### 📅 [2025-03-06] - Version 1.2.0  
🔹 **New Features:**  
- Added JWT authentication  
- Improved database indexing  

🛠️ **Bug Fixes:**  
- Fixed profile update API issue  
- Resolved Redis cache inconsistency  

---

### 📅 [2025-02-20] - Version 1.1.0  
🔹 **Enhancements:**  
- Integrated Redis caching  
- Optimized query performance  

🛠️ **Bug Fixes:**  
- Fixed null pointer exception in login API  

---

## 📜 API Endpoints  
| Method | Endpoint         | Description |
|--------|-----------------|-------------|
| `POST` | `/users/signup` | Register a new user |
| `POST` | `/users/login`  | User authentication |
| `GET`  | `/users/{id}`   | Fetch user details |
| `PUT`  | `/users/update` | Update user details |

---

## 🏗️ Setup & Deployment  
```sh
mvn clean package  
docker-compose up -d
