<template>
  <div>
    <!-- Ваш navbar -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
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
              <router-link class="nav-link" to="/about">About</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/">Список постов</router-link>
            </li>
            <li class="nav-item">
              <router-link class="nav-link" to="/create-new-post">Создать пост</router-link>
            </li>
          </ul>
        </div>
      </div>
    </nav>

    <!-- Оборачиваем router-view в transition для анимаций -->
    <router-view v-slot="{ Component }">
      <transition name="fade" mode="out-in">
        <keep-alive :include="['PostsView']">
          <component :is="Component" />
        </keep-alive>
      </transition>
    </router-view>
  </div>
</template>

<script setup>
// Можно добавить общую логику для layout, если необходимо
</script>

<script> export default { name: 'MainLayout' }; </script>

<style scoped>
.nav-link.router-link-exact-active {
  color: #42b983;
  font-weight: bold;
}

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.5s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}
</style>
