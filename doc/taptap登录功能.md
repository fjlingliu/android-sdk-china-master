```markdown
# 1. 加载库引用

```gradle
implementation 'io.github.sinaweibosdk:core:13.10.5@aar'
implementation 'com.taptap.sdk:tap-core:4.3.12'
implementation 'com.taptap.sdk:tap-kit:4.3.12'
implementation 'com.taptap.sdk:tap-login:4.3.12'
implementation "com.xinji.sdk.china:login-taptap:1.0.0.1"
```

# 2. 冲突解决

如果编译时出现文件冲突，请在应用级 `build.gradle` 文件的 `android` 块中添加：

```gradle
android {
    packagingOptions {
        // taptap 登录冲突解决
        exclude 'META-INF/kotlinx-serialization-json.kotlin_module'
        exclude 'META-INF/com.android.tools/proguard/coroutines.pro'
        exclude 'META-INF/kotlinx-serialization-protobuf.kotlin_module'
        exclude 'META-INF/kotlinx_coroutines_core.version'
    }
}
```