<template>
  <div class="container">
    <div class="popup-overlay" @click.self="closePopup">
      <div class="popup-content">
        <!-- <div class="map-container">
          <div id="map"></div>
        </div> -->
        <button @click="closePopup" class="close-button">X</button>
        <div>
          <li class="detail-text">
            <p>{{ props.shop.title }}</p>
          </li>
          <li class="detail-text">
            <p>{{ props.shop.address }}</p>
          </li>
          <li v-if="props.shop.link"><a :href="props.shop.link" target="_blank">링크</a></li>
          <router-link
            :to="{
              name: 'reviewRegister',
              query: { title: props.shop.title, address: props.shop.address },
            }"
            >리뷰 등록
          </router-link>
        </div>
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
  /* background: rgba(0, 0, 0, 0.5); */
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
  flex-direction: column;
  display: flex;
}

body.dark .popup-content {
  background-color: #10182f;
}

.popup-iframe {
  width: 100%;
  height: 1000px;
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

/* .map-container {
  width: 100%;
  height: 400px;
  margin-bottom: 10px;
}

#map {
  width: 100%;
  height: 100%;
} */
</style>
