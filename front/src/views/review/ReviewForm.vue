<template>
  <div class="container">
    <div class="form-container">
      <ul>
        <li>{{ review.shop.title }}</li>
        <li>{{ review.shop.address }}</li>
        <div class="mb-3">
          <label class="form-label" for="reviews">가게 리뷰</label>
          <textarea
            type="text"
            class="form-control"
            v-model="review.content"
            id="reviews"
            rows="3"
          ></textarea>
        </div>
        <div class="mb-3">
          <label class="form-label" for="rating">점수</label>
          <input type="text" class="form-control px-4" v-model="review.rating" id="rating" />
        </div>
      </ul>
    </div>

    <div class="items-container">
      <div v-for="(item, idx) in review.items" :key="idx" class="item">
        <input
          type="text"
          class="form-control px-4"
          v-model="item.name"
          placeholder="메뉴를 적어주세요"
        />
        <textarea
          type="text"
          class="form-control"
          v-model="item.content"
          placeholder="메뉴에 대한 리뷰 내용을 적어주세요"
          rows="3"
        ></textarea>
        <input
          type="text"
          class="form-control px-4"
          v-model="item.rating"
          placeholder="점수를 적어주세요"
        />
        <button class="btn btn-primary" @click="removeItem(idx)">메뉴제거</button>
      </div>
    </div>

    <div class="actions">
      <button class="btn btn-primary" @click="addItem">메뉴 추가</button>
      <button v-if="route.name === 'reviewRegister'" class="btn btn-primary" @click="reviewPost">
        리뷰 등록
      </button>
      <button v-if="route.name === 'editReview'" class="btn btn-primary" @click="reviewEdit">
        리뷰 수정
      </button>
    </div>

    <div class="upload-controls">
      <input
        type="file"
        multiple
        @change="handleFileChange"
        accept="image/*"
        style="display: none"
        id="fileInput"
      />
      <button @click="imageUpload"><p>이미지 업로드</p></button>
    </div>

    <div v-if="files.length" class="file-list">
      <ul>
        <li v-for="(file, index) in files" :key="index">
          {{ file.name }}
          <button @click="removeFile(index)" class="remove-btn">x</button>
        </li>
      </ul>
    </div>
    <div v-if="images && route.name === 'editReview'" class="file-list">
      <ul>
        <li v-for="(image, idx) in images">
          {{ image.fileName }}
          <button @click="removeOriginFile(image.id, idx)" class="remove-btn"><p>x</p></button>
        </li>
      </ul>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { RequestItem } from '@/model/RequestItem';
import type { RequestReview } from '@/model/RequestReview';
import type { RequestShop } from '@/model/RequestShop';
import axios from 'axios';
import { onMounted, ref } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();
const shop: RequestShop = {
  title: route.query.title as string,
  address: route.query.address as string,
};
const items = ref<RequestItem[]>([]);
const files = ref<File[]>([]);
const review = ref<RequestReview>({ shop: shop, items: items.value });
const images = ref<{ fileName: string; id: number; url: string }[]>();
const fileInput = ref<HTMLInputElement>();

const handleFileChange = (event: Event) => {
  const target = event.target as HTMLInputElement;
  if (target.files) {
    files.value = [...files.value, ...Array.from(target.files)];
    target.value = '';
  }
};

const imageUpload = () => {
  const fileInput = document.getElementById('fileInput') as HTMLInputElement;
  fileInput.click();
};

const removeFile = (idx: number) => {
  files.value.splice(idx, 1);
};

const removeOriginFile = (id: number, idx: number) => {
  images.value?.splice(idx, 1);
  review.value?.deleteImages?.push(id);
};

const addItem = () => {
  review.value.items?.push({ name: '' });
};

const removeItem = (idx: number) => {
  review.value.items?.splice(idx, 1);
};

const reviewPost = () => {
  validForm();

  axios
    .post('/v1/reviews', getFormData(), {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
    .then((res) => {
      router.push({ name: 'searchShop' });
      alert('리뷰등록 완료');
    })
    .catch((err) => {
      alert('리뷰등록 실패');
    });
};

const reviewEdit = () => {
  validForm();
  axios
    .patch(`/v1/reviews/${route.params.id}`, getFormData(), {
      headers: {
        'Content-Type': 'multipart/form-data',
      },
    })
    .then((res) => {
      alert('리뷰수정 완료');
      router.push({ name: 'reviewDetail', params: { id: res.data } });
    })
    .catch((err) => {
      alert('리뷰수정 실패');
    });
};

const validForm = () => {
  if (
    items.value.length === 0 &&
    !review.value.content &&
    !review.value.rating &&
    review.value.rating !== 0
  ) {
    alert('메뉴가 없으면 가게의 리뷰 내용 또는 점수는 필수입니다.');
    return;
  }

  const reviewrating = review.value.rating;
  if (reviewrating && (reviewrating < 0.0 || reviewrating > 10.0)) {
    alert('점수는 0~10 사이 입니다.');
    return;
  }

  items.value.forEach((i) => {
    if (!i.name || (!i.content && !i.rating)) {
      alert('메뉴이름과 리뷰 또는 점수를 적어주시거나 메뉴를 제거해 주세요');
      return;
    }

    if (i.rating && (i.rating < 0.0 || i.rating > 10.0)) {
      alert('점수는 0~10 사이 입니다.');
      return;
    }
  });
};

const getFormData = (): FormData => {
  const formData = new FormData();
  formData.append(
    'request',
    new Blob([JSON.stringify(review.value)], {
      type: 'application/json',
    }),
  );
  files.value.forEach((file) => formData.append('files', file));

  return formData;
};

onMounted(() => {
  if (route.name === 'reviewRegister' && (!route.query.title || !route.query.address)) {
    router.push({ name: 'searchShop' });
  }
  if (route.name === 'editReview') {
    const reviewId = route.params.id;
    axios.get(`/v1/reviews/${reviewId}`).then((res) => {
      review.value = res.data;
      review.value.deleteImages = [];
      images.value = res.data.images;
    });
  }
});
</script>

<style scoped>
.container {
  display: flex;
  flex-direction: column;
  align-items: center;
  height: 100vh;
  padding: 20px;
}

.form-container {
  margin-top: 150px;
  width: 100%;
  max-width: 600px;
}

ul {
  list-style: none;
  padding: 0;
  margin: 0;
  width: 100%;
  text-align: center;
}

ul li {
  margin-bottom: 10px;
}

.items-container {
  display: flex;
  flex-wrap: wrap;
  gap: 10px;
  margin-bottom: 20px;
  justify-content: space-evenly;
  width: 100%;
}

.item {
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 10px;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  width: calc(33.333% - 20px);
  box-sizing: border-box;
}

.item input {
  width: 100%;
}

.item button {
  margin-top: 10px;
}

.actions {
  display: flex;
  gap: 10px;
}

.actions button {
  padding: 10px 20px;
  font-size: 16px;
  cursor: pointer;
}

input.form-control {
  width: 100%;
  padding: 8px;
  font-size: 16px;
  border: 1px solid #ccc;
  border-radius: 4px;
}

.upload-controls {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 20px;
}

.upload-controls button {
  margin-left: 10px;
  margin-top: 10px;
  background: none;
  border: none;
  cursor: pointer;
}

.upload-controls button:hover {
  text-decoration: underline;
}

.remove-btn {
  margin-left: 10px;
  background: none;
  border: none;
  cursor: pointer;
}

.remove-btn:hover {
  text-decoration: underline;
}

@media (max-width: 768px) {
  .form-container {
    margin-top: 10px;
    max-width: 100%;
  }

  .items-container {
    flex-direction: column;
  }

  .item {
    width: 100%;
  }

  .actions {
    flex-direction: column;
    gap: 5px;
  }

  input.form-control {
    font-size: 14px;
  }

  .actions button {
    font-size: 14px;
    padding: 8px 16px;
  }
}

@media (max-width: 576px) {
  .form-container {
    margin-top: 5px;
  }

  .item {
    width: 100%;
    padding: 5px;
  }

  .actions button {
    font-size: 12px;
    padding: 6px 12px;
  }

  input.form-control {
    font-size: 12px;
    padding: 6px;
  }
}
</style>
