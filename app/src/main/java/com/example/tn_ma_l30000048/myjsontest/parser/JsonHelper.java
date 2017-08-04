package com.example.tn_ma_l30000048.myjsontest.parser;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tn-ma-l30000048 on 17/7/27.
 */

public class JsonHelper {

    public static JSONObject readLocalJson(Context context, String name) {
        AssetManager assetManager = context.getAssets();
        JSONObject jsonObject = null;
        try {
            InputStream is = assetManager.open("jsons/layoutv2/" + name);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String content = new String(buffer, "UTF-8");
            jsonObject = new JSONObject(content);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static JSONObject findLocalJsonByName(Context context, String name) {
        AssetManager assetManager = context.getAssets();
        JSONObject jsonObject = null;
        try {
            String[] fn = assetManager.list("jsons/layoutv2");
            int i;
            for (i = 0; i < fn.length; i++) {
                if (fn[i].contains(name))
                    break;
            }
            jsonObject = readLocalJson(context, name + ".json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    static JSONObject getLayout(JSONObject json){
        JSONObject layout=null;
        try {
            layout=json.getJSONObject("layout");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return layout;
    }

    static JSONObject getStyles(JSONObject json){
        JSONObject styles= null;
        try {
            styles = json.getJSONObject("styles");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return styles;
    }

    static JSONArray getSubNodes(JSONObject json){
        JSONArray jsonArray=null;
        try {
            jsonArray=json.getJSONArray("subNode");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    static JSONObject getHeaderInfo(JSONObject json){
        JSONObject headerInfo= null;
        try {
            headerInfo = json.getJSONObject("headerInfo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return headerInfo;
    }

    static JSONObject getRequestInfo(JSONObject json){
        JSONObject requestInfo= null;
        try {
            requestInfo = json.getJSONObject("requestInfo");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return requestInfo;
    }

    public static int parse_color(String color_string) {
        Pattern rgb = Pattern.compile("rgb *\\( *([0-9]+), *([0-9]+), *([0-9]+) *\\)");
        Pattern rgba = Pattern.compile("rgba *\\( *([0-9]+), *([0-9]+), *([0-9]+), *([0-9.]+) *\\)");
        Matcher rgba_m = rgba.matcher(color_string);
        Matcher rgb_m = rgb.matcher(color_string);
        if (rgba_m.matches()) {
            float a = Float.valueOf(rgba_m.group(4));
            int alpha = (int) Math.round(a * 255);
            String hex = Integer.toHexString(alpha).toUpperCase();
            if (hex.length() == 1) hex = "0" + hex;
            hex = "0000" + hex;
            return Color.argb(Integer.parseInt(hex, 16), Integer.valueOf(rgba_m.group(1)), Integer.valueOf(rgba_m.group(2)), Integer.valueOf(rgba_m.group(3)));
        } else if (rgb_m.matches()) {
            return Color.rgb(Integer.valueOf(rgb_m.group(1)), Integer.valueOf(rgb_m.group(2)), Integer.valueOf(rgb_m.group(3)));
        } else {
            // Otherwise assume hex code
            return Color.parseColor(color_string);
        }
    }

}
