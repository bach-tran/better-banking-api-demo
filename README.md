# Simple Banking Application

# API Documentation

## Authentication
---
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

---
`GET /current-user`

Used to check what the client is logged in as

### Request
Headers
| Key | Value | Description |
| :-- | :---- | :---------- |
| Cookie | `JSESSIONID=<session id>` | Used to identify which Http Session the client is associated with |

### Response
Response Body (if logged in)
```json
{
    "id": 2,
    "username": "jane_doe",
    "password": "test12345",
    "role": "customer"
}
```

Response Body (if not logged in)
```json
{
    "message": "User is not logged in!"
}
```

Status Codes
| Status Code | Description |
| :---------- | :---------- |
| 200 OK | User is logged in |
| 401 Unauthorized | User is not logged in |

---
`POST /logout`

### Request
Headers
| Key | Value | Description |
| :-- | :---- | :---------- |
| Cookie | `JSESSIONID=<session id>` | Used to identify which Http Session the client is associated with |

### Response
Status Codes
| Status Code | Description |
| :---------- | :---------- |
| 204 No Content | HTTP Session invalidated successfully |

## Accounts
`GET /accounts`

Used for customers to see a list of their own accounts or for admins to see all accounts in the system

### Request
Headers
| Key | Value | Description |
| :-- | :---- | :---------- |
| Cookie | `JSESSIONID=<session id>` | Used to identify which Http Session the client is associated with |

### Response
Response Body (Admin viewing all accounts)
```json
[
    {
        "id": 1,
        "balance": 541.43,
        "bankUserId": 2
    },
    {
        "id": 2,
        "balance": 1120.15,
        "bankUserId": 2
    },
    {
        "id": 3,
        "balance": 56.43,
        "bankUserId": 3
    }
]
```

Response Body (Customer viewing their own accounts)
```json
[
    {
        "id": 10,
        "balance": 123.45,
        "bankUserId": 2
    },
    {
        "id": 20,
        "balance": 123456.78,
        "bankUserId": 2
    },
    {
        "id": 30,
        "balance": 12.34,
        "bankUserId": 2
    }
]
```

Response Body (Not logged in)
```json
{
    "message": "Not logged in!"
}
```

Status Codes
| Status Code | Description |
| :---------- | :---------- |
| 200 OK | Successfully retrieved accounts |
| 401 Unauthorized | Not logged in |

---
`POST /accounts`

Used for customers to add a new account

### Request
Headers
| Key | Value | Description |
| :-- | :---- | :---------- |
| Cookie | `JSESSIONID=<session id>` | Used to identify which Http Session the client is associated with |

Request Body
```json
{
    "initialBalance": 14123.00
}
```

### Response
Response Body (if successful)
```json
{
    "id": 4,
    "balance": 14123.0,
    "bankUserId": 3
}
```

Response Body (if not logged in)
```json
{
    "message": "Not logged in!"
}
```

Response Body (if admin)
```json
{
    "message": "Not a customer!"
}
```

Status Codes
| Status Code | Description |
| :---------- | :---------- |
| 201 Created | Successfully added account |
| 401 Unauthorized | User is not logged in or is not a customer |