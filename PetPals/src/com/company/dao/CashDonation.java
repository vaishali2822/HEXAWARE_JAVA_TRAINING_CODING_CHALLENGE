package com.company.dao;

import java.time.LocalDateTime;

public class CashDonation extends Donation {
    private LocalDateTime donationDate;

    public CashDonation(String donorName, double amount, LocalDateTime donationDate) {
        super(donorName, amount);
        this.donationDate = donationDate;
    }

    public LocalDateTime getDonationDate() {
        return donationDate;
    }

    public void setDonationDate(LocalDateTime donationDate) {
        this.donationDate = donationDate;
    }

    @Override
    public void recordDonation() {
        
        System.out.println("Recording cash donation...");
        System.out.println("Donor: " + getDonorName());
        System.out.println("Amount: $" + getAmount());
        System.out.println("Donation Date: " + donationDate);
       
    }
}
