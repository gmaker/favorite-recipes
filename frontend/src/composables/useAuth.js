import { ref } from 'vue';
import { useRouter } from 'vue-router';

const isAuthenticated = ref(localStorage.getItem('auth') === 'true');
export default function useAuth() {
  const router = useRouter();

  function login() {
    isAuthenticated.value = true;
    localStorage.setItem('auth', 'true');
    router.push({ name: 'profile' });
  }

  function logout() {
    isAuthenticated.value = false;
    localStorage.setItem('auth', 'false');
    router.push({ name: 'home' });
  }

  return {
    isAuthenticated,
    login,
    logout,
  };
}
