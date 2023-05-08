<template>
  <div class="chatAppBody">
    <div class="chatTitle">ChatGPT</div>
    <div class="chatBox">
      <div v-for="row in msgList" v-bind:key="row.name">
        <div v-if="!row.isme">
          <div class="chatNotice">{{ row.time }}</div>
          <div class="chatRow">
            <el-avatar class="chatAvatar" :size="30" :src="row.avator"></el-avatar>
            <div class="chatMsgContent">
              <div class="chatUsername">{{ row.name }}</div>
              <div class="chatContent" v-html="row.content"></div>
            </div>
          </div>
        </div>
        <div v-if="row.isme">
          <div class="chatNotice" v-if="row.show_time">{{ row.time }}</div>
          <div class="chatRow chatRowMe">
            <div class="chatContent" v-html="row.content"></div>
          </div>
        </div>
      </div>
    </div>
    <div class="chatBottom">
      <div class="chatAreaBox">
        <div class="chatArea">
          <textarea class="chatAreaInput" v-model="originMessage" @keyup.ctrl.enter.exact="chatToUser($event)"
                    @keyup.enter.exact="chatToUser($event,'enter')"></textarea>
          <div @click="chatToUser($event)" class="chatSendBtn iconfont icon-fasong"></div>
        </div>
      </div>
    </div>
    <audio id="chatMessageAudio" style="display: none;"></audio>
  </div>
</template>

<script>
export default {
  name: 'chatApp',
  data() {
    return {
      window: window,
      secret: "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",
      message: "",
      originMessage: "",
      msgList: [],
      sendDisable: false,
    }
  },
  methods: {
    //发送给客户
    chatToUser: function (e) {
      let _this = this;
      if (this.sendDisable) return;
      //用户点击了ctrl+enter触发
      if (e && e.ctrlKey && e.keyCode === 13) {
        this.originMessage += '\n';
        return;
      }
      if (this.originMessage === "") return;
      _this.message += `(You:${this.originMessage})`;
      let sendMessage = {
        "prompt": _this.message,
        "max_tokens": 2048,
        "model": "text-davinci-003",
      };
      let showMessage = {
        isme: true,
        content: this.originMessage,
        show_time: false,
      }
      this.msgList.push(showMessage);
      this.scrollBottom();
      let headers = {
        headers: {
          'Authorization': `Bearer ${this.secret}`,
        },
      }
      this.sendDisable = true;
      this.$http.post('https://api.openai.com/v1/completions', sendMessage, headers).then(function (response) {
        _this.sendDisable = false;
        if (response.data.choices.length <= 0) {
          this.$message({
            message: response.data.error.message,
            type: 'error'
          });
          return;
        }
        _this.originMessage = "";
        let retMessage = response.data.choices[0].text;
        _this.message += retMessage;
        let showMessage = {
          isme: false,
          avator: "https://goflychat.oss-cn-hangzhou.aliyuncs.com/static/upload/avator/2022June/32a988a3c2f8700119fa1f5da1b6a4bd.png",
          content: retMessage.replaceAll("\n", "<br>"),
          time: new Date()
        }
        _this.msgList.push(showMessage);
        _this.scrollBottom();
      })
    },
    //滚动到底部
    scrollBottom: function () {
      var _this = this;
      this.$nextTick(function () {
        var container = _this.$el.querySelector(".chatBox");
        container.scrollTop = 999999999;
      });
    }
  }
}
</script>

<style lang="scss">
.chatAppBody {
  display: flex;
  flex-direction: column;
  height: 100vh - 100;
  background-color: #f1f5f8;
}

.chatTitle {
  background: #fff;
  padding: 5px 0px;
  text-align: center;
  font-size: 14px;
}

.chatBox {
  flex: 1;
  padding: 0px 5px;
  padding-bottom: 15px;
  overflow: auto;
}

.chatBottom {
  display: flex;
  flex-direction: column;
}

.chatRow {
  display: flex;
  align-items: flex-end;
  margin: 5px 0px;
}

.chatAvatar {
  margin-right: 5px;
  flex-shrink: 0;
}

.chatUsername {
  font-size: 12px;
  white-space: nowrap;
  color: #999;
  margin-bottom: 2px;
}

.chatContent {
  border-radius: 10px 10px 10px 0px;
  padding: 10px;
  background-color: rgb(255, 255, 255);
  box-shadow: 0 5px 30px rgb(50 50 93 / 8%), 0 1px 3px rgb(0 0 0 / 5%);
  font-size: 14px;
  word-break: break-all;
  line-height: 21px;
  display: inline-block;
}

.chatRowMe {
  justify-content: flex-end;
}

.chatRowMe .chatContent {
  border-radius: 10px 10px 0px 10px;
}

.chatNotice {
  text-align: center;
  color: #bbb;
  margin: 8px 0;
  font-size: 12px;
}

.chatAreaBox {
  margin: 0px 10px;
  margin-bottom: 10px;
  box-shadow: 0 5px 30px rgb(50 50 93 / 8%), 0 1px 3px rgb(0 0 0 / 5%);
  border-radius: 10px;
}

.chatArea {
  display: flex;
  padding: 3px 5px;
  align-items: center;
  background: #fff;
  border-radius: 10px;
}

.chatArea .iconfont {
  color: #383838;
  font-size: 18px;
  margin: 0px 6px;
  cursor: pointer;
}

.chatArea .iconfont:hover {
  color: #409eff;
}

.chatAreaInput {
  border-radius: 10px;
  border: 0;
  flex: 1;
  outline: none;
  resize: none;
  box-sizing: border-box;
  color: #505050;
  min-height: 50px;
  font-size: 16px;
}

.chatCopyright {
  color: #999a9b;
  font-size: 12px;
  text-align: center;
  margin-bottom: 10px;
  filter: grayscale(1);
  opacity: .9;
  font-family: Inter, -apple-system, system-ui, BlinkMacSystemFont, Segoe UI, Roboto, Helvetica Neue, Tahoma, Arial, sans-serif;
}

.chatContent a {
  color: #07a9fe;
  text-decoration: none;
}

.alink {
  display: inline-block;
  word-break: break-all;
  color: #07a9fe;
  font-size: 12px;
  cursor: pointer;
}

</style>