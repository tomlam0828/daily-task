import axios from "axios";
import { GET_ERRORS, SET_CURRENT_USER } from "./types";
import setJWTToken from "../securityUtils/setJWTToken";
import jwt_decode from "jwt-decode";

export const createNewUser = (newUser, history) => async dispatch => {
    try {
        await axios.post("/api/users/register", newUser);
        history.push("/login");
        dispatch({
            type: GET_ERRORS,
            payload: {}
        })
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        })
    }
}

export const login = LoginRequest => async dispatch => {
    try {
        // post action => login request
        const res = await axios.post("/api/users/login", LoginRequest);
        // extract token from res.data
        const { token } = res.data
        // store token in localstorage
        localStorage.setItem("jwtToken", token);
        // set token in header (important)
        setJWTToken(token);
        // decode token on react
        const decoded = jwt_decode(token);
        // dispatch to securityreducer
        dispatch({
            type: SET_CURRENT_USER,
            payload: decoded
        })
    } catch (error) {
        dispatch({
            type: GET_ERRORS,
            payload: error.response.data
        })
    }

}