<template>
  <div class="container">
    <div class="popup-overlay" @click.self="closePopup">
      <div class="popup-content">
        <button @click="closePopup" class="close-button">X</button>
        <!-- <iframe frameborder="0" class="popup-iframe"> -->
        <div>
          <li class="detail-text">{{ props.shop.title }}</li>
          <li class="detail-text">{{ props.shop.address }}</li>
          <li v-if="props.shop.link"><a :href="props.shop.link" target="_blank">링크</a></li>
          <router-link
            :to="{
              name: 'reviewRegister',
              query: { title: props.shop.title, address: props.shop.address },
            }"
            >리뷰 등록
          </router-link>
        </div>
        <!-- </iframe> -->
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Shop } from '@/model/Shop';

const emit = defineEmits(['close']);
const props = defineProps<{ shop: Shop }>();

const closePopup = () => {
  emit('close');
};
</script>

<style scoped>
body.dark .detail-text {
  color: black;
}
.popup-overlay {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}

.popup-content {
  position: relative;
  background: white;
  padding: 20px;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  width: 80%;
  max-width: 800px;
}

.popup-iframe {
  width: 100%;
  height: 500px;
}

.close-button {
  position: absolute;
  top: 10px;
  right: 10px;
  background: none;
  border: none;
  font-size: 1.2em;
  cursor: pointer;
}
</style>
