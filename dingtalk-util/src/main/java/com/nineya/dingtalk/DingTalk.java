package com.nineya.dingtalk;

import com.nineya.dingtalk.message.MessageBuild;
import com.nineya.tool.charset.Charsets;
import com.nineya.tool.http.HttpClient;
import com.nineya.tool.http.HttpRequest;
import com.nineya.tool.security.CryptoUtil;
import com.nineya.tool.text.CheckText;

import java.net.URLEncoder;
import java.util.Base64;

/**
 * 机器人实例
 */
public class DingTalk {
    /**
     * url
     */
    private final String webhook;
    /**
     * 安全秘钥
     */
    private final String secret;
    /**
     * http请求配置
     */
    private final HttpClient client;

    public static DingTalk instance(String webhook) {
        return new DingTalk(webhook, null);
    }

    public static DingTalk instance(String webhook, String secret) {
        return new DingTalk(webhook, secret);
    }

    private DingTalk(String webhook, String secret) {
        this.webhook = webhook;
        this.secret = secret;
        this.client = new HttpClient()
            .setContentEncoding(Charsets.UTF_8)
            .setContentType("application/json");
    }

    public boolean send(String jsonBody) {
        System.out.println(jsonBody);
        HttpRequest request = HttpRequest
            .sendPost(webhook)
            .setBody(jsonBody);
        if (!CheckText.isEmpty(secret)) {
            long timeStamp = System.currentTimeMillis();
            request.addParams("timestamp", String.valueOf(timeStamp));
            request.addParams("sign", buildSign(timeStamp));
        }
        String response = client.execute(request).getBody();
        System.out.println(response);
        return true;
    }

    /**
     * 使用时间戳生成令牌
     * @param timeStamp 时间戳
     * @return 令牌
     */
    private String buildSign(long timeStamp) {
        String sign = timeStamp + "\n" + secret;
        try {
            byte[] signData = CryptoUtil.hmacSHA256(secret, sign);
            return URLEncoder.encode(Base64.getEncoder().encodeToString(signData), Charsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        DingTalk talk = DingTalk.instance("https://oapi.dingtalk.com/robot/send?access_token=0471c3ac101ca898f395725b49c14f8e7e231598534c78eac61b37886eafbcce",
            "SEC5cba8a00283470c49d514faae73b79682399f14b77d6e779e9973f857f99981a");
        talk.send(MessageBuild.text().content("12345").atAll(true).atMobile("13004999290").build().toJsonString());
    }
}
