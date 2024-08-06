<template>
  <div class="container">
    <div class="hero-anime">
      <div class="section full-height">
        <div class="absolute-center">
          <div class="section">
            <div class="container">
              <div class="row">
                <div class="col-12">
                  <h1>
                    <span>반</span><span>갑</span><span>습</span><span>니</span><span>다</span
                    ><br /><span>식</span><span>도</span><span>락</span> <span>노</span>
                    <span>트</span><span>입</span><span>니</span><span>다</span>
                  </h1>
                  <p v-if="!username">로그인을 하시면 서비스를 이용하실 수 있습니다.</p>
                  <p v-if="username">환영합니다 {{ username }}님</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
const username = localStorage.getItem('username');
</script>

<style scoped>
.section {
  position: relative;
  width: 100%;
  display: block;
}
.full-height {
  height: 100vh;
}
.over-hide {
  overflow: hidden;
}
.absolute-center {
  position: absolute;
  top: 50%;
  left: 0;
  width: 100%;
  margin-top: 40px;
  transform: translateY(-50%);
  z-index: 20;
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
  opacity: 1;
  transform: translate(0);
  transition: all 300ms linear;
  transition-delay: 1700ms;
}
body.hero-anime p {
  opacity: 0;
  transform: translateY(40px);
  transition-delay: 1700ms;
}
body.hero-anime h1 span:nth-child(1) {
  opacity: 0;
  transform: translateY(-20px);
}
body.hero-anime h1 span:nth-child(2) {
  opacity: 0;
  transform: translateY(-30px);
}
body.hero-anime h1 span:nth-child(3) {
  opacity: 0;
  transform: translateY(-50px);
}
body.hero-anime h1 span:nth-child(4) {
  opacity: 0;
  transform: translateY(-10px);
}
body.hero-anime h1 span:nth-child(5) {
  opacity: 0;
  transform: translateY(-50px);
}
body.hero-anime h1 span:nth-child(6) {
  opacity: 0;
  transform: translateY(-20px);
}
body.hero-anime h1 span:nth-child(7) {
  opacity: 0;
  transform: translateY(-40px);
}
body.hero-anime h1 span:nth-child(8) {
  opacity: 0;
  transform: translateY(-10px);
}
body.hero-anime h1 span:nth-child(9) {
  opacity: 0;
  transform: translateY(-30px);
}
body.hero-anime h1 span:nth-child(10) {
  opacity: 0;
  transform: translateY(-20px);
}
h1 span:nth-child(1) {
  transition-delay: 1000ms;
}
h1 span:nth-child(2) {
  transition-delay: 700ms;
}
h1 span:nth-child(3) {
  transition-delay: 900ms;
}
h1 span:nth-child(4) {
  transition-delay: 800ms;
}
h1 span:nth-child(5) {
  transition-delay: 1000ms;
}
h1 span:nth-child(6) {
  transition-delay: 700ms;
}
h1 span:nth-child(7) {
  transition-delay: 900ms;
}
h1 span:nth-child(8) {
  transition-delay: 800ms;
}
h1 span:nth-child(9) {
  transition-delay: 600ms;
}
h1 span:nth-child(10) {
  transition-delay: 700ms;
}
body.hero-anime h1 span:nth-child(11) {
  opacity: 0;
  transform: translateY(30px);
}
body.hero-anime h1 span:nth-child(12) {
  opacity: 0;
  transform: translateY(50px);
}
body.hero-anime h1 span:nth-child(13) {
  opacity: 0;
  transform: translateY(20px);
}
body.hero-anime h1 span:nth-child(14) {
  opacity: 0;
  transform: translateY(30px);
}
body.hero-anime h1 span:nth-child(15) {
  opacity: 0;
  transform: translateY(50px);
}
h1 span:nth-child(11) {
  transition-delay: 1300ms;
}
h1 span:nth-child(12) {
  transition-delay: 1500ms;
}
h1 span:nth-child(13) {
  transition-delay: 1400ms;
}
h1 span:nth-child(14) {
  transition-delay: 1200ms;
}
h1 span:nth-child(15) {
  transition-delay: 1450ms;
}

body.dark p {
  transition-delay: 0ms !important;
}
</style>
