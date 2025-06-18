# Student Schedule Viewer (Spring Boot + Excel + H2/MySQL)

# 📚 Student Schedule Viewer

A Spring Boot web application where an **administrator uploads an Excel file** containing class schedules. The system stores the data in a database, and **students can view their weekly or monthly schedule** via a user-friendly interface — without uploading or editing anything.

---

## 🧩 Key Features

### 👩‍🏫 Admin Role
- Upload `.xlsx` Excel files with student schedules
- Parses and imports data into the database
- One centralized source of truth for student schedules

### 👨‍🎓 Student Role
- View personal schedule by selecting name or ID
- Switch between weekly and monthly views
- Read-only access — no changes allowed

---

## ✨ Features

- ✅ Upload `.xlsx` Excel file containing schedule data
- ✅ Parse Excel rows into Java objects
- ✅ Persist schedule entries in H2 (or MySQL) database using Spring Data JPA
- ✅ Display a visual calendar of classes (weekly and monthly views)
- ✅ Simple, intuitive web interface using Thymeleaf

---

## 🗂️ Excel File Format (Sample)

| Day      | Start Time | End Time | Course Name     | Location   |
|----------|------------|----------|------------------|------------|
| Monday   | 09:00 AM   | 10:15 AM | Intro to CS      | Room 101   |
| Wednesday| 09:00 AM   | 10:15 AM | Intro to CS      | Room 101   |
| Friday   | 11:00 AM   | 12:15 PM | Calculus I       | Room 205   |

- Columns can be in any order, but headers are required.
- Accepts `.xlsx` format only (Excel 2007+).

---

## 📦 Tech Stack

- **Spring Boot** – for application framework
- **Spring Web** – to handle web requests
- **Spring Data JPA** – for database interaction
- **H2 / MySQL** – for data persistence

---

Work in progress...

---
