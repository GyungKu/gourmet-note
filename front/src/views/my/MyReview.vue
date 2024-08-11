<template>
  <div class="container">
    <div class="card-container">
      <div class="card" v-for="(review, idx) in reviewList" :key="idx">
        <div class="card-header">
          <router-link href="#" :to="{ name: 'reviewDetail', params: { id: review.id } }">
            <img v-if="!review.images" src="@/assets/image/noImage.png" alt="rover" />
            <img v-if="review.images" :src="review.images[0].url" alt="rover" />
          </router-link>
        </div>
        <div class="card-body">
          <span class="tag tag-teal">카테고리?</span>
          <router-link :to="{ name: 'reviewDetail', params: { id: review.id } }">
            <h4>{{ review.shop.title }}</h4>
          </router-link>
          <p>{{ review.content ? review.content : '리뷰내용이 없습니다.' }}</p>
          <div class="review">
            <!-- <img
              src="https://yt3.ggpht.com/a/AGF-l7-0J1G0Ue0mcZMw-99kMeVuBmRxiPjyvIYONg=s900-c-k-c0xffffffff-no-rj-mo"
              alt="review"
            /> -->
            <div class="review-info">
              <h5>작성일: {{ formatDate(review.createdAt) }}</h5>
              <small>평점: {{ review.rating ? review.rating : 'x' }}</small>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Pagination -->
    <div class="pagination">
      <nav aria-label="Page navigation example">
        <ul class="pagination justify-content-center">
          <li class="page-item" v-if="responsePage.pageNumber > 2">
            <a class="page-link" @click.prevent="clickPage(1)" href="#">First</a>
          </li>
          <li class="page-item" v-if="responsePage.pageNumber > 5">
            <a class="page-link" @click.prevent="clickPage(startPage - 1)" href="#">Prev</a>
          </li>
          <li class="page-item" v-for="page in pages" :key="page">
            <a
              class="page-link"
              @click.prevent="clickPage(page)"
              :class="{ active: responsePage.pageNumber === page }"
              href="#"
              >{{ page }}</a
            >
          </li>
          <li class="page-item" v-if="responsePage.pageNumber < responsePage.totalPages">
            <a class="page-link" @click.prevent="clickPage(endPage + 1)" href="#">Next</a>
          </li>
          <li class="page-item" v-if="responsePage.pageNumber < responsePage.totalPages">
            <a class="page-link" @click.prevent="clickPage(responsePage.totalPages)" href="#"
              >Last</a
            >
          </li>
        </ul>
      </nav>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { ResponsePage } from '@/model/ResponsePage';
import type { ResponseReview } from '@/model/ResponseReview';
import axios from 'axios';
import { computed, onMounted, ref } from 'vue';

const reviewList = ref<ResponseReview[]>([]);
const responsePage = ref<ResponsePage>({ pageNumber: 1, totalPages: 1 });
const startPage = ref<number>(
  responsePage.value.pageNumber - ((responsePage.value.pageNumber - 1) % 5),
);
const endPage = ref<number>(startPage.value + 4);

const pages = computed(() => {
  const result = [];
  for (let i = startPage.value; i <= Math.min(endPage.value, responsePage.value.totalPages); i++) {
    result.push(i);
  }
  return result;
});

const fetchReview = () => {
  axios
    .get('/v1/reviews', {
      params: {
        page: responsePage.value.pageNumber,
      },
    })
    .then((res) => {
      const content = res.data.content;
      reviewList.value = content;
      responsePage.value.totalPages = res.data.totalPages;
    })
    .catch((err) => {
      if (err.response.status === 404) alert(err.response.data.message);
    });
};

const clickPage = (page: number) => {
  responsePage.value.pageNumber = page;
  fetchReview();
};

const formatDate = (createdAt: string) => {
  const date = new Date(createdAt);
  const year = date.getFullYear();
  const month = String(date.getMonth() + 1).padStart(2, '0');
  const day = String(date.getDate()).padStart(2, '0');

  return `${year}-${month}-${day}`;
};

onMounted(() => {
  fetchReview();
});
</script>

<style scoped>
* {
  box-sizing: border-box;
}
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 20px;
  margin-top: 5rem;
  height: 100vh;
}
.card-container {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
  margin-top: 30px;
  width: 100%;
  max-width: 1200px;
}
.card {
  margin: 10px;
  background-color: #fff;
  border-radius: 10px;
  box-shadow: 0 2px 20px rgba(0, 0, 0, 0.2);
  overflow: hidden;
  width: 100%;
  max-width: 300px; /* 최대 폭 설정 */
  height: auto; /* 높이는 자동으로 조정 */
}
.card-header img {
  width: 100%;
  height: 200px;
  object-fit: cover;
}
.card-body {
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  padding: 20px;
}

body.dark .card-header {
  background-color: #10182f;
}
body.dark .card-body {
  background-color: #10182f;
}

.tag {
  background: #cccccc;
  border-radius: 50px;
  font-size: 12px;
  margin: 0;
  color: #fff;
  padding: 2px 10px;
  text-transform: uppercase;
  cursor: pointer;
}
.tag-teal {
  background-color: #47bcd4;
}
.tag-purple {
  background-color: #5e76bf;
}
.tag-pink {
  background-color: #cd5b9f;
}

.card-body p {
  font-size: 13px;
  margin: 0 0 20px;
}
.review {
  display: flex;
  margin-top: auto;
}

.review img {
  border-radius: 50%;
  width: 40px;
  height: 40px;
  margin-right: 10px;
}
.review-info h5 {
  margin: 0;
}
.review-info small {
  color: #545d7a;
}

.pagination {
  display: flex;
  justify-content: center;
  flex-wrap: wrap;
  margin-top: 30px;
  width: 100%;
  max-width: 1200px;
}

.pagination ul {
  display: flex;
  list-style: none;
  padding: 0;
  margin: 0;
  margin-bottom: 50px;
}

.pagination .page-item {
  margin: 0 5px;
}

.pagination .page-link {
  padding: 8px 16px;
  font-size: 14px;
  cursor: pointer;
  text-decoration: none;
  color: black;
}

.page-link.active {
  font-weight: bold;
}

body.dark .pagination a {
  background-color: #10182f;
  color: #f7f8fc;
}

a {
  text-decoration: none;
  color: black;
}

body.dark a {
  color: white;
}

@media (max-width: 768px) {
  .card-container {
    justify-content: center;
    margin-top: 20px;
  }

  .card {
    max-width: 100%;
  }

  .card-body p {
    font-size: 12px;
    margin: 0 0 10px;
  }

  .pagination {
    flex-direction: row;
  }

  .page-link {
    padding: 6px 12px;
    font-size: 12px;
  }
}

/* 미디어 쿼리: 화면 폭이 576px 이하일 때 */
@media (max-width: 576px) {
  .card {
    margin: 5px;
  }

  .card-body p {
    font-size: 11px;
    margin: 0 0 5px;
  }

  .review-info h5 {
    font-size: 14px;
  }

  .review-info small {
    font-size: 12px;
  }

  .pagination {
    flex-direction: row;
    margin-top: 10px;
  }

  .page-item {
    margin: 2px 0;
  }

  .page-link {
    padding: 4px 8px;
    font-size: 10px;
  }
}
</style>
