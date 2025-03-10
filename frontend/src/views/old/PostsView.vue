<template>
  <div class="container mt-4">
    <h1>Посты</h1>
    <div class="mt-3">
      <label for="searchInput" class="form-label">
        Поиск
        <input
          id="searchInput"
          v-model="searchTerm"
          type="text"
          class="form-control"
          placeholder="Поиск постов..."
        />
      </label>
    </div>

    <div class="mb-3">
      <label for="sortSelect" class="form-label">
        Сортировать по
        <select v-model="sortKey" class="form-select">
          <option value="id">По ID</option>
          <option value="title">По заголовку</option>
        </select>
      </label>
    </div>

    <ul class="list-group">
      <li
        v-for="post in sortedPosts"
        :key="post.id"
        class="list-group-item"
      >
        <router-link :to="{ name: 'postDetails', params: { id: post.id } }">
          <h5>{{ post.title }}</h5>
        </router-link>
        <p>{{ post.body }}</p>
      </li>
    </ul>

    <div ref="loadMoreElement" class="text-center mt-3">
      <div v-if="loading" class="spinner-border" role="status">
        <span class="visually-hidden">Загрузка...</span>
      </div>
    </div>
  </div>
</template>

<script setup>
import {
  ref, computed, onMounted, onBeforeUnmount, onDeactivated, onActivated,
} from 'vue';
import axios from 'axios';

const posts = ref([]);
const loading = ref(false);
const page = ref(1);
const hasMore = ref(true);
const perPage = 10;
const loadMoreElement = ref(null);
let observer = null;
const savedScroll = ref(0);

const searchTerm = ref('');
const sortKey = ref('id');

onDeactivated(() => {
  savedScroll.value = window.scrollY;
});

onActivated(() => {
  window.scrollTo(0, savedScroll.value);
});

async function loadPosts() {
  loading.value = true;
  try {
    const response = await axios.get(
      `https://jsonplaceholder.typicode.com/posts?_limit=${perPage}&_page=${page.value}`,
    );
    if (response.data.length < perPage) {
      hasMore.value = false;
    }
    posts.value.push(...response.data);
    page.value += 1;
  } catch (error) {
    console.error('Ошибка загрузки постов:', error);
  } finally {
    loading.value = false;
    setTimeout(() => {
      if (document.documentElement.scrollHeight <= window.innerHeight && hasMore.value) {
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

const filteredPosts = computed(() => {
  if (!searchTerm.value.trim()) {
    return posts.value;
  }
  const term = searchTerm.value.toLowerCase();
  return posts.value.filter((post) => post.title.toLowerCase().includes(term)
    || post.body.toLowerCase()
      .includes(term));
});

const sortedPosts = computed(() => {
  const postsToSort = [...filteredPosts.value];
  postsToSort.sort((a, b) => {
    if (sortKey.value === 'title') {
      return a.title.localeCompare(b.title);
    }
    return a.id - b.id;
  });
  return postsToSort;
});

</script>

<script> export default { name: 'PostsView' }; </script>

<style scoped>
/* Можно добавить дополнительные стили при необходимости */
</style>
