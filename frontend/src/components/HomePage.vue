<template>
  <div class="home">
    <h1>{{ msg }}</h1>
    <router-link to="/movies" class="btn btn-primary">Movies Page</router-link>
  </div>
</template>

<script>
export default {
  name: 'HomePage',
  data() {
    return {
      msg: "Welcome to Your Vue.js App"
    };
  }
};
</script>

<style scoped>
.home {
  text-align: center;
  margin-top: 50px;
}
</style>
