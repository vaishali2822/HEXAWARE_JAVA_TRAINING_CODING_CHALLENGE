package com.company.dao;

import java.time.LocalDateTime;

import com.company.exception.InsufficientFundsException;

public class ItemDonation extends Donation {
    private String itemType;

    public ItemDonation(String donorName, double amount, String itemType) {
        super(donorName, amount);
        this.itemType = itemType;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    @Override
    public void recordDonation() {
    	try {
            // Check for insufficient funds
            if (getAmount() < 10) {
                throw new InsufficientFundsException(getAmount());
            }
        System.out.println("Recording item donation...");
        System.out.println("Donor: " + getDonorName());
        System.out.println("Amount: $" + getAmount());
        System.out.println("Item Type: " + itemType);
    	} catch (InsufficientFundsException e) {
            System.err.println(e.getMessage());
        }
    }
}
