Feature: End2End Tests

  Scenario: Create Account
    When I create account
    Then Response status code is 201
    And Response body is "1"
    When I request balance of 1
    Then Response body is "0"

  Scenario: Deposit
    When I create account
    And I deposit 100 on 1
    Then Response status code is 202
    When I request balance of 1
    Then Response body is "100"

  Scenario: Deposit (Account doesn't exist)
    When I deposit 100 on 42
    Then Response status code is 400

  Scenario: Withdraw
    When I create account
    And I deposit 100 on 1
    And I withdraw 80 from 1
    Then Response status code is 202
    When I request balance of 1
    Then Response body is "20"

  Scenario: Withdraw (Account doesn't exist)
    When I withdraw 1 from 42
    Then Response status code is 400

  Scenario: Withdraw (Insufficient funds)
    When I create account
    And I deposit 10 on 1
    And I withdraw 15 from 1
    Then Response status code is 400
    When I request balance of 1
    Then Response body is "10"

  Scenario: Transfer
    When I create account
    And I create account
    And I deposit 100 on 1
    And I transfer 20 from 1 to 2
    Then Response status code is 202
    When I request balance of 1
    Then Response body is "80"
    When I request balance of 2
    Then Response body is "20"

  Scenario: Transfer (From account doesn't exist)
    When I create account
    And I transfer 0 from 42 to 1
    Then Response status code is 400

  Scenario: Transfer (To account doesn't exist)
    When I create account
    And I transfer 0 from 1 to 42
    Then Response status code is 400

  Scenario: Transfer (Insufficient funds)
    When I create account
    And I create account
    And I deposit 10 on 1
    And I deposit 10 on 2
    And I transfer 15 from 1 to 2
    Then Response status code is 400
    When I request balance of 1
    Then Response body is "10"
    When I request balance of 2
    Then Response body is "10"