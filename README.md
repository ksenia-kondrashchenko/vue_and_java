# Test project with Vue and Java
Project contains two modules:
- backend - java backend with Spring Boot
- frontend - vue.js based frontend

# Run locally
Execute "mvn package" from the root project folder

Execute "mvn spring-boot:run" from the backend folder

Open localhost:8080 in your browser

#API
### Get Balance (default)
```javascript
GET http://localhost:8080/api/account
```

### Get list of all transactions
```javascript
GET http://localhost:8080/api/account/transactions
```

### Get transaction by ID
```javascript
GET http://localhost:8080/api/account/transactions/{id}
```

### Create new transaction
```javascript
POST http://localhost:8080/api/account/transactions
Request Body
{
	"type": "credit", // supported values debit/credit 
	"amount": 5050.55
}
```
