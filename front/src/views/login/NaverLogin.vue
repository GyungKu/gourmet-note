<template>
  <div class="container">
    <div id="login-button">
      <img src="https://dqcgrsy5v35b9.cloudfront.net/cruiseplanner/assets/img/icons/login-w-icon.png">
      </img>
    </div>
  </div>
</template>

<script setup lang="ts">
import axios from 'axios';
import { onMounted } from 'vue';
import { useRoute } from 'vue-router';

const route = useRoute();

onMounted(() => {
  const code = route.query.code;
  axios
    .get('/v1/users/naver', {
      params: {
        code: code,
      },
    })
    .then((res) => {
      if (res.data.type === 'signup') {
        alert('회원가입 완료');
      } else {
        alert('로그인 완료');
      }
      localStorage.setItem('username', res.data.username);
      window.opener.location.href = '/';
      window.close();
    })
    .catch(() => {
      window.close();
      alert('로그인에 실패했습니다.');
    });
});
</script>

<style scoped>
.container {
  height: 100vh;
}
img{
  display: block;
  margin: auto;
  width: 100%;
  height: auto;
}

#login-button{
  cursor: pointer;
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  padding: 30px;
  margin: auto;
  width: 100px;
  height: 100px;
  border-radius: 50%;
  background: rgba(3,3,3,.8);
  overflow: hidden;
  opacity: 0.4;
  box-shadow: 10px 10px 30px #000;}
</style>