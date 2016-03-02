package com.example.carlos.saisbrurystest;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.carlos.saisbrurystest.model.ItemDetails;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainPresenter implements IMainPresenter {

    private IMainView mView;
    private ArrayList<ItemDetails> data;
    private ArrayList<String> items;
    private String URL = "http://www.sainsburys.co.uk/webapp/wcs/stores/servlet/CategoryDisplay?listView=tr" +
            "ue&orderBy=FAVOURITES_FIRST&parent_category_rn=12518&top_category=125" +
            "18&langId=44&beginIndex=0&pageSize=20&catalogId=10137&searchTerm=&categ" +
            "oryId=185749&listId=&storeId=10151&promotionId=#langId=44&storeId=10151&cat" +
            "alogId=10137&categoryId=185749&parent_category_rn=12518&top_category=1251" +
            "8&pageSize=20&orderBy=FAVOURITES_FIRST&searchTerm=&beginIndex=0&hide" +
            "Filters=true";
    private String mHtml;

    private Context context;

    public MainPresenter(IMainView mainView, Context pContext) {
        this.mView = mainView;
        data = new ArrayList<ItemDetails>(14);
        items = new ArrayList<String>(14);
        context = pContext;
    }

    @Override
    public ArrayList<ItemDetails> getData() {

        /* Using the firefox right click menu option View Selection Source, I saw this code:

        <h3>
	         <a onclick="s_objectID=&quot;http://www.sainsburys.co.uk/shop/gb/groceries/ripe---ready/sainsburys-avocado-xl-pinkerton-loose-_1&quot;;return this.s_oc?this.s_oc(e):true" href="http://www.sainsburys.co.uk/shop/gb/groceries/ripe---ready/sainsburys-avocado-xl-pinkerton-loose-300g">
	            Sainsbury's Avocado Ripe &amp; Ready XL Loose 300g
	            <img src="http://c2.sainsburys.co.uk/wcsstore7.11.2.26/ExtendedSitesCatalogAssetStore/images/catalog/productImages/51/0000000202251/0000000202251_M.jpeg" alt="">
	         </a>
	    </h3>

        So my idea was to load the HTML code from the url and then save it in a String variable to retrieve the name, image and price.
        I wanted to used the retrieveData method to obtain the String from the HTML
        I wanted to used the retrieveItems method to obtain each item from the String

        But I retrieve encrypted code*/
        //retrieveData();
        //retrieveDataWebView();

        data = new ArrayList<ItemDetails>(6);
        data.add(new ItemDetails("Ite 1", "£5.50"));
        data.add(new ItemDetails("Ite 2", "£0.50"));
        data.add(new ItemDetails("Ite 3", "£1.20"));
        data.add(new ItemDetails("Ite 4", "£3.20"));
        data.add(new ItemDetails("Ite 5", "£5.00"));
        data.add(new ItemDetails("Ite 6", "£1.50"));
        return data;
    }

    public String getTotalItems() {
        return String.valueOf(data.size());
    }

    public ArrayList<String> retrieveItems(String page) {

        ArrayList<String> items = new ArrayList<>(11);
        for (int i = 0; i < page.length() - 10; i++) {
            if (page.substring(i, i + 10).equals("<a onclick")) {
                String aux = page.substring(i, page.length());
                int exit = aux.indexOf("</a>");

                String item = aux.substring(0, exit + 4);
                items.add(item);
            }
        }
        return items;
    }

    public void retrieveData() {

        mHtml = "";
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    HttpClient client = new DefaultHttpClient();
                    HttpGet request = new HttpGet(URL);
                    HttpResponse response = null;
                    String html = "";
                    response = client.execute(request);

                    InputStream in = response.getEntity().getContent();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder str = new StringBuilder();
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        str.append(line);
                    }
                    in.close();
                    html = str.toString();
                    mHtml = html;

                    if (mHtml.length() > 0)
                        items = retrieveItems(mHtml);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @SuppressLint("JavascriptInterface")
    public void retrieveDataWebView() {

        /*The content of the html retrieved was encrypted but it contains this:

        <noscript>Please enable JavaScript to view the page content.</noscript>

        So I thought that loading the url in a WebView, with the JavaScript enabled, could work...but it isnt.
        It is retrieving the same encrypted code--> Creating a breakpoint in the line 165 and checking the value of the variable
*/
        WebView wvbrowser = new WebView(context);
        wvbrowser.getSettings().setJavaScriptEnabled(true);
        wvbrowser.addJavascriptInterface(new LoadListener(), "HTMLOUT");
        wvbrowser.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url,
                                      Bitmap favicon) {
            }

            public void onPageFinished(WebView view, String url) {
                view.loadUrl("javascript:window.HTMLOUT.processHTML('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>');");
            }
        });
        wvbrowser.loadUrl(URL);
    }

    class LoadListener{
        public void processHTML(String html)
        {
            retrieveItems(html);
        }
    }
}
