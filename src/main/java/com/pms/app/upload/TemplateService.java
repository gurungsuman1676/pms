package com.pms.app.upload;

import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by arjun on 6/13/2017.
 */
public interface TemplateService {

    void process() throws IOException;


}