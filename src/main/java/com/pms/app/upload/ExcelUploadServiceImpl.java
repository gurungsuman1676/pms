package com.pms.app.upload;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * Created by anepal on 2/20/2017.
 */

@Service
public class ExcelUploadServiceImpl implements ExcelUploadService {
    private final BeanFactory beanFactory;

    @Autowired
    public ExcelUploadServiceImpl(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }


    @Override
    public void uploadClothes(MultipartFile file, HttpServletResponse httpServletResponse, String type) throws Exception {

        TemplateService templateService = null;
        if (type.equals("KNITTING_MULTIPLE")) {
            templateService = (TemplateService) beanFactory.getBean("dannyTemplate", file);
        } else if (type.equals("KNITTING_SINGLE")) {
            templateService = (TemplateService) beanFactory.getBean("phillipeTemplate", file);
        } else {
            templateService = (TemplateService) beanFactory.getBean("rukelTemplate", file);
        }
        templateService.process();
    }
}