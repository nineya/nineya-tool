package com.nineya.tool.image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.nineya.tool.spi.Builder;
import com.nineya.tool.util.ImageUtil;

/**
 * 将 图片转为 txt 文件内容
 *
 * @author 殇雪话诀别
 */
public class ImageToText implements Builder<String> {
    /**
     * 待处理的图片
     */
    private BufferedImage image;
    /**
     * 图片高度，像素点
     */
    private int height = 100;

    private ImageToText(BufferedImage image) {
        this.image = image;
    }

    public static ImageToText create(BufferedImage image) {
        return new ImageToText(image);
    }

    public static ImageToText create(String filePath) throws IOException {
        return new ImageToText(ImageIO.read(new File(filePath)));
    }

    public static ImageToText create(InputStream stream) throws IOException {
        return new ImageToText(ImageIO.read(stream));
    }


    /**
     * 图片高度，占有的字符数量
     * 一个字符高度等于2个像素点
     *
     * @param characterNum 字符数
     */
    public ImageToText setHeightCharacterNum(int characterNum) {
        this.height = characterNum * 2;
        return this;
    }

    /**
     * 获取图片中两个对应像素颜色（y-1 和 y）的深度(图片，像素x坐标，像素y坐标)
     * 因为2两符号才能组成一个正方形，所以必须由两个高度的像素点来组成一个符号
     * 横向为x，纵向为y
     * @param x 要获取的像素点位置
     * @param y 要获取的像素点位置
     * @return 像素值
     */
    private int getImagePixel(int x, int y) {
        // 求(x-1,y)像素的颜色
        int pixel = image.getRGB(x, y - 1);
        // 因为已经设置为灰度图片，RGB三值相同，这里只求一个值
        int num = (pixel & 0xff);
        pixel = image.getRGB(x, y);
        num += (pixel & 0xff);
        return num / 2;
    }

    /**
     * 根据像素点的颜色深度，进行符号转换
     *
     * @param n 0-255
     * @return
     */
    private String convertSymbol(int n) {
        if (n < 10) {
            return "@";
        } else if (n < 40) {
            return "&";
        } else if (n < 60) {
            return "%";
        } else if (n < 80) {
            return "#";
        } else if (n < 100) {
            return "$";
        } else if (n < 120) {
            return "*";
        } else if (n < 140) {
            return "|";
        } else if (n < 160) {
            return "!";
        } else if (n < 180) {
            return "\"";
        } else if (n < 200) {
            return ";";
        } else {
            return " ";
        }
    }

    /**
     * 执行字符串的转换操作
     *
     * @return
     */
    private String conversion() {
        int weight = image.getWidth();
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i < height; i += 2) {
            for (int j = 0; j < weight; j++) {
                sb.append(convertSymbol(getImagePixel(j, i)));
            }
            sb.append("\r\n");
        }
        return sb.toString();
    }

    @Override
    public String build() {
        image = ImageUtil.enlargeImageByHeight(image, height);
        image = ImageUtil.colorlessImage(image);
        return conversion();
    }
}
