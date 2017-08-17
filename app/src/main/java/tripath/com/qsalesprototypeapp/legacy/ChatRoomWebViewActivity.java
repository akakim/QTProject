package tripath.com.qsalesprototypeapp.legacy;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
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
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ScrollView;

import tripath.com.qsalesprototypeapp.BaseActivity;
import tripath.com.qsalesprototypeapp.R;
import tripath.com.qsalesprototypeapp.restclient.client.RestApiClient;
import tripath.com.qsalesprototypeapp.wiget.CustomFrameLayout;
import tripath.com.qsalesprototypeapp.wiget.CustomScrollView;
import tripath.com.qsalesprototypeapp.wiget.DilatingDotsProgressBar;
import tripath.com.qsalesprototypeapp.wiget.NestedWebView;

public class ChatRoomWebViewActivity extends BaseActivity implements Handler.Callback{


    final String TAG = getClass().getSimpleName();
    String url="";
    NestedWebView webView;
    WebViewClient webViewClient;
    WebChromeClient webChromeClient;
    DilatingDotsProgressBar dilatingDotsProgressBar;


    WebView.LayoutParams defaultLayoutParams;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_room_web_view);



        webView = (NestedWebView)findViewById( R.id.webView);

        defaultLayoutParams = new WebView.LayoutParams(webView.getLayoutParams());


        dilatingDotsProgressBar = ( DilatingDotsProgressBar ) findViewById( R.id.progress );


        WebSettings webSettings = webView.getSettings();

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
                dilatingDotsProgressBar.show();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG,"onPageFinished");
                dilatingDotsProgressBar.hide();
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                Log.d(TAG,"onLoadResource");
                super.onLoadResource(view, url);
            }

            /**
             * 크롬 클라이언트 버전에 따라서 될수 있고 안될 수 있다.
             * @param view
             * @param url
             */
            @Override
            public void onPageCommitVisible(WebView view, String url) {


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
                super.onScaleChanged(view, oldScale, newScale);
            }
        };
        webChromeClient = new WebChromeClient(){


            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                Log.d(TAG,"onProgressChanged");
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
                return super.onJsAlert(view, url, message, result);
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

            @Override
            public Bitmap getDefaultVideoPoster() {
                Log.d(TAG,"getDefaultVideoPoster");
                return super.getDefaultVideoPoster();
            }

            @Override
            public View getVideoLoadingProgressView() {
                Log.d(TAG,"getVideoLoadingProgressView");
                return super.getVideoLoadingProgressView();
            }

            @Override
            public void getVisitedHistory(ValueCallback<String[]> callback) {
                Log.d(TAG,"getVisitedHistory");
                super.getVisitedHistory(callback);
            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                Log.d(TAG,"onShowFileChooser");
                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }




        };

        webView.setWebViewClient( webViewClient );
        webView.setWebChromeClient( webChromeClient );

        webView.getSettings().setJavaScriptEnabled(true );
        webView.addJavascriptInterface( baseHandler , "hybrid");
        Intent intent = getIntent();

        String key = intent.getStringExtra("key");
        Log.d(TAG,"URL : " + RestApiClient.basicURL + "/AutoGroundCounsel?code=" + key );
        url = RestApiClient.basicURL + "/AutoGroundCounsel?code=" + key;
        webView.loadUrl(RestApiClient.basicURL + "/AutoGroundCounsel?code=" + key );

    }


    @Override
    protected void onResume() {
        super.onResume();
        webView.performClick();
//        Intent intent = getIntent();
//
//        String key = intent.getStringExtra("key");
//        Log.d(TAG,"URL : " + RestApiClient.basicURL + "/AutoGroundCounsel?code=" + key );
//        url = RestApiClient.basicURL + "/AutoGroundCounsel?code=" + key;
//        webView.loadUrl(RestApiClient.basicURL + "/AutoGroundCounsel?code=" + key );



    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
//        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService( INPUT_METHOD_SERVICE );
//        inputMethodManager.viewClicked(webView);
//        webView.loadUrl("javascript:alert('Hello from Android')");


    }

    @Override
    public boolean handleMessage(Message msg) {
        Log.d(TAG,"msg what " + msg.what);
        Log.d(TAG,"msg obj " + msg.obj.toString());
        return false;
    }
}
