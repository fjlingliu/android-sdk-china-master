
# TopOn广告对接补充文档

> 注：如果没有接广告的需求可忽略本文档

## 导入广告第三方支持库

```gradle
// SDK适配广告的支持库
implementation "com.xinji.sdk.china:ads-topon:1.0.0.1"
```

## 调用方法

### 注册广告展示

```java
XJGame.registerAds(Activity, OnXinJiAdsListener)
```

注：该方法需要在授权后方可调用，推荐在主页面的onCreate中调用

#### 激励广告注册
```java
XJGame.registerAds(this, XJAdType.AD_TYPE_REWARD_VIDEO, "", new OnXinJiAdsListener());
```

#### 插屏广告注册
```java
XJGame.registerAds(this, XJAdType.AD_TYPE_INTERSTITIAL, "", new OnXinJiAdsListener());
```

#### banner广告注册
```java
XJGame.registerAds(this, XJAdType.AD_TYPE_BANNER, "由运维提供（广告标志名称）", new OnXinJiAdsListener());
```

### 展示广告

```java
XJGame.showAds(Activity, XJAdType, String, XJAdPosition)
```

| 参数类型 | 是否可为空 | 参数说明 |
|---------|-----------|----------|
| Activity | 否 | 上下文 |
| XJAdType | 否 | 广告类型：激励，插屏，banner |
| String | 可 | 广告位标志名称，当为banner类型时必传此字段，**该字段由运维提供** |
| XJAdPosition | 可 | 广告显示位置，当为banner类型时必传 |

#### 激励广告展示
```java
XJGame.showAds(MainActivity.this, XJAdType.AD_TYPE_REWARD_VIDEO, "", null)
```

#### 插屏广告展示
```java
XJGame.showAds(MainActivity.this, XJAdType.AD_TYPE_INTERSTITIAL, "", null)
```

#### Banner广告展示
```java
XJAdPosition xjAdPosition = new XJAdPosition();
xjAdPosition.gravity = XJAdGravity.AD_BANNER_GRAVITY_MIDDLE;
xjAdPosition.height = 200;
xjAdPosition.width = getResources().getDisplayMetrics().widthPixels;

XJGame.showAds(MainActivity.this, XJAdType.AD_TYPE_BANNER, "banner_test", xjAdPosition);
```

## 集成注意

### (1) 资源优化

如果您开启了 **shrinkResource**，则需要在 `res/raw` 路径下加一个 `keep.xml`，内容如下：

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources xmlns:tools="http://schemas.android.com/tools"
    tools:keep="@layout/anythink_*,@string/anythink_*,@drawable/anythink_*,@id/anythink_*,@dimen/anythink_*,@style/anythink_*,@color/anythink_*,@anim/anythink_*"
    tools:shrinkMode="safe" />
```

如果引入了部分第三方的资源优化框架（如：AndResProguard），需要将所有以 **anythink** 为前缀的资源添加到白名单中，例如：

```
R.string.anythink_*
R.drawable.anythink_*
R.layout.anythink_*
R.id.anythink_*
R.dimen.anythink_*
R.style.anythink_*
R.color.anythink_*
R.anim.anythink_*
```