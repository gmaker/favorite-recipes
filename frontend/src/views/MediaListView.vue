- ё<template>
  <div class="container mt-4">
    <h1>Мои медиафайлы</h1>

    <!-- Форма для загрузки видео/гиф -->
    <div class="mb-3">
      <label for="mediaInput" class="form-label">
        Загрузить видео или GIF
        <input
          id="mediaInput"
          type="file"
          class="form-control"
          accept="video/*,image/gif"
          multiple
          @change="handleFileChange"
        />
      </label>
    </div>

    <!-- Список загруженных медиа -->
    <ul class="list-group">
      <li
        v-for="(media, index) in mediaList"
        :key="index"
        class="list-group-item"
      >
        <!-- Если это видео -->
        <div v-if="media.type.startsWith('video')">
          <video
            :src="media.url"
            controls
            muted
            class="media-video"
            :ref="setVideoRef"
            preload="metadata"
            style="max-width: 100%;"
          >
            <track kind="captions" label="Captions" src="" default/>
          </video>
        </div>
        <!-- Если это GIF или другое изображение -->
        <div v-else-if="media.type.startsWith('image')">
          <img :src="media.url" alt="Медиа" style="max-width: 100%;"/>
        </div>
      </li>
    </ul>
  </div>
</template>

<script setup>
import {
  ref, onMounted, onBeforeUnmount,
} from 'vue';

// Список для хранения загруженных медиа (объекты с типом и data URL)
const mediaList = ref([]);
let observer = null;

function setVideoRef(el) {
  if (el && observer) {
    observer.observe(el);
  }
}

// Обработчик выбора файлов
function handleFileChange(event) {
  const { files } = event.target;
  // Если выбранно несколько файлов, обрабатываем каждый
  Array.from(files)
    .forEach((file) => {
      const reader = new FileReader();
      reader.onload = (e) => {
        // Сохраняем объект с MIME-типом и URL (data URL)
        mediaList.value.push({
          type: file.type,
          url: e.target.result,
        });
      };
      reader.readAsDataURL(file);
    });
}

function handleIntersection(entries) {
  entries.forEach((entry) => {
    if (entry.isIntersecting) {
      console.warn(entry.target);
      entry.target.play()
        .catch((err) => console.warn('Ошибка воспроизведения:', err));
    } else {
      entry.target.pause();
    }
  });
}

onMounted(() => {
  const centerOffset = window.innerHeight / 2 - 100;
  observer = new IntersectionObserver(handleIntersection, {
    rootMargin: `-${centerOffset}px 0px -${centerOffset}px 0px`,
    threshold: 0, // или можно задать порог, например, 1 для полного попадания
  });
});

onBeforeUnmount(() => {
  if (observer) {
    observer.disconnect();
  }
});

</script>

<style scoped>
/* Дополнительные стили можно добавить по желанию */
</style>
