# TestProject

We design and implement a RESTful API (including the data model and the backing
implementation) for money transfers between accounts.

## Installation

Make sure you have postgres installed and ready to run.

First package the application as a JAR file:

```bash
mvn clean package -DskipTests
```

Then, use docker-compose in the root directory to create an image and a container of our project.

```bash
docker-compose up
```
This also populates our database with three users and three accounts.

## Set up
Our model includes three tables - Customers, Accounts, and Transactions. 

The Customers table includes the userId (primary key), the name, the email, and the total balance. The original idea was that a customer can have many accounts and the balance would be the total sum of the balance in all their accounts, but this was not implemented.

The Accounts table contains the accountId (primary key), userId, balance, and createdAt.

The Transactions table contains the transactionId (primary key), fromAccountId, toAccountId, amount, transactionTime, status (authorised or failed), and type (1 for top-up, 2 for transfer).

## Rest Controllers

### Customer Controller

```bash
# Add Customer REST API

# checks implented to ensure that firstName, lastName, and email are filled
# we also cannot add a user that has the email already registered
curl -X POST http://repo:8080/api/customers -H "Content-Type: application/json" -d "{\"firstName\":\"Emma\", \"lastName\":\"Borg\", \"email\":\"emmab@gmail.com\", \"balance\":50}"

# Get Customer REST API
curl -X GET http://localhost:8080/api/customers/{id}

# Get all Customers REST API
curl -X GET http://localhost:8080/api/customers

# Update Customer REST API
curl -X PUT http://localhost:8080/api/customers/{id} -H "Content-Type: application/json" -d "{\"firstName\":\"Emma\", \"lastName\":\"Borg\", \"email\":\"emmab@gmail.com\", \"balance\":50}"

# Delete Customer REST API
curl -X DELETE http://localhost:8080/api/customers/{id}
```
### Account Controller

```bash
# Get Account REST API
curl -X GET http://localhost:8080/api/accounts/{id}

# Get all Accounts REST API
curl -X GET http://localhost:8080/api/accounts
```
### Transaction Controller

```bash
# Add transfer between accounts REST API
curl -X POST "http://localhost:8080/api/transactions/{fromAccountId}/{toAccountId}?amount={amount}"

# Add Top up REST API
curl -X POST "http://localhost:8080/api/transactions/topup/{accountId}?amount={50}"

# Get Transaction REST API
curl -X GET http://localhost:8080/api/transactions/{id}

# Get All transactions REST API
curl -X GET http://localhost:8080/api/transactions
```

## Handling Errors

When adding a customer, firstName, lastName, and email must not be null. Also, we can not add a user with the same email.

When getting, deleting or updating a customer, the customerId must exist.

When viewing an account, the accountId must exist.

When transferring between accounts, both accounts must exist, the amount being transferred must be greater than 0, we can not transfer to the same account, and the user sending the money must have enough money in their account. In the final error, a transaction record is still created but the status is set to failed.

When topping up, the accountId must exist and the amount being topped up must be greater than 0.

When view a transaction, the transactionId must exist.
