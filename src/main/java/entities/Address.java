/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dto.AddressDTO;
import dto.PersonDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Address.deleteAllRows", query = "DELETE FROM Address"),
    @NamedQuery(name = "Address.all", query = "SELECT a FROM Address a"),
    @NamedQuery(name = "Address.specific", query = "SELECT a FROM Address a WHERE a.street= :street AND a.city= :city AND a.zip= :zip")})
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = 0;

    private String street, city;
    private int zip;

    @OneToMany(mappedBy = "address") //, cascade = CascadeType.PERSIST 
    private List<Person> persons = new ArrayList();

    public Address() {
    }

    public Address(String street, String city, int zip, List<Person> persons) {
        this.street = street;
        this.city = city;
        this.zip = zip;
        this.persons = persons;
    }
    
    public Address(String street, String city, int zip) {
        this.street = street;
        this.city = city;
        this.zip = zip;
    }

    public Address(AddressDTO residence) {
        this.id = residence.getId();
        this.street = residence.getRoad();
        this.city = residence.getTown();
        this.zip = residence.getZipcode();
    }
    
    public void addPerson(Person p) {
        this.persons.add(p);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.street);
        hash = 79 * hash + Objects.hashCode(this.city);
        hash = 79 * hash + this.zip;
        hash = 79 * hash + Objects.hashCode(this.persons);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Address other = (Address) obj;
        if (this.zip != other.zip) {
            return false;
        }
        if (!Objects.equals(this.street, other.street)) {
            return false;
        }
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        if (!Objects.equals(this.persons, other.persons)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Address{" + "id=" + id + ", street=" + street + ", city=" + city + ", zip=" + zip + ", persons=" + persons + '}';
    }

}
