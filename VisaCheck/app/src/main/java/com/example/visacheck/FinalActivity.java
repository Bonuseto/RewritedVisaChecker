package com.example.visacheck;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class FinalActivity extends AppCompatActivity {

    String applicationNumber;
    String applicationNumberFake;
    String type;
    String year;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        this.getSupportActionBar().hide();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final);


        Intent intent = getIntent();
        applicationNumber =  intent.getStringExtra("applicationNumber");
        applicationNumberFake =  intent.getStringExtra("applicationNumberFake");
        type =  intent.getStringExtra("type");
        year =  intent.getStringExtra("year");

        final ArrayList<String> visaStatus = new ArrayList<String>(); //bad workaround
        final WebView myWebview = findViewById(R.id.webview);
        class MyJavaScriptInterface {
            @JavascriptInterface
            @SuppressWarnings("unused")
            public void processHTML(final String html) {
                myWebview.post(new Runnable() {
                    @Override public void run() {
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

                myWebview.loadUrl("javascript:document.getElementById('edit-ioff-application-number').value = '" + applicationNumber + "';void(0);");
                myWebview.loadUrl("javascript:document.getElementById('edit-ioff-application-number-fake').value = '" + applicationNumberFake + "';void(0);");
                myWebview.loadUrl("javascript:document.getElementById('edit-ioff-application-code').value = '" + type + "';void(0);");
                myWebview.loadUrl("javascript:document.getElementById('edit-ioff-application-year').value = '" + year + "';void(0);");
                myWebview.loadUrl("javascript:document.getElementById('edit-submit-button').click();");
                myWebview.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementsByClassName('alert alert-warning')[0].innerText);");
                myWebview.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementsByClassName('alert alert-success')[0].innerText);");
                myWebview.loadUrl("javascript:window.HTMLOUT.processHTML(document.getElementsByClassName('alert alert-danger')[0].innerText);");

            }
        });
        myWebview.loadUrl("https://frs.gov.cz/ioff/application-status");
    }

}