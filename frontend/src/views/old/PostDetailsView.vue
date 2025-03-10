<template>
  <div class="container mt-4" v-if="!loading">
    <h1>{{ post.title }}</h1>
    <p>{{ post.body }}</p>
    <button class="btn btn-secondary mt-3" @click="goBack">
      Вернуться к списку постов
    </button>
  </div>
  <div class="container mt-4 text-center" v-else>
    <div class="spinner-border" role="status">
      <span class="visually-hidden">Загрузка...</span>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import axios from 'axios';

const route = useRoute();
const router = useRouter();
const post = ref({});
const loading = ref(true);

function goBack() {
  router.back();
}

onMounted(async () => {
  try {
    // Получаем id из параметров маршрута
    const postId = route.params.id;
    const response = await axios.get(`https://jsonplaceholder.typicode.com/posts/${postId}`);
    post.value = response.data;
  } catch (error) {
    console.error('Ошибка загрузки поста:', error);
  } finally {
    loading.value = false;
  }
});
</script>

<script> export default { name: 'PostDetailsView' }; </script>

<style scoped>
/* Дополнительные стили при необходимости */
</style>
