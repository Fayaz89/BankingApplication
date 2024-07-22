package com.pack.card.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Document(collection = "Card")
public class Card {

    @Id
    private String cardNumber;
   // private String userId; // Reference to the user who owns the card
    
    private String cardHolderName;
    private String status; // e.g., ACTIVE, INACTIVE, ON_HOLD
    private double spendingLimit;
    private String expiryDate; // Add expiry date field
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getCardHolderName() {
		return cardHolderName;
	}
	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public double getSpendingLimit() {
		return spendingLimit;
	}
	public void setSpendingLimit(double spendingLimit) {
		this.spendingLimit = spendingLimit;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Card(String cardNumber, String cardHolderName, String status, double spendingLimit, String expiryDate) {
		super();
		this.cardNumber = cardNumber;
		this.cardHolderName = cardHolderName;
		this.status = status;
		this.spendingLimit = spendingLimit;
		this.expiryDate = expiryDate;
	}
	public Card() {
		super();
	}
	    
    
}


