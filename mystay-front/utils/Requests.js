import axios from "axios";
import * as SecureStore from 'expo-secure-store';

const headerBuilder = async (headers) => {
    let token = await getToken();
    return {
        ...headers, "Authorization": `Bearer ${token}`
    }
}

var instance = axios.create({
    baseURL: 'http://localhost:8443'
});

const getToken = async () => {
    return await SecureStore.getItemAsync('userToken');
}

export const get = async (path, headers) => {
    try{
        headers = await headerBuilder(headers);
        const response = await instance.get(`/${path}`, {headers, timeout: 3000});
        return response.data;
    } catch (error) {
        if (axios.isAxiosError(error) && error.code === 'ECONNABORTED') {
            
            throw new Error('No estas conectado a la wifi del hotel');
        } else if (axios.isAxiosError(error) && !error.response) {
            console.log(error.toJSON())

            throw new Error('No estas conectado a la wifi del hotel');
        } else {
            throw error;
        }
    }
}

export const post = async (path, body, headers) => {
    try {
        headers = await headerBuilder(headers);
        const response = await instance.post(`/${path}`, body, { headers, timeout: 3000 });
        return response.data;
    } catch (error) {
        if (axios.isAxiosError(error) && error.code === 'ECONNABORTED') {
            throw new Error('No estas conectado a la wifi del hotel');
        } else if (axios.isAxiosError(error) && !error.response) {
            throw new Error('No estas conectado a la wifi del hotel');
        } else {
            throw error;
        }
    }
};

export const put = async (path, body, headers) => {
    try{
        headers = await headerBuilder(headers);
        const response = await instance.put(`/${path}`, body, {headers, timeout: 3000});
        return response.data;
    } catch (error) {
        if (axios.isAxiosError(error) && error.code === 'ECONNABORTED') {
            throw new Error('No estas conectado a la wifi del hotel');
        } else if (axios.isAxiosError(error) && !error.response) {
            throw new Error('No estas conectado a la wifi del hotel');
        } else {
            throw error;
        }
    }
}

export const validJWT = (errMsg, signOut) => {
    if (errMsg === "Invalid JWT Token"){
        alert("Sesion invalida vuelve a entrar")
        signOut()
        return false;
    }

    return true;
}