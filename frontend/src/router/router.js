// src/router/router.js
import Vue from 'vue';
import Router from 'vue-router';
import HomePage from '../components/HomePage.vue';
import Movies from '../components/MoviesPage.vue';

Vue.use(Router);

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      name: 'HomePage',
      component: HomePage
    },
    {
      path: '/movies',
      name: 'Movies',
      component: Movies
    }
  ]
});
