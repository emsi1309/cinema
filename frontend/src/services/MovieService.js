import axios from 'axios';

const BASEURL = "http://localhost:8080/movies";


// Tạo instance của axios
const apiClient = axios.create({
    baseURL: BASEURL,
    headers: {
        'Content-Type': 'multipart/form-data',
    },
});

// Hàm tạo movie
export const createMovie = async (movieData) => {
    try {
        return await apiClient.post("/addnew", movieData);
    } catch (error) {
        console.error('Error creating movie:', error);
        throw error;
    }
};

export const getAllMovies = async () =>{
    try {
        const response = await apiClient.get('/allmovie');
        return response.data;
    } catch (error) {
        console.error('Error fetching movies:', error);
        throw error;
    }

}

export default {
    createMovie,getAllMovies,
};
