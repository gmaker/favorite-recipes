<template>
  <div v-if="visible" class="container mt-4">
    <h1>Вход в систему</h1>
    <form @submit.prevent="onSubmit">
      <div class="mb-3">
        <label for="email" class="form-label">Email
          <input id="email" type="email" class="form-control" v-model="email" required/>
        </label>
      </div>
      <div class="mb-3">
        <label for="password" class="form-label">Пароль
          <input id="password" type="password" class="form-control" v-model="password" required/>
        </label>
      </div>
      <button type="submit" class="btn btn-primary">Войти</button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue';
import useAuth from '@/composables/useAuth';

const { login } = useAuth();
const email = ref('');
const password = ref('');
const visible = localStorage.getItem('auth') !== 'true';

function onSubmit() {
  if (email.value && password.value) {
    login();
  } else {
    alert('Пожалуйста, заполните все поля.');
  }
}
</script>
