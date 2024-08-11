<template>
  <div class="modal-overlay" @click="closePopup">
    <div class="modal-content" @click.stop>
      <button class="close-btn" @click="closePopup">×</button>
      <div class="modal-body">
        <div class="map-container">
          <div id="map"></div>
        </div>
        <div class="text-content">
          <p>{{ props.shop.title }}</p>
          <p>{{ props.shop.title }}</p>
          <p>{{ props.shop.title }}</p>
          <p>{{ props.shop.title }}</p>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import type { Shop } from '@/model/Shop';
import { onMounted } from 'vue';

const emit = defineEmits(['close']);
const props = defineProps<{ shop: Shop }>();

const closePopup = () => {
  emit('close');
};

const appKey = import.meta.env.VITE_KAKAO_MAP_KEY;

const loadScript = () => {
  const script = document.createElement('script');
  script.src = `//dapi.kakao.com/v2/maps/sdk.js?appkey=${appKey}&autoload=false`;
  script.onload = () => {
    console.log('kakao maps script onloaded');
    window.kakao.maps.load(loadMap);
  };
  script.onerror = () => {
    console.log('kakao maps script onerror');
  };
  document.head.appendChild(script);
};

const loadMap = () => {
  const container = document.getElementById('map');

  const options = {
    center: new kakao.maps.LatLng(33.450701, 126.570667),
    level: 3,
  };

  new window.kakao.maps.Map(container, options);
};

onMounted(() => {
  if (window.kakao && window.kakao.maps) {
    loadMap();
  } else {
    loadScript();
  }
  loadMap();
});
</script>

<style scoped>
.modal-overlay {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
}
.modal-content {
  background: white;
  border-radius: 8px;
  padding: 20px;
  max-width: 500px;
  width: 100%;
  position: relative;
}
.close-btn {
  position: absolute;
  top: 10px;
  right: 10px;
  border: none;
  background: none;
  font-size: 20px;
  cursor: pointer;
}
.text-content {
  margin-top: 20px;
}
.map-container {
  height: 300px;
}
</style>
