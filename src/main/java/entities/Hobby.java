/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import dto.HobbyDTO;
import dto.PersonDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

/**
 *
 * @author
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Hobby.getAll", query = "SELECT h FROM Hobby h"),
    @NamedQuery(name = "Hobby.deleteAllRows", query = "DELETE FROM Hobby"),
    @NamedQuery(name = "Hobby.getHobbyByName", query = "SELECT h FROM Hobby h WHERE h.name = :name")
})
public class Hobby implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id = 0;

    @Column(unique = true)
    private String name;

    private String description;

    @ManyToMany(mappedBy = "hobbies", cascade = CascadeType.PERSIST)
    private List<Person> persons = new ArrayList();

    public Hobby() {

    }

    public Hobby(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Hobby(HobbyDTO hobby) {
        this.id = hobby.getId();
        this.name = hobby.getHobbyName();
        this.description = hobby.getHobbyDescription();
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.name);
        hash = 41 * hash + Objects.hashCode(this.description);
        hash = 41 * hash + Objects.hashCode(this.persons);
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
        final Hobby other = (Hobby) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.persons, other.persons)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Hobby{" + "id=" + id + ", name=" + name + ", description=" + description + ", persons=" + persons + '}';
    }

}
