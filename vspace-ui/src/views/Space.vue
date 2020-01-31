<template>
    <div>
        <mu-appbar class="header" color="primary" title="V - SPACE">
            <div slot="right" style="margin-right: 50px">您好！{{user.username}}</div>
        </mu-appbar>
        <div class="wrapper">
            <mu-container :fluid="true" style="height: 100%">
                <mu-row gutter style="height: 100%">
                    <mu-col span="8" style="height: 100%">
                        <mu-paper :z-depth="12" class="message-box">
                            <div id="inbox" class="inbox">
                                <message-list :msgList="msgList"></message-list>
                            </div>
                            <div class="outbox" v-if="user && user.username && started">
                                <div class="editor-box">
                                    <textarea class="editor" v-model="forSend" contenteditable="true" @keydown.enter.exact="enterKeyup($event)" @keydown.ctrl.enter="ctrlEnterKeyup($event)"></textarea>
                                </div>
                                <div class="tool-bar">
                                    <send-btn @send="send" v-model="sendKey"></send-btn>
                                </div>
                            </div>
                            <div class="outbox" v-else>
                                <mu-flex justify-content="center" style="margin-top: 10px;">
                                    <mu-text-field v-model="username" placeholder="输入用户名开始聊天"></mu-text-field>
                                    <mu-button color="primary" @click="noLoginStart">{{startBtn}}</mu-button>
                                </mu-flex>
                                <!-- <mu-flex justify-content="center">
                                    <mu-button flat color="primary">我已注册，登录聊天</mu-button>
                                </mu-flex> -->
                            </div>
                        </mu-paper>
                    </mu-col>
                    <mu-col span="4">
                         <mu-paper :z-depth="12" class="side-box">
                             <mu-tabs center :value.sync="sideTab">
                                 <mu-tab>
                                     <mu-icon value="people" left></mu-icon>成员
                                 </mu-tab>
                                 <mu-tab>
                                     <mu-icon value="chat" left></mu-icon>我的消息
                                 </mu-tab>
                             </mu-tabs>
                             <div v-show="sideTab === 0" style="padding-top: 10px">
                                 <user-list :user-list="userList"></user-list>
                             </div>
                             <div v-show="sideTab === 1" style="padding: 20px">
                                 <message-list :msgList="privateMsg"></message-list>
                             </div>
                         </mu-paper>
                    </mu-col>
                </mu-row>
            </mu-container>
        </div>
    </div>
</template>

<script>
import MessageList from '../components/MessageList'
import SendBtn from '../components/SendBtn'
import UserList from '../components/UserList'
import { mapState } from 'vuex'
import { mapMutations } from 'vuex'
import * as types from '../store/mutation-types'

export default {
    name: 'space',
    components: {MessageList, SendBtn, UserList},
    props: ['spaceName'],
    data() {
        return {
            socket: null,
            startBtn: '开始',
            started: false,
            username: '',
            sendKey: 'ctrl_enter',
            forSend: '',
            sideTab: 0,
            msgList: [],
            privateMsg: [],
            userList: []
        }
    },
    computed: mapState(['user']),
    methods: {
        ...mapMutations({
            setUsername: types.SET_USERNAME
        }),
        send: function() {
            if (this.forSend) {
                this.socket.send(JSON.stringify({
                    type: 20,
                    from: this.user.uid,
                    groupId: this.spaceName,
                    content: this.forSend
                }));
            }
            this.forSend = '';
        },
        enterKeyup: function(e) {
            if ('enter' === this.sendKey) {
                e.preventDefault();
                this.send();
            }
        },
        ctrlEnterKeyup: function(e) {
            if ('enter' !== this.sendKey) {
                e.preventDefault();
                this.send();
            }
        },
        scrollToBottom: function () {
          this.$nextTick(() => {
            var container = this.$el.querySelector("#inbox");
            container.scrollTop = container.scrollHeight;
          })
        },
        noLoginStart: function() {
            if (!this.started) {
                this.init();
            }
            this.setUsername(this.username)
            let startMsg = {
                type: 30,
                uid: this.username,
                spaceName: this.spaceName,
                username: this.username
            }
            this.socket.send(JSON.stringify(startMsg));
        },
        init: function () {
            if(typeof(WebSocket) === "undefined"){
                this.$toast.error('您的浏览器不支持WebSocket')
            }else {
                // 实例化socket
                this.socket = new WebSocket('ws://localhost:8088?spaceName=' + this.spaceName)
                // 监听socket连接
                this.socket.onopen = this.open
                // 监听socket错误信息
                this.socket.onerror = this.error
                // 监听socket消息
                this.socket.onmessage = this.getMessage
                this.socket.onclose = this.close
            }
        },
        open: function () {
            this.$toast.success('连接成功')
            this.started = true;
        },
        error: function () {
            this.$toast.error('网络异常')
        },
        getMessage: function (msgEvent) {
            let msg = JSON.parse(msgEvent.data);
            if(msg.type === 10) {
                this.userList = msg.users
            } else if(msg.type === 11) {
                this.userList.push(...msg.users)
            } else if(msg.type === 20) {
                this.msgList.push(msg);
                if (msg.from === this.user.uid || msg.to === this.user.uid) {
                    this.privateMsg.push(msg);
                }
            }
        },
        close: function () {
            this.$alert('连接已关闭', '提示')
            this.startBtn = '重连'
            this.started = false
        }
    },
    mounted: function() {
        this.init();
        this.scrollToBottom();
    },
    updated: function() {
        this.scrollToBottom();
    },
    destroyed: function() {
        this.socket.close();
    }
}

</script>

<style scoped>
    .header {
        position: absolute;
        width: 100%;
    }
    .wrapper {
        height: 100vh;
        width: 100%;
        padding-top: 80px; 
    }
    .message-box {
        width: 100%;
        height:95%;
        background-color: #fff;
        padding: 20px 20px;
    }
    .side-box {
        width: 100%;
        height: 600px;
    }
    .inbox {
        width: 100%;
        height: 80%;
        overflow: auto;
    }
    .outbox {
        width: 100%;
        height: 20%;
        border: none;
    }
    .editor-box {
        height: 100%;
        padding-bottom: 30px;
    }
    .outbox .editor {
        width: 100%;
        height: 100%;
        outline: none;
        overflow: auto;
    }
    .outbox .tool-bar {
        margin-top: -28px;
    }
    
</style>