# Loan Calculation App

This app calculates the total cost of a loan and generates a payback plan per each month.

## Technologies used

Java 17, Spring Boot, Gradle, H2 Database, JUnit 5

## Build and Run

Navigate to ~\loanpayback and run with:

```bash
./gradlew bootRun
```
Using Postman or a similar tool, make a POST request to http://localhost:8080/calculate.

### Request body example:

```
{
    "amount": 20000,
    "paybackTimeYears": 1,
    "loanType": "MORTGAGE"
}
```
- It is not obligatory to provide ```loanType```, as it will resolve to ```MORTGAGE``` by default.
- Currently supported loanTypes are: "MORTGAGE"

### Response example:

```
[
    {
        "period": 1,
        "monthlyPayment": 1698.43,
        "interest": 58.33,
        "principal": 1640.10,
        "remainingAmount": 18359.90,
        "totalInterest": 58.33
    },
    {
        "period": 2,
        "monthlyPayment": 1698.43,
        "interest": 53.55,
        "principal": 1644.88,
        "remainingAmount": 16715.02,
        "totalInterest": 111.88
    },
    ...
]
```
