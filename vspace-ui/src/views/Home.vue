<template>
  <div class="home">
      <mu-flex justify-content="center" align-items="center" class="favicon">
        <h1>V - SPACE</h1>
      </mu-flex>
      <mu-flex wrap="wrap" justify-content="center" align-items="center">
        <div class="entrance" v-elevation="12">
            <mu-flex justify-content="center" direction="column" align-items="center">
              <mu-flex justify-content="center" align-items="start" class="form-group">
                <mu-text-field v-model="nickname" placeholder="昵称"></mu-text-field>
              </mu-flex>
              <mu-flex justify-content="center" align-items="start" class="form-group">
                <mu-text-field v-model="space" placeholder="要去哪里"></mu-text-field>
              </mu-flex>
            </mu-flex>
            <mu-flex justify-content="center" align-item="center" class="launch-btn">
              <mu-button full-width color="primary">GO</mu-button>
            </mu-flex>
        </div>
      </mu-flex>
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
  }
}
</script>

<style scoped>
  .favicon {
    width: 100%;
    height: 56px;
    margin-top: 120px;
    text-align: center;
  }
  .favicon h1 {
    font-size: 68px;
    color: #2196f3;
  }
  .entrance {
    width: 600px; 
    background-color: #fff;
    margin: 50px auto;
    padding: 20px 60px;
  }
  .form-group {
    text-align: center;
  }
</style>