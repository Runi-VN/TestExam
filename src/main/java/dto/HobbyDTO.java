/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import entities.Hobby;
import entities.Person;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author
 */
public class HobbyDTO
{
    private int id;
    private String hobbyName, hobbyDescription;

    public HobbyDTO()
    {
    }

    public HobbyDTO(Hobby hobby)
    {
        this.id = hobby.getId();
        this.hobbyName = hobby.getName();
        this.hobbyDescription = hobby.getDescription();
    }
    
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getHobbyName()
    {
        return hobbyName;
    }

    public void setHobbyName(String hobbyName)
    {
        this.hobbyName = hobbyName;
    }

    public String getHobbyDescription()
    {
        return hobbyDescription;
    }

    public void setHobbyDescription(String hobbyDescription)
    {
        this.hobbyDescription = hobbyDescription;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 79 * hash + Objects.hashCode(this.hobbyName);
        hash = 79 * hash + Objects.hashCode(this.hobbyDescription);
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
        final HobbyDTO other = (HobbyDTO) obj;
        if (!Objects.equals(this.hobbyName, other.hobbyName))
        {
            return false;
        }
        if (!Objects.equals(this.hobbyDescription, other.hobbyDescription))
        {
            return false;
        }
        return true;
    }

    
}
