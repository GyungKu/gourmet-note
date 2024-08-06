<template>
  <div class="container">
    <div class="col-lg-9 text-center">
      <div
        class="narrow-w form-search d-flex align-items-stretch mb-3 aos-init aos-animate shop-input"
        data-aos="fade-up"
        data-aos-delay="200"
      >
        <input
          type="text"
          class="form-control px-4"
          placeholder="가게 이름을 입력해주세요"
          @keydown.enter="shopSearch"
          v-model="query"
        />
        <button type="button" class="btn btn-primary" @click="shopSearch">Search</button>
      </div>
      <div class="shop-list">
        <li v-for="(shop, idx) in shopList" :key="idx">
          <a href="#" @click="openPopup(shop)">{{ shop.title }}</a>
          <PopupView v-if="isVisible" :shop="selectShop" @close="closePopup" />
          | {{ shop.address }}
          <button class="btn btn-link">
            <router-link
              :to="{ name: 'reviewRegister', query: { title: shop.title, address: shop.address } }"
              >리뷰 등록
            </router-link>
          </button>
        </li>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import PopupView from '@/components/PopupView.vue';
import type { Shop } from '@/model/Shop';
import axios from 'axios';
import { ref } from 'vue';

const query = ref<string>('');
const shopList = ref<Shop[]>([]);
const isVisible = ref<boolean>(false);
const selectShop = ref<Shop>({ title: '', address: '' });

const shopSearch = () => {
  axios
    .get('/v1/shops', {
      params: {
        query: query.value,
      },
    })
    .then((res) => {
      shopList.value = res.data.items.map((item: any) => {
        return { title: item.title, link: item.link, address: item.address };
      });
      if (shopList.value.length === 0) alert('검색결과가 없습니다.');
    });
};

const openPopup = (shop: Shop) => {
  selectShop.value = shop;
  isVisible.value = true;
};

const closePopup = () => {
  isVisible.value = false;
};
</script>

<style scoped>
.container {
  display: grid;
  place-items: center;
  height: 100vh;
}

.col-lg-9 {
  text-align: center;
}

.shop-input {
  width: 500px;
  margin: 0 auto;
}

.shop-list {
  width: 800px;
  margin: 0 auto;
  list-style: none;
  padding: 0;
}

.shop-list li {
  margin-bottom: 10px;
}
</style>
