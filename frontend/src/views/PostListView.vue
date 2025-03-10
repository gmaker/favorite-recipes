<template>
  <div class="container mt-4">
    <h1>Список постов</h1>

    <div class="card mb-3">
      <div class="card-body">
        <div class="row mb-2">
          <div class="col-auto">
            <div class="form-check">
              <label class="form-check-label" for="filterCooked">
                Проверенные
                <input
                  class="form-check-input"
                  type="checkbox"
                  id="filterCooked"
                  v-model="filterCooked">
              </label>
            </div>
          </div>
        </div>
        <!-- Инпут с тегами -->
        <div class="mb-3">
          <label for="tagInput" class="form-label">
            Теги
            <div class="d-flex flex-wrap align-items-center border p-2" style="min-height: 40px;">
              <!-- Отображаем добавленные теги -->
              <span
                v-for="(tag, index) in tagList"
                :key="index"
                class="badge bg-primary me-1 mb-1">
          {{ tag }}
          <button
            type="button"
            class="btn-close btn-close-white btn-sm ms-1"
            aria-label="Удалить"
            @click="removeTag(index)">
          </button>
          </span>
              <!-- Инпут для ввода тегов -->
              <input
                id="tagInput"
                type="text"
                class="form-control border-0 flex-grow-1 p-0"
                style="min-width: 100px;"
                v-model="tagInput"
                @keydown="handleTagInputKeydown"
                @blur="addTagOnBlur"
                placeholder="Добавьте тег..."
              />
            </div>
          </label>
        </div>
      </div>
    </div>

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
  ref, onMounted, onBeforeUnmount, onDeactivated, onActivated, watch,
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

const filterCooked = ref(true);
const tagInput = ref('');
const tagList = ref([]);

function addTag() {
  const tag = tagInput.value.trim();
  if (tag !== '' && !tagList.value.includes(tag)) {
    tagList.value.push(tag);
  }
  tagInput.value = '';
}

function addTagOnBlur() {
  addTag();
}

function handleTagInputKeydown(event) {
  // Если введена запятая, пробел или Enter — добавляем тег
  if (event.key === ',' || event.key === ' ' || event.key === 'Enter') {
    event.preventDefault();
    addTag();
  } else if (event.key === 'Backspace' && tagInput.value === '') {
    // Если инпут пустой и нажата Backspace, удаляем последний тег
    tagList.value.pop();
  }
}

function removeTag(index) {
  tagList.value.splice(index, 1);
}

// При изменении фильтра сбрасываем список и начинаем загрузку заново
watch([filterCooked, tagList], () => {
  posts.value = [];
  page.value = 0;
  hasMore.value = true;
  loadPosts();
}, { deep: true });

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
    // Формируем объект параметров для запроса
    const params = {
      limit: perPage,
      page: page.value,
      sort: 'id,desc',
      cooked: filterCooked.value ? 'true' : 'false',
    };

    // Если список тегов не пустой, передаём их в виде строки, разделённой запятыми
    if (tagList.value.length > 0) {
      params.tags = tagList.value.join(',');
    }

    const response = await axios.get('/api/posts', { params });
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
  if (observer && loadMoreElement.value) {
    observer.unobserve(loadMoreElement.value);
  }
});

function convertMarkdown(markdownText) {
  return marked(markdownText);
}
</script>

<script>
export default {
  name: 'PostListView',
};
</script>

<style scoped>
/* Дополнительные стили по необходимости */
</style>
