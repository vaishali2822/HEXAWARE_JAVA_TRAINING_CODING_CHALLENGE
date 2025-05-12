package com.company.model;


import com.company.dao.IAdoptable;

public class Dog extends Pet implements IAdoptable 
{
    private String dogBreed;

    public Dog(String name, int age, String breed, String dogBreed) 
    {
        super(name, age, breed);
        this.dogBreed = dogBreed;
    }

    public String getDogBreed() 
    {
        return dogBreed;
    }

    public void setDogBreed(String dogBreed) 
    {
        this.dogBreed = dogBreed;
    }

    @Override
    public String toString() 
    {
        return "Dog{" +
                "dogBreed='" + dogBreed + '\'' +
                "} " + super.toString();
    }
    @Override
    public void adopt() {
        System.out.println("Adopting a dog: " + getName());
    }
}
