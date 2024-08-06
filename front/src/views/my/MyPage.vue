<template>
  <div class="container">
    <div class="userinfo">
      <div class="info">
        <h4>{{ myInfo?.username }}</h4>
        <p>작성 리뷰: {{ myInfo?.reviewCount }}</p>
        <button class="button expand follow" @click="update">이름 수정</button>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import axios from 'axios';
import { onMounted, ref } from 'vue';

const myInfo = ref<{ username: string; reviewCount: number }>();

const getMyInfo = () => {
  axios.get('/v1/users').then((res) => {
    myInfo.value = res.data;
  });
};

const update = () => {
  const username = prompt('변경할 닉네임을 입력해주세요');

  if (!username || username?.length > 8 || username?.length < 2) {
    alert('닉네임의 길이는 2 ~ 8자 입니다.');
    return;
  }
  axios
    .patch('/v1/users', { username: username })
    .then((res) => {
      localStorage.setItem('username', res.data.username);
      alert('수정이 완료되었습니다.');
      location.reload();
    })
    .catch((err) => {
      alert(err.response.data.message);
    });
};

onMounted(() => {
  getMyInfo();
});
</script>

<style scoped>
.container {
  height: 100vh;
  display: flex;
}
p {
  font-size: 15px;
}
.userinfo {
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 20px rgba(0, 0, 0, 0.2);
  width: 350px;
  margin: auto;
  text-align: center;
  overflow: hidden;
}
.info {
  display: flex;
  flex-direction: column;
  justify-content: center;
  margin: auto;
  width: 283px;
  height: 250px;
  text-align: center;
}
.photo {
  width: 100px;
  height: 100px;
  border-radius: (50%);
  overflow: hidden;
  display: inline-block;
}
.info h4 {
  font-size: 32px;
  margin: 10px 0 0 0;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
}
p {
  margin: 5px 0 5px 0;
}
ul {
  margin: 0 0 20px 0;
  list-style: none;
  max-width: 200px;
  display: inline-block;
}
li {
  display: inline;
  font-size: 10px;
  padding-right: 10px;
}
&:after {
  content: '•';
  position: relative;
  left: 6px;
}
&:last-child &:after {
  content: '';
}
.point {
  margin-bottom: 30px;
}
p {
  margin: 0 0 5px 0;
}
.progress {
  margin: auto;
}
.follow {
  background: #7094e1;
  margin: 0;
  font-size: 16px;
  text-shadow: (1px 1px 0px #20386a);
  font-weight: 400;
}

body.dark .userinfo {
  background-color: #10182f;
}
</style>
