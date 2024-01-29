package com.keboda.eomsback.authorityReview.service;

import com.github.pagehelper.Page;
import com.keboda.eomsback.authorityReview.pojo.AuthorityRecordsVo;
import com.keboda.eomsback.entity.SearchVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface IAuthorityReviewService {
    Page<AuthorityRecordsVo> searchRecordsListPageHelper(SearchVo searchVo);

    String importRecords(MultipartFile file, String year) throws IOException;

    void deleteRecords(List<AuthorityRecordsVo> authorityRecordsVoList);

    Map<String,List<AuthorityRecordsVo>> contrastRecords(SearchVo searchVo);

    void currentPermissionExcel(HttpServletResponse response) throws IOException;

    void contrastPermissionExcel(HttpServletResponse response,String year);
}
