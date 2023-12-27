<template>
  <div>
    <input type="file" @change="handleFileUpload">
    <el-input
        type="textarea"
        autosize
        placeholder="请输入内容"
        v-model="textarea1">
    </el-input>
  </div>
</template>

<script>
import {createWorker} from "tesseract.js";

export default {
  name: "UtilsIndex",
  data() {
    return {
      fileList: [],
      textarea1: null
    }
  },
  methods:{
    ocr: async() => {
      const worker = await createWorker("chi_sim", 1);
      const ret = await worker.recognize('D:/Project/FLUX/FLUX 立库资料/服务工单/FLUX WCS实施服务订单(KBD-20230816-02)V2.pdf');
      this.textarea1 = ret.data.text;
      await worker.terminate();
    },
    handleFileUpload(event){
      const file = event.target.files[0];
      const reader = new FileReader();
      reader.onload = (e) => {
        console.log(e.target.result); // 这是文件的Base64编码
      };
      reader.readAsDataURL(file);
    }
  }
}
</script>

<style scoped>
.upload-demo{
  margin: 20px 0;
  text-align: center;
  height: 200px;
}
</style>