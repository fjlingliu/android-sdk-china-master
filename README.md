
# SDK 接入文档

| 版本号 | 修改时间    | 内容                                 |
|--------|-------------|--------------------------------------|
| 2.2.0  | 2023-04-10  | **升级oaid，兼容不同oaid的aar**<br>**升级头条事件管理6.14.3 SDK**<br>**依赖导入修改** |
| 2.2.5  | 2023-07-17  | **隐私政策oaid修改**<br>**升级到热云1.9.3**<br>**模拟器识别优化**<br>**新增快手分包**<br>**新增悬浮球红包功能** |
| 2.2.6  | 2023-08-02  | **生命周期onResume需要传入参数activity** |
| 2.2.12 | 2024-01-25  | **添加支付异常信息回调**             |
| 2.2.18 | 2024-08-28  | **授权新增数数回调**                 |
| 2.2.28 | 2025-03-12  | **新增微信登录成功返回参数**         |
| 2.2.30 | 2025-04-16  | **百度流更新到v2.7.2**               |

## 目录

1. [概述](#概述)
2. [接入流程](#接入流程)
   - [2.1 申请应用配置](#申请应用配置)
   - [2.2 导入SDK依赖](#导入sdk依赖)
     - [2.2.1 依赖导入](#依赖导入)
     - [2.2.2 依赖冲突解决](#依赖库冲突解决)
   - [2.3 build配置](#build配置)
3. [接口说明](#接口说明)
   - [3.1 Application初始化接口](#application初始化接口)
   - [3.2 授权接口](#授权接口)
   - [3.3 登录接口](#登录接口)
   - [3.4 支付接口](#支付接口)
   - [3.5 角色提交信息接口](#角色提交信息接口)
   - [3.6 应用退出接口](#应用退出接口)
   - [3.7 注销登录接口](#注销登录接口)
   - [3.8 切换登录接口](#切换登录接口)
   - [3.9 生命周期](#生命周期)
4. [注意事项](#注意事项)
   - [4.1 所有SDK接口调用都需要在调用初始化、授权接口成功以后才能继续执行](#所有-sdk-接口调用-都需要在调用application初始化授权接口成功以后才能继续执行)
   - [4.2 接口请在主线程调用](#接口请在主线程调用)
   - [4.3 Apk请勿加固](#apk请勿加固)
   - [4.4 如遇到因权限问题造成APP闪退，则请将编译版本改为28](#如遇到因权限问题造成app闪退则请将编译版本改为28)

## 概述

本文档面向安卓开发者，用于指导开发者快速接入SDK，提供多个接口给集成者调用。

**重要提醒：所有的 SDK 操作都必须在应用成功授权之后才能继续执行**

## 接入流程

### 申请应用配置

开发者需要向浩凡SDK运营人员索取出包物料（请勿直接使用demo中的参数）

物料包必要参数：
- 游戏pid
- 游戏名称
- 金额单位

### 导入SDK依赖

#### 建议开发环境

- 开发工具：Android Studio 3.0版本及以上
- Gradle版本建议：5.4.1版本及以上
- Gradle插件版本建议：3.2.2版本及以上
- SDK支持版本：最低支持Android 5.0以上版本（minSdkVersion>=21，targetSdkVersion 30）

#### 依赖导入

注：2.2.1版本开始需要支持androidx环境

```gradle
dependencies {
    // system和version查看Demo中libs
    implementation files('libs/${system}_SDK_${version}.aar')
    implementation files('libs/oaid_sdk_${version1}.aar')
    implementation files('libs/xinji-sdk-base_${version2}.aar')
    implementation 'androidx.appcompat:appcompat:${version3}'
    
    // 如果项目未使用kotlin，必须添加以下依赖
    implementation 'org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.3.61'
}
```

#### 依赖库冲突解决

SDK的AAR包已包含以下第三方库，避免重复导入造成冲突：

```gradle
dependencies {
    // 安卓X依赖
    implementation files('libs/android.core-1.1.0.jar')
    implementation files('libs/annotation-1.1.0.jar')
    implementation files('libs/fragment-1.1.0.jar')
    implementation files('libs/viewpager-1.0.0.jar')
    implementation files('libs/recyclerview-1.1.0.jar')
    
    // SDK相关
    implementation files('libs/Union_SDK_U.1.6.0_Multi.jar')
    implementation files('libs/bcprov-jdk16-139.jar')
    implementation(name: 'bdlite-5.2.4-alpha.0', ext: 'aar')
    implementation(name: 'GDTActionSDK.min.1.6.5', ext: 'aar')
    implementation(name: 'monitorsdk-1.0.4', ext: 'aar')
    implementation(name: 'sdk_gism-1.3.2', ext: 'aar')
    implementation(name: 'oaid_sdk_1.0.25', ext: 'aar')
    implementation files('libs/PRODUCT_complete_snda-woaandroid-2.5.1.1-18962.jar')
    implementation files('libs/universal-image-loader-1.9.5.jar')
    
    // 头条包
    implementation(name: 'bdlite-5.2.4-alpha.0', ext: 'aar')
    implementation(name: 'GDTActionSDK.min.1.6.5', ext: 'aar')
    implementation(name: 'monitorsdk-1.0.4', ext: 'aar')
    implementation(name: 'captcha-release', ext: 'aar')
    
    // 易盾一键登录
    implementation(name: 'quicklogin_libary-external-release-3.0.2', ext: 'aar')
    implementation(name: 'CMCCSSOSDK-WY-release', ext: 'aar')
    implementation(name: 'Ui-factory_oauth_mobile_3.9.1.8', ext: 'aar')
    implementation(name: 'CTAccount_sdk_api_v3.8.3_all_wy', ext: 'aar')
    implementation(name: 'base-sdk-libary-release', ext: 'aar')
    
    // 其他
    implementation files('libs/android-logging-log4j-1.0.3.jar')
    implementation files('libs/log4j-1.2.17.jar')
    implementation files('libs/httpmime-4.3.6.jar')
    implementation files('libs/httpcore-4.4.1.jar')
    implementation(name: 'humesdk-1.0.0', ext: 'aar')
    implementation(name: 'Baidu_ocpc_action_2_4', ext: 'aar')
    implementation files('libs/common-2.0.3.jar')
    implementation files('libs/reader-2.0.3.jar')
    implementation(name: 'quick_login_android_5.9.3', ext: 'aar')
}
```

### build配置

在应用build.gradle中添加：

```gradle
defaultConfig {
    manifestPlaceholders.put("APPLOG_SCHEME", "rangersapplog.byAx6uYt".toLowerCase())
    ndk {
        abiFilters 'armeabi-v7a', 'armeabi', 'arm64-v8a', 'x86', 'x86_64'
    }
}

packagingOptions {
    doNotStrip "*/armeabi-v7a/*.so"
    doNotStrip "*/x86/*.so"
    doNotStrip "*/arm64-v8a/*.so"
    doNotStrip "*/x86_64/*.so"
    doNotStrip "armeabi.so"
}
```

### 配置应用参数

#### 抖音数据采集（如不接可忽略此步骤）

将config.json放入assets文件夹

配置Activity接收抖音scheme：

```xml
<activity
    android:name=".XXGameActivity"
    android:exported="true">
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data
            android:host="${applicationId}"
            android:scheme="dygame${appid}" />
    </intent-filter>
</activity>
```

处理scheme：

```java
@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Uri uri = getIntent().getData();
    if (uri != null) {
        String url = uri.toString();
        boolean handled = XJGame.handleScheme(url);
        if (!handled) {
            // 自行处理其他scheme
        }
    }
}
```

#### 百度流事件管理（如不接可忽略此步骤）

Manifest配置：

```xml
<meta-data
    android:name="baiduAppKey"
    android:value="运营提供" />
<meta-data
    android:name="baiduSecretKey"
    android:value="运营提供" />
```

## 接口说明

### Application初始化接口

```java
XJGame.onCreate(Application application);
```

### 授权接口

```java
XJGame.authorization(Activity mContext, HashMap<String, String> dataMap, AuthCallBack callback);
```

参数说明：

| 参数 | 类型 | 是否可空 | 描述 |
|------|------|----------|------|
| mContext | Activity | 否 | 上下文 |
| dataMap | HashMap<String, String> | 否 | 授权参数 |
| callback | AuthCallBack | 否 | 授权回调 |

dataMap参数：

| KEY | 测试数据 | 可否为空 | 描述 |
|-----|----------|----------|------|
| pid | 310000 | 不可以 | 游戏id |
| gameid | 310000 | 不可以 | 游戏id |
| gameName | 安卓测试 | 不可以 | 游戏名称 |
| screentype | ScreenType.SCREEN_PORTRAIT | 不可以 | 屏幕方向 横屏：1竖屏：2 |
| fullScreen | true | 不可以 | 是否全屏 |
| oaid | String | 可以 | oaid的值 |
| assetFileName | String | 可以 | oaid证书文件名（放到assets中） |
| oaidLibraryString | String | 可以 | oaid库字符串 |

AuthCallBack回调：

```java
public interface AuthCallBack {
    void onAuthSuccess(); // 授权成功
    void onAuthFailed(); // 授权失败
    void onTrackEvents(String eventName, String data); // 数数回调
}
```

### 登录接口

```java
// 正常登录
XJGame.login(Activity activity, LoginCallBack callBack);

// 用户注册（注册成功后自动登录）
XJGame.register(Activity activity, RegisterCallBack callBack);
```

LoginCallBack回调：

```java
public interface LoginCallBack {
    void onLoginSuccess(UserInfo mUserInfo); // 登录成功
    void onLoginFail(); // 登录失败
    void onLoginCancel(); // 登录取消
}
```

UserInfo类：

| 字段 | 说明 |
|------|------|
| userNo | 用户唯一标识（cp用户唯一标识码） |
| loginName | 用户名 |
| token | token（用于登录验证） |
| loginType | 登录类型(18:微信) |
| extra | 其他信息（json格式） |

extra字段说明：

| 字段 | 说明 |
|------|------|
| sex | 性别 |
| country | 国家 |
| province | 省份 |
| city | 城市 |
| headImg | 头像 |
| nickName | 昵称 |
| openId | 微信公众号或小程序唯一标识 |
| unionId | 微信开放平台唯一标识 |

### 支付接口

```java
XJGame.pay(Activity activity, BuildOrderRequest request, PayCallBack callBack);
```

BuildOrderRequest参数：

| 字段 | 是否可空 | 说明 |
|------|----------|------|
| amount | 否 | 订单金额(整形字符串) |
| companyOrderNo | 否 | 厂商订单ID（唯一） |
| gamersRole | 否 | 游戏角色名 |
| productName | 否 | 商品名称 |
| serverNum | 否 | 区服ID |
| extra | 否 | 扩展信息 |
| serverName | 否 | 区服名称 |
| gamersRoleId | 否 | 角色id |
| gamersGrade | 否 | 角色等级 |
| userNo | 否 | 用户唯一标志 |
| loginName | 否 | 登录账号 |

PayCallBack回调：

```java
public interface PayCallBack {
    void onSuccess(); // 支付成功
    void onFailed(XJPayException e); // 支付失败
    void onCancel(); // 支付取消
}
```

### 角色提交信息接口

```java
XJGame.submitUserGameRole(Context context, UserGameRoleRequest request);
```

UserGameRoleRequest参数：

| 字段 | 是否可空 | 说明 |
|------|----------|------|
| gamersGrade | 否 | 游戏等级 |
| gamersRole | 否 | 角色名称 |
| pid | 否 | 游戏ID |
| gameId | 否 | 游戏ID |
| gamersRoleId | 否 | 玩家角色ID |
| serverNum | 否 | 区服ID |
| serverName | 否 | 区服名称 |
| userNo | 否 | 用户唯一标志 |
| loginName | 否 | 登录账号 |

### 应用退出接口

```java
XJGame.exit(ExitCallBack exitCallBack);
```

### 注销登录接口

```java
XJGame.logoutAccount();
```

### 切换登录接口

```java
XJGame.switchAccount(mContext);
```

注册注销回调：

```java
XJGame.registerLogoutCallBack(LogoutAccountCallBack logoutAccountCallBack);
```

### 生命周期函数

Application生命周期：

```java
XJGame.onCreate(Application application);
```

Activity生命周期：

```java
// 在Activity的对应方法中调用
XJGame.onResume(this);
XJGame.onPause();
XJGame.onActivityResult(requestCode, resultCode, data);
XJGame.onConfigurationChanged(newConfig);
```

## 注意事项

### 所有SDK接口调用都需要在调用Application初始化、授权接口成功以后才能继续执行

### 接口请在主线程调用

### Apk请勿加固

### 如遇到因权限问题造成APP闪退，则请将编译版本改为28

### Apk需支持V2或V2以上签名能力
```