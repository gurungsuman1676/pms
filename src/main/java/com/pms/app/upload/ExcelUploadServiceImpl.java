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
    public void uploadClothes(MultipartFile file, HttpServletResponse httpServletResponse, String type, String templateType, String orderType) throws Exception {

        if (orderType.isEmpty()) {
            throw new RuntimeException("Invalid order type selected");
        }

        if (templateType.isEmpty()) {
            throw new RuntimeException("Invalid Template selected");
        }
        int clothType;
        try {
            clothType = Integer.valueOf(type);

        } catch (Exception e) {
            throw new RuntimeException("Invalid Cloth Type selected");
        }
        TemplateService templateService = (TemplateService) beanFactory.getBean(templateType, file, clothType, orderType);
        templateService.process();
    }
}
