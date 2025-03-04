import { createRouter, createWebHashHistory } from 'vue-router';
import MediaListView from '@/views/MediaListView.vue';

const HomeView = () => import('../views/HomeView.vue');
const AboutView = () => import('../views/AboutView.vue');
const LoginView = () => import('../views/LoginView.vue');
const ProfileView = () => import('../views/ProfileView.vue');
const NotFound = () => import('../views/NotFound.vue');
const PostsView = () => import('../views/PostsView.vue');
const PostDetailsView = () => import('../views/PostDetailsView.vue');
const PostListView = () => import('../views/PostListView.vue');
const CreatePostView = () => import('../views/CreatePostView.vue');

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView,
  },
  {
    path: '/about',
    name: 'about',
    component: AboutView,
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView,
  },
  {
    path: '/profile',
    name: 'profile',
    component: ProfileView,
  },
  {
    path: '/posts',
    name: 'posts',
    component: PostsView,
  },
  {
    path: '/posts/:id',
    name: 'postDetails',
    component: PostDetailsView,
  },
  {
    path: '/mlist',
    name: 'mediaList',
    component: MediaListView,
  },
  {
    path: '/post-list',
    name: 'postList',
    component: PostListView,
  },
  {
    path: '/create-post',
    name: 'createPost',
    component: CreatePostView,
  },
  {
    path: '/:pathMatch(.*)*',
    name: 'notFound',
    component: NotFound,
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

router.beforeEach((to, from, next) => {
  const isAuthenticated = localStorage.getItem('auth') === 'true';
  if (to.name === 'profile' && !isAuthenticated) {
    next({ name: 'login' });
  } else {
    next();
  }
});

export default router;
