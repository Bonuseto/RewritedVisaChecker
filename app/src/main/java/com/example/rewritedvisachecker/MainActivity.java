package com.example.rewritedvisachecker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText et_appNum, et_appNumFak, et_type, et_year;
    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean flagOnlyFirstRequest = true;
    Button resultBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_appNum = findViewById(R.id.applicationNumber);
        et_appNumFak = findViewById(R.id.applicationNumberFake);
        et_type = findViewById(R.id.type);
        et_year = findViewById(R.id.year);

        resultBtn = findViewById(R.id.resultButton);
        resultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ArrayList<String> visaStatus = new ArrayList<String>(); //bad workaround
                final WebView myWebview = findViewById(R.id.webview);
                class MyJavaScriptInterface {
                    @JavascriptInterface
                    @SuppressWarnings("unused")
                    public void processHTML(final String html) {
                        Log.d(TAG, "processHTML");

                        myWebview.post(new Runnable() {
                            @Override
                            public void run() {
                                visaStatus.add(html); //bad workaround
                                final TextView textVisaStatus = findViewById(R.id.textView);
                                textVisaStatus.setText(visaStatus.get(0)); //bad workaround
                            }
                        });
                    }
                }

                myWebview.getSettings().setJavaScriptEnabled(true);
                myWebview.addJavascriptInterface(new MyJavaScriptInterface(), "HTMLOUT");

                myWebview.setWebViewClient(new WebViewClient() {
                    @Override
                    public void onPageFinished(WebView view, String url) {

                        Log.d(TAG, "onPageFinished");

                        if (flagOnlyFirstRequest) {
                            flagOnlyFirstRequest = false;
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    myWebview.loadUrl("javascript:document.getElementById('edit-ioff-application-number').value = '" + et_appNum.getText().toString() + "';void(0);");
                                }
                            }, 500);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    myWebview.loadUrl("javascript:document.getElementById('edit-ioff-application-number-fake').value = '" + et_appNumFak.getText().toString() + "';void(0);");
                                }
                            }, 700);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    myWebview.loadUrl("javascript:document.getElementById('edit-ioff-application-code').value = '" + et_type.getText().toString() + "';void(0);");
                                }
                            }, 900);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    myWebview.loadUrl("javascript:document.getElementById('edit-ioff-application-year').value = '" + et_year.getText().toString() + "';void(0);");
                                }
                            }, 1200);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    myWebview.loadUrl("javascript:document.getElementById('edit-submit-button').click();");
                                }
                            }, 1400);


                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    myWebview.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementsByClassName('alert alert-info')[0].innerText);");
                                }
                            }, 1800);
                        }

                    }




                });
                myWebview.loadUrl("https://frs.gov.cz/ioff/application-status");
            }
        });
    }
}