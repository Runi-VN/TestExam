/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Address;
import entities.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author
 */
public class AddressDTO
{
    private int id;
    private String road, town;
    private int zipcode;

    public AddressDTO()
    {
    }

    public AddressDTO(Address address)
    {
        this.id = address.getId();
        this.road = address.getStreet();
        this.town = address.getCity();
        this.zipcode = address.getZip();
    }
    
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getRoad()
    {
        return road;
    }

    public void setRoad(String road)
    {
        this.road = road;
    }

    public String getTown()
    {
        return town;
    }

    public void setTown(String town)
    {
        this.town = town;
    }

    public int getZipcode()
    {
        return zipcode;
    }

    public void setZipcode(int zipcode)
    {
        this.zipcode = zipcode;
    }

    @Override
    public int hashCode()
    {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.road);
        hash = 37 * hash + Objects.hashCode(this.town);
        hash = 37 * hash + this.zipcode;
        return hash;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
        {
            return true;
        }
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final AddressDTO other = (AddressDTO) obj;
        if (this.zipcode != other.zipcode)
        {
            return false;
        }
        if (!Objects.equals(this.road, other.road))
        {
            return false;
        }
        if (!Objects.equals(this.town, other.town))
        {
            return false;
        }
        return true;
    }

    
}
