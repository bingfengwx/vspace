<template>
    <div>
        <div class="msg-item" :key="msg.mid" v-for="msg in msgList">
            <div v-if="user && user.uid === msg.from">
                <div class="username my-username">{{msg.sendTime}} - {{msg.username}}</div>
                <div class="bubble-own">
                    <div class="bubble-arrow"></div>
                    <div class="msg-content">{{msg.content}}</div>
                </div>
            </div>
            <div v-else>
                <div class="username">{{msg.username}} - {{msg.sendTime}}</div>
                <div class="bubble">
                    <div class="bubble-arrow"></div>
                    <div class="msg-content">{{msg.content}}</div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import {mapState} from 'vuex'

export default {
    name: 'message-list',
    props: {
        msgList: Array
    },
    computed: mapState(['user'])
}
</script>

<style scoped>
    .msg-item {
        margin-bottom: 10px;
    }
    .bubble-own {
        display: flex;
        flex-direction: column;
        flex-wrap: wrap-reverse;
    }
    .bubble-arrow {
        width: 0;
        height: 0;
        border: 10px solid;
    }
    .bubble .bubble-arrow {
        margin-left: 25px;
    }
    .bubble-own .bubble-arrow {
        margin-right: 25px;
    }
    .msg-content {
        margin: 0 10px;
        padding: 10px;
        border-radius: 8px;
        width: fit-content;
        min-width: 50px;
    }
    .my-username {
        text-align: right !important;
    }
    .bubble .msg-content {
        background-color: #9dd1fc;
    }
    .bubble-own .msg-content {
        background-color: #f1ae62;
    }
    .bubble .bubble-arrow {
        border-color: transparent transparent #9dd1fc;
    }
    .bubble-own .bubble-arrow {
        border-color: transparent transparent #f1ae62;
    }
</style>