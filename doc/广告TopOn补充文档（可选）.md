
# TopOn广告对接补充文档

> 注：如果没有接广告的需求可忽略本文档

## 导入广告第三方支持库

```gradle
// SDK适配广告的支持库
implementation "com.xinji.sdk.china:ads-topon:1.0.0.2"
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
    tools:keep="@string/anythink_*,@drawable/anythink_*,@layout/anythink_*,@id/anythink_*,@dimen/anythink_*,@style/anythink_*,@color/anythink_*,@string/ksad_*,@drawable/ksad_*,@layout/ksad_*,@id/ksad_*,@style/ksad_*,@color/ksad_*,@attr/ksad_*,@dimen/ksad_*,@anim/bullet_bottom_dialog_enter,@anim/bullet_bottom_dialog_exit,@anim/cj_pay_activity_add_in_animation,@anim/cj_pay_activity_fade_in_animation,@anim/cj_pay_activity_fade_out_animation,@anim/cj_pay_activity_remove_out_animation,@anim/cj_pay_expo_easeout_interpolator,@anim/cj_pay_fragment_down_out_animation,@anim/cj_pay_fragment_up_in_animation,@anim/cj_pay_quadratic_easein_interpolator,@anim/cj_pay_slide_in_from_bottom_with_bezier,@anim/cj_pay_slide_out_to_bottom_with_bezier,@anim/cj_pay_slide_right_in,@anim/ec_alpha_in,@anim/ec_alpha_out,@anim/ec_base_enter,@anim/ec_base_exit,@anim/ec_bottom_in,@anim/ec_bottom_out,@anim/ec_commerce_activity_in,@anim/ec_commerce_activity_out,@anim/ec_commerce_pre_out,@anim/ec_pop_bottom_in,@anim/ec_pop_bottom_out,@anim/ec_pop_slide_in,@anim/ec_pop_slide_out,@anim/ec_slide_in,@anim/ec_slide_out,@anim/ec_zoom_in,@anim/ec_zoom_out,@anim/shopping_popup_fade_in,@anim/shopping_popup_fade_out,@anim/ttlive_alpha_in,@anim/ttlive_alpha_out,@anim/ttlive_dialog_popup_enter,@anim/ttlive_dialog_popup_exit,@anim/ttlive_popup_enter,@anim/ttlive_popup_exit,@anim/ttlive_slide_in_bottom,@anim/ttlive_slide_in_bottom_fast,@anim/ttlive_slide_in_bottom_normal,@anim/ttlive_slide_in_left,@anim/ttlive_slide_in_right,@anim/ttlive_slide_in_top,@anim/ttlive_slide_out_bottom,@anim/ttlive_slide_out_bottom_fast,@anim/ttlive_slide_out_bottom_normal,@anim/ttlive_slide_out_left,@anim/ttlive_slide_out_right,@anim/ttlive_slide_out_top,@color/ec_store_window_background,@color/tt_appdownloader_notification_material_background_color,@color/tt_appdownloader_notification_title_color,@color/tt_appdownloader_s1,@color/tt_appdownloader_s13,@color/tt_appdownloader_s18,@color/tt_appdownloader_s4,@color/tt_appdownloader_s8,@color/ttdownloader_transparent,@drawable/tt_appdownloader_action_bg,@drawable/tt_appdownloader_action_new_bg,@drawable/tt_appdownloader_ad_detail_download_progress,@drawable/tt_appdownloader_detail_download_success_bg,@drawable/tt_appdownloader_download_progress_bar_horizontal,@drawable/tt_appdownloader_download_progress_bar_horizontal_new,@drawable/tt_appdownloader_download_progress_bar_horizontal_night,@drawable/ttdownloader_bg_appinfo_btn,@drawable/ttdownloader_bg_appinfo_dialog,@drawable/ttdownloader_bg_button_blue_corner,@drawable/ttdownloader_bg_kllk_btn1,@drawable/ttdownloader_bg_kllk_btn2,@drawable/ttdownloader_bg_transparent,@drawable/ttdownloader_bg_white_corner,@drawable/ttdownloader_dash_line,@drawable/ttdownloader_icon_back_arrow,@drawable/ttdownloader_icon_download,@drawable/ttdownloader_icon_yes,@id/auto,@id/cancel_tv,@id/confirm_tv,@id/dash_line,@id/delete_tv,@id/iv_app_icon,@id/iv_detail_back,@id/iv_privacy_back,@id/line,@id/ll_download,@id/message_tv,@id/permission_list,@id/privacy_webview,@id/title_bar,@id/tt_appdownloader_action,@id/tt_appdownloader_desc,@id/tt_appdownloader_download_progress,@id/tt_appdownloader_download_progress_new,@id/tt_appdownloader_download_size,@id/tt_appdownloader_download_status,@id/tt_appdownloader_download_success,@id/tt_appdownloader_download_success_size,@id/tt_appdownloader_download_success_status,@id/tt_appdownloader_download_text,@id/tt_appdownloader_icon,@id/tt_appdownloader_root,@id/tv_app_detail,@id/tv_app_developer,@id/tv_app_name,@id/tv_app_privacy,@id/tv_app_version,@id/tv_empty,@id/tv_give_up,@id/tv_permission_description,@id/tv_permission_title,@layout/tt_appdownloader_notification_layout,@layout/ttdownloader_activity_app_detail_info,@layout/ttdownloader_activity_app_privacy_policy,@layout/ttdownloader_dialog_appinfo,@layout/ttdownloader_dialog_select_operation,@layout/ttdownloader_item_permission,@string/tt_appdownloader_button_cancel_download,@string/tt_appdownloader_button_queue_for_wifi,@string/tt_appdownloader_button_start_now,@string/tt_appdownloader_download_percent,@string/tt_appdownloader_download_remaining,@string/tt_appdownloader_download_unknown_title,@string/tt_appdownloader_duration_hours,@string/tt_appdownloader_duration_minutes,@string/tt_appdownloader_duration_seconds,@string/tt_appdownloader_jump_unknown_source,@string/tt_appdownloader_label_cancel,@string/tt_appdownloader_label_cancel_directly,@string/tt_appdownloader_label_ok,@string/tt_appdownloader_label_reserve_wifi,@string/tt_appdownloader_notification_download,@string/tt_appdownloader_notification_download_complete_open,@string/tt_appdownloader_notification_download_complete_with_install,@string/tt_appdownloader_notification_download_complete_without_install,@string/tt_appdownloader_notification_download_continue,@string/tt_appdownloader_notification_download_delete,@string/tt_appdownloader_notification_download_failed,@string/tt_appdownloader_notification_download_install,@string/tt_appdownloader_notification_download_open,@string/tt_appdownloader_notification_download_pause,@string/tt_appdownloader_notification_download_restart,@string/tt_appdownloader_notification_download_resume,@string/tt_appdownloader_notification_download_space_failed,@string/tt_appdownloader_notification_download_waiting_net,@string/tt_appdownloader_notification_download_waiting_wifi,@string/tt_appdownloader_notification_downloading,@string/tt_appdownloader_notification_install_finished_open,@string/tt_appdownloader_notification_insufficient_space_error,@string/tt_appdownloader_notification_need_wifi_for_size,@string/tt_appdownloader_notification_no_internet_error,@string/tt_appdownloader_notification_no_wifi_and_in_net,@string/tt_appdownloader_notification_paused_in_background,@string/tt_appdownloader_notification_pausing,@string/tt_appdownloader_notification_prepare,@string/tt_appdownloader_notification_request_btn_no,@string/tt_appdownloader_notification_request_btn_yes,@string/tt_appdownloader_notification_request_message,@string/tt_appdownloader_notification_request_title,@string/tt_appdownloader_notification_waiting_download_complete_handler,@string/tt_appdownloader_resume_in_wifi,@string/tt_appdownloader_tip,@string/tt_appdownloader_wifi_recommended_body,@string/tt_appdownloader_wifi_recommended_title,@string/tt_appdownloader_wifi_required_body,@string/tt_appdownloader_wifi_required_title,@style/AlphaAnimation,@style/BottomAnimation,@style/Bullet.Bottom.Dialog.Animation,@style/Dialog.BottomSheet.Transparent,@style/ECBaseDialogFragmentAnimation,@style/ECBottomInWindowAnimation,@style/ECBottomOutWindowAnimation,@style/ECHalfScreenAnchorV4Anime,@style/ECSlideInWindowAnimation,@style/ECSlideOutWindowAnimation,@style/EC.Widget.Design.BottomSheet.Modal,@style/ExpandAnimation,@style/PopupWindowFadeAnimationStyle,@style/SKUPanelDialogAnimation,@style/SlideAnimation,@style/StoreAppBottomSheetStyle,@style/bottom_sheet_anime,@style/commerce_dialog_dim_non_enter_animation,@style/ec_plugin_progress_dialog,@style/ec_sku_prerender_dialog_anim,@style/tt_appdownloader_style_detail_download_progress_bar,@style/tt_appdownloader_style_notification_text,@style/tt_appdownloader_style_notification_title,@style/tt_appdownloader_style_progress_bar,@style/tt_appdownloader_style_progress_bar_new,@style/ttdownloader_translucent_dialog,@style/ttlive_PopupWindowAnimationStyle,@style/ttlive_bottom_dialog_anim,@style/ttlive_bottom_dialog_anim_fast,@style/ttlive_bottom_dialog_anim_normal,@style/ttlive_comb_dialog_anim,@style/ttlive_dialog_popup_animation,@style/ttlive_right_dialog_anim"
    tools:shrinkMode="safe" />

```

如果接入了部分第三方的资源优化框架（如：AndResProguard），则必须将此文件中的资源添加到白名单，否则将导致崩溃或者广告异常。

```
R.string.anythink_*
R.drawable.anythink_*
R.layout.anythink_*
R.id.anythink_*
R.dimen.anythink_*
R.style.anythink_*
R.color.anythink_*
 
R.string.ksad_*
R.drawable.ksad_*
R.layout.ksad_*
R.id.ksad_*
R.style.ksad_*
R.color.ksad_*
R.attr.ksad_*
R.dimen.ksad_*
 
R.anim.bullet_bottom_dialog_enter
R.anim.bullet_bottom_dialog_exit
R.anim.cj_pay_activity_add_in_animation
R.anim.cj_pay_activity_fade_in_animation
R.anim.cj_pay_activity_fade_out_animation
R.anim.cj_pay_activity_remove_out_animation
R.anim.cj_pay_expo_easeout_interpolator
R.anim.cj_pay_fragment_down_out_animation
R.anim.cj_pay_fragment_up_in_animation
R.anim.cj_pay_quadratic_easein_interpolator
R.anim.cj_pay_slide_in_from_bottom_with_bezier
R.anim.cj_pay_slide_out_to_bottom_with_bezier
R.anim.cj_pay_slide_right_in
R.anim.ec_alpha_in
R.anim.ec_alpha_out
R.anim.ec_base_enter
R.anim.ec_base_exit
R.anim.ec_bottom_in
R.anim.ec_bottom_out
R.anim.ec_commerce_activity_in
R.anim.ec_commerce_activity_out
R.anim.ec_commerce_pre_out
R.anim.ec_pop_bottom_in
R.anim.ec_pop_bottom_out
R.anim.ec_pop_slide_in
R.anim.ec_pop_slide_out
R.anim.ec_slide_in
R.anim.ec_slide_out
R.anim.ec_zoom_in
R.anim.ec_zoom_out
R.anim.shopping_popup_fade_in
R.anim.shopping_popup_fade_out
R.anim.ttlive_alpha_in
R.anim.ttlive_alpha_out
R.anim.ttlive_dialog_popup_enter
R.anim.ttlive_dialog_popup_exit
R.anim.ttlive_popup_enter
R.anim.ttlive_popup_exit
R.anim.ttlive_slide_in_bottom
R.anim.ttlive_slide_in_bottom_fast
R.anim.ttlive_slide_in_bottom_normal
R.anim.ttlive_slide_in_left
R.anim.ttlive_slide_in_right
R.anim.ttlive_slide_in_top
R.anim.ttlive_slide_out_bottom
R.anim.ttlive_slide_out_bottom_fast
R.anim.ttlive_slide_out_bottom_normal
R.anim.ttlive_slide_out_left
R.anim.ttlive_slide_out_right
R.anim.ttlive_slide_out_top
R.color.ec_store_window_background
R.color.tt_appdownloader_notification_material_background_color
R.color.tt_appdownloader_notification_title_color
R.color.tt_appdownloader_s1
R.color.tt_appdownloader_s13
R.color.tt_appdownloader_s18
R.color.tt_appdownloader_s4
R.color.tt_appdownloader_s8
R.color.ttdownloader_transparent
R.drawable.tt_appdownloader_action_bg
R.drawable.tt_appdownloader_action_new_bg
R.drawable.tt_appdownloader_ad_detail_download_progress
R.drawable.tt_appdownloader_detail_download_success_bg
R.drawable.tt_appdownloader_download_progress_bar_horizontal
R.drawable.tt_appdownloader_download_progress_bar_horizontal_new
R.drawable.tt_appdownloader_download_progress_bar_horizontal_night
R.drawable.ttdownloader_bg_appinfo_btn
R.drawable.ttdownloader_bg_appinfo_dialog
R.drawable.ttdownloader_bg_button_blue_corner
R.drawable.ttdownloader_bg_kllk_btn1
R.drawable.ttdownloader_bg_kllk_btn2
R.drawable.ttdownloader_bg_transparent
R.drawable.ttdownloader_bg_white_corner
R.drawable.ttdownloader_dash_line
R.drawable.ttdownloader_icon_back_arrow
R.drawable.ttdownloader_icon_download
R.drawable.ttdownloader_icon_yes
R.id.auto
R.id.cancel_tv
R.id.confirm_tv
R.id.dash_line
R.id.delete_tv
R.id.iv_app_icon
R.id.iv_detail_back
R.id.iv_privacy_back
R.id.line
R.id.ll_download
R.id.message_tv
R.id.permission_list
R.id.privacy_webview
R.id.title_bar
R.id.tt_appdownloader_action
R.id.tt_appdownloader_desc
R.id.tt_appdownloader_download_progress
R.id.tt_appdownloader_download_progress_new
R.id.tt_appdownloader_download_size
R.id.tt_appdownloader_download_status
R.id.tt_appdownloader_download_success
R.id.tt_appdownloader_download_success_size
R.id.tt_appdownloader_download_success_status
R.id.tt_appdownloader_download_text
R.id.tt_appdownloader_icon
R.id.tt_appdownloader_root
R.id.tv_app_detail
R.id.tv_app_developer
R.id.tv_app_name
R.id.tv_app_privacy
R.id.tv_app_version
R.id.tv_empty
R.id.tv_give_up
R.id.tv_permission_description
R.id.tv_permission_title
R.layout.tt_appdownloader_notification_layout
R.layout.ttdownloader_activity_app_detail_info
R.layout.ttdownloader_activity_app_privacy_policy
R.layout.ttdownloader_dialog_appinfo
R.layout.ttdownloader_dialog_select_operation
R.layout.ttdownloader_item_permission
R.string.tt_appdownloader_button_cancel_download
R.string.tt_appdownloader_button_queue_for_wifi
R.string.tt_appdownloader_button_start_now
R.string.tt_appdownloader_download_percent
R.string.tt_appdownloader_download_remaining
R.string.tt_appdownloader_download_unknown_title
R.string.tt_appdownloader_duration_hours
R.string.tt_appdownloader_duration_minutes
R.string.tt_appdownloader_duration_seconds
R.string.tt_appdownloader_jump_unknown_source
R.string.tt_appdownloader_label_cancel
R.string.tt_appdownloader_label_cancel_directly
R.string.tt_appdownloader_label_ok
R.string.tt_appdownloader_label_reserve_wifi
R.string.tt_appdownloader_notification_download
R.string.tt_appdownloader_notification_download_complete_open
R.string.tt_appdownloader_notification_download_complete_with_install
R.string.tt_appdownloader_notification_download_complete_without_install
R.string.tt_appdownloader_notification_download_continue
R.string.tt_appdownloader_notification_download_delete
R.string.tt_appdownloader_notification_download_failed
R.string.tt_appdownloader_notification_download_install
R.string.tt_appdownloader_notification_download_open
R.string.tt_appdownloader_notification_download_pause
R.string.tt_appdownloader_notification_download_restart
R.string.tt_appdownloader_notification_download_resume
R.string.tt_appdownloader_notification_download_space_failed
R.string.tt_appdownloader_notification_download_waiting_net
R.string.tt_appdownloader_notification_download_waiting_wifi
R.string.tt_appdownloader_notification_downloading
R.string.tt_appdownloader_notification_install_finished_open
R.string.tt_appdownloader_notification_insufficient_space_error
R.string.tt_appdownloader_notification_need_wifi_for_size
R.string.tt_appdownloader_notification_no_internet_error
R.string.tt_appdownloader_notification_no_wifi_and_in_net
R.string.tt_appdownloader_notification_paused_in_background
R.string.tt_appdownloader_notification_pausing
R.string.tt_appdownloader_notification_prepare
R.string.tt_appdownloader_notification_request_btn_no
R.string.tt_appdownloader_notification_request_btn_yes
R.string.tt_appdownloader_notification_request_message
R.string.tt_appdownloader_notification_request_title
R.string.tt_appdownloader_notification_waiting_download_complete_handler
R.string.tt_appdownloader_resume_in_wifi
R.string.tt_appdownloader_tip
R.string.tt_appdownloader_wifi_recommended_body
R.string.tt_appdownloader_wifi_recommended_title
R.string.tt_appdownloader_wifi_required_body
R.string.tt_appdownloader_wifi_required_title
R.style.AlphaAnimation
R.style.BottomAnimation
R.style.Bullet.Bottom.Dialog.Animation
R.style.Dialog.BottomSheet.Transparent
R.style.ECBaseDialogFragmentAnimation
R.style.ECBottomInWindowAnimation
R.style.ECBottomOutWindowAnimation
R.style.ECHalfScreenAnchorV4Anime
R.style.ECSlideInWindowAnimation
R.style.ECSlideOutWindowAnimation
R.style.EC.Widget.Design.BottomSheet.Modal
R.style.ExpandAnimation
R.style.PopupWindowFadeAnimationStyle
R.style.SKUPanelDialogAnimation
R.style.SlideAnimation
R.style.StoreAppBottomSheetStyle
R.style.bottom_sheet_anime
R.style.commerce_dialog_dim_non_enter_animation
R.style.ec_plugin_progress_dialog
R.style.ec_sku_prerender_dialog_anim
R.style.tt_appdownloader_style_detail_download_progress_bar
R.style.tt_appdownloader_style_notification_text
R.style.tt_appdownloader_style_notification_title
R.style.tt_appdownloader_style_progress_bar
R.style.tt_appdownloader_style_progress_bar_new
R.style.ttdownloader_translucent_dialog
R.style.ttlive_PopupWindowAnimationStyle
R.style.ttlive_bottom_dialog_anim
R.style.ttlive_bottom_dialog_anim_fast
R.style.ttlive_bottom_dialog_anim_normal
R.style.ttlive_comb_dialog_anim
R.style.ttlive_dialog_popup_animation
R.style.ttlive_right_dialog_anim
```
