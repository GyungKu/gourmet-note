<template>
  <div class="navigation-wrap bg-light start-header start-style">
    <div class="container">
      <div class="row">
        <div class="col-12">
          <nav class="navbar navbar-expand-md navbar-light">
            <router-link class="navbar-brand" :to="{ name: 'home' }" href="#">
              <img src="@/assets/image/logo.png" alt="Logo" />
            </router-link>

            <button
              class="navbar-toggler"
              type="button"
              @click="toggleNavbar"
              aria-controls="navbarSupportedContent"
              :aria-expanded="isNavbarOpen"
              aria-label="Toggle navigation"
            >
              <span class="navbar-toggler-icon"></span>
            </button>

            <div
              class="collapse navbar-collapse"
              :class="{ show: isNavbarOpen }"
              id="navbarSupportedContent"
            >
              <ul class="navbar-nav ml-auto py-4 py-md-0">
                <li
                  class="nav-item pl-4 pl-md-0 ml-0 ml-md-4"
                  :class="{ active: route.name === 'home' }"
                >
                  <router-link class="nav-link" :to="{ name: 'home' }" @click="handleNavClick"
                    >Home</router-link
                  >
                </li>
                <li
                  class="nav-item pl-4 pl-md-0 ml-0 ml-md-4"
                  :class="{ active: route.name === 'login' }"
                  v-if="!username"
                >
                  <router-link class="nav-link" :to="{ name: 'login' }" @click="handleNavClick"
                    >Login</router-link
                  >
                </li>
                <li
                  class="nav-item pl-4 pl-md-0 ml-0 ml-md-4"
                  :class="{ active: route.name === 'searchShop' }"
                  v-if="username"
                >
                  <router-link class="nav-link" :to="{ name: 'searchShop' }" @click="handleNavClick"
                    >Search</router-link
                  >
                </li>
                <li
                  class="nav-item pl-4 pl-md-0 ml-0 ml-md-4"
                  :class="{ active: route.name === 'myReview' }"
                  v-if="username"
                >
                  <router-link class="nav-link" :to="{ name: 'myReview' }" @click="handleNavClick"
                    >MyReview</router-link
                  >
                </li>
                <li
                  class="nav-item pl-4 pl-md-0 ml-0 ml-md-4"
                  :class="{ active: route.name === 'myPage' }"
                  v-if="username"
                >
                  <router-link class="nav-link" :to="{ name: 'myPage' }" @click="handleNavClick"
                    >MyPage</router-link
                  >
                </li>
                <li class="nav-item pl-4 pl-md-0 ml-0 ml-md-4" v-if="username">
                  <a class="nav-link" @click="logout" href="#">Logout</a>
                </li>
                <li class="nav-item pl-4 pl-md-0 ml-0 ml-md-4">
                  <a class="nav-link" @click.prevent="toggleDarkMode" href="#">dark-mode</a>
                </li>
              </ul>
            </div>
          </nav>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { useLoginStore } from '@/stores/UserLoginStore';
import axios from 'axios';
import { ref } from 'vue';
import { useRoute } from 'vue-router';

defineOptions({ name: 'AppHeader' });
const emit = defineEmits<{
  (e: 'toggle-dark-mode'): void;
}>();
const route = useRoute();
const loginStore = useLoginStore();
const username = loginStore.username;
const isNavbarOpen = ref<boolean>(false);

const toggleNavbar = () => {
  isNavbarOpen.value = !isNavbarOpen.value;
};

const handleNavClick = () => {
  isNavbarOpen.value = false;
};

const toggleDarkMode = () => {
  emit('toggle-dark-mode');
};

const logout = () => {
  localStorage.removeItem('username');
  axios.get('/users/logout');
  location.href = '/';
};
</script>

<style scoped></style>
