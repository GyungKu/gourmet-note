<template>
  <div class="container">
    <div class="login-box">
      <h2>Login</h2>
      <div class="user-box">
        <input type="text" v-model="requestLogin.email" @keydown.enter="login" />
        <label>Username</label>
      </div>
      <div class="user-box">
        <input type="password" v-model="requestLogin.password" @keydown.enter="login" />
        <label>Password</label>
      </div>
      <div class="button-row">
        <button type="button" @click="naverLogin" class="login-button">
          <img src="@/assets/image/naverLogin.png" />
        </button>
        <button type="button" class="custom-btn btn-1" @click="login">TestLogin</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import axios from 'axios';
import { ref } from 'vue';

const requestLogin = ref<{ email: string; password: string }>({ email: '', password: '' });

const login = async () => {
  if (!requestLogin.value.email && !requestLogin.value.password) {
    alert('항목을 모두 입력해주세요');
    return;
  }

  try {
    const loginRes = await axios.post('/v1/users/login', requestLogin.value);
    localStorage.setItem('username', loginRes.data.username);
    location.href = '/';
    alert('로그인 성공');
  } catch {
    alert('로그인 실패');
  }
};

const naverLogin = () => {
  const REDIRECT_URL = `${import.meta.env.VITE_REDIRECT_URL}/naver`;
  const NAVER_CLIENT_ID = import.meta.env.VITE_NAVER_CLIENT_ID;
  const url = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${NAVER_CLIENT_ID}&redirect_uri=${REDIRECT_URL}&state=1234`;
  window.open(url, 'popup', 'width=600,height=600');
};
</script>

<style scoped>
.container {
  height: 100vh;
}
.login-box {
  position: absolute;
  top: 50%;
  left: 50%;
  width: 400px;
  padding: 40px;
  transform: translate(-50%, -50%);
  background: rgba(0, 0, 0, 0.5);
  box-sizing: border-box;
  box-shadow: 0 15px 25px rgba(0, 0, 0, 0.6);
  border-radius: 10px;
}

.login-box h2 {
  margin: 0 0 30px;
  padding: 0;
  color: #fff;
  text-align: center;
}

.login-box .user-box {
  position: relative;
  margin-bottom: 20px;
}

.login-box .user-box input {
  width: 100%;
  padding: 10px 0;
  font-size: 16px;
  color: #fff;
  margin-bottom: 30px;
  border: none;
  border-bottom: 1px solid #fff;
  outline: none;
  background: transparent;
}
.login-box .user-box label {
  position: absolute;
  top: 0;
  left: 0;
  padding: 10px 0;
  font-size: 16px;
  color: #fff;
  pointer-events: none;
  transition: 0.5s;
}

.login-box .user-box input:focus ~ label,
.login-box .user-box input:valid ~ label {
  top: -20px;
  left: 0;
  color: #03e9f4;
  font-size: 12px;
}

.login-box a {
  position: relative;
  display: inline-block;
  padding: 10px 20px;
  color: #03e9f4;
  font-size: 16px;
  text-decoration: none;
  text-transform: uppercase;
  overflow: hidden;
  transition: 0.5s;
  margin-top: 40px;
  letter-spacing: 4px;
}

.login-box .button-row {
  display: flex;
  justify-content: space-between; /* Align buttons to the left and right */
  margin-top: 20px;
}

.login-box .button-row .login-button,
.login-box .button-row .custom-btn {
  background: none;
  border: none;
  cursor: pointer;
  display: flex;
  justify-content: center;
}

.login-box .button-row .login-button img {
  width: 50px;
  height: auto;
}

.custom-btn {
  width: 130px;
  height: 40px;
  color: #fff;
  border-radius: 5px;
  padding: 10px 25px;
  font-family: 'Lato', sans-serif;
  font-weight: 500;
  background: transparent;
  cursor: pointer;
  transition: all 0.3s ease;
  box-shadow:
    inset 2px 2px 2px 0px rgba(255, 255, 255, 0.5),
    7px 7px 20px 0px rgba(0, 0, 0, 0.1),
    4px 4px 5px 0px rgba(0, 0, 0, 0.1);
  outline: none;
  display: flex;
  justify-content: center;
  margin-top: 10px;
}

.login-box .login-button-container {
  display: flex;
  justify-content: flex-end;
}

.login-box .login-button-container .custom-btn {
  margin-left: auto;
}
</style>
