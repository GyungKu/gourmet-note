import { createApp } from 'vue';
import { createPinia } from 'pinia';

import App from './App.vue';
import router from './router';
import axios from 'axios';

const app = createApp(App);

axios.defaults.baseURL = `${import.meta.env.VITE_BASE_URL}`;
axios.defaults.headers.common['Content-Type'] = 'application/json';
axios.defaults.withCredentials = true;
app.config.globalProperties.axios = axios;

axios.interceptors.response.use(
  (res) => {
    return res;
  },
  (err) => {
    if (err.response && err.response.status === 401) {
      localStorage.removeItem('username');
      location.href = '/login';
      alert('로그인이 필요한 서비스입니다.');
    }
    if (err.response && err.response.data.validationErrors) {
      const errors = err.response.data.validationErrors;
      errors.array.forEach((e: {}) => {
        console.log(e);
      });
    }
    return Promise.reject(err);
  },
);

app.use(createPinia()).use(router).mount('#app');
