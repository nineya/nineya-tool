package com.nineya.dingtalk;

import java.net.URLEncoder;
import java.util.Base64;

import com.alibaba.fastjson.JSONObject;
import com.nineya.dingtalk.message.Message;
import com.nineya.tool.charset.Charsets;
import com.nineya.tool.http.HttpClient;
import com.nineya.tool.http.HttpRequest;
import com.nineya.tool.security.CryptoUtil;
import com.nineya.tool.text.CheckText;
import com.nineya.tool.validate.Assert;

/**
 * 机器人实例
 */
public class DingTalk {
    /**
     * 安全秘钥
     */
    private final String secret;
    /**
     * http请求配置
     */
    private final HttpClient client;

    private static final String DEFAULT_URL = "https://oapi.dingtalk.com/robot/send";

    public static DingTalk instance(String accessToken) {
        return new DingTalk(accessToken, null);
    }

    public static DingTalk instance(String accessToken, String secret) {
        return new DingTalk(accessToken, secret);
    }

    private DingTalk(String accessToken, String secret) {
        Assert.notEmptyAllowed(accessToken, "access_token");
        this.secret = secret;
        this.client = new HttpClient()
                .setContentEncoding(Charsets.UTF_8)
                .setContentType("application/json")
                .addParams("access_token", accessToken);
    }

    private DingTalk setAccessToken(String accessToken) {
        return this;
    }

    public Response send(Message message) {
        return send(message.toJsonString());
    }

    public Response send(String jsonBody) {
        HttpRequest request = HttpRequest
            .sendPost(DEFAULT_URL)
            .setBody(jsonBody);
        if (!CheckText.isEmpty(secret)) {
            long timeStamp = System.currentTimeMillis();
            request.addParams("timestamp", String.valueOf(timeStamp));
            request.addParams("sign", buildSign(timeStamp));
        }
        String response = client.execute(request).getBody();
        return JSONObject.parseObject(response, Response.class);
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
}
