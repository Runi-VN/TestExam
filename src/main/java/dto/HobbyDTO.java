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
    private List<PersonDTO> people = new ArrayList();

    public HobbyDTO()
    {
    }

    public HobbyDTO(Hobby hobby)
    {
        this.id = hobby.getId();
        this.hobbyName = hobby.getName();
        this.hobbyDescription = hobby.getDescription();
        for (Person p : hobby.getPersons())
        {
            people.add(new PersonDTO(p));
        }
    }
    
    public void addPersonDTO(PersonDTO p) {
        this.people.add(p);
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

    public List<PersonDTO> getPeople()
    {
        return people;
    }

    public void setPeople(List<PersonDTO> people)
    {
        this.people = people;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.hobbyName);
        hash = 41 * hash + Objects.hashCode(this.hobbyDescription);
        hash = 41 * hash + Objects.hashCode(this.people);
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
        if (!Objects.equals(this.people, other.people))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "HobbyDTO{" + "id=" + id + ", hobbyName=" + hobbyName + ", hobbyDescription=" + hobbyDescription + ", people=" + people + '}';
    }
    
    
    
}
