package com.nineya.tool.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nineya.tool.image.ImageToText;
import com.nineya.tool.text.TimeUtil;
import com.nineya.tool.validate.Assert;

/**
 * 图片相关的功能控制器
 *
 * @author 殇雪话诀别
 */
@RestController
@RequestMapping("image")
public class ImageController {
    /**
     * 最大的图片大小，单位为字节
     */
    private static final int MAX_SIZE = 512 * 1024;
    /**
     * 图片文件名模板
     */
    private String fileNameFormat;

    public ImageController() {
        String homePath = "image";
        File file = new File(homePath);
        int n = 0;
        while (file.isFile()) {
            n++;
            file = new File(homePath + "-" + n);
        }
        if (!file.exists()) {
            file.mkdirs();
        }
        this.fileNameFormat = "'" + file.getAbsolutePath() + File.separator + "'yyyyMMddHHmmss'_%s.png'";
    }

    /**
     * 将图片转text
     *
     * @return
     */
    @PostMapping("toText")
    public String imageToText(@RequestParam("image") MultipartFile image,
                              @RequestParam("characterNum") int characterNum) throws IOException {
        Assert.trueAllowed(image.getSize() <= MAX_SIZE, "图片文件不能大于 512 Kb");
        Assert.trueAllowed(characterNum <= 500, "字符高度不能大于 500");
        String type = image.getContentType();
        if (!"image/jpg".equalsIgnoreCase(type) && !"image/png".equalsIgnoreCase(type)
            && !"image/jpeg".equalsIgnoreCase(type)) {
            throw new IllegalArgumentException("文件内容不符合要求");
        }
        String filePath = String.format(TimeUtil.currentTime(fileNameFormat), (int) (Math.random() * 1000));
        image.transferTo(new File(filePath));

        return ImageToText.create(filePath).setHeightCharacterNum(characterNum).build();
    }
}
