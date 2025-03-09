<template>
  <div class="container mt-4">
    <h1>Список постов</h1>
    <!-- Список постов -->
    <div v-for="post in posts" :key="post.id" class="card mb-3">
      <div class="card-body">
        <h5 class="card-title">{{ post.title }}</h5>
        <!-- Для отображения Markdown можно использовать библиотеку marked -->
        <div v-html="convertMarkdown(post.body)"></div>
      </div>
    </div>

    <!-- Элемент для отслеживания при прокрутке -->
    <div ref="loadMoreElement" class="text-center mt-3">
      <div v-if="loading" class="spinner-border" role="status">
        <span class="visually-hidden">Загрузка...</span>
      </div>
    </div>
  </div>
</template>

<script setup>

import { marked } from 'marked';
import {
  ref, onMounted, onBeforeUnmount, onDeactivated, onActivated,
} from 'vue';
import axios from 'axios';

const posts = ref([]);
const loading = ref(false);
const page = ref(0);
const hasMore = ref(true);
const perPage = 10;
const loadMoreElement = ref(null);
let observer = null;
const savedScroll = ref(0);

onDeactivated(() => {
  savedScroll.value = window.scrollY;
});

onActivated(() => {
  window.scrollTo(0, savedScroll.value);
});

async function loadPosts() {
  if (loading.value) return;
  if (!hasMore.value) return;
  loading.value = true;
  try {
    const response = await axios.get('/api/posts', {
      params: {
        limit: perPage,
        page: page.value,
        sort: 'id,desc',
      },
    });
    hasMore.value = response.data.length >= perPage;
    posts.value.push(...response.data);
    page.value += 1;
  } catch (error) {
    console.error('Ошибка загрузки постов:', error);
    hasMore.value = false;
  } finally {
    loading.value = false;
    setTimeout(() => {
      if (document.documentElement.scrollHeight <= window.innerHeight) {
        loadPosts();
      }
    }, 100);
  }
}

function handleIntersection(entries) {
  entries.forEach((entry) => {
    if (entry.isIntersecting && !loading.value) {
      loadPosts();
    }
  });
}

onMounted(() => {
  observer = new IntersectionObserver(handleIntersection, {
    threshold: 0.5,
  });
  if (loadMoreElement.value) {
    observer.observe(loadMoreElement.value);
  }
  loadPosts();
});

onBeforeUnmount(() => {
  console.log('onBeforeUnmount');
  if (observer && loadMoreElement.value) {
    observer.unobserve(loadMoreElement.value);
  }
});

function convertMarkdown(markdownText) {
  return marked(markdownText);
}
</script>

<script> export default { name: 'PostListView' }; </script>

<style scoped>
/* Дополнительные стили по необходимости */
</style>
