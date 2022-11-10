# Simple Banking Application

# API Documentation

## Authentication
`POST /login`

Allows the user to log in. When a user logs in successfully, the server will generate an HTTP Session that contains the user credentials (cookie-based authorization).

### Request
Request Body
```json
{
    "username": "john_doe",
    "password": "password123"
}
```

### Response
Response Body (if successful)
```json
{
    "id": 1,
    "username": "john_doe",
    "password": "password123",
    "role": "admin"
}
```

Response Body (if not successful)
```json
{
    "message": "Invalid login"
}
```

Status Codes
| Status Code | Description |
| :---------- | :---------- |
| 200 OK | Successfully logged in |
| 400 Bad Request | Invalid credentials |