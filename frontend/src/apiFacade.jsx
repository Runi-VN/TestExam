//Change this before deployment (production)
import { localURL as URL } from "./settings.js";
function handleHttpErrors(res) {
  if (!res.ok) {
    return Promise.reject({ status: res.status, fullError: res.json() });
  }
  return res.json();
}

const ApiFacade = () => {
  //Insert utility-methods from a latter step (d) here
  function makeOptions(method, addToken, body) {
    var opts = {
      method: method,
      headers: {
        "Content-type": "application/json",
        Accept: "application/json"
      }
    };
    if (addToken && loggedIn()) {
      opts.headers["x-access-token"] = getToken();
    }
    if (body) {
      opts.body = JSON.stringify(body);
    }
    return opts;
  }

  function setToken(token) {
    localStorage.setItem("jwtToken", token);
  }

  function getToken() {
    return localStorage.getItem("jwtToken");
  }

  function loggedIn() {
    const loggedIn = getToken() != null;
    return loggedIn;
  }

  // const fetchSwapi = () => {
  //   //Remember to always include options from the makeOptions fucntion with >true< as the second parameter
  //   //if you want to access a protected endpoint
  //   const options = makeOptions("GET", true); //True add's the token
  //   return fetch(URL + "/api/info/five", options).then(handleHttpErrors);
  // };

  const fetchGetData = (endpoint, value) => {
    const options = makeOptions("GET", true); //True add's the token
    return fetch(URL + `/api/krak/${endpoint}/${value}`, options).then(handleHttpErrors);
  };

  const getRole = () => {
    let jwt = localStorage.getItem("jwtToken");
    let jwtData = jwt.split(".")[1];
    let decodedJwtJsonData = window.atob(jwtData);
    let decodedJwtData = JSON.parse(decodedJwtJsonData);
    return decodedJwtData.roles;
  };

  const login = (user, pass, setRoles) => {
    const options = makeOptions("POST", true, {
      username: user,
      password: pass
    });
    return fetch(URL + "/api/login", options)
      .then(handleHttpErrors)
      .then(res => {
        setToken(res.token);
        setRoles(getRole());
      });
  };

  const logout = () => {
    localStorage.removeItem("jwtToken");
  };

  return {
    login,
    logout,
    fetchGetData
  };
};

let returnVal = ApiFacade();
export default returnVal;
