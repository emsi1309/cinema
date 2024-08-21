<template>
    <div class="container mt-5">
      <h2>Add New Movie</h2>
      <form @submit.prevent="submitForm" enctype="multipart/form-data">
        <div class="mb-3">
          <label for="title" class="form-label">Title:</label>
          <input type="text" v-model="title" id="title" class="form-control" required />
        </div>
        <div class="mb-3">
          <label for="images" class="form-label">Image Files:</label>
          <input type="file" @change="handleImagesChange" id="images" class="form-control" multiple required />
        </div>
        <div class="mb-3">
          <label for="trailer" class="form-label">Trailer File:</label>
          <input type="file" @change="handleTrailerChange" id="trailer" class="form-control" required />
        </div>
        <button type="submit" class="btn btn-primary">Submit</button>
      </form>

      <h2 class="mt-5">Movie List</h2>
      <table class="table table-striped">
        <thead>
        <tr>
          <th>Title</th>
          <th>Images</th>
          <th>Trailer</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="movie in movies" :key="movie.id">
          <td>{{ movie.title }}</td>
          <td>
            <div v-if="movie.imageUrls && movie.imageUrls.length > 0">
              <img :src="`${urlImage}${movie.imageUrls[0]}`" alt="Movie Image" class="movie-image" width="140" height="240" />
            </div>
          </td>

          <td>
            <div v-if="movie.trailerUrl">
              <video controls width="320" height="240">
                <source :src="`${urlImage}${movie.trailerUrl}`" type="video/mp4" />
                Your browser does not support the video tag.
              </video>
            </div>
          </td>
        </tr>
        </tbody>
      </table>

    </div>
  </template>
  
  <script>
//  import axios from 'axios';
  import movieService from '../services/MovieService.js';
  
  export default {
    data() {
      return {
        title: '',
        images: [],
        trailer: null,
        movies:[],
        urlImage: 'http://localhost:8081'
      };
    },
    methods: {
      handleImagesChange(event) {
        this.images = event.target.files;
      },
      handleTrailerChange(event) {
        this.trailer = event.target.files[0];
      },
      async submitForm() {
        const formData = new FormData();
        formData.append('title', this.title);
  
        for (let i = 0; i < this.images.length; i++) {
          formData.append('images', this.images[i]);
        }

        formData.append('trailer', this.trailer);

        try{
          const response = await movieService.createMovie(formData);
            console.log('Movie added successfully:', response.data);
            if (response.status === 201) {
              console.log(response.status);
            }
        }catch (error) {
            console.error('Failed to add movie:', error.message);

        }
      },
      async fetchMovies() {
        try {
          this.movies = await movieService.getAllMovies();
          console.log(this.movies);
        } catch (error) {
          console.error('Failed to fetch movies:', error.message);
        }
      }
    },
    mounted() {
      this.fetchMovies();
    }
  };
  </script>
  
  <style scoped>
  .container {
    max-width: 600px;
  }
  </style>
  