import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import MuseUI from 'muse-ui'
import MuseUIMessage from 'muse-ui-message'
import MuseUIToast from 'muse-ui-toast'
import 'muse-ui/dist/muse-ui.css'
import 'muse-ui-message/dist/muse-ui-message.css'
import theme from 'muse-ui/lib/theme'

theme.add('teal', {
  background: {
    paper: '#eee',
  }
}, 'light');

Vue.config.productionTip = false
Vue.use(MuseUI)
Vue.use(MuseUIMessage)
Vue.use(MuseUIToast, {
  position: 'top',                  // 弹出的位置
  time: 3000,                       // 显示的时长
  closeIcon: 'close',               // 关闭的图标
  close: true,                      // 是否显示关闭按钮
  successIcon: 'check_circle',      // 成功信息图标
  infoIcon: 'info',                 // 信息信息图标
  warningIcon: 'priority_high',     // 提醒信息图标
  errorIcon: 'warning'              // 错误信息图标
})

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
