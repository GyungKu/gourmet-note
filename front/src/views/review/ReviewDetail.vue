<template>
  <div class="container">
    <div class="review-container">
      <div class="carousel-container">
        <div id="carouselExampleControls" class="carousel slide" data-bs-ride="carousel">
          <div class="carousel-inner">
            <div
              class="carousel-item"
              data-bs-interval="2000"
              v-if="review.images"
              v-for="(image, idx) in review.images"
              :class="{ active: idx === 0 }"
              :key="idx"
            >
              <img :src="image.url" class="d-block w-100" alt="..." />
            </div>
            <div v-if="!review.images" class="carousel-item active">
              <img src="@/assets/image/noImage.png" class="d-block w-100" alt="..." />
            </div>
          </div>
          <div v-if="review.images && review.images.length > 1">
            <a
              class="carousel-control-prev"
              href="#carouselExampleControls"
              role="button"
              data-slide="prev"
            >
              <span class="carousel-control-prev-icon" aria-hidden="true"></span>
              <span class="sr-only">Previous</span>
            </a>
            <a
              class="carousel-control-next"
              href="#carouselExampleControls"
              role="button"
              data-slide="next"
            >
              <span class="carousel-control-next-icon" aria-hidden="true"></span>
              <span class="sr-only">Next</span>
            </a>
          </div>
        </div>
      </div>
      <div class="text-content">
        <div class="card">
          <div class="card-body review">
            <h5 class="card-title">{{ review.shop.title }}</h5>
            <h6 class="card-subtitle mb-2 text-body-secondary">{{ review.shop.address }}</h6>
            <p class="card-text">
              {{ review.content ? review.content : '리뷰 내용이 없습니다.' }}
            </p>
            <small>평점: {{ review.rating ? review.rating : 'x' }}</small>
            <div>
              <button>
                <router-link :to="{ name: 'editReview', id: review.id }">리뷰수정</router-link>
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <div class="items-card">
      <div class="row gx-4 gx-lg-5" v-if="review?.items">
        <div class="col-md-4 mb-5" v-for="item in review.items">
          <div class="card h-100">
            <div class="card-body">
              <h2 class="card-title">{{ item.name }}</h2>
              <p class="card-text">
                {{ item.content ? item.content : '리뷰내용이 없습니다.' }}
              </p>
            </div>
            <div class="card-footer">
              <small>평점: {{ item.rating ? item.rating : 'x' }}</small>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { ResponseReview } from '@/model/ResponseReview';
import axios from 'axios';
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const review = ref<ResponseReview>({ shop: { title: '', address: '' }, id: 0, createdAt: '' });
const route = useRoute();
const router = useRouter();

const fetchReview = () => {
  const reviewId = route.params.id;
  axios
    .get(`/v1/reviews/${reviewId}`)
    .then((res) => {
      review.value = res.data;
    })
    .catch((err) => {
      if (err.response.status === 403) {
        alert('접근 권한이 없습니다.');
        router.push({ name: 'myReview' });
      }
      if (err.response.status === 404) {
        router.push({ name: 'myReview' });
      }
    });
};

onMounted(() => {
  fetchReview();
});
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  height: 100vh;
  margin-top: 150px;
}

.review-container {
  display: flex;
  align-items: flex-start;
  justify-content: center;
  gap: 100px;
  margin-bottom: 50px;
}

.carousel-container {
  width: 100%;
  max-width: 450px;
  height: auto;
  overflow: hidden;
  position: relative;
}

.carousel-inner {
  width: 100%;
  height: auto;
}

.carousel-item img {
  width: 100%;
  height: auto;
  object-fit: cover;
}

.text-content {
  flex: 1;
  width: 100%;
  max-width: 600px;
  padding: 0 10px;
  height: auto;
}

.card-body.review {
  height: auto; /* 모바일에서는 높이 자동 조정 */
  padding: 1rem; /* 모바일에서 패딩 조정 */
}

.card-body {
  padding: 1.5rem;
}

.items-card {
  margin-top: 20px;
}

.card {
  margin-bottom: 20px;
}

.carousel-control-prev-icon,
.carousel-control-next-icon {
  background-color: rgba(0, 0, 0, 0.5);
}

body.dark .card-body {
  background-color: #10182f;
}
body.dark .card-footer {
  background-color: #172346;
}

@media (max-width: 768px) {
  .review-container {
    flex-direction: column;
    align-items: center;
    gap: 40px;
  }

  .carousel-container {
    width: 100%;
    height: auto;
  }

  .text-content {
    max-width: 100%;
  }

  .card-body.review {
    height: auto;
  }
}

@media (max-width: 576px) {
  .carousel-container {
    height: auto;
  }

  .card-body {
    padding: 1rem;
  }

  .items-card {
    margin-top: 10px;
  }
}
</style>
