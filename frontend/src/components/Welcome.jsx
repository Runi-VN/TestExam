import React from "react";
import { Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "../Welcome.css";

const Welcome = () => {
  return (
    <>
      <h1>Welcome Page</h1>
      <h6>
        <ins>After</ins> having set up the backend:
      </h6>
      <ol>
        <li>
          <code>npm install</code>
        </li>
        <li>
          <code>npm start</code>
        </li>
      </ol>
      <h6>Common issues:</h6>
      <ul>
        <li>
          <code>npm audit fix --force</code>
        </li>
      </ul>
      <h6>File overview</h6>
      <label>
        <i>src/components</i>
      </label>
      <br />
      <ul className="border border-secondary customBorder">
        <li>
          <code>Welcome.jsx</code> - Where you are
        </li>
        <li>
          <code>Swapi.jsx</code> - Handles fetch call to the backend (Using
          <a target="_blank" rel="noopener noreferrer" href="https://swapi.co/">
            {" "}
            https://swapi.co/
          </a>
          )
        </li>
        <li>
          <code>Login.jsx</code> - Handles login
        </li>
      </ul>
      <br />
      <label>
        <i>src/</i>
      </label>
      <br />
      <ul className="border border-secondary customBorder">
        <li>
          <code>apiFacade.jsx</code> - Utility to help with fetching,
          login/logout
        </li>
        <li>
          <code>App.js</code> - Main component, handles routes &{" "}
          <code>loggedIn</code>
        </li>
        <li>
          <code>index.js</code> - Render component
        </li>
        <li>
          <code>settings.js</code> - Holds global settings, such as fetch URLs
        </li>

        <span>
          <i>CSS files</i>
        </span>
      </ul>
      <p>
        <Link to="#/login">Login</Link> using the credentials you set in the
        backend <code>utils\SetupTestUsers.java</code>
      </p>
    </>
  );
};

export default Welcome;
