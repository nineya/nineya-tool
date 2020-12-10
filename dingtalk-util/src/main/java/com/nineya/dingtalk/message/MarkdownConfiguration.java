package com.nineya.dingtalk.message;


import com.nineya.tool.validate.Assert;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.stream.Collectors;

/**
 * markdown配置信息
 */
public class MarkdownConfiguration <T extends DingtalkBuild> {
    /**
     * markdown消息内容
     */
    private StringBuilder markdown;
    private T build;
    private static final String LINK_FORMAT = "[%s](%s)";
    private static final String IMG_FORMAT = "![%s](%s)";
    private static final String ONE_TITLE_FORMAT = "\n# %s";
    private static final String TWO_TITLE_FORMAT = "\n## %s";
    private static final String THREE_TITLE_FORMAT = "\n### %s";
    private static final String FOUR_TITLE_FORMAT = "\n#### %s";
    private static final String FIVE_TITLE_FORMAT = "\n##### %s";
    private static final String SIX_TITLE_FORMAT = "\n###### %s";
    private static final String BOLD_FORMAT = "**%s**";
    private static final String ITALIC_FORMAT = "*%s*";
    private static final String BOLD_AND_ITALIC_FORMAT = " ***%s*** ";
    private static final String ORDER_LIST_FORMAT = "\n%d. %s";
    private static final String QUOTE_PREFIX = "\n> ";

    public MarkdownConfiguration(T build) {
        this.markdown = new StringBuilder();
        this.build = build;
    }

    /**
     * 文本加斜体
     *
     * @param text 文本内容
     * @return
     */
    public MarkdownConfiguration addItalic(String text) {
        this.markdown.append(String.format(ITALIC_FORMAT, text));
        return this;
    }

    /**
     * 文本加粗
     *
     * @param text 文本内容
     * @return
     */
    public MarkdownConfiguration addBold(String text) {
        this.markdown.append(String.format(BOLD_FORMAT, text));
        return this;
    }

    /**
     * 文本斜体加粗
     *
     * @param text 文本内容
     * @return
     */
    public MarkdownConfiguration addBoldAndItalic(String text) {
        this.markdown.append(String.format(BOLD_AND_ITALIC_FORMAT, text));
        return this;
    }

    /**
     * 添加文本信息，不添加换行，如果要添加多个段落需使用 {@newLine} 添加新的行
     * 可以用于直接传入markdown文档，如果文档过于复杂不建议使用该类进行文档创建，因为
     * 整体流程过于繁琐
     *
     * @param text 文本信息
     * @return
     */
    public MarkdownConfiguration addText(String text) {
        this.markdown.append(text);
        return this;
    }

    /**
     * 创建新行
     *
     * @return
     */
    public MarkdownConfiguration newLine() {
        this.markdown.append("\n");
        return this;
    }

    /**
     * 添加一个链接
     *
     * @param url  链接地址
     * @param desc 链接描述
     * @return
     */
    public MarkdownConfiguration addLink(String url, String desc) {
        Assert.notAllowedEmpty(url, "URL");
        markdown.append(String.format(LINK_FORMAT, desc, url));
        return this;
    }

    /**
     * 添加一段引用
     *
     * @param text 引用文章
     * @return
     */
    public MarkdownConfiguration addQuote(String text) {
        String[] texts = text.split("\n");
        this.markdown.append(Arrays.stream(texts)
            .collect(Collectors.joining(QUOTE_PREFIX, QUOTE_PREFIX, "\n\n")));
        return this;
    }

    /**
     * 添加无序列表
     *
     * @param list 列表内容
     * @return
     */
    public MarkdownConfiguration addUnOrderList(Collection<?> list) {
        this.markdown.append(list.stream()
            .map(n -> "\n- " + n).collect(Collectors.joining()));
        return this;
    }

    public MarkdownConfiguration addUnOrderList(Object[] list) {
        this.markdown.append(Arrays.stream(list)
            .map(n -> "\n- " + n).collect(Collectors.joining()));
        return this;
    }

    public MarkdownConfiguration addOrderList(Collection list) {
        Iterator iterator = list.iterator();
        int i = 1;
        while (iterator.hasNext()) {
            this.markdown.append(String.format(ORDER_LIST_FORMAT, i++, iterator.next()));
        }
        return this;
    }

    public MarkdownConfiguration addOrderList(Object[] list) {
        for (int i = 0; i < list.length; i++) {
            this.markdown.append(String.format(ORDER_LIST_FORMAT, i + 1, list[i]));
        }
        return this;
    }

    /**
     * 添加一张图片
     *
     * @param imgUrl 图片url
     * @param desc   图片描述
     * @return
     */
    public MarkdownConfiguration addImg(String imgUrl, String desc) {
        Assert.notAllowedEmpty(imgUrl, "图片URL");
        markdown.append(String.format(IMG_FORMAT, desc, imgUrl));
        return this;
    }

    /**
     * 添加一张图片，不添加描述信息
     *
     * @param imgUrl 图片url
     * @return
     */
    public MarkdownConfiguration addImg(String imgUrl) {
        return addImg(imgUrl, "");
    }

    /**
     * 添加标题
     *
     * @param title 标题内容
     * @return
     */
    public MarkdownConfiguration addOneTitle(String title) {
        Assert.notAllowedEmpty(title, "标题");
        this.markdown.append(String.format(ONE_TITLE_FORMAT, title));
        return this;
    }

    /**
     * 添加标题
     *
     * @param title 标题内容
     * @return
     */
    public MarkdownConfiguration addTw0Title(String title) {
        Assert.notAllowedEmpty(title, "标题");
        this.markdown.append(String.format(TWO_TITLE_FORMAT, title));
        return this;
    }

    /**
     * 添加标题
     *
     * @param title 标题内容
     * @return
     */
    public MarkdownConfiguration addThreeTitle(String title) {
        Assert.notAllowedEmpty(title, "标题");
        this.markdown.append(String.format(THREE_TITLE_FORMAT, title));
        return this;
    }

    /**
     * 添加标题
     *
     * @param title 标题内容
     * @return
     */
    public MarkdownConfiguration addFourTitle(String title) {
        Assert.notAllowedEmpty(title, "标题");
        this.markdown.append(String.format(FOUR_TITLE_FORMAT, title));
        return this;
    }

    /**
     * 添加标题
     *
     * @param title 标题内容
     * @return
     */
    public MarkdownConfiguration addFiveTitle(String title) {
        Assert.notAllowedEmpty(title, "标题");
        this.markdown.append(String.format(FIVE_TITLE_FORMAT, title));
        return this;
    }

    /**
     * 添加标题
     *
     * @param title 标题内容
     * @return
     */
    public MarkdownConfiguration addSixTitle(String title) {
        Assert.notAllowedEmpty(title, "标题");
        this.markdown.append(String.format(SIX_TITLE_FORMAT, title));
        return this;
    }

    protected String build() {
        return markdown.toString();
    }

    /**
     * 取得构造器
     *
     * @return
     */
    public T end() {
        return build;
    }
}