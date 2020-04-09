package com.example.rolplay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Objects;

public class InformacionActivity extends AppCompatActivity {

    //Declaración de variables
    private WebView mWebViewInformacion;
    private String linkCondiciones, linkPoliticaDatos, linkApi, link404;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_informacion);

        //TODO: Sustituir links por originales
        //Inicialización de variables
        linkCondiciones = "https://help.instagram.com/581066165581870";
        linkPoliticaDatos = "https://help.instagram.com/519522125107875";
        linkApi = "https://www.instagram.com/developer/";
        link404 = "https://i.stack.imgur.com/WOlr3.png";

        //Activa la barra superior
        Toolbar toolbar = findViewById(R.id.InformacionActivity_toolbar);
        setSupportActionBar(toolbar);

        //Carga la página web deseada
        mWebViewInformacion = findViewById(R.id.InformacionActivity_webview);
        mWebViewInformacion.getSettings().setJavaScriptEnabled(true);
        mWebViewInformacion.setWebViewClient(new WebViewClient());
        if (Objects.equals(getIntent().getStringExtra("link"), "condiciones")) {
            mWebViewInformacion.loadUrl(linkCondiciones);
        } else if (Objects.equals(getIntent().getStringExtra("link"), "politicaDatos")) {
            mWebViewInformacion.loadUrl(linkPoliticaDatos);
        } else if (Objects.equals(getIntent().getStringExtra("link"), "api")) {
            mWebViewInformacion.loadUrl(linkApi);
        } else {
            mWebViewInformacion.loadUrl(link404);
        }


        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
