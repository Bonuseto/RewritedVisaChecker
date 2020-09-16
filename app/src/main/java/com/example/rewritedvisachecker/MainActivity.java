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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText et_appNum, et_appNumFak;
    private Spinner sp_type;
    private static final String TAG = MainActivity.class.getSimpleName();
    private boolean flagOnlyFirstRequest = true;
    Button resultBtn;
    String[] visa_types = {"DP","DV","PP","ZK","ZM","ST","TP","CD","VP","MK"};
    String[] visa_years = {"2020","2019","2018"};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_appNum = (EditText) findViewById(R.id.applicationNumber);
        et_appNumFak = (EditText) findViewById(R.id.applicationNumberFake);
        final String[] string_type = new String[1];
        final String[] string_year = new String[1];


        class VisaTypesSpinnerClass implements AdapterView.OnItemSelectedListener
        {
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
            {
                string_type[0] =  visa_types[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }
        class YearsSpinnerClass implements AdapterView.OnItemSelectedListener
        {
            public void onItemSelected(AdapterView<?> parent, View v, int position, long id)
            {
                string_year[0] =  visa_years[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        }

        Spinner spin = (Spinner) findViewById(R.id.type);
        ArrayAdapter<String> aa = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, visa_types);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        spin.setOnItemSelectedListener(new VisaTypesSpinnerClass());

        Spinner spin2 = (Spinner) findViewById(R.id.year);
        ArrayAdapter<String> aa2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, visa_years);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin2.setAdapter(aa2);
        spin2.setOnItemSelectedListener(new YearsSpinnerClass());



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
                                final TextView textVisaStatus = findViewById(R.id.txt_result_visa);
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
                                    myWebview.loadUrl("javascript:document.getElementById('edit-ioff-application-code').value = '" + string_type[0] + "';void(0);");
                                }
                            }, 900);

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    myWebview.loadUrl("javascript:document.getElementById('edit-ioff-application-year').value = '" + string_year[0] + "';void(0);");
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