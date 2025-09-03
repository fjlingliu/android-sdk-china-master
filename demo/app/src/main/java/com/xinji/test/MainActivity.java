package com.xinji.test;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;


import com.xinji.test.R;
import com.xinji.sdk.base.ads.AdsException;
import com.xinji.sdk.base.ads.OnXinJiAdsListener;
import com.xinji.sdk.base.ads.XJAdType;
import com.xinji.sdk.base.ads.XJRewardItem;
import com.xinji.sdk.base.exception.XJBaseException;
import com.xinji.sdk.base.share.XJOnGetShareLinkListener;
import com.xinji.sdk.base.share.XJOnShareListener;
import com.xinji.sdk.base.share.XJShareInfo;
import com.xinji.sdk.base.share.XJSharePlatform;
import com.xinji.sdk.base.share.XJShareType;
import com.xinji.sdk.callback.AdCallBack;
import com.xinji.sdk.callback.AuthCallBack;
import com.xinji.sdk.callback.ExitCallBack;
import com.xinji.sdk.callback.LoginCallBack;
import com.xinji.sdk.callback.LogoutAccountCallBack;
import com.xinji.sdk.callback.PayCallBack;
import com.xinji.sdk.callback.RegisterCallBack;
import com.xinji.sdk.constant.ScreenType;

import com.xinji.sdk.entity.UserInfo;
import com.xinji.sdk.exception.XJPayException;
import com.xinji.sdk.http.request.BuildOrderRequest;
import com.xinji.sdk.http.request.UserGameRoleRequest;
import com.xinji.sdk.util.XJGame;
import com.xinji.sdk.util.common.TelephoneUtil;
import com.xinji.sdk.util.common.ToastUtil;

import java.util.HashMap;


/**
 * @author yuanchun
 * @content 新纪手游SDK Demo
 * @time 2016/5/6
 */
public class MainActivity extends Activity {

    private Button pay_bt;                                          // 立即支付
    private Button login_bt;                                        // 登录
    private Button auth_bt;                                         // 授权
    private Button collect_bt;                                      // 提交角色信息
    private Button logOut_bt;                                       // 退出登录
    private Button switchBtn;
    private Button ad_bt;                                       // 广告
    private Button share_bt;                                       // 广告
    private TextView textView_channel;                                       // 广告
    private String pid;                                           // PID
    public boolean fullScreen = true;                                    // 当前游戏是否为全屏展示(全屏：不带状态栏)
    public int screentype;                                        // 当前游戏为横屏
    public String gameName;
    public Context mContext;


    private UserInfo userInfo;
    private String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 模拟当前游戏是否为全屏展示
        if (fullScreen) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_main);


        //此位置可以不用读配置文件，可以直接初始化
        pid = "310000";
        fullScreen = true;
        screentype = ScreenType.SCREEN_PORTRAIT;
//        screentype = ScreenType.SCREEN_LAND;

        gameName = "安卓测试";

        initView();

        // 模拟当前游戏是横屏还是竖屏
        if (screentype == ScreenType.SCREEN_LAND) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);// 横屏
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);// 竖屏
        }


        // 注册注销账号时的回调,在用户注销账号前调用都可以
        XJGame.registerLogoutCallBack(new LogoutAccountCallBack() {
            @Override
            public void onSwitch() {
                // 切换账号,切换账号回调前，SDk会自动唤起登录面板，无需再调用SDK逻辑
                login_bt.setEnabled(true);
            }

            @Override
            public void onLogout() {
                // 注销账号,切换账号回调前，SDk会自动唤起登录面板，无需再调用SDK逻辑
                login_bt.setEnabled(true);
            }
        });

        // 授权
        auth_bt.setOnClickListener(new View.OnClickListener() {

            @TargetApi(Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {
                auth();
            }
        });
        // 登录
        login_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                XJGame.login(MainActivity.this, new LoginCallBack() {
                    @Override
                    public void onLoginSuccess(UserInfo mUserInfo) {
                        login_bt.setEnabled(false);
                        System.out.println("登录成功");
                        Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                        userInfo = mUserInfo;
                        System.out.println("mUserInfo.getExtra() = " + mUserInfo.getExtra());
                    }

                    @Override
                    public void onLoginFail() {
                        System.out.println("登录失败");
                    }

                    @Override
                    public void onLoginCancel() {
                        System.out.println("退出登录");
                    }
                });

            }
        });

        switchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (XJGame.isInited()) {
                } else {
                    Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_LONG).show();
                }
            }
        });
        pay_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (XJGame.isLogin() && userInfo != null) {

                    // 创建订单信息
                    BuildOrderRequest request = new BuildOrderRequest();
                    request.setAmount(Long.parseLong("1"));
                    request.setCompanyOrderNo(System.currentTimeMillis() + "");
                    request.setGamersRole("骷髅大怪");
                    request.setProductName("测试订单");
                    request.setServerNum("123");
                    request.setExtra("无");
                    request.setServerName("区服1");

                    request.setGamersRoleId("1108");
                    request.setGamersGrade("1");
                    if (userInfo != null) {
                        request.setUserNo(userInfo.getUserNo());
                        request.setLoginName(userInfo.getLoginName());
                    }

                    /**
                     * 支付接口,需要同时提供支付和登录的回调接口,若用户没用登录,将直接跳转至登录界面 如果不使用回调,传null即可
                     */
                    XJGame.pay(MainActivity.this, request, new PayCallBack() {
                        @Override
                        public void onSuccess() {
                            System.out.println("支付成功");
                        }

                        @Override
                        public void onFailed(XJPayException e) {
                            System.out.println("支付失败:" + e.getMessage());
                        }

                        @Override
                        public void onCancel() {
                            System.out.println("支付取消");
                        }

                    });

                } else {
                    Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_LONG).show();
                }
            }
        });
        // 数据采集
        collect_bt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!XJGame.isLogin() || userInfo == null) {
                    ToastUtil.showToast("您还未登录", mContext);
                    return;
                }

                UserGameRoleRequest request = new UserGameRoleRequest();
                if (userInfo != null) {
                    request.setUserNo(userInfo.getUserNo());
                    request.setLoginName(userInfo.getLoginName());
                }
                request.setGamersGrade("1");
                request.setGamersRole("骷髅大怪");
                request.setPid(pid);
                request.setGameId(pid);
                request.setGamersRoleId("1108");
                request.setServerNum("123");
                request.setServerName("区服1");
                XJGame.submitUserGameRole(MainActivity.this, request);

            }
        });
        logOut_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (XJGame.isLogin()) {
                    XJGame.logoutAccount();
                } else {
                    Toast.makeText(MainActivity.this, "请先登录", Toast.LENGTH_LONG).show();
                }

            }
        });
        //广告
        ad_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XJGame.showAds(MainActivity.this, XJAdType.AD_TYPE_REWARD_VIDEO, "", null);

            }
        });
        //分享
        share_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initShare();
            }
        });


        Intent intent = getIntent();
        if (intent != null) {
            handleOpenURL(intent.getData());
        }

    }

    private void handleOpenURL(Uri uri) {
        if (uri != null) {
            boolean result = XJGame.gbCommonSDKHandleOpenURL(uri.toString());
            if (result) {
                // 该scheme符合抖音打开游戏的统一scheme格式，一般不需要额外处理，如果你需要解析scheme中携带的参数，请按照跟抖音业务侧约定的协议自行解析
            } else {
                // 该scheme不是抖音打开游戏的统一scheme格式，SDK不关心该scheme的数据，请自行处理
            }
        }
    }

    private void auth() {

        // 对应用进行授权,若无授权或者是授权失败,将导致无法使用本支付SDK
        HashMap<String, String> dataMap = new HashMap<String, String>();
        dataMap.put("screentype", screentype + "");
        dataMap.put("fullScreen", fullScreen + "");
        dataMap.put("pid", pid);
        dataMap.put("gameid", pid);
        dataMap.put("gameName", gameName);


        XJGame.authorization(MainActivity.this, dataMap, new AuthCallBack() {
            @Override
            public void onAuthSuccess() {
                ToastUtil.showToast("授权成功", MainActivity.this);
                registerAds();
            }

            @Override
            public void onAuthFailed(String s) {
                ToastUtil.showToast("授权失败", MainActivity.this);
            }


            @Override
            public void onTrackEvents(String eventName, String data) {
                Log.e(TAG, "eventName = " + eventName + ", data = " + data);
            }

            @Override
            public void onLogout() {

            }
        });
    }


    private void initView() {
        auth_bt = (Button) findViewById(R.id.button_auth);
        login_bt = (Button) findViewById(R.id.button_login);
        pay_bt = (Button) findViewById(R.id.button_pay);
        collect_bt = (Button) findViewById(R.id.button_collect);
        logOut_bt = (Button) findViewById(R.id.button_logout);


        switchBtn = findViewById(R.id.button_switch);
        ad_bt = (Button) findViewById(R.id.button_ad);
        share_bt = (Button) findViewById(R.id.button_share);
        textView_channel = (TextView) findViewById(R.id.textView_channel);

    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && XJGame.isInited()) {
            XJGame.exit(this, new ExitCallBack() {
                @Override
                public void onExit() {
                    System.out.println("游戏退出了");
                }
            });
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        XJGame.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        XJGame.onConfigurationChanged(this, newConfig);
    }

    @Override
    protected void onPause() {
        super.onPause();
        XJGame.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        XJGame.onResume(this);
    }

    private void registerAds() {
        XJGame.registerAds(MainActivity.this, XJAdType.AD_TYPE_REWARD_VIDEO, "", new OnXinJiAdsListener() {


            @Override
            public void onLoadSuc(XJRewardItem item) {
                Log.e(TAG, "onLoadSuc item = " + item.toString());
            }

            @Override
            public void onLoadFailed(AdsException e) {
                Log.e("XJ_AD", e.getMessage());
            }

            @Override
            public void onDisplaySuc(XJRewardItem item) {
                Log.d(TAG, "展示广告成功，价格：" + item.amount);
            }

            @Override
            public void onDisplayFailed(AdsException e) {
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onAdReward(XJRewardItem item) {
                Log.e(TAG, "onAdReward item = " + item.toString());

            }

            @Override
            public void onAdClose(XJRewardItem item) {
                Log.e(TAG, "onAdClose item = " + item.toString());
            }
        });
    }

    private void testImageShare(String link) {
        String path = mContext.getFilesDir().getAbsolutePath();
        FileUtil.moveAssets2File("test.png", this);
        XJShareInfo info = new XJShareInfo();
        info.shareType = XJShareType.SHARE_TYPE_IMAGE.getType();
        info.shareUrl = link;
        info.title = "测试发送";
        info.logo = R.mipmap.ic_launcher;
        info.imageUrl = path + "/test.png";
        info.description = "ddddddddddddddddddddddddddddddddddddddd";
        XJGame.onShare(this, getSharePlatform(), info, new XJOnShareListener() {

            @Override
            public void onShareSuc() {

            }

            @Override
            public void onShareError(XJBaseException e) {

            }

            @Override
            public void onShareCancel() {

            }
        });
    }

    private void testLinkShare(String link) {
        XJShareInfo info = new XJShareInfo();
        info.shareType = XJShareType.SHARE_TYPE_LINK.getType();
        info.shareUrl = link;
        info.title = "测试发送";
        info.logo = R.mipmap.ic_launcher;
        info.scene = getShareScene();
        info.description = "dddddddddddddddddddddddddddddddd";
        XJGame.onShare(this, getSharePlatform(), info, new XJOnShareListener() {

            @Override
            public void onShareSuc() {

            }

            @Override
            public void onShareError(XJBaseException e) {

            }

            @Override
            public void onShareCancel() {

            }
        });
    }

    /**
     * 获取分享平台
     *
     * @return
     */
    private XJSharePlatform getSharePlatform() {
        RadioGroup rgPlaform = findViewById(R.id.rg_share_plaform);
        if (rgPlaform.getCheckedRadioButtonId() == R.id.rb_share_wb) {
            return XJSharePlatform.SHARE_PLATFORM_WB;
        }
        return XJSharePlatform.SHARE_PLATFORM_WX;
    }

    private int getShareScene() {
        RadioGroup rgScene = findViewById(R.id.rg_share_scene);
        if (rgScene.getCheckedRadioButtonId() == R.id.rb_share_scene_0) {
            return 0;
        } else if (rgScene.getCheckedRadioButtonId() == R.id.rb_share_scene_1) {
            return 1;
        }
        return 2;
    }

    /**
     * 分享
     */
    private boolean mIsShareImg = true;

    private void initShare() {
        XJGame.getShareLink(new XJOnGetShareLinkListener() {
            @Override
            public void onGetShareUrlSuc(String link) {
                if (mIsShareImg) {
                    testImageShare(link);
                    mIsShareImg = false;
                } else {
                    mIsShareImg = true;
                    testLinkShare(link);
                }
            }

            @Override
            public void onGetShareUrlFail(String msg) {

            }
        });
    }
}
