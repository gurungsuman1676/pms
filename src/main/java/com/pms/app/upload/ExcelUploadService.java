package com.pms.app.upload;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;

/**
 * Created by anepal on 2/20/2017.
 */
public interface ExcelUploadService {
    void uploadClothes(MultipartFile file, HttpServletResponse httpServletResponse, String type, String templateType, String orderType) throws IOException, ParseException, Exception;
}
