<template>
  <div class="InputContainer-contentSection flexauto">
    <input
      id="search-input"
      type="text"
      placeholder='How about "Cleveland Cavaliers vs Boston Celtics"?'
      class="Search"
    />
    <button class="SearchButton">Search</button>
  </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const isPause = ref<boolean>(false);

const slidePause = () => {
  isPause.value = !isPause.value;
};
</script>
<style scoped></style>
