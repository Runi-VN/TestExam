/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dto.HobbyDTO;
import dto.PersonDTO;
import java.util.List;

/**
 *
 * @author
 */
public interface KrakFacadeInterface
{
    public PersonDTO getPersonByID (int id);

    public PersonDTO getPersonByEmail (String email);

    public PersonDTO getPersonByPhone (String phone);
    
    public List<PersonDTO> getPersonsByHobby (String hobbyName);
    
    public List<HobbyDTO> getAllHobbies ();
    
    public HobbyDTO adminAddHobby (HobbyDTO hobby);
    
    public HobbyDTO adminEditHobby (HobbyDTO hobby);
    
    public HobbyDTO adminDeleteHobby (int id);
    
    public PersonDTO adminAddPerson (PersonDTO person);
    
    public PersonDTO adminEditPerson (PersonDTO person);
    
    public PersonDTO adminDeletePerson (int id);   
}
