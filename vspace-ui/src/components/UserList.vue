<template>
    <ul>
        <li :key="user" v-for="user in userList">{{user}}</li>
    </ul>
</template>

<script>
export default {
    props: {
        userList: Array
    }
}
</script>

<style scoped>
    ul {
        list-style: none;
        line-height: 36px;
        padding: 0;
        margin: 0;
    }
    li {
        cursor: pointer;
        padding-left: 20px;
    }
    li:hover {
        background-color: #f0f0f0;
    }
</style>