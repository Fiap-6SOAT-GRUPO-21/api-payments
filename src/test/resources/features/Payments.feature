Feature: API - Payments

  Scenario: Make a payment
  When the order details and the payment provider are received
  Then the payment is successfully processed

  Scenario: Find an existing payment
  Given a payment has already been requested
  When the payment identifier is received
  Then the payment details are returned