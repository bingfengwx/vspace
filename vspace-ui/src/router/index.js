import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Space from '../views/Space.vue'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'home',
    component: Home
  },
  {
    path: '/space',
    redirect: '/'
  },
  {
    path: '/space/:spaceName',
    name: 'space',
    component: Space,
    props: true
  }
]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
