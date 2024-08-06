import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useLoginStore = defineStore('login', () => {
  const username = ref<string>(localStorage.getItem('username') || '');
  return { username };
});
