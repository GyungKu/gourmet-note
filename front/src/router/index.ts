import Home from '@/views/Home.vue';
import Login from '@/views/login/Login.vue';
import NaverLogin from '@/views/login/NaverLogin.vue';
import MyPage from '@/views/my/MyPage.vue';
import MyReview from '@/views/my/MyReview.vue';
import OnlyTest from '@/views/OnlyTest.vue';
import OnlyTest2 from '@/views/OnlyTest2.vue';
import ReviewDetail from '@/views/review/ReviewDetail.vue';
import ReviewForm from '@/views/review/ReviewForm.vue';
import SearchShop from '@/views/review/SearchShop.vue';
import { createRouter, createWebHistory } from 'vue-router';

const router = createRouter({
  history: createWebHistory(),
  routes: [
    { name: 'home', path: '/', component: Home },
    { name: 'test', path: '/test', component: OnlyTest },
    { name: 'test2', path: '/test2', component: OnlyTest2 },
    { name: 'login', path: '/login', component: Login, meta: { requiresAuth: false } },
    {
      name: 'naverLogin',
      path: '/login/naver',
      component: NaverLogin,
      meta: { requiresAuth: false },
    },
    { name: 'searchShop', path: '/shops', component: SearchShop, meta: { requiresAuth: true } },
    {
      name: 'reviewRegister',
      path: '/reviews/register',
      component: ReviewForm,
      meta: { requiresAuth: true },
    },
    {
      name: 'editReview',
      path: '/reviews/edit/:id',
      component: ReviewForm,
      meta: { requiresAuth: true },
    },
    {
      name: 'myReview',
      path: '/reviews/my',
      component: MyReview,
      meta: { requiresAuth: true },
    },
    {
      name: 'myPage',
      path: '/my',
      component: MyPage,
      meta: { requiresAuth: true },
    },
    {
      name: 'reviewDetail',
      path: '/reviews/:id',
      component: ReviewDetail,
      meta: { requiresAuth: true },
    },
    {
      path: '/:pathMatch(.*)*',
      redirect: '/',
    },
  ],
});

router.beforeEach((to, from, next) => {
  if (to.meta.requiresAuth && !localStorage.getItem('username')) {
    router.push({ name: 'login', query: { path: to.path } });
    alert('로그인이 필요한 서비스입니다.');
  }
  if (to.meta.requiresAuth === false && localStorage.getItem('username')) {
    router.push({ name: 'home' });
  }
  next();
});

export default router;
