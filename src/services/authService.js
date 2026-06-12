import axios from "axios";

const BASE_URL = "http://localhost:8080/api/auth";

export const sendOtp = (data) => {
  return axios.post(`${BASE_URL}/send-otp`, data);
};

export const registerUser = (data) => {
  return axios.post(`${BASE_URL}/register`, data);
};

export const loginUser = (data) => {
  return axios.post(`${BASE_URL}/login`, data);
};

export const verifyOtp = (email, otp) => {
  return axios.post(`${BASE_URL}/verify-otp`, { email, otp });
};

export const resendOtp = (email) => {
  return axios.post(`${BASE_URL}/resend-otp`, { email });
};

export const getToken = () => {
  return localStorage.getItem("token");
};