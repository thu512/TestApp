package com.gsitm.theme.model;

import io.realm.RealmObject;

//realm 오브젝트
public class Data extends RealmObject {

    //웹에서 넘어온 파라미터들
    String data1;
    String data2;

    public Data() {
    }

    public Data(String data1, String data2) {
        this.data1 = data1;
        this.data2 = data2;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }
}
