<template>
  <div>
    <div id="map"></div>
  </div>
</template>

<script setup lang="ts">
import type { Shop } from '@/model/Shop';
import { watch } from 'vue';

const props = defineProps<{ shop: Shop }>();

const loadKaKaoMap = () => {
  const appKey = import.meta.env.VITE_KAKAO_MAP_KEY;
  const script = document.createElement('script');
  script.src = `https://dapi.kakao.com/v2/maps/sdk.js?appkey=${appKey}&autoload=false&libraries=services`;
  document.head.appendChild(script);

  script.onload = () => {
    window.kakao.maps.load(() => {
      const container = document.getElementById('map');
      const options = {
        center: new window.kakao.maps.LatLng(33.450701, 126.570667),
        level: 3,
      };
      const map = new window.kakao.maps.Map(container, options);
      const geocoder = new window.kakao.maps.services.Geocoder();
      geocoder.addressSearch(props.shop.address, (res: any, status: any) => {
        if (status === window.kakao.maps.services.Status.OK) {
          const latLng = new window.kakao.maps.LatLng(res[0].y, res[0].x);
          new window.kakao.maps.Marker({
            map: map,
            position: latLng,
          });
          map.setCenter(latLng);
        }
      });
    });
  };
};

watch(
  () => props.shop,
  (shop) => {
    if (shop) loadKaKaoMap();
  },
  { immediate: true },
);
</script>

<style scoped>
#map {
  width: 100%;
  height: 250px;
  z-index: 10;
}

body.dark #map {
  position: relative;
  width: 100%;
  height: 250px;
  z-index: 15;
}
</style>
