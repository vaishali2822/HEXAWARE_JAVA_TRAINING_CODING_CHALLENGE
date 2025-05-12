package com.company.dao;

public abstract class Donation 
{
    private String donorName;
    private double amount;

    public Donation(String donorName, double amount) 
    {
        this.donorName = donorName;
        this.amount = amount;
    }

    public String getDonorName() 
    {
        return donorName;
    }

    public void setDonorName(String donorName) 
    {
        this.donorName = donorName;
    }

    public double getAmount() 
    {
        return amount;
    }

    public void setAmount(double amount) 
    {
        this.amount = amount;
    }

    public abstract void recordDonation();
}