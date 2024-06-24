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
    </div>
  </template>
  
  <script>
  import axios from 'axios';
  
  export default {
    data() {
      return {
        title: '',
        images: [],
        trailer: null
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
  
        try {
          const response = await axios.post('http://localhost:8080/movies', formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          });
  
          console.log('Movie added successfully:', response.data);
        } catch (error) {
          console.error('Failed to add movie:', error.message);
        }
      }
    }
  };
  </script>
  
  <style scoped>
  .container {
    max-width: 600px;
  }
  </style>
  