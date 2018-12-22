# Duck Ledger #

Duck Ledger is an accounting and money transfer service providing a simple REST API. The provided solution is based on a
single-node in-memory solution and has correspondent limitations (no durability, no linear scalability).

## REST API ##

The provided API consist of the following methods:

`POST` `/account` Creates new account and returns its identifier.

`POST` `/deposit/:id?amount=:amount` Deposits specified amount on specified account.

`POST` `/withdraw/:id?amount=:amount` Withdraws specified amount from specified account.

`POST` `/transfer?fromId=:fromId&amp;toId=:toId&amp;amount=:amount`  Transfers specified amount between specified
accounts.

`GET` `/balance/:id` Returns balance of specified account.

## Try it out ##

You can use `curl` to try this service.

```
$ curl -X POST http://localhost:8080/account
1
$ curl -X POST http://localhost:8080/account
2
$ curl -X POST http://localhost:8080/deposit/1?amount=100
$ curl -X POST http://localhost:8080/deposit/2?amount=100
$ curl -X POST http://localhost:8080/transfer?fromId=1&amp;toId=2&amp;amount=42
$ curl http://localhost:8080/balance/1
58
$ curl http://localhost:8080/balance/2
142
```