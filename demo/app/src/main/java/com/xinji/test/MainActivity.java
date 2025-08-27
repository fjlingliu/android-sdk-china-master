package com.xinji.test;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.jlmxj.HUAWEI.R;
import com.xinji.sdk.base.ads.AdsException;
import com.xinji.sdk.base.ads.OnXinJiAdsListener;
import com.xinji.sdk.base.ads.XJAdType;
import com.xinji.sdk.base.ads.XJRewardItem;
import com.xinji.union.UnionGame;
import com.xinji.union.base.callback.IAuthCallback;
import com.xinji.union.base.callback.IExitCallback;
import com.xinji.union.base.callback.ILoginCallback;
import com.xinji.union.base.callback.IPayCallback;
import com.xinji.union.base.callback.ISubmitRoleCallback;
import com.xinji.union.base.data.IXJUserInfo;
import com.xinji.union.base.exception.XJBaseException;
import com.xinji.union.base.req.XJAuthReq;
import com.xinji.union.base.req.XJOrderReq;
import com.xinji.union.base.req.XJRoleReq;

import java.util.Random;



/**
 * @author chenqm
 * @content 浩凡SDK Demo
 */
public class MainActivity extends Activity {
    private Button mBtAuth;                                         // 授权
    private Button mBtLogin;                                        //登录
    private Button mBtSubmit;                                        // 提交角色信息
    private Button mBtLogOut;                                       // 注销登录
    private Button btnShowAd, btnRegisterAd;                                          // 展示广告
    public String pid = "30834";//宝宝烹饪师  oppo

    public Context mContext;


    private IXJUserInfo mUserInfo;// Google支付
    private String gameRoleId;
    private String TAG=getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);

        gameRoleId = new Random().nextInt(1000000) + "";

        initView();


        // 授权
        mBtAuth.setOnClickListener(v -> doAuth());
        //登录
        mBtLogin.setOnClickListener(v -> doLogin());
        // 创建订单信息
        findViewById(R.id.bt_pay).setOnClickListener(v -> startPay());

        // 提交角色信息
        mBtSubmit.setOnClickListener(v -> startSubmitRole());
        //注销登录
        mBtLogOut.setOnClickListener(v -> {
            UnionGame.getInstance().logoutAccount(MainActivity.this);
            mBtLogin.setEnabled(true);
        });
        btnRegisterAd.setOnClickListener(v->{
            startRegisterAd();
        });
        btnShowAd.setOnClickListener(v->{
            startShowAd();
        });

    }


    private void startShowAd() {
        UnionGame.getInstance().showAds(this, XJAdType.AD_TYPE_REWARD_VIDEO,"",null);
    }
    private void startRegisterAd(){
        UnionGame.getInstance().registerAds(this, XJAdType.AD_TYPE_REWARD_VIDEO, "", new OnXinJiAdsListener() {
            @Override
            public void onLoadSuc(XJRewardItem xjRewardItem) {
                Log.d(TAG,"加载广告成功："+xjRewardItem.placementId);
            }

            @Override
            public void onLoadFailed(AdsException e) {
                Log.d(TAG,"加载广告失败："+e.getMessage());
            }

            @Override
            public void onDisplaySuc(XJRewardItem xjRewardItem) {
                Log.d(TAG,"展示广告成功："+xjRewardItem.placementId);
            }

            @Override
            public void onDisplayFailed(AdsException e) {
                Log.d(TAG,"展示广告失败："+e.getMessage());
            }

            @Override
            public void onAdReward(XJRewardItem xjRewardItem) {
                Log.d(TAG,"广告收益："+xjRewardItem.ecpm);
            }

            @Override
            public void onAdClose(XJRewardItem xjRewardItem) {

                Log.d(TAG,"广告关闭："+xjRewardItem.placementId);
            }
        });
    }

    private void initView() {
        mBtAuth = findViewById(R.id.bt_auth);
        mBtLogin = findViewById(R.id.bt_login);
        mBtSubmit = findViewById(R.id.bt_submit);
        mBtLogOut = findViewById(R.id.bt_logout);
        btnShowAd = findViewById(R.id.bt_show_ad);
        btnRegisterAd = findViewById(R.id.bt_register_ad);
    }

    private void startSubmitRole() {
        XJRoleReq request = new XJRoleReq();
        request.setUserNo(mUserInfo.getXJUserNo());
        request.setLoginName(mUserInfo.getXJLoginName());
        request.setGameGrade("1");
        request.setRoleName("超神");
        request.setGamersRoleId(gameRoleId);
        request.setServerNum("1001");
        request.setServerName("妙蛙種子");
        request.setType(1);//1、创建角色接口2、角色进入游戏接口3、角色升级接口4、角色改名接口
        UnionGame.getInstance().submitUserGameRole(MainActivity.this, request, new ISubmitRoleCallback() {
            @Override
            public void onSubmitSuccess() {
                Log.e("onSubmitSuccess", "提交成功");
            }

            @Override
            public void onSubmitFailed(XJBaseException e) {
                Log.e("onSubmitFailed", "提交失败");
            }
        });
    }

    private void startPay() {

        XJOrderReq request = new XJOrderReq();
        request.setUserNo(mUserInfo.getXJUserNo());
        request.setLoginName(mUserInfo.getXJLoginName());

        request.setAmount(1);
        request.setCompanyOrderNo("" + System.currentTimeMillis());
        request.setGamersRoleId(gameRoleId);
        request.setGamersRole("超神");
        request.setProductName("测试订单");
        request.setProductDes("商品描述");
        request.setServerName("区服名称");
        request.setServerNum("区服ID");
        request.setGrade("1");
        request.setProductId("com.hkck.llgame_bbprs_1");
        request.setExtra("备注");
        UnionGame.getInstance().pay(MainActivity.this, request, new IPayCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(mContext, "支付成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailed(XJBaseException e) {
                Toast.makeText(mContext, "支付失敗："+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(mContext, "支付取消", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void doAuth() {
        // 对应用进行授权,若无授权或者是授权失败,将导致无法使用本支付SDK
        XJAuthReq request = new XJAuthReq();
        request.setPid(pid);
        UnionGame.getInstance().authorization(MainActivity.this, request, new IAuthCallback() {
            @Override
            public void onLogout() {
                Toast.makeText(mContext, "退出登录成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onBindThirdPart(IXJUserInfo userInfo) {
            }

            @Override
            public void onCancellationAccount() {

            }

            @Override
            public void onInitFail(XJBaseException e) {
                Toast.makeText(mContext, e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onInitSuc() {
                Toast.makeText(mContext, "初始化成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTrackEvents(String eventName, String data) {
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UnionGame.getInstance().onActivityResult(requestCode, resultCode, data);
    }

    private void doLogin() {
        UnionGame.getInstance().login(MainActivity.this, new ILoginCallback() {
            @Override
            public void onLoginModeSuccess(IXJUserInfo userInfo) {
                mUserInfo = userInfo;
                Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoginModeFail(XJBaseException e) {
                Toast.makeText(MainActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoginCancel() {
                Toast.makeText(MainActivity.this, "取消登录", Toast.LENGTH_SHORT).show();
            }
        });
    }




    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

    }

    @Override
    protected void onResume() {
        super.onResume();
        UnionGame.getInstance().onActivityResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        UnionGame.getInstance().onActivityPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UnionGame.getInstance().onActivityDestroy(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            UnionGame.getInstance().showExitTipDialog(this, new IExitCallback() {
                @Override
                public void onExit() {
                    finish();
                }
            });
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_HOME) {
            return true;

        }
        return super.onKeyDown(keyCode, event);
    }


}
