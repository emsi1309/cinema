// src/router/router.js
import Vue from 'vue';
import Router from 'vue-router';
import HomePage from '../components/HomePage.vue';
import AddMovie from '../components/AddMovie.vue';

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
      path: '/add-movie',
      name: 'AddMovie',
      component: AddMovie
    }
  ]
});
