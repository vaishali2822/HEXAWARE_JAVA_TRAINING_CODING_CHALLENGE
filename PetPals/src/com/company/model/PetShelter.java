package com.company.model;

import java.util.ArrayList;
import java.util.List;

import com.company.exception.InvalidPetAgeException;
import com.company.exception.NullReferenceException;
import com.company.exception.InsufficientFundsException;

public class PetShelter 
{
    private List<Pet> availablePets;

    public PetShelter() 
    {
        this.availablePets = new ArrayList<>();
    }

    public void addPet(Pet pet) 
    {
    	try 
    	{
            // Check for invalid age
            if (pet.getAge() <= 0) 
            {
                throw new InvalidPetAgeException(pet.getAge());
            }
            
            availablePets.add(pet);
            System.out.println("Pet added to the shelter successfully.");
        }
    	catch (InvalidPetAgeException e) 
    	{
            System.err.println(e.getMessage());
        }
    }

    public void removePet(Pet pet) 
    {
        availablePets.remove(pet);
    }

    public void listAvailablePets() 
    {
        for (Pet pet : availablePets)
        {
        	 try {
                 if (pet.getName() == null || pet.getBreed() == null) {
                     throw new NullReferenceException("Null reference in pet information.");
                 }
                 System.out.println(pet.toString());
             } catch (NullReferenceException e) {
                 System.err.println(e.getMessage());
             }
        	
        }
        	
    }
}
