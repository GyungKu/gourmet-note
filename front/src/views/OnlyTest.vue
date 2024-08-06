<template>
  <div id="carouselExampleControls" class="carousel slide" data-ride="carousel">
    <div class="carousel-inner">
      <div class="carousel-item active">
        <div class="carousel-caption">
          <img src="@/assets/image/noImage.png" />
        </div>
      </div>
      <div class="carousel-item">
        <div class="carousel-caption">
          <img src="@/assets/image/naverLogin.png" />
        </div>
      </div>
    </div>
    <a class="carousel-control-prev" href="#" role="button" data-slide="prev">
      <span class="carousel-control-prev-icon" aria-hidden="true"></span>
      <span class="sr-only">Previous</span>
    </a>
    <a class="carousel-control-next" href="#" role="button" data-slide="next">
      <span class="carousel-control-next-icon" aria-hidden="true"></span>
      <span class="sr-only">Next</span>
    </a>
  </div>
</template>

<style scoped>
.carousel {
  margin-top: 250px;
}
.carousel-inner {
  height: 100px;
}
.carousel-caption {
  color: #fff;
  top: 50%;
}
</style>
