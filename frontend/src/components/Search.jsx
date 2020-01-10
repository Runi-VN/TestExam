import React, { useState } from "react";
import facade from "../apiFacade";

const Search = ({ permission }) => {
  if (!permission) {
    return (
      <>
        <p>You are not logged in</p>
      </>
    );
  } else {
    return (
      <>
        <SearchFunction />
      </>
    );
  }
};

const SearchFunction = () => {
  const [persons, setPersons] = useState();
  const [hobbies, setHobbies] = useState();
  const [errorMessage, setErrorMessage] = useState();

  const [ID, setID] = useState(0);

  const getPersonData = (endpoint, value) => {
    facade
      .fetchGetData(endpoint, value)
      .then(res => {
        console.log(res);
        setPersons(res);
        setErrorMessage("");
      })
      .catch(err => {
        if (err.status) {
          err.fullError.then(e => {
            setErrorMessage(e.message);
            console.log(e.code, e.message);
          });
        } else {
          console.log("Network error");
        }
      });
  };

  const handleChange = event => {
    setID(event.target.value);
  };

  const handleSubmit = () => {
    getPersonData("id", ID);
    setID(0);
  };

  return (
    <>
      <h4>{errorMessage}</h4>
      <h1>Search for people and hobbies</h1>
      <input id="personID" value={ID} type="number" onChange={handleChange} />
      <button type="button" onClick={handleSubmit}>
        Get by ID
      </button>
      {persons && <MemberTable members={!Array.isArray(persons) ? [persons] : persons} />}
    </>
  );
};

function MemberTable({ members }) {
  const tableItems = members.map(member => (
    <Row
      key={member.id}
      fName={member.fName}
      id={member.id}
      hobbylist={member.hobbylist}
      lName={member.lName}
      mail={member.mail}
      residence={member.residence}
      telephone={member.telephone}
    />
  ));
  return (
    <table className="table">
      <thead>
        <tr>
          <th>First Name</th>
          <th>Hobbies</th>
          <th>ID</th>
          <th>Last Name</th>
          <th>Email</th>
          <th>Address</th>
          <th>City</th>
          <th>Phone</th>
        </tr>
      </thead>
      <tbody>{tableItems}</tbody>
    </table>
  );
}

function Row(props) {
  console.log(props.hobbylist);
  return (
    <tr>
      <td>{props.fName}</td>
      <td>
      {props.hobbylist.map((element, index) => {
        return <li key={index}>{element.hobbyName}</li>;
      })}
      </td>
      <td>{props.id}</td>
      <td>{props.lName}</td>
      <td>{props.mail}</td>
      <td>{(props.residence.road)}</td>
      <td>{(props.residence.town)}</td>
      <td>{props.telephone}</td>
    </tr>
  );
}


export default Search;
