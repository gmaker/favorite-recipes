<template>
  <form @submit.prevent="handleSubmit">
    <div>
      <h1>Создание поста</h1>
      <label for="post-title" class="form-label">
        Заголовок поста
        <input id="post-title" type="text" v-model="title" placeholder="Заголовок поста" required/>
      </label>
      <div ref="editorContainer"></div>
      <button type="submit">Сохранить пост</button>
    </div>
  </form>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import Editor from '@toast-ui/editor';
import axios from 'axios';
import { useRouter } from 'vue-router';
import '@toast-ui/editor/dist/toastui-editor.css';

// Генерируем временный идентификатор для медиа
// Можно использовать UUID, но здесь для простоты timestamp.
const tempMediaGroupId = Date.now();

const title = ref('');
const editorContainer = ref(null);
let editorInstance = null;

const router = useRouter();

onMounted(() => {
  editorInstance = new Editor({
    el: editorContainer.value,
    initialEditType: 'markdown',
    previewStyle: 'vertical',
    height: '500px',
    hooks: {
      // Хук вызывается при перетаскивании изображения в редактор
      addImageBlobHook: async (blob, callback) => {
        const formData = new FormData();
        // Передаём файл и временный идентификатор (пока пост ещё не создан)
        formData.append('file', blob);
        formData.append('postId', tempMediaGroupId);
        try {
          const response = await axios.post('/api/media/upload', formData, {
            headers: { 'Content-Type': 'multipart/form-data' },
          });
          // Сервер возвращает URL сохранённого файла
          const imageUrl = response.data.url;
          // Вставляем ссылку в Markdown-редактор
          callback(imageUrl, 'alt text');
        } catch (error) {
          console.error('Ошибка загрузки изображения:', error);
        }
      },
    },
  });
});

const savePost = async () => {
  // Получаем Markdown-текст, где уже встроены ссылки на медиа
  const body = editorInstance.getMarkdown();
  // Отправляем DTO с данными поста. Обратите внимание, что post.id не передаётся,
  // так как он будет сгенерирован на сервере (автоинкремент).
  // Дополнительно передаём tempMediaGroupId, чтобы сервер знал, какие медиа обновить.
  const newPost = {
    title: title.value,
    body,
    tempMediaGroupId,
  };
  try {
    const response = await axios.post('/api/posts', newPost);
    const postId = response.data.id;
    alert('Пост успешно создан!');
    router.push({
      name: 'post',
      params: { id: postId },
    });
  } catch (error) {
    console.error('Ошибка сохранения поста:', error);
  }
};

const handleSubmit = async (event) => {
  if (!event.target.checkValidity()) {
    event.target.reportValidity();
    return;
  }
  await savePost();
};
</script>

<script> export default { name: 'CreateNewPostView' }; </script>
