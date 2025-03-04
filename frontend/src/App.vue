<template>
  <nav class="navbar navbar-expand-lg navbar-light bg-light"
       :class="{ 'bg-dark navbar-dark': isDarkMode }">
    <div class="container-fluid">
      <router-link class="navbar-brand" to="/">My App</router-link>
      <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
              data-bs-target="#navbarNav" aria-controls="navbarNav"
              aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>
      <div class="collapse navbar-collapse" id="navbarNav">
        <ul class="navbar-nav">
          <li class="nav-item">
            <router-link class="nav-link" to="/">Home</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/about">About</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/posts">Posts</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/mlist">Media List</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/post-list">Список постов</router-link>
          </li>
          <li class="nav-item">
            <router-link class="nav-link" to="/create-post">Создать пост</router-link>
          </li>
          <!-- Если авторизован, показываем ссылки на профиль и выход -->
          <li class="nav-item" v-if="authStatus">
            <router-link class="nav-link" to="/profile">Profile</router-link>
          </li>
          <li class="nav-item" v-if="authStatus">
            <a class="nav-link" href="#" @click.prevent="logout">Logout</a>
          </li>

          <li class="nav-item" v-else>
            <router-link class="nav-link" to="/login">Login</router-link>
          </li>
        </ul>
        <button class="btn btn-outline-secondary" @click="toggleTheme">
          {{ isDarkMode ? 'Светлая тема' : 'Тёмная тема' }}
        </button>
      </div>
    </div>
  </nav>
  <!-- Оборачиваем router-view в transition для анимаций -->
  <router-view v-slot="{ Component }">
    <transition name="fade" mode="out-in">
      <keep-alive :include="['PostsView']">
        <component :is="Component"/>
      </keep-alive>
    </transition>
  </router-view>

</template>
<style scoped>
.nav-link.router-link-exact-active {
  color: #42b983;
  font-weight: bold;
}

/* Пример простых стилей для темного режима */
.dark-mode {
  background-color: #121212;
  color: #f5f5f5;
}

/* Переопределение стилей для контейнеров, если нужно */
.dark-mode .container {
  background-color: #1e1e1e;
}

.fade-enter-active, .fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from, .fade-leave-to {
  opacity: 0;
}
</style>

<script setup>
import { ref, computed } from 'vue';
import useAuth from '@/composables/useAuth';

const {
  isAuthenticated,
  logout,
} = useAuth();
const authStatus = computed(() => isAuthenticated.value);
const isDarkMode = ref(false);

function toggleTheme() {
  isDarkMode.value = !isDarkMode.value;
  // Применяем класс к body (можно также управлять корневым элементом приложения)
  if (isDarkMode.value) {
    document.body.classList.add('dark-mode');
  } else {
    document.body.classList.remove('dark-mode');
  }
}

</script>
