import Vue from 'vue';
import App from './App.vue';
import router from './router/router'; // Sửa đường dẫn import đúng cấu trúc thư mục

import 'bootstrap/dist/css/bootstrap.min.css';

Vue.config.productionTip = false;

new Vue({
  router,
  render: h => h(App),
}).$mount('#app');
