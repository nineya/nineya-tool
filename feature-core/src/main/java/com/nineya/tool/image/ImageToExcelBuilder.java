package com.nineya.tool.image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.util.Units;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;

import com.nineya.tool.util.ImageUtil;

/**
 * 图片转excel
 *
 * @author 殇雪话诀别
 */
public class ImageToExcelBuilder {
    /**
     * 表格行数，将被转为图片的像素点高度
     */
    private int rowNum;
    /**
     * 表格列数，将被转为图片的像素点宽度
     */
    private int colNum;
    /**
     * 字体默认高度，单位：Points，这个高度将会被换算到表格的宽高
     * 使一个表格刚好可以容纳一个值
     */
    private short fontSize = 14;
    /**
     * 待处理的图片
     */
    private BufferedImage image;

    private String sheetName = "nineya";
    /**
     * 创建工作簿
     */
    private final SXSSFWorkbook workbook;
    /**
     * 缓存相同颜色的style点，不进行重复new
     */
    private Map<Integer, XSSFCellStyle> cellStyles = new HashMap<>();

    public ImageToExcelBuilder(BufferedImage image) {
        this.image = image;
        this.workbook = new SXSSFWorkbook();
    }

    public static ImageToExcelBuilder create(BufferedImage image) {
        return new ImageToExcelBuilder(image);
    }

    public static ImageToExcelBuilder create(String filePath) throws IOException {
        return new ImageToExcelBuilder(ImageIO.read(new File(filePath)));
    }

    public static ImageToExcelBuilder create(InputStream stream) throws IOException {
        return new ImageToExcelBuilder(ImageIO.read(stream));
    }

    public ImageToExcelBuilder setRowNum(int rowNum) {
        this.rowNum = rowNum;
        return this;
    }

    public ImageToExcelBuilder setColNum(int colNum) {
        this.colNum = colNum;
        return this;
    }

    public ImageToExcelBuilder setFontSize(short fontSize) {
        this.fontSize = fontSize;
        return this;
    }

    public ImageToExcelBuilder setSheetName(String sheetName) {
        this.sheetName = sheetName;
        return this;
    }

    public void build(OutputStream outputStream) {
        BufferedImage img = ImageUtil.enlargeImageByHeight(image, rowNum);
        // 创建工作表
        SXSSFSheet sheet = workbook.createSheet(sheetName);
        int rowHeight = (int) (fontSize * 1.2);
        // 设置行高
        sheet.setDefaultRowHeightInPoints(rowHeight);
        // 为了使长宽相等
        // 在poi中表格宽度单位等于 1/256 个字符， 可以使用 /256.0*Units.DEFAULT_CHARACTER_WIDTH 换算为Pixels(像素)
        // Pixels 和 Points 可以使用 Units 工具类换算，工具类中设置了默认的像素与点的dpi为 96/72
        // 这里将 Points 换算为 Pixels 最后换算为 1/256 个字符单位
        // 但是实际按这个值进行转换总会让宽大于高，所以这里未采用 256 这个数值
        int colWidth = Units.pointsToPixel(rowHeight * 240.0 / Units.DEFAULT_CHARACTER_WIDTH);
        // 设置默认列宽貌似有bug，未能成功，打开文件时提示异常，所以只好单独设置宽度
        // sheet.setDefaultColumnWidth(colWidth);

        short width = (short) img.getWidth();
        int height = img.getHeight();
        short minx = (short) img.getMinX();
        int miny = img.getMinY();
        // 设置默认列宽貌似有bug，未能成功，打开文件时提示异常，所以只好单独设置宽度
        for (short j = minx; j < width; j++) {
            sheet.setColumnWidth(j, colWidth);
        }
        // 按 pol 的要求，按列读取数据
        for (int i = miny; i < height; i++) {
            System.out.print("i = " + i + "  styleNum = " + cellStyles.size() + "\r");
            // 获取一列并设置高度
            SXSSFRow sheetRow = sheet.createRow(i);
            for (short j = minx; j < width; j++) {
                CellStyle style = createStyle(img.getRGB(j, i));
                // 设置单元格内容
                Cell cell = sheetRow.createCell(j);
                cell.setCellStyle(style);
            }
        }

        //写入文件
        try {
            workbook.write(outputStream);
            workbook.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    private BufferedImage resize() {
        if (rowNum <= 0 && colNum <= 0) {
            throw new IllegalArgumentException("rowNum 和 colNum 至少设置一项");
        }
        if (rowNum > 0) {
            if (colNum > 0) {
                return ImageUtil.tensileImageBySize(image, colNum, rowNum);
            }
            return ImageUtil.enlargeImageByHeight(image, rowNum);
        }
        return ImageUtil.enlargeImageByWidth(image, colNum);
    }

    /**
     * 传入颜色，生成单元格信息
     *
     * @param argb
     */
    private CellStyle createStyle(int argb) {
        XSSFCellStyle style = cellStyles.get(argb);
        if (style != null) {
            return style;
        }
        style = (XSSFCellStyle) workbook.createCellStyle();
        // 设置背景色
        XSSFColor clr = new XSSFColor(new Color(argb));
        style.setFillForegroundColor(clr);
        style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyles.put(argb, style);
        return style;
    }

}
