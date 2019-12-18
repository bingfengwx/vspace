<template>
    <div class="send-btn">
        <mu-button small color="primary" @click="send">发送
            <mu-menu placement="top-start" open-on-hover>
                <mu-icon right value="arrow_drop_down" style="line-height: 36px;"></mu-icon>
                <mu-list slot="content" :value="sendKey">
                    <mu-list-item button value="enter" @click="sendKey = 'enter'">
                        <mu-list-item-title>ENTER</mu-list-item-title>
                        <mu-list-item-action>
                            <mu-radio value="enter" v-model="sendKey" uncheck-icon="panorama_fish_eye" checked-icon="check_circle_outline"></mu-radio>
                        </mu-list-item-action>
                    </mu-list-item>
                    <mu-list-item button value="ctrl_enter" @click="sendKey = 'ctrl_enter'">
                        <mu-list-item-title>CTRL + ENTER</mu-list-item-title>
                        <mu-list-item-action>
                            <mu-radio value="ctrl_enter" v-model="sendKey" checked-icon="check_circle_outline"></mu-radio>
                        </mu-list-item-action>
                    </mu-list-item>
                </mu-list>
            </mu-menu>
        </mu-button>
    </div>
</template>

<script>
export default {
    data() {
        return {
            sendKey: this.value
        }
    },
    props: {
        value: String
    },
    watch: {
        sendKey: function(val) {
            this.$emit('input', val);
        }
    },
    methods: {
        send: function() {
            this.$emit('send');
        }
    }
}
</script>

<style scoped>
    .send-btn {
        float: right;
    }
</style>