<template>
  <div class="block">
<!--    <div>-->
<!--      <el-card>-->
<!--        <span>图片灰阶   (范围：0 ~ 255) </span>-->
<!--        <el-input-number v-model="grayscale" style="float: right" :min="0" :step="1"-->
<!--                         :max="255"  :controls="false"></el-input-number>-->
<!--      </el-card>-->
<!--    </div>-->
    <div class="title">
      <span style="font-size: 15px; ">发票识别</span>
      <el-button type="info" size="small" style="float: right; background-color: #FFFFFF; color: #000000;" @click="ocr">
        OCR识别
      </el-button>
    </div>
    <el-card>
      <el-upload multiple ref="upload"
                 list-type="picture-card"
                 :data="{'grayscale':grayscale}"
                 action="/api/invoice/ocr"
                 :auto-upload="false"
                 accept=".pdf,.jpg,.png,.jpeg"
                 :limit="6"
                 :before-upload="handleBefore"
                 :on-exceed="handleExceed"
                 :on-success="handleSuccess"
                 :on-error="handleError"
                 :on-progress="handleProgress">
        <i slot="default" class="el-icon-upload"></i>
        <div slot="file" slot-scope="{file}">
          <img class="el-upload-list__item-thumbnail" v-if="file.raw.type.indexOf('image') !== -1" :src="file.url"
               alt="">
          <pdf v-if="file.raw.type === 'application/pdf'" :src="cMapPdf(file.url)"></pdf>
          <span class="el-upload-list__item-actions">
                  <span class="el-upload-list__item-preview" @click="handlePictureCardPreview(file)">
                    <i class="el-icon-zoom-in"></i>
                  </span>
                  <span class="el-upload-list__item-delete" @click="handleRemove(file)">
                    <i class="el-icon-delete"></i>
                  </span>
                </span>
        </div>
      </el-upload>

      <el-dialog :visible.sync="preview.image.dialogVisible">
        <img width="100%" :src="preview.image.url" alt="">
      </el-dialog>
      <el-dialog :visible.sync="preview.pdf.dialogVisible">
        <pdf :src="preview.pdf.url"></pdf>
      </el-dialog>
    </el-card>

    <div class="title">
      <span style="font-size: 15px">识别内容</span>

      <el-button type="info" size="small"
                 style="float: right;margin-left: 10px; background-color: #FFFFFF; color: #000000;">保存
      </el-button>
      <el-button type="info" size="small" style="float: right; background-color: #FFFFFF; color: #000000">验证
      </el-button>

    </div>
    <el-card>
      <el-table :data="documentList" style="width: 100%" max-height="300px" element-loading-spinner="el-icon-loading">
        <el-table-column label="发票代码">
          <template slot-scope="scope">
            <el-input v-model="scope.row.invoiceCode"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="发票号码" >
          <template slot-scope="scope">
            <el-input v-model="scope.row.invoiceNo"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="发票金额">
          <template slot-scope="scope">
            <el-input v-model="scope.row.amount"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="发票日期" :show-overflow-tooltip="false">
          <template slot-scope="scope">
            <el-date-picker
                v-model="scope.row.invoiceDate"
                type="date"
                value-format="yyyy-MM-dd"
                format="yyyy-MM-dd"
                placeholder="选择日期">
            </el-date-picker>
          </template>
        </el-table-column>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button type="text" size="medium"><i class="el-icon-delete"></i></el-button>
          </template>
        </el-table-column>
      </el-table>

    </el-card>

  </div>
</template>

<script>
import {createWorker} from "tesseract.js";
import pdf from 'vue-pdf';
import {Message} from 'element-ui'

export default {
  name: "ocr",
  components: {
    pdf
  },
  data() {
    return {
      grayscale: 150,
      preview: {
        pdf: {dialogVisible: false, url: ''},
        image: {dialogVisible: false, url: ''}
      },
      documentList: []
    }
  },
  methods: {
    ocr() {
      this.$refs.upload.submit();
    },
    handleRemove(file) {
      const uploadFiles = this.$refs.upload.uploadFiles
      for (let i = 0; i < uploadFiles.length; i++) {
        if (uploadFiles[i]['url'] === file.url) {
          uploadFiles.splice(i, 1)
        }
      }
    },
    handlePictureCardPreview(file) {
      if (file.raw.type === 'application/pdf') {
        this.preview.pdf = {dialogVisible: true, url: this.cMapPdf(file.url)}
      } else {
        this.preview.image = {dialogVisible: true, url: file.url}
      }
    },
    handleExceed() {
      Message.warning('一次最大上传6个')
    },
    handleBefore(file) {
      if (file.type.indexOf('image') === -1 && file.type.indexOf('pdf') === -1) {
        Message.warning('请上传pdf或图片格式文件,非法文件将直接被删除');
        return false
      }
      return true
    },
    handleProgress(event, file, fileList){},
    handleError(err, file, fileList){
      console.log(err)
      Message.error(err)
    },
    handleSuccess(response, file, fileList){
      if(response.status === 200){
        let obj = response.result;
        let f = true;
        this.documentList.filter(e => e.invoiceCode === obj.invoiceCode).forEach(e => f = false);
        if(f) this.documentList.push(obj);

      }else{
        Message.error(response.message);
      }
    },
    cMapPdf(url) {
      return pdf.createLoadingTask({
        url: url,
        cMapPacked: true,
        cMapUrl: 'https://cdn.jsdelivr.net/npm/pdfjs-dist@2.6.347/cmaps/'
      })
    }
  }
}
</script>

<style lang="scss" scoped>

body {
  background-color: #bdf4a2 !important;

  .block {
    margin: 20px;

    ::v-deep .el-input__inner{
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    }

    .title{
      margin-top: 20px;
      margin-bottom: 15px;
      padding-left: 10px;
      ::v-deep .el-button{
        border: 1px solid #e9e9e9;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
      }
    }

    ::v-deep .el-table tbody tr:hover > td {
      background-color: #FFFFFF !important;
      color: #000000
    }
  }
}

</style>