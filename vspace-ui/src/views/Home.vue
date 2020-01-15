<template>
  <div class="home">
    <div class="main">
      <mu-flex justify-content="center" align-items="center" class="favicon">
        <h1>V - SPACE</h1>
      </mu-flex>
      <mu-flex wrap="wrap" justify-content="center" align-items="center">
        <div class="entrance" v-elevation="12">
            <mu-flex justify-content="center" direction="column" align-items="center">
              <!-- <mu-flex justify-content="center" align-items="start" class="form-group">
                <mu-text-field v-model="nickname" placeholder="昵称"></mu-text-field>
              </mu-flex> -->
              <mu-flex justify-content="center" align-items="start" class="form-group">
                <mu-text-field v-model="space" placeholder="要去哪里？"></mu-text-field>
              </mu-flex>
            </mu-flex>
            <mu-flex justify-content="center" align-item="center" class="launch-btn">
              <mu-button full-width color="primary" @click="go">GO</mu-button>
            </mu-flex>
        </div>
      </mu-flex>
    </div>
  </div>
</template>

<script>
// @ is an alias to /src

export default {
  name: 'home',
  components: {
  },
  data() {
    return {
      nickname: '',
      space: ''
    }
  },
  methods: {
    go: function() {
      this.$router.push({name: 'space', params: {spaceName: this.space}});
    }
  }
}
</script>

<style scoped>
  .home {
    height: 100vh;
    background: url(../assets/bg.jpg);
    background-size: cover;
    display: flex;
    display: -webkit-flex;
    align-items:center;
    justify-content:center;
  }
  .favicon {
    width: 100%;
    height: 56px;
    text-align: center;
  }
  .favicon h1 {
    font-size: 68px;
    color: #fff;
  }
  .entrance {
    background-color: #fff;
    margin: 50px 0 120px 0;
    padding: 30px 80px;
  }
  .form-group {
    text-align: center;
  }
</style>