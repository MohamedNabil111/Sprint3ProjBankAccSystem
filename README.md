## Bank Account System
This is a project for a bank account system with Spring Boot that supports account creation, deposits, withdrawals, transaction tracking, and customer management. The project covers back-end architecture including JPA, repositories, entity relationships, DTOs, REST APIs, validation, exception handling, and REST best practices.

---

## Features

### Customer Endpoints
- **POST `/api/customers`**: Create a new customer ![Endpoint Example](docs/images/customer/create_customer.png)
- **GET `/api/customers`**: Get all customers ![Endpoint Example](docs/images/customer/get_all_customers.png)
- **GET `/api/customers/{id}`**: Get a customer by ID ![Endpoint Example](docs/images/customer/get_customer_by_ID.png)
- **PUT `/api/customers/{id}`**: Update a customer by ID ![Endpoint Example](docs/images/customer/update_customer_by_ID.png)
- **DELETE `/api/customers/{id}`**: Delete a customer by ID ![Endpoint Example](docs/images/customer/delete_customer_by_ID.png)
- **GET `/api/customers/email/{email}`**: Get a customer by email ![Endpoint Example](docs/images/customer/get_customer_by_email.png)
- **GET `/api/customers/search?term="name"`**: Search customers by name ![Endpoint Example](docs/images/customer/search_customers_by_name.png)
- **GET `/api/customers/count`**: Get customers count ![Endpoint Example](docs/images/customer/get_customers_count.png)

### BankAccount Endpoints
- **POST `/api/accounts`**: Create a new account ![Endpoint Example](docs/images/account/create_bank_account.png)
- **GET `/api/accounts`**: Get all accounts ![Endpoint Example](docs/images/account/get_all_accounts.png)
- **GET `/api/accounts/{id}`**: Get an account by ID ![Endpoint Example](docs/images/account/get_account_by_ID.png)
- **POST `/api/accounts/deposit?accountId="toAccountId"&amount="amount"`**: deposit to an account by ID ![Endpoint Example](docs/images/account/deposit_to_account.png)
- **POST `/api/accounts/withdraw?accountId="fromAccountId"&amount="amount"`**: withdraw from an account by ID ![Endpoint Example](docs/images/account/withdraw_from_account.png)
- **POST `/api/accounts/transfer?fromAccountId="fromAccountId"&toAccountId="toAccountId"&amount="amount"`**: transfer from an account to another account ![Endpoint Example](docs/images/account/transfer_from_account_to_another_account.png)
- **GET `/api/accounts?accountType="accountType"`**: Get accounts by accountType (Savings, etc..)
- **GET `/api/accounts?amount="amount"`**: Get accounts with balance greater than a specific amount
- **GET `/api/accounts?min="min"&max="max"`**: Get accounts with balance in a specific range

### Transaction Endpoints
- **GET `/api/v1/accounts/{accountId}/transactions`**: Get transactions for a specific account ![Endpoint Example](docs/images/transaction/get_transactions_for_account.png)
- **GET `/api/v1/transactions/{id}`**: Get a transaction by ID ![Endpoint Example](docs/images/transaction/get_transaction_by_ID.png)

---

## Validation Rules

- `name`: must be between 2 and 100 characters
- `email`: Must be valid and unique
- `phone`: Must be between 10 and 15 characters
- `address`: Max 255 characters
- `transactionType`: enum with values ['DEPOSIT', 'WITHDRAWAL', 'TRANSFER']
- `accountNumber`: 32 characters

Invalid input results in HTTP 400 with descriptive error messages.

---

## Exception Handling

- **Validation errors** return `400 BAD REQUEST` with detailed error messages.
- **Resource not found** returns `404 NOT FOUND`.
- **Duplicate customer email** returns `409 CONFLICT`.
- All exceptions are handled by a global `@ControllerAdvice`.
- Screenshots of handled exceptions are attached in docs/images/exceptionHandling folder
