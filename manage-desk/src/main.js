import Vue from 'vue'
import App from './App.vue'
import './assets/common.less'
import dataV from '@jiaminghi/data-view'
// import * as Three from 'three'
import Axios from 'axios'
import Antd from 'ant-design-vue'
import 'ant-design-vue/dist/antd.css'
// 将axios挂载到原型上
Vue.prototype.$axios = Axios
// 将three.js挂载到原型上
// Vue.prototype.$three = Three
Vue.config.productionTip = false
Vue.use(dataV)
Vue.use(Antd)
new Vue({
  render: h => h(App)
}).$mount('#app')
