<template>
  <div class="block">
    <div class="title">
      <span style="font-size: 15px; ">增值税发票识别（pdf, jpg, png, jpeg）</span>
    </div>
    <el-card>
      <el-upload multiple ref="upload"
                 list-type="picture-card"
                 action="/api/invoice/vatIdentify"
                 accept=".pdf,.jpg,.png,.jpeg"
                 :limit="8"
                 :before-upload="handleBefore"
                 :on-exceed="handleExceed"
                 :on-success="handleSuccess"
                 :on-error="handleError"
                 :on-progress="handleProgress">
        <i slot="default" class="el-icon-upload"></i>
        <div slot="file" slot-scope="{file}">
          <img class="el-upload-list__item-thumbnail" v-if="file.raw.type.indexOf('image') !== -1" :src="file.url"
               alt="">
          <pdf v-if="file.raw.type === 'application/pdf'" :src="cMapPdf(file.url)" :page="1"></pdf>
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
        <pdf :src="preview.pdf.url" :page="1"></pdf>
      </el-dialog>
    </el-card>

    <div class="title">
      <span style="font-size: 15px">识别结果</span>
      <el-button type="primary" size="small"
                 style="float: right;margin-left: 10px;"
                 @click="saveInvoice">保存
      </el-button>
      <el-button type="success" size="small" style="float: right;">验证
      </el-button>
    </div>
    <el-card>
      <el-table :data="documentList" style="width: 100%" max-height="300px" element-loading-spinner="el-icon-loading">
        <el-table-column label="发票代码" :show-overflow-tooltip="false">
          <template slot-scope="scope">
            <el-input v-model="scope.row.invoiceCode"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="发票号码" :show-overflow-tooltip="false">
          <template slot-scope="scope">
            <el-input v-model="scope.row.invoiceNo"></el-input>
          </template>
        </el-table-column>
        <el-table-column label="发票金额" :show-overflow-tooltip="false">
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
            <el-button type="text" size="medium" @click="deleteInvoice(scope.$index)">删除 <i
                class="el-icon-delete"></i></el-button>
          </template>
        </el-table-column>
      </el-table>

    </el-card>

  </div>
</template>

<script>

import pdf from 'vue-pdf-signature'

export default {
  name: "ocr",
  components: {
    pdf
  },
  data() {
    return {
      preview: {
        pdf: {dialogVisible: false, url: ''},
        image: {dialogVisible: false, url: ''}
      },
      documentList: [],
    }
  },
  methods: {
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
    saveInvoice() {
      if (!this.documentList.length) return
      let row;
      for (let i = 0; i < this.documentList.length; i++) {
        row = this.documentList[i];
        if (row.invoiceCode.length !== 12) {
          this.$message.warning('发票代码格式错误');
          return;
        }
        if (row.invoiceNo.length !== 8) {
          this.$message.warning('发票号码格式错误');
          return;
        }
        if (!row.amount) {
          this.$message.warning('发票金额不能为0');
          return;
        }
        if (!row.invoiceDate) {
          this.$message.warning('发票日期不能为空');
          return;
        }
      }
      this.$http.post('/api/invoice/saveVatInvoice', this.documentList).then(res =>{
        this.$message.success(res.data.message);
        this.documentList = [];
        this.$refs.upload.clearFiles();
      });
    },
    deleteInvoice(index) {
      this.documentList.splice(index, 1)
    },
    handleExceed() {
      this.$message.warning('一次最大上传8个')
    },
    handleBefore(file) {
      if (file.type.indexOf('image') === -1 && file.type.indexOf('pdf') === -1) {
        this.$message.warning('请上传pdf或图片格式文件');
        return false
      }
      return true
    },
    handleProgress(event, file, fileList) {
    },
    handleError(err, file, fileList) {
      this.$message.error(err)
    },
    handleSuccess(response, file, fileList) {
      if (response.status === 200) {
        let obj = response.result;
        let f = true;
        this.documentList.filter(e => e.invoiceCode === obj.invoiceCode).forEach(e => f = false);
        if (f) this.documentList.push(obj);

      } else {
        // Message.error(response.message);
        const uploadFiles = this.$refs.upload.uploadFiles
        for (let i = 0; i < uploadFiles.length; i++) {
          if (uploadFiles[i]['url'] === file.url) {
            uploadFiles.splice(i, 1)
          }
        }
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
  .block {
    ::v-deep .el-input__inner {
      box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
    }

    .title {
      margin-top: 20px;
      margin-bottom: 15px;
      padding-left: 10px;

      ::v-deep .el-button {
        border: 1px solid #e9e9e9;
        box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);
        background-color: #FFFFFF;
        color: #000000;
      }
    }

    ::v-deep .el-table tbody tr:hover > td {
      background-color: #FFFFFF !important;
      color: #000000
    }
  }
}

</style>