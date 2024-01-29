package com.keboda.eomsback.authorityReview.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.keboda.eomsback.authorityReview.pojo.AuthorityRecordsVo;
import com.keboda.eomsback.authorityReview.service.IAuthorityReviewService;
import com.keboda.eomsback.entity.BaseResult;
import com.keboda.eomsback.entity.SearchVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/authorityReview")
public class authorityReviewController {

    @Autowired
    private IAuthorityReviewService iAuthorityReviewService;

    @RequestMapping("/searchRecordsListPageHelper")
    private BaseResult searchRecordsListPageHelper(@RequestBody SearchVo searchVo){
        PageHelper.startPage(searchVo.getCurrentPage(),searchVo.getSize());
        Page<AuthorityRecordsVo> page = iAuthorityReviewService.searchRecordsListPageHelper(searchVo);
        return BaseResult.success(page,page.getTotal());
    }

    @RequestMapping("/importRecords")
    public BaseResult importRecords(MultipartFile file, String year) throws IOException {
        String message = iAuthorityReviewService.importRecords(file,year);
        return BaseResult.success(message);
    }

    @RequestMapping("/deleteRecords")
    public BaseResult deleteRecords(@RequestBody List<AuthorityRecordsVo> authorityRecordsVoList){
        iAuthorityReviewService.deleteRecords(authorityRecordsVoList);
        return BaseResult.success("删除记录成功");
    }

    @RequestMapping("/contrastRecords")
    public BaseResult contrastRecords(@RequestBody SearchVo searchVo){
        return BaseResult.success(iAuthorityReviewService.contrastRecords(searchVo));
    }

    @RequestMapping("/currentPermissionExcel")
    public void currentPermissionExcel(HttpServletResponse response) throws IOException {
        iAuthorityReviewService.currentPermissionExcel(response);
    }


    @RequestMapping("/contrastPermissionExcel")
    public void contrastPermissionExcel(@RequestParam("year") String year, HttpServletResponse response) {
        iAuthorityReviewService.contrastPermissionExcel(response,year);
    }
}
