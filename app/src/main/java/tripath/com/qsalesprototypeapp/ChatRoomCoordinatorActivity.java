package tripath.com.qsalesprototypeapp;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ClientCertRequest;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.HttpAuthHandler;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import com.android.volley.NoConnectionError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tripath.com.qsalesprototypeapp.manager.JavascriptManager;
import tripath.com.qsalesprototypeapp.manager.SharedManager;
import tripath.com.qsalesprototypeapp.restclient.client.RestApiClient;
import tripath.com.qsalesprototypeapp.restclient.entity.AuthCode;
import tripath.com.qsalesprototypeapp.wiget.ContentLayoutBehavior;
import tripath.com.qsalesprototypeapp.wiget.DilatingDotsProgressBar;
import tripath.com.qsalesprototypeapp.wiget.DlgDone;
import tripath.com.qsalesprototypeapp.wiget.DlgDoneCancel;
import tripath.com.qsalesprototypeapp.wiget.NestedWebView;


public class ChatRoomCoordinatorActivity extends BaseActivity   {

    final String TAG = getClass().getSimpleName();
    String url="";
    WebView webView;
    WebViewClient webViewClient;
    WebChromeClient webChromeClient;
    CoordinatorLayout mainLayout;


    Handler handler =null;
    WebView.LayoutParams defaultLayoutParams;

    boolean isOnStop = false;
    boolean isOnPageStarted  = false;
    boolean isOnLoadResource  = false;
    boolean isOnPageFinished  = false;

    final int BACK_CODE = 1000;
    final int CANCEL_CODE = 1001;
    private BroadcastReceiver mRegistrationBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_chat_room_coordinator);
        backPressActionMode = 2;
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch ( msg.what ){
                    case BACK_CODE :
                        doBack = true;
                        break;
                    case CANCEL_CODE :
                        doBack = false;
                        break;
                    case StaticValues.JAVA_SCRIPT_BACK_CALLBACK:
                        doBack = true;
                        onBackPressed();
                        break;
                }
            }
        };

//        layoutContainer = (CustomFrameLayout) findViewById( R.id.layoutContainer);
        mainLayout = ( CoordinatorLayout )findViewById( R.id.mainLayout);



//        CoordinatorLayout.LayoutParams layoutParams = new CoordinatorLayout.LayoutParams(
//                CoordinatorLayout.LayoutParams.MATCH_PARENT,
//                CoordinatorLayout.LayoutParams.MATCH_PARENT
//        );
//        layoutParams.setBehavior(new tripath.com.qsalesprototypeapp.wiget.ContentLayoutBehavior ());

        dilatingDotsProgressBar = ( DilatingDotsProgressBar ) findViewById( R.id.progress );


        webView = (WebView)findViewById( R.id.webView);
        initWebView();


        mRegistrationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();

                Log.d(TAG,"getAction");
                if(action.equals(StaticValues.REFRESH_CHATROOMS)){
                    showNotiDialog("[알림]", SharedManager.getString(ChatRoomCoordinatorActivity.this,StaticValues.MESSAGE));
                }
            }
        };
    }





    @Override
    protected void onStop() {
        isOnStop = true;

//        if(dilatingDotsProgressBar.isShown()){
//            dilatingDotsProgressBar.hide();
//        }

        if(isLoading())
            removeLoading();
        webView.clearCache(true);

        super.onStop();
        Log.d(TAG,"onStop");

    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver( mRegistrationBroadcastReceiver );
        Log.d(TAG,"onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"onDestroy");
        if(webView != null) {
            webView.destroy();
            webView = null;
        }
    }

    @Override
    public void onBackPressed() {


        Log.d( "onBackPressed", "isOnPageFinished" + isOnPageFinished );

        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        isOnStop = false;

        LocalBroadcastManager.getInstance(this).registerReceiver(mRegistrationBroadcastReceiver,new IntentFilter(StaticValues.REFRESH_CHATROOMS));
    }

    public void initWebView(){

        WebSettings webSettings = webView.getSettings();

        String userAgentStr = webSettings.getUserAgentString();

        webSettings.setUserAgentString(userAgentStr + " HELLO WEBVIEW");
        webSettings.setJavaScriptEnabled( true );
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);

        // Enable pinch to zoom without the zoom buttons
        webSettings.setBuiltInZoomControls(true);

        // Allow use of Local Storage
        webSettings.setDomStorageEnabled(true);


        // 키보드이슈때문에 넣은 것
        webSettings.setLoadWithOverviewMode(false);
        webSettings.setUseWideViewPort(false);
        webSettings.setSupportZoom(false);
        webSettings.setBuiltInZoomControls (false);


        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            webSettings.setDisplayZoomControls(false);
        }

        // Enable remote debugging via chrome://inspect
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        webViewClient = new WebViewClient(){


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d(TAG,"onPageStarted");
                customDotsProgressDialog.show();
//                dilatingDotsProgressBar.show();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG,"onPageFinished");
//                dilatingDotsProgressBar.hide();
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
            }

            /**
             * 크롬 클라이언트 버전에 따라서 될수 있고 안될 수 있다.
             * @param view
             * @param url
             */
            @Override
            public void onPageCommitVisible(WebView view, String url) {

                Log.d(TAG,"onPageCommitVisible");
                super.onPageCommitVisible(view, url);
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                Log.d(TAG,"shouldOverrideKeyEvent");
                return super.shouldOverrideKeyEvent(view, event);
            }

            @Override
            public void onFormResubmission(WebView view, Message dontResend, Message resend) {
                Log.d(TAG,"onFormResubmission");
                super.onFormResubmission(view, dontResend, resend);
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                Log.d(TAG,"doUpdateVisitedHistory");
                super.doUpdateVisitedHistory(view, url, isReload);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.d(TAG,"onReceivedSslError");
                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
                Log.d(TAG,"onReceivedClientCertRequest");
                super.onReceivedClientCertRequest(view, request);
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }

            @Override
            public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
                Log.d(TAG,"onUnhandledKeyEvent");
                super.onUnhandledKeyEvent(view, event);
            }

            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                Log.e(TAG,":onScaleChanged");
                super.onScaleChanged(view, oldScale, newScale);
            }


            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Log.e(TAG,"onReceivedError");
                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                Log.e(TAG,"onReceivedHttpError");
                Log.e("onReceivedHttpError","response Code : " + errorResponse.getStatusCode());
                super.onReceivedHttpError(view, request, errorResponse);
            }
        };


        webChromeClient = new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.d(TAG,"onProgressChanged");
                Log.d(TAG,"newProgress : "+ newProgress);

                if( newProgress >= 72){
                   removeLoading();
                }
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                Log.d(TAG,"onReceivedTitle");
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                Log.d(TAG,"onReceivedIcon");
                super.onReceivedIcon(view, icon);
            }

            @Override
            public void onReceivedTouchIconUrl(WebView view, String url, boolean precomposed) {
                Log.d(TAG,"onReceivedTouchIconUrl");
                super.onReceivedTouchIconUrl(view, url, precomposed);
            }

            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                Log.d(TAG,"onShowCustomView");
                super.onShowCustomView(view, callback);
            }

            @Override
            public void onHideCustomView() {

                Log.d(TAG,"onHideCustomView");
                super.onHideCustomView();
            }

            @Override
            public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
                Log.d(TAG,"onCreateWindow");
                return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
            }

            @Override
            public void onRequestFocus(WebView view) {
                Log.d(TAG,"onRequestFocus");
                super.onRequestFocus(view);
            }

            @Override
            public void onCloseWindow(WebView window) {
                Log.d(TAG,"onCloseWindow");
                super.onCloseWindow(window);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.d(TAG,"onJsAlert");
                Log.d(TAG,"url : " + url );

//
//                if( isOnStop ){
//                    result.confirm();
//
//                }else {
//                    if (view.getProgress() < 100){
//                        result.confirm();
//                    }else {
//                        return super.onJsAlert(view, url, message, result);
//                    }
//                }
//
//                return true;

                if( isOnStop ){
                    result.confirm();
                    return true;
                }else {

                    result.confirm();
                    new AlertDialog.Builder(ChatRoomCoordinatorActivity.this)
                            .setTitle("Alert title")
                            .setMessage(message)
                            .setPositiveButton(android.R.string.ok,
                                    null )
                            .setCancelable(false)
                            .create()
                            .show();
                    return true;
                }
//                if(view.getProgress() < 100) {
//                    if (isOnStop) {
//                        Log.d(TAG, "onJsAlert isOnStop + " + isOnStop);
//                        return true;
//                    } else {
//                        Log.d(TAG, "onJsAlert isOnStop - " + isOnStop);
//                        return super.onJsAlert(view, url, message, result);
//                    }
//                }
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                Log.d(TAG,"onJsConfirm");
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Log.d(TAG,"onJsPrompt");
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
                Log.d(TAG,"onJsBeforeUnload");
                return super.onJsBeforeUnload(view, url, message, result);
            }


            @Override
            public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
                Log.d(TAG,"onGeolocationPermissionsShowPrompt");
                super.onGeolocationPermissionsShowPrompt(origin, callback);
            }

            @Override
            public void onGeolocationPermissionsHidePrompt() {
                Log.d(TAG,"onGeolocationPermissionsHidePrompt");
                super.onGeolocationPermissionsHidePrompt();
            }

            @Override
            public void onPermissionRequest(PermissionRequest request) {
                Log.d(TAG,"onPermissionRequest");
                super.onPermissionRequest(request);
            }

            @Override
            public void onPermissionRequestCanceled(PermissionRequest request) {
                Log.d(TAG,"onPermissionRequestCanceled");
                super.onPermissionRequestCanceled(request);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                return super.onConsoleMessage(consoleMessage);
            }

            /**
             * input TAG
             * @param webView
             * @param filePathCallback
             * @param fileChooserParams
             * @return
             */
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                Log.d(TAG,"onShowFileChooser");
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }



        };

        webView.setWebViewClient( webViewClient );
        webView.setWebChromeClient( webChromeClient );

        webView.getSettings().setJavaScriptEnabled(true );
        webView.addJavascriptInterface( new JavascriptManager( handler ), "hybridApp");
        Intent intent = getIntent();

        String key = intent.getStringExtra("key");
        url = RestApiClient.basicURL + "/AutoGroundCounsel?code=" + key;
        Log.d(TAG,"url : " + url);
        Map<String,String> addtionalHeader= new HashMap<String,String>();

        addtionalHeader.put("key","value");
        webView.loadUrl(RestApiClient.basicURL + "/AutoGroundCounsel?code=" + key , addtionalHeader);
    }


    @Override
    public void onDone(DlgDone dlg) {
        dlg.dismiss();
        Intent intent = new Intent(this,ChatRoomCoordinatorActivity.class);
        intent.putExtra("key",SharedManager.getString(this,StaticValues.CHATROOM_AUTH_CODE));
        startActivity(intent);
    }

    @Override
    public void onDismiss(DlgDone dlg) {
        super.onDismiss(dlg);
    }


    @Override
    public void onDone(DlgDoneCancel dlg) {
        dlg.dismiss();
        Intent intent = new Intent(this,ChatRoomCoordinatorActivity.class);

        intent.putExtra("key",SharedManager.getString(this,StaticValues.CHATROOM_AUTH_CODE));
        intent.addFlags( Intent.FLAG_ACTIVITY_NEW_TASK );
        startActivity(intent);
        finish();
    }

    @Override
    public void onCancel(DlgDoneCancel dlg) {
        super.onCancel(dlg);
    }

    @Override
    public void onDismiss(DlgDoneCancel dlg) {
        super.onDismiss(dlg);
    }
}
