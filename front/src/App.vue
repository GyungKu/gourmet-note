<template>
  <body :class="{ dark: isDark }">
    <Header @toggle-dark-mode="toggleDarkMode" v-if="route.name !== 'naverLogin'" />
    <div class="main-content">
      <RouterView />
      <a class="logo" target="_blank">
        <img src="@/assets/image/logo.png" alt="" />
      </a>
    </div>
  </body>
</template>

<script setup lang="ts">
import { onMounted, ref } from 'vue';
import Header from './components/Header.vue';
import { RouterView, useRoute } from 'vue-router';

const route = useRoute();
const isDark = ref<boolean>(false);

const toggleDarkMode = () => {
  isDark.value = !isDark.value;
  localStorage.setItem('darkMode', JSON.stringify(isDark.value));
};

onMounted(() => {
  const darkMode = localStorage.getItem('darkMode');
  if (darkMode !== null) {
    isDark.value = JSON.parse(darkMode);
  }
});
</script>

<style>
@import url('https://fonts.googleapis.com/css?family=Poppins:100,100i,200,200i,300,300i,400,400i,500,500i,600,600i,700,700i,800,800i,900,900i&subset=devanagari,latin-ext');

/* #Primary
================================================== */

body {
  font-family: 'Poppins', sans-serif;
  font-size: 16px;
  line-height: 24px;
  font-weight: 400;
  color: #212112;
  background-image: url('https://s3-us-west-2.amazonaws.com/s.cdpn.io/1462889/pat-back.svg');
  background-position: center;
  background-repeat: repeat;
  background-size: 7%;
  background-color: #fff;
  overflow-x: hidden;
  transition: all 200ms linear;
}

::selection {
  color: #fff;
  background-color: #8167a9;
}
::-moz-selection {
  color: #fff;
  background-color: #8167a9;
}

/* #Navigation
================================================== */

.start-header {
  opacity: 1;
  transform: translateY(0);
  padding: 20px 0;
  box-shadow: 0 10px 30px 0 rgba(138, 155, 165, 0.15);
  -webkit-transition: all 0.3s ease-out;
  transition: all 0.3s ease-out;
}
.navigation-wrap {
  position: fixed;
  width: 100%;
  top: 0;
  left: 0;
  z-index: 1000;
  -webkit-transition: all 0.3s ease-out;
  transition: all 0.3s ease-out;
}
.navbar {
  padding: 0;
}
.navbar-brand img {
  height: 28px;
  width: auto;
  display: block;
  /* filter: brightness(10%); */
  -webkit-transition: all 0.3s ease-out;
  transition: all 0.3s ease-out;
}
.navbar-toggler {
  float: right;
  border: none;
  padding-right: 0;
}
.navbar-toggler:active,
.navbar-toggler:focus {
  outline: none;
}
.navbar-light .navbar-toggler-icon {
  width: 24px;
  height: 17px;
  background-image: none;
  position: relative;
  border-bottom: 1px solid #000;
  transition: all 300ms linear;
}
.navbar-light .navbar-toggler-icon:after,
.navbar-light .navbar-toggler-icon:before {
  width: 24px;
  position: absolute;
  height: 1px;
  background-color: #000;
  top: 0;
  left: 0;
  content: '';
  z-index: 2;
  transition: all 300ms linear;
}
.navbar-light .navbar-toggler-icon:after {
  top: 8px;
}
.navbar-toggler[aria-expanded='true'] .navbar-toggler-icon:after {
  transform: rotate(45deg);
}
.navbar-toggler[aria-expanded='true'] .navbar-toggler-icon:before {
  transform: translateY(8px) rotate(-45deg);
}
.navbar-toggler[aria-expanded='true'] .navbar-toggler-icon {
  border-color: transparent;
}
.nav-link {
  color: #212121 !important;
  font-weight: 500;
  transition: all 200ms linear;
}
.nav-item:hover .nav-link {
  color: #8167a9 !important;
}
.nav-item.active .nav-link {
  color: #777 !important;
}
.nav-link {
  position: relative;
  padding: 5px 0 !important;
  display: inline-block;
}
.nav-item:after {
  position: absolute;
  bottom: -5px;
  left: 0;
  width: 100%;
  height: 2px;
  content: '';
  background-color: #8167a9;
  opacity: 0;
  transition: all 200ms linear;
}
.nav-item:hover:after {
  bottom: 0;
  opacity: 1;
}
.nav-item.active:hover:after {
  opacity: 0;
}
.nav-item {
  position: relative;
  transition: all 200ms linear;
}

/* #Primary style
================================================== */

.bg-light {
  background-color: #fff !important;
  transition: all 200ms linear;
}
h1 {
  font-size: 48px;
  line-height: 1.2;
  font-weight: 700;
  color: #212112;
  text-align: center;
}
p {
  text-align: center;
  margin: 0;
  padding-top: 10px;
  /* opacity: 1; */
  /* transform: translate(0);
  transition: all 300ms linear; */
  /* transition-delay: 1700ms; */
}
h1 span {
  display: inline-block;
  transition: all 300ms linear;
  opacity: 1;
  transform: translate(0);
}

.dropdown-toggle::after {
  display: none;
}

.nav-item.show .dropdown-menu {
  opacity: 1;
  visibility: visible;
  max-height: 999px;
  transform: translate3d(0, 0px, 0);
}

body.dark {
  color: #fff;
  background-color: #1f2029;
}
body.dark .navbar-brand img {
  filter: brightness(100%);
}
body.dark h1 {
  color: #fff;
}
body.dark h1 span {
  transition-delay: 0ms !important;
}
body.dark p {
  color: #fff;
  /* transition-delay: 0ms !important; */
}
body.dark .bg-light {
  background-color: #14151a !important;
}
body.dark .start-header {
  box-shadow: 0 10px 30px 0 rgba(0, 0, 0, 0.15);
}
body.dark .nav-link {
  color: #fff !important;
}
body.dark .nav-item.active .nav-link {
  color: #999 !important;
}
body.dark .navbar-light .navbar-toggler-icon {
  border-bottom: 1px solid #fff;
}
body.dark .navbar-light .navbar-toggler-icon:after,
body.dark .navbar-light .navbar-toggler-icon:before {
  background-color: #fff;
}
body.dark .navbar-toggler[aria-expanded='true'] .navbar-toggler-icon {
  border-color: transparent;
}

/* #Media
================================================== */

@media (max-width: 767px) {
  h1 {
    font-size: 38px;
  }
  .nav-item:after {
    display: none;
  }
  .nav-item::before {
    position: absolute;
    display: block;
    top: 15px;
    left: 0;
    width: 11px;
    height: 1px;
    content: '';
    border: none;
    background-color: #000;
    vertical-align: 0;
  }

  body.dark .nav-item::before {
    background-color: #fff;
  }
}

/* #Link to page
================================================== */

.logo {
  position: absolute;
  bottom: 30px;
  right: 30px;
  display: block;
  z-index: 100;
  transition: all 250ms linear;
}
.logo img {
  height: 26px;
  width: auto;
  display: block;
  filter: brightness(10%);
  transition: all 250ms linear;
}
body.dark .logo img {
  filter: brightness(100%);
}
</style>
