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
public class PersonDTO
{
    private int id;
    private String mail, telephone, fName, lName;
    private List<HobbyDTO> hobbylist = new ArrayList();
    private AddressDTO residence;

    public PersonDTO()
    {
    }

    public PersonDTO(Person person)
    {
        this.id = person.getId();
        this.mail = person.getEmail();
        this.telephone = person.getPhone();
        this.fName = person.getFirstName();
        this.lName = person.getLastName();
        this.residence = new AddressDTO(person.getAddress());
        for (Hobby h : person.getHobbies())
        {
            hobbylist.add(new HobbyDTO(h));
        }
    }

    public void addHobbyDTO(HobbyDTO h) {
        this.hobbylist.add(h);
    }
    
    public int getId()
    {
        return id;
    }

    public void setId(int id)
    {
        this.id = id;
    }

    public String getMail()
    {
        return mail;
    }

    public void setMail(String mail)
    {
        this.mail = mail;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public String getfName()
    {
        return fName;
    }

    public void setfName(String fName)
    {
        this.fName = fName;
    }

    public String getlName()
    {
        return lName;
    }

    public void setlName(String lName)
    {
        this.lName = lName;
    }

    public List<HobbyDTO> getHobbylist()
    {
        return hobbylist;
    }

    public void setHobbylist(List<HobbyDTO> hobbylist)
    {
        this.hobbylist = hobbylist;
    }

    public AddressDTO getResidence()
    {
        return residence;
    }

    public void setResidence(AddressDTO residence)
    {
        this.residence = residence;
    }

    @Override
    public int hashCode()
    {
        int hash = 7;
        hash = 61 * hash + Objects.hashCode(this.mail);
        hash = 61 * hash + Objects.hashCode(this.telephone);
        hash = 61 * hash + Objects.hashCode(this.fName);
        hash = 61 * hash + Objects.hashCode(this.lName);
        hash = 61 * hash + Objects.hashCode(this.hobbylist);
        hash = 61 * hash + Objects.hashCode(this.residence);
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
        final PersonDTO other = (PersonDTO) obj;
        if (!Objects.equals(this.mail, other.mail))
        {
            return false;
        }
        if (!Objects.equals(this.telephone, other.telephone))
        {
            return false;
        }
        if (!Objects.equals(this.fName, other.fName))
        {
            return false;
        }
        if (!Objects.equals(this.lName, other.lName))
        {
            return false;
        }
        if (!Objects.equals(this.hobbylist, other.hobbylist))
        {
            return false;
        }
        if (!Objects.equals(this.residence, other.residence))
        {
            return false;
        }
        return true;
    }

    @Override
    public String toString()
    {
        return "PersonDTO{" + "id=" + id + ", mail=" + mail + ", telephone=" + telephone + ", fName=" + fName + ", lName=" + lName + ", hobbylist=" + hobbylist + ", residence=" + residence + '}';
    }
    
    
}
