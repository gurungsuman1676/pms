package com.pms.app.util.impl;

import com.itextpdf.text.pdf.Barcode128;
import com.pms.app.util.BarcodeService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

@Component
public class BarcodeServiceImpl implements BarcodeService {

    @Override
    public String getBarcodeForCloth(Long id) {
        Barcode128 barcode128 = new Barcode128();
        barcode128.setCode(id + "");
        barcode128.setBarHeight(25f); // great! but what about width???
        Image awtImage = barcode128.createAwtImage(Color.BLACK, Color.WHITE);
        BufferedImage bImage = new BufferedImage(awtImage.getWidth(null), awtImage.getHeight(null), BufferedImage.TYPE_INT_RGB);
        Graphics2D g = bImage.createGraphics();
        g.drawImage(awtImage, 0, 0, null);
        g.dispose();
        try {
            ImageIO.write(bImage, "jpg", new File("sb-admin-angular-master/app/images/" + ("barcode-") + id + ".jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "barcode-" + id + ".jpg";
    }
}
