import Vue from 'vue'
import Vuex from 'vuex'
import * as types from '../store/mutation-types'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    user: null
  },
  mutations: {
    [types.SET_USERNAME](state, username) {
      state.user = {
        uid: username,
        username: username
      }
    }
  },
  actions: {
  },
  modules: {
  }
})
