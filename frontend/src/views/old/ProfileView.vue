<template>
  <div class="container mt-4">
    <h1>Личный кабинет</h1>
    <p>Здесь пользователь сможет загрузить фото для профиля.</p>
    <div class="mb-3">
      <label for="profile-image" class="form-label">Выберите изображение
        <input id="profile-image" type="file" @change="onFileChange" accept="image/*"
               class="form-control" />
      </label>
    </div>
    <div v-if="imageUrl">
      <h5>Превью выбранного фото:</h5>
      <img :src="imageUrl" alt="Preview" class="img-thumbnail" style="max-width: 300px;" />
    </div>
  </div>
</template>
<script setup>
import { ref } from 'vue';

const imageUrl = ref(null);

function onFileChange(event) {
  const file = event.target.files[0];
  const MAX_SIZE = 3 * 1024 * 1024; // 3 МБ в байтах

  if (!file) {
    imageUrl.value = null;
    return;
  }

  if (file.size > MAX_SIZE) {
    alert('Размер файла превышает 3 МБ. Пожалуйста, выберите файл меньшего размера.');
    imageUrl.value = null;
    return;
  }

  if (file.type.startsWith('image/')) {
    const reader = new FileReader();
    reader.onload = (e) => {
      imageUrl.value = e.target.result;
    };
    reader.readAsDataURL(file);
  } else {
    imageUrl.value = null;
  }
}
</script>

<style scoped>
/* Дополнительные стили можно добавить при необходимости */
</style>
