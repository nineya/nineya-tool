package com.nineya.tool.util;

import java.awt.*;
import java.awt.color.ColorSpace;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;

/**
 * 图片处理工具类
 *
 * @author 殇雪话诀别
 */
public class ImageUtil {
    /**
     * 将图片修改为黑白无色
     *
     * @param image 图片源
     * @return 输出修改后的图片
     */
    public static BufferedImage colorlessImage(BufferedImage image) {
        int iw = image.getWidth();
        int ih = image.getHeight();
        Graphics2D srcG = image.createGraphics();
        RenderingHints rhs = srcG.getRenderingHints();

        ColorSpace cs = ColorSpace.getInstance(ColorSpace.CS_GRAY);
        ColorConvertOp theOp = new ColorConvertOp(cs, rhs);
        BufferedImage dstImg = new BufferedImage(iw, ih, BufferedImage.TYPE_INT_RGB);
        theOp.filter(image, dstImg);
        return dstImg;
    }

    /**
     * 将图片放大n倍
     *
     * @param image 图片源
     * @param n     倍数
     * @return 修改后的图片
     */
    public static BufferedImage enlargeImage(BufferedImage image, double n) {
        // 得到源图宽
        int width = image.getWidth();
        // 得到源图长
        int height = image.getHeight();
        int targetWidth = (int) (width * n);
        int targetHeight = (int) (height * n);
        return tensileImageBySize(image, targetWidth, targetHeight);
    }

    /**
     * 不改变图片比例，将图片将图片缩放到指定高度
     *
     * @param targetHeight 目标高度
     * @return
     */
    public static BufferedImage enlargeImageByHeight(BufferedImage image, int targetHeight) {
        // 得到源图宽
        int width = image.getWidth();
        // 得到源图长
        int height = image.getHeight();
        int targetWidth = targetHeight * width / height;
        return tensileImageBySize(image, targetWidth, targetHeight);
    }

    /**
     * 不改变图片比例，将图片将图片缩放到指定宽度
     *
     * @param targetWidth 目标高度
     * @return
     */
    public static BufferedImage enlargeImageByWidth(BufferedImage image, int targetWidth) {
        // 得到源图宽
        int width = image.getWidth();
        // 得到源图长
        int height = image.getHeight();
        int targetHeight = targetWidth * height / width;
        return tensileImageBySize(image, targetWidth, targetHeight);
    }

    /**
     * 拉伸图片为指定的长宽比，调整长度不足的一面，只拉伸不会压缩
     *
     * @param image 图片源
     * @param n     宽高比(宽度/高度)
     * @return 修改后的图片
     */
    public static BufferedImage tensileImage(BufferedImage image, double n) {
        // 得到源图宽
        int width = image.getWidth(null);
        // 得到源图长
        int height = image.getHeight(null);
        double rate = width / height;
        // 宽高比小于指定的值，说明宽度不足
        if (rate < n) {
            width = (int) (height * rate);
        } else {
            height = (int) (width / rate);
        }
        return tensileImageBySize(image, width, height);
    }

    /**
     * 拉伸图片为指定大小
     *
     * @param image  图片源
     * @param width  调整后的图片宽度
     * @param height 调整后的图片高度
     * @return 修改后的图片
     */
    public static BufferedImage tensileImageBySize(BufferedImage image, int width, int height) {
        BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        tag.getGraphics().drawImage(image, 0, 0, width, height, null);
        return tag;
    }

    /**
     * 取得图片的像素点为一个int的一维数组
     * pixel   = rgbArray[offset + (y-startY)*scansize + (x-startX)]
     * getRGb又可以用于拼合图片
     * offset是在原数组上的值
     * scansize 我理解为一行的长度
     * 最终执行的内容和 getRGB(int x, int y) 没有什么不同
     *
     * @param image 图片
     * @return int一维数组
     */
    public static int[] JoinPixel(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int minx = image.getMinX();
        int miny = image.getMinY();
        return image.getRGB(minx, miny, width, height, null, 0, width - minx);
    }

    /**
     * 取得像素点为 二维数组
     *
     * @param image
     * @return
     */
    public static int[][] imagePixel(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        int minx = image.getMinX();
        int miny = image.getMinY();
        int[][] pixels = new int[width - minx][height - miny];
        for (int i = minx; i < width; i++) {
            for (int j = miny; j < height; j++) {
                pixels[i - minx][j - miny] = image.getRGB(i, j);
            }
        }
        return pixels;
    }
}
