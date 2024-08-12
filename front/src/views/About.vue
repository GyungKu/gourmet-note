<template>
  <div class="container">
    <div class="about-text">
      <p>
        혹시 배달주문이나 식사를 할 식당을 고를 때, <br />
        이전에 방문했던 경험이 기억나지 않아서 고민해 본적이 있으신가요?<br />
        또는 공개적으로 리뷰를 남기기에는 부담스러운데, <br />
        좋았던 곳이나 그렇지 않았던 곳에 대한 기록을 남기고 싶으셨던 적이 있으신가요?<br />

        <br />

        이제 그런 고민을 해결해 드릴 수 있습니다.<br />
        저희 서비스는 음식점에 대한 리뷰를 비공개로 남길 수 있도록 도와드립니다.<br />
        개인적으로만 확인할 수 있는 기록을 남겨, 다음 번 방문 시 유용하게 참고하세요.<br />
        글뿐만 아니라 사진도 함께 업로드할 수 있습니다.<br />
      </p>
    </div>
  </div>
</template>

<style scoped>
.container {
  height: 100vh;
}
.about-text {
  position: absolute;
  top: 50%;
  left: 0;
  width: 100%;
  margin-top: 40px;
  transform: translateY(-50%);
  z-index: 20;
}
.about-text p {
  font-family: 'Georgia', serif;
  font-size: 20px;
}

body.dark .about-text p {
  color: white;
}
</style>
