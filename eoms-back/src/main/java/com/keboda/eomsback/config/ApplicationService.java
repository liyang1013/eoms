package com.keboda.eomsback.config;

import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.io.resource.Resource;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;
import com.keboda.eomsback.base.pojo.Operation;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationService implements DisposableBean {

    /**
     * 销毁前保存数据
     */
    @Override
    public void destroy() {
        Resource operation = ResourceUtil.getResourceObj("json/operation.json");
        FileWriter operationWriter = new FileWriter(operation.getUrl().getPath());
        operationWriter.write(JSONUtil.toJsonStr(OperationConfig.getOperation()));

        Resource contact = ResourceUtil.getResourceObj("json/contact.json");
        FileWriter contactWriter = new FileWriter(contact.getUrl().getPath());
        contactWriter.write(JSONUtil.toJsonStr(ContactConfig.getContact()));

    }
}
