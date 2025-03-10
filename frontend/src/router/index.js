import { createRouter, createWebHashHistory } from 'vue-router';

import MainLayout from '@/layouts/MainLayout.vue';

const AboutView = () => import('../views/AboutView.vue');
const NotFoundView = () => import('../views/NotFoundView.vue');
const PostListView = () => import('../views/PostListView.vue');
const CreateNewPostView = () => import('../views/CreateNewPostView.vue');

const routes = [
  {
    path: '/',
    component: MainLayout,
    children: [
      {
        path: '',
        name: 'PostList',
        component: PostListView,
      },
      {
        path: 'create-new-post',
        name: 'CreateNewPost',
        component: CreateNewPostView,
      },
      {
        path: 'about',
        name: 'About',
        component: AboutView,
      },
      {
        path: '/posts/:id',
        name: 'Post',
        component: AboutView,
      },
      {
        path: '/:pathMatch(.*)*',
        name: 'NotFound',
        component: NotFoundView,
      },
    ],
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
