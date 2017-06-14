package com.pms.app.upload;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * Created by arjun on 6/13/2017.
 */
public class RukelTemplate extends AbstractTemplate implements TemplateService {
    private static final String CUSTOMER_NAME_ALIAS = "CUSTOMER NAME";
    private static final String DELIVERY_DATE = "DELIVERY DATE";
    private static final String ORDER_NO = "ORDER NO";
    private static final String COLOR_NAME = "COLOR NAME";
    private static final String COLOR_CODE = "COLOR CODE";
    private static final int clothType = 0;

    public RukelTemplate(MultipartFile file) throws IOException {
        super(file, 1);
    }

    @Override
    public void process() {

    }

}
