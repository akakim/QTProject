package tripath.com.qsalesprototypeapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.icu.util.Output;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.webkit.ClientCertRequest;
import android.webkit.ConsoleMessage;
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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

//import org.apache.http.entity.mime.MultipartEntityBuilder;
//import org.apache.http.entity.mime.content.ContentBody;

import org.apache.http.entity.mime.MultipartEntityBuilder;

import java.io.File;
import java.net.HttpURLConnection;

public class QSampleActivity extends BaseActivity implements TextView.OnEditorActionListener{

    String basicURL = "https://banobagi.qsales.co.kr/pt/nfdemo.html";

    WebView webView;
    WebViewClient webViewClient;
    WebChromeClient webChromeClient;
    ValueCallback valueCallback;
    WebChromeClient.FileChooserParams fileChooserParams;


    private ValueCallback<Uri[]> filePathCallbackLollipop= null;
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what ){
                case StaticValues.HANDLER_MESSAGE:
                    Log.d("Handler :", msg.obj.toString() ) ;
                    Toast.makeText(getApplicationContext(),"javascriptInterface called",Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qsample);

        EditText editUrl = (EditText)findViewById(R.id.editUrl);
        editUrl.setOnEditorActionListener( this );
        editUrl.setVisibility(View.GONE);
//        editUrl.setText(basicURL);

        webView = ( WebView ) findViewById( R.id.webView );

        webViewClient = new WebViewClient(){
            final String TAG  = "WebViewClient";

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
//                Log.d(TAG,"shouldOverrideUrlLoading ");
//                Log.d(TAG,request.toString());

                return super.shouldOverrideUrlLoading(view, request);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                Log.d(TAG,"onPageStarted ");
                Log.d(TAG,url);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG,"onPageFinished ");
                Log.d(TAG,url);
                super.onPageFinished(view, url);
            }

            @Override
            public void onLoadResource(WebView view, String url) {
                Log.d(TAG,"onLoadResource ");
                Log.d(TAG,url);
                super.onLoadResource(view, url);
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                Log.d(TAG,"onReceivedError ");
                Log.d(TAG,"url : " +  url);
                super.onPageCommitVisible(view, url);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Log.d(TAG,"onReceivedError ");
                Log.d(TAG,"request URI : " + request.getUrl().toString());
                Log.d(TAG,"error Message" +error.toString());

                super.onReceivedError(view, request, error);
            }

            @Override
            public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
                Log.d(TAG,"onReceivedHttpError ");
                Log.d(TAG,errorResponse.toString());
                super.onReceivedHttpError(view, request, errorResponse);


            }

            @Override
            public void onFormResubmission(WebView view, Message dontResend, Message resend) {
                Log.d(TAG,"onReceivedError ");
                Log.d(TAG,"dontResend what: " +  dontResend.what);
                Log.d(TAG,"dontResend what: " +  dontResend.toString());

                super.onFormResubmission(view, dontResend, resend);
            }

            @Override
            public void doUpdateVisitedHistory(WebView view, String url, boolean isReload) {
                Log.d(TAG,"doUpdateVisitedHistory ");
                Log.d(TAG,"url : " +  url);
                Log.d(TAG,"isReload : " +  isReload);
                super.doUpdateVisitedHistory(view, url, isReload);
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                Log.d(TAG,"onReceivedSslError ");
                Log.d(TAG,error.toString());
                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
                Log.d(TAG,"onReceivedClientCertRequest ");
                Log.d(TAG," request : " +  request.toString());
                super.onReceivedClientCertRequest(view, request);
            }

            @Override
            public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
                Log.d(TAG,"onReceivedHttpAuthRequest ");
                Log.d(TAG,"host "+ host);
                Log.d(TAG,"realm "+ realm);

                super.onReceivedHttpAuthRequest(view, handler, host, realm);
            }

            @Override
            public boolean shouldOverrideKeyEvent(WebView view, KeyEvent event) {
                Log.d(TAG,"shouldOverrideKeyEvent ");
                Log.d(TAG,"event"+ event.getAction());
                Log.d(TAG,"event"+ event.toString());


                return super.shouldOverrideKeyEvent(view, event);
            }

            @Override
            public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
                Log.d(TAG,"onUnhandledKeyEvent ");
                Log.d(TAG,"event"+ event.getAction());
                Log.d(TAG,"event"+ event.toString());
                super.onUnhandledKeyEvent(view, event);
            }

            @Override
            public void onScaleChanged(WebView view, float oldScale, float newScale) {
                Log.d(TAG,"onScaleChanged ");
                Log.d(TAG,"oldScale"+ oldScale);
                Log.d(TAG,"newScale"+ newScale);

                super.onScaleChanged(view, oldScale, newScale);
            }

            @Override
            public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
                Log.d(TAG,"onReceivedLoginRequest ");
                Log.d(TAG,"realm"+ realm);
                Log.d(TAG,"account"+ account);
                Log.d(TAG,"args"+ args);

                super.onReceivedLoginRequest(view, realm, account, args);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {

                Log.d(TAG,"shouldInterceptRequest ");
                Log.d(TAG,request.toString());


                return super.shouldInterceptRequest(view, request);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG,"shouldOverrideUrlLoading");
                Log.d(TAG,"url : " + url );

                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                Log.d(TAG,"shouldInterceptRequest");
                Log.d(TAG,"url : " + url );

                return super.shouldInterceptRequest(view, url);
            }

            @Override
            public void onTooManyRedirects(WebView view, Message cancelMsg, Message continueMsg) {
                Log.d(TAG,"onTooManyRedirects");
                Log.d(TAG,"continueMsg : " + continueMsg.toString() );

                super.onTooManyRedirects(view, cancelMsg, continueMsg);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                Log.d(TAG,"onReceivedError");
                Log.d(TAG,"url : " + failingUrl );
                super.onReceivedError(view, errorCode, description, failingUrl);
            }


        };
        webChromeClient = new WebChromeClient() {
            String TAG = "webChomeClient";
            @Override
            public void onPermissionRequest(PermissionRequest request) {
                Log.d(TAG,"onPermissionRequest");
                Log.d(TAG,request.toString());
                super.onPermissionRequest(request);
            }

            @Override
            public void onPermissionRequestCanceled(PermissionRequest request) {
                Log.d(TAG,"onPermissionRequestCanceled");
                Log.d(TAG,request.toString());
                super.onPermissionRequestCanceled(request);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                Log.d(TAG,"onJsAlert");
                Log.d(TAG,message.toString());
                Log.d(TAG,result.toString());

                return super.onJsAlert(view, url, message, result);
            }

            @Override
            public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
                Log.d(TAG,"onJsConfirm");
                Log.d(TAG,message.toString());
                Log.d(TAG,result.toString());
                return super.onJsConfirm(view, url, message, result);
            }

            @Override
            public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
                Log.d(TAG,"onJsPrompt");
                Log.d(TAG,message.toString());
                Log.d(TAG,result.toString());
                return super.onJsPrompt(view, url, message, defaultValue, result);
            }

            @Override
            public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
                Log.d(TAG,"onConsoleMessage");
                Log.d(TAG,consoleMessage.message());
                return super.onConsoleMessage(consoleMessage);
            }

            /**
             * For Android 5.0 + 롤리팝이상에서 http://acorn-world.tistory.com/62
             * Tell the client to show a file chooser.
             * This is called to handle HTML forms with 'file' input type,
             * in response to the user pressing the "Select File" button.
             * To cancel the request, call filePathCallback.onReceiveValue(null) and return true.
             * @return
             */
            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
                Log.d(TAG,"onShowFileChooser");
                Log.d(TAG,"filename Hint : " + fileChooserParams.getFilenameHint());
                Log.d(TAG,"fileChooserParams.get Mode " + fileChooserParams.getMode());
                Log.d(TAG,"get Title "+ fileChooserParams.getTitle());

                for(String acceptType : fileChooserParams.getAcceptTypes()){
                    Log.d("acceptType",acceptType);
                }

                if(valueCallback != null) {
                    valueCallback.onReceiveValue(null);
                }else{
                    Log.e(TAG,"filePathCallback is NULL ");
                }
                valueCallback = filePathCallback;
                valueCallback.onReceiveValue(new Uri[]{Uri.parse("")} );
                Intent galleryIntent = fileChooserParams.createIntent();

                Intent chooser = new Intent(Intent.ACTION_CHOOSER);
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);


                Intent [] intents = { cameraIntent };


                chooser.putExtra(Intent.EXTRA_INTENT, galleryIntent);
                chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, intents);
                startActivityForResult( chooser , StaticValues.JAVA_SCRIPT_CALLBACK);

                return super.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }


            @Override
            public Bitmap getDefaultVideoPoster() {

                return null;

            }


        };
        setUpWebViewDefaults ( webView );

        webView.loadUrl("http://172.19.132.194:8080/TestController");

    }

    @Override
    public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
        switch (textView.getId()){
            case R.id.editUrl:
                if(actionId == EditorInfo.IME_NULL
                        && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    webView.loadUrl(textView.getText().toString());
                    return true;
                }
                break;
        }
        return false;
    }

    private void setUpWebViewDefaults(WebView webView) {
        WebSettings settings = webView.getSettings();

        // Enable Javascript
        settings.setJavaScriptEnabled(true);

        // Use WideViewport and Zoom out if there is no viewport defined
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);

        // Enable pinch to zoom without the zoom buttons
        settings.setBuiltInZoomControls(true);

        // Allow use of Local Storage
        settings.setDomStorageEnabled(true);


        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.HONEYCOMB) {
            // Hide the zoom controls for HONEYCOMB+
            settings.setDisplayZoomControls(false);
        }

        // Enable remote debugging via chrome://inspect
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(true);
        }

        webView.setWebViewClient( webViewClient );
        webView.setWebChromeClient( webChromeClient );

    }

    @Override
    public void onBackPressed() {
        if(webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Log.d("onActivityResult",""+ resultCode);
        switch (requestCode){
            case StaticValues.JAVA_SCRIPT_CALLBACK:

                if(valueCallback == null){
                    Log.e(getClass().getSimpleName()," valueCallback is null");
                }else {

                    if(data == null){
                        Toast.makeText(this,"파일을 선택해주세요",Toast.LENGTH_SHORT).show();
                        break;
                    }else {
//                        MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
//
//
                        Uri uriData =  data.getData();
                        File file = new File (uriData.getPath());
//
//                        multipartEntityBuilder.addBinaryBody(file.getName(),file);

                        Uri[] results = new Uri[]{Uri.parse(file.getPath())};

                        valueCallback.onReceiveValue( results );

                    }

                    if (valueCallback != null) valueCallback.onReceiveValue(null);
                    valueCallback = null;


//                    MultiPartEntity
//                    if(data.getData() == null){
//                        Toast.makeText(this,"파일을 선택해주세요",Toast.LENGTH_SHORT).show();
//                        break;
//                    }
//                    Log.d("Uri Value", data.getData().toString());
//                    valueCallback.onReceiveValue( WebChromeClient.FileChooserParams.parseResult(resultCode,data ));
                }


            break;
        }
//        valueCallback.onReceiveValue(data.getData());
        super.onActivityResult(requestCode, resultCode, data);
    }
}
