# Customer API Test Cases

## 1. Create Customer (Valid Data)

**Method:** POST  
**URL:** `http://localhost:8080/api/customer`  
**Headers:**  
`Content-Type: application/json`  
**Body:**
```json
{
  "name": "John Doe",
  "email": "john.doe@email.com",
  "phone": "1234567890",
  "address": "123 Main St, New York, NY 10001"
}
```
**Expected Status:** 201 Created  
**Expected Response:** Customer object with generated ID and timestamps

---

## 2. Create Another Customer

**Method:** POST  
**URL:** `http://localhost:8080/api/customer`  
**Headers:**  
`Content-Type: application/json`  
**Body:**
```json
{
  "name": "Jane Smith",
  "email": "jane.smith@email.com",
  "phone": "9876543210",
  "address": "456 Oak Avenue, Los Angeles, CA 90210"
}
```
**Expected Status:** 201 Created

---

## 3. Create Customer with Duplicate Email (Error Test)

**Method:** POST  
**URL:** `http://localhost:8080/api/customer`  
**Headers:**  
`Content-Type: application/json`  
**Body:**
```json
{
  "name": "Bob Johnson",
  "email": "john.doe@email.com",
  "phone": "5555555555",
  "address": "789 Pine Street, Chicago, IL 60601"
}
```
**Expected Status:** 409 Conflict  
**Expected Response:**
```json
{
  "error": "Duplicate Resource",
  "message": "Customer with email john.doe@email.com already exists",
  "status": 409,
  "timestamp": "2024-08-07T10:30:00.123456",
  "path": "uri=/api/customer"
}
```

---

## 4. Create Customer with Invalid Data (Validation Test)

**Method:** POST  
**URL:** `http://localhost:8080/api/customer`  
**Headers:**  
`Content-Type: application/json`  
**Body:**
```json
{
  "name": "",
  "email": "invalid-email",
  "phone": "123",
  "address": ""
}
```
**Expected Status:** 400 Bad Request  
**Expected Response:**
```json
{
  "error": "Validation Failed",
  "message": "Invalid input data",
  "status": 400,
  "timestamp": "2024-08-07T10:30:00.123456",
  "path": "uri=/api/customer",
  "validationErrors": {
    "name": "Name is required",
    "email": "Email should be valid",
    "phone": "Phone number must be between 10 and 15 characters",
    "address": "Address is required"
  }
}
```

---

## 5. Get All Customers

**Method:** GET  
**URL:** `http://localhost:8080/api/customer`  
**Expected Status:** 200 OK  
**Expected Response:** Array of all customer objects

---

## 6. Get Customer by ID (Valid)

**Method:** GET  
**URL:** `http://localhost:8080/api/customer/1`  
**Expected Status:** 200 OK  
**Expected Response:** Customer object with ID 1

---

## 7. Get Customer by Non-existent ID

**Method:** GET  
**URL:** `http://localhost:8080/api/customer/999`  
**Expected Status:** 404 Not Found  
**Expected Response:**
```json
{
  "error": "Resource Not Found",
  "message": "Customer with ID 999 not found",
  "status": 404,
  "timestamp": "2024-08-07T10:30:00.123456",
  "path": "uri=/api/customer/999"
}
```

---

## 8. Get Customer by Email

**Method:** GET  
**URL:** `http://localhost:8080/api/customer/email/john.doe@email.com`  
**Expected Status:** 200 OK  
**Expected Response:** Customer object with matching email

---

## 9. Get Customer by Non-existent Email

**Method:** GET  
**URL:** `http://localhost:8080/api/customer/email/nonexistent@email.com`  
**Expected Status:** 404 Not Found

---

## 10. Search Customers by Name

**Method:** GET  
**URL:** `http://localhost:8080/api/customer/search?term=John`  
**Expected Status:** 200 OK  
**Expected Response:** Array of customers with names containing `"John"`

---

## 11. Search Customers by Email

**Method:** GET  
**URL:** `http://localhost:8080/api/customer/search?term=jane.smith`  
**Expected Status:** 200 OK  
**Expected Response:** Array of customers with emails containing `"jane.smith"`

---

## 12. Update Customer (Valid)

**Method:** PUT  
**URL:** `http://localhost:8080/api/customer/1`  
**Headers:**  
`Content-Type: application/json`  
**Body:**
```json
{
  "name": "John Updated Doe",
  "email": "john.updated@email.com",
  "phone": "1111111111",
  "address": "123 Updated Street, New York, NY 10001"
}
```
**Expected Status:** 200 OK  
**Expected Response:** Updated customer object

---

## 13. Update Customer with Duplicate Email

**Method:** PUT  
**URL:** `http://localhost:8080/api/customer/1`  
**Headers:**  
`Content-Type: application/json`  
**Body:**
```json
{
  "name": "John Doe",
  "email": "jane.smith@email.com",
  "phone": "1234567890",
  "address": "123 Main St, New York, NY 10001"
}
```
**Expected Status:** 409 Conflict

---

## 14. Update Non-existent Customer

**Method:** PUT  
**URL:** `http://localhost:8080/api/customer/999`  
**Headers:**  
`Content-Type: application/json`  
**Body:**
```json
{
  "name": "Non Existent",
  "email": "nonexistent@email.com",
  "phone": "9999999999",
  "address": "999 Non Existent St"
}
```
**Expected Status:** 404 Not Found

---

## 15. Check if Customer Exists (Valid ID)

**Method:** GET  
**URL:** `http://localhost:8080/api/customer/1/exists`  
**Expected Status:** 200 OK  
**Expected Response:**
```json
{
  "exists": true
}
```

---

## 16. Check if Customer Exists (Invalid ID)

**Method:** GET  
**URL:** `http://localhost:8080/api/customer/999/exists`  
**Expected Status:** 200 OK  
**Expected Response:**
```json
{
  "exists": false
}
```

---

## 17. Get Customer Count

**Method:** GET  
**URL:** `http://localhost:8080/api/customer/count`  
**Expected Status:** 200 OK  
**Expected Response:**
```json
{
  "totalCustomers": 2
}
```

---

## 18. Delete Customer (Valid)

**Method:** DELETE  
**URL:** `http://localhost:8080/api/customer/2`  
**Expected Status:** 200 OK  
**Expected Response:**
```json
{
  "message": "Customer with ID 2 has been deleted successfully",
  "status": "success"
}
```

---

## 19. Delete Non-existent Customer

**Method:** DELETE  
**URL:** `http://localhost:8080/api/customer/999`  
**Expected Status:** 404 Not Found

---

## 21. Create Customer with Long Name

**Method:** POST  
**URL:** `http://localhost:8080/api/customer`  
**Body:**
```json
{
  "name": "This is a very long name that exceeds the maximum allowed characters for a customer name in our system",
  "email": "longname@email.com",
  "phone": "1234567890",
  "address": "123 Long Name Street"
}
```
**Expected Status:** 400 Bad Request (validation error)

---

## 22. Create Customer with Minimum Valid Data

**Method:** POST  
**URL:** `http://localhost:8080/api/customer`  
**Body:**
```json
{
  "name": "AB",
  "email": "ab@c.co",
  "phone": "1234567890",
  "address": "1"
}
```
**Expected Status:** 201 Created

---

## 23. Search with Empty Term

**Method:** GET  
**URL:** `http://localhost:8080/api/customer/search?term=`  
**Expected Status:** 200 OK  
**Expected Response:** All customers or empty array

---

## 24. Create Customer with Special Characters

**Method:** POST  
**URL:** `http://localhost:8080/api/customer`  
**Body:**
```json
{
  "name": "José María García-López",
  "email": "jose.garcia@email.com",
  "phone": "1234567890",
  "address": "Calle Mayor, 123, 28001 Madrid, España"
}
```
**Expected Status:** 201 Created
