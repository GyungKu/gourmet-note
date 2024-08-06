<template>
  <div class="container">
    <div class="login-box">
      <h2>Login</h2>
      <form>
        <div class="user-box">
          <button @click="naverLogin" class="login-button">
            <img src="@/assets/image/naverLogin.png" />
          </button>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup lang="ts">
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
  display: flex;
  justify-content: center;
  align-items: center;
  height: 100%;
}

.login-box form a {
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

.login-box .user-box button {
  background: none;
  border: none;
  padding: 0;
  cursor: pointer;
  display: flex;
  justify-content: center;
}

.login-box .user-box img {
  width: 50px;
  height: auto;
}
</style>
