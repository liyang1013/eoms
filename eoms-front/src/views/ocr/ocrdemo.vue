<template>
  <el-upload
      class="upload-demo"
      drag
      action="https://jsonplaceholder.typicode.com/posts/"
      multiple>
    <i class="el-icon-upload"></i>
    <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
    <div class="el-upload__tip" slot="tip">只能上传jpg/png文件</div>
  </el-upload>
</template>

<script>
import { createWorker } from 'tesseract.js';

export default {
  name: "ocrdemo",
  methods:{
    ocr: async () => {
      const worker = await createWorker('pdf');
      const ret = await worker.recognize('D:/Project/FLUX/FLUX 立库资料/服务工单/FLUX WCS实施服务订单(KBD-20230816-02)V2.pdf');
      console.log(ret.data.text);
      await worker.terminate();
    }
  }
}
</script>

<style scoped>

</style>