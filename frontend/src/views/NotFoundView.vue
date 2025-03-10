<template>
  <div class="container mt-4">
    <h1>404 - Страница не найдена</h1>
    <p>К сожалению, запрошенная вами страница не существует.</p>
    <router-link to="/" class="btn btn-primary mt-3">Вернуться на главную</router-link>
  </div>
</template>

<script>
export default {
  name: 'NotFound',
};
</script>

<style scoped>
/* Здесь можно добавить свои стили */
</style>
