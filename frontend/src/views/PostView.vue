<template>
  <div class="container mt-4" v-if="!loading">
    <h1>{{ post.title }}</h1>

    <div :key="post.id" class="card mb-3">
      <div class="card-body">
        <div v-html="convertMarkdown(post.body)"></div>
      </div>
    </div>

    <router-link to="/post-list" class="btn btn-secondary mt-3">
      К списку постов
    </router-link>
  </div>
  <div class="container mt-4 text-center" v-else>
    <div class="spinner-border" role="status">
      <span class="visually-hidden">Загрузка...</span>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import { marked } from 'marked';
import axios from 'axios';

const route = useRoute();
const post = ref({});
const loading = ref(true);

onMounted(async () => {
  try {
    // Получаем id из параметров маршрута
    const postId = route.params.id;
    const response = await axios.get(`/api/posts/${postId}`);
    post.value = response.data;
  } catch (error) {
    console.error('Ошибка загрузки поста:', error);
  } finally {
    loading.value = false;
  }
});

function convertMarkdown(markdownText) {
  return marked(markdownText);
}
</script>

<script> export default { name: 'PostView' }; </script>

<style scoped>
/* Дополнительные стили при необходимости */
</style>
