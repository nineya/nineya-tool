# 钉钉自定义群机器人工具

## 1. 快速开始

1. 创建DingTalk实例：

```java
  String accessToken = "0471c3ac1...b37886eafbcce"
  String secret = "SEC5cba...9e9973f857f99981a"
  DingTalk talk = DingTalk.instance(accessToken, secret);
```

`accessToken`为创建机器人时提供的`Webhook`后面的`access_token`参数；

`secret`安全设置为*加签*时添加该属性，自动完成签名；



2. 发送一条Text消息：

```java
Message message = MessageBuild.text().content("消息内容").build();
Response response = talk.send(message);
```

3. 完成！



## 2. 对消息类型的封装

### 2.1 创建一条消息

`Message`采用建造者模式，支持创建多种消息类型：

```java
public interface MessageBuild extends DingtalkBuild<Message> {

    /**
     * 创建text类型消息的构建器
     * @return 消息构建器
     */
    static TextMessageBuild text() {
        return new TextMessageBuild();
    }

    /**
     * 创建link类型消息的构建器
     * @return 消息构建器
     */
    static LinkMessageBuild link() {
        return new LinkMessageBuild();
    }

    /**
     * 创建markdown类型消息的构建器
     * @return 消息构建器
     */
    static MarkdownMessageBuild markdown() {
        return new MarkdownMessageBuild();
    }

    /**
     * 创建actionCard类型消息的构建器
     * @return 消息构建器
     */
    static ActionCardMessageBuild actionCard() {
        return new ActionCardMessageBuild();
    }

    /**
     * 创建feedCard类型消息的构建器
     * @return 消息构建器
     */
    static FeedCardMessageBuild feedCard() {
        return new FeedCardMessageBuild();
    }
}
```



### 2.2 创建Text消息

创建Text消息建造器

```java
MessageBuild.text()
```

支持的配置方法有：

|                  方法                   | 必须 |         说明          |
| :-------------------------------------: | :--: | :-------------------: |
|         content(String content)         |  是  |       消息内容        |
|                 atAll()                 |  否  |        @所有人        |
|         atMobile(String mobile)         |  否  | 添加一个被@人的手机号 |
| atMobiles(Collection<String> atMobiles) |  否  | 添加一组被@人的手机号 |



### 2.3 创建Link消息

创建Link消息建造器

```java
MessageBuild.link()
```

支持的配置方法有：

|             方法              | 必须 |              说明              |
| :---------------------------: | :--: | :----------------------------: |
|      title(String title)      |  是  |            消息标题            |
|       text(String text)       |  是  | 消息内容。如果太长只会部分展示 |
| messageUrl(String messageUrl) |  是  |       点击消息跳转的URL        |
|     picUrl(String picUrl)     |  否  |            图片URL             |



### 2.4 创建Markdown消息

创建Markdown消息建造器

```java
MessageBuild.markdown()
```

支持的配置方法有：

- at用户这块钉钉有bug，群只有一个人时不能成功@

|                        方法                         | 必须 |             说明             |
| :-------------------------------------------------: | :--: | :--------------------------: |
|                 title(String title)                 |  是  |    首屏会话透出的展示内容    |
| text(BuilderAdapter<MarkdownConfiguration> adapter) |      | markdown格式的消息处理适配器 |
|            MarkdownConfiguration text()             |      |      获取markdown配置类      |
|                       atAll()                       |  否  |           @所有人            |
|               atMobile(String mobile)               |  否  |    添加一个被@人的手机号     |
|       atMobiles(Collection<String> atMobiles)       |  否  |    添加一组被@人的手机号     |



### 2.5 创建ActionCard消息

创建ActionCard消息建造器

```java
MessageBuild.actionCard()
```

支持的配置方法有：

|                            方法                            | 必须 |             说明             |
| :--------------------------------------------------------: | :--: | :--------------------------: |
|                    title(String title)                     |  是  |    首屏会话透出的展示内容    |
|                MarkdownConfiguration text()                |      | markdown格式的消息处理适配器 |
|                MarkdownConfiguration text()                |      |      获取markdown配置类      |
|     overall(BuilderAdapter<OverallActionCard> adapter)     |      |    整体跳转消息类型适配器    |
| independent(BuilderAdapter<IndependentActionCard> adapter) |      |    独立跳转消息类型适配器    |
|              btnOrientation(boolean vertical)              |  否  |       按键是否竖直排列       |



### 2.6 创建FeedCard消息

创建FeedCard消息建造器

```java
MessageBuild.feedCard()
```

支持的配置方法有：

|                          方法                           | 必须 |            说明            |
| :-----------------------------------------------------: | :--: | :------------------------: |
| addLink(String title, String messageUrl, String picUrl) |  是  | 添加一条信息，可以添加多组 |



## 3. 其他类型

### 3.1 MarkdownConfiguration markdown支持

Markdown消息和ActionCard消息消息采用markdown书写消息内容，工具类提供了markdown文本创建支持，支持的配置方法有：

|                 方法                  |         说明         |
| :-----------------------------------: | :------------------: |
|       appendItalic(String text)       |     追加斜体文本     |
|        appendBold(String text)        |     追加粗体文本     |
|   appendBoldAndItalic(String text)    |  追加粗体且斜体文本  |
|        appendText(String text)        |     追加普通文本     |
|               newLine()               |       创建新行       |
|  appendLink(String url, String desc)  |     追加一个链接     |
|      addQuote(String text) |     添加一段引用     |
| addUnOrderList(Collection<?> list) |     添加无序列表     |
| addUnOrderList(Object[] list) |     添加无序列表     |
| addOrderList(Collection list) |     添加有序列表     |
| addOrderList(Object[] list) |     添加有序列表     |
| addImg(String imgUrl, String desc) | 添加一张带描述的图片 |
| addImg(String imgUrl) | 添加一张不带描述的图片 |
| addOneTitle(String title) | 添加一级标题 |
| addTwoTitle(String title) | 添加二级标题 |
| addThreeTitle(String title) | 添加三级标题 |
| addFourTitle(String title) | 添加四级标题 |
| addFiveTitle(String title) | 添加五级标题 |
| addSixTitle(String title) | 添加六级标题 |
| T end() | 返回创建本配置的构造器 |



### 3.2 OverallActionCard整体跳转

用于创建ActionCard整体跳转类型消息，支持的配置方法有：

|              方法               | 必须 |       说明       |
| :-----------------------------: | :--: | :--------------: |
| singleTitle(String singleTitle) |  是  |   设置按键标题   |
|   singleUrl(String singleUrl)   |  是  | 设置按键跳转链接 |



### 3.3 IndependentActionCard独立跳转

用于创建ActionCard独立跳转类型消息，支持的配置方法有：

|                  方法                  | 必须 |           说明           |
| :------------------------------------: | :--: | :----------------------: |
| addBtn(String title, String actionUrl) |  是  | 添加一个按键，可添加多组 |



### 3.4 Response响应实体

执行`talk.send(message)`发送一条消息将返回该消息实体，包含消息是否发送成功和响应消息。

|  参数   |  类型  |          说明           |
| :-----: | :----: | :---------------------: |
| errcode |  int   | 错误码，为0表示发送成功 |
| errmsg  | String |      错误消息内容       |

钉钉机器人开发文档：https://ding-doc.dingtalk.com/doc#/serverapi2/qf2nxq/9e91d73c