package com.example.tn_ma_l30000048.myjsontest.model;

import java.util.List;

/**
 * Created by tn-ma-l30000048 on 17/7/25.
 * text,imageUrl and so on
 */

public class AtomicData {
    //0: 可直接使用的数据源 String eg:"the data of to show"
    //1: 从Data json中得到的数据 eg:[path0,path1,path2,path3]
    int dataType;
    String data;
    List<String> dataPaths;

    public int getDataType() {
        return dataType;
    }

    public void setDataType(int dataType) {
        this.dataType = dataType;
    }

    public List<String> getDataPaths() {
        return dataPaths;
    }

    public void setDataPaths(List<String> dataPaths) {
        this.dataPaths = dataPaths;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    @Override
    public String toString() {
        if (dataPaths == null)
            return "dataType:" + dataType + " direct data:" + data;
        return "dataType:" + dataType + " dataPath size:" + dataPaths.size();
    }
}
