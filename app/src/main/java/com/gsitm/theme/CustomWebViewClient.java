package com.gsitm.theme;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import com.gsitm.theme.model.Data;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Set;

import io.realm.Realm;
import io.realm.RealmQuery;
import io.realm.RealmResults;

public class CustomWebViewClient extends WebViewClient {

    Context context;


    public CustomWebViewClient(Context context) {
        this.context = context;
    }



    //url요청 가로채기
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {

            Uri uri = request.getUrl();
            Log.i("웹뷰", " "+uri.getScheme());

            //gsepartner 일 경우
            if(uri.getScheme().equals("gsepartner")){
                Log.i("웹뷰", " "+request.getMethod());
                if(request.getMethod().equals("GET")){
                    getRequest(view, request);
                }
                return true;
            }
            //일반 스킴
            else{
                return false;
            }

    }

    //웹으로 데이터 전송
    public void getRequest(WebView view, WebResourceRequest request){
        Uri uri = request.getUrl();
        //파라미터 파싱 -> 콜백 함수명 추출 -> 맵형태로
        String callBack = uri.getQueryParameter("callback");
        String data1;
        String data2;

        Set<String> keys = uri.getQueryParameterNames();
        HashMap<String, String> params = new HashMap<>();
        for (String key: keys) {
            params.put(key, uri.getQueryParameter(key));
        }
        Data data = new Data(params.get("sendData1"), params.get("sendData2"));

        saveData(data);

        Log.d("웹뷰", "URL "+uri.toString()+" 콜백 함수명: "+callBack);
        Log.d("웹뷰", "data1: "+data.getData1()+" data2: "+data.getData2());

        //웹으로 전송할 앱내에 데이터
        JSONObject json = new JSONObject();
        try {
            json.put("token","abcdefg1234");
            json.put("name","LCJ");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        //웹 자바스크립트 콜백함수 호출
        view.loadUrl("javascript:"+callBack+"("+json+")");
    }

    //Realm 데이터 저장
    public void saveData(final Data data){

        Realm realm = Realm.getDefaultInstance();

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                realm.copyToRealm(data);
                RealmQuery<Data> query = realm.where(Data.class);
                RealmResults<Data> result1 = query.findAll();
                for (Data data : result1) {
                    Log.d("REALM", "data1: " + data.getData1() + " data2: " + data.getData2());
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Toast.makeText(context, "데이터 저장 성공!",Toast.LENGTH_SHORT).show();
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Toast.makeText(context, "데이터 저장 실패!",Toast.LENGTH_SHORT).show();
            }
        });

    }

}
