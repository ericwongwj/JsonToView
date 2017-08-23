package com.example.tn_ma_l30000048.myjsontest.activity;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.tn_ma_l30000048.myjsontest.R;
import com.example.tn_ma_l30000048.myjsontest.model.CollectionView;
import com.example.tn_ma_l30000048.myjsontest.model.Divider;
import com.example.tn_ma_l30000048.myjsontest.model.Image;
import com.example.tn_ma_l30000048.myjsontest.model.Indicator;
import com.example.tn_ma_l30000048.myjsontest.model.Model;
import com.example.tn_ma_l30000048.myjsontest.model.Module;
import com.example.tn_ma_l30000048.myjsontest.model.RichText;
import com.example.tn_ma_l30000048.myjsontest.model.TableView;
import com.example.tn_ma_l30000048.myjsontest.model.TextLabel;
import com.example.tn_ma_l30000048.myjsontest.parser.JsonHelper;
import com.example.tn_ma_l30000048.myjsontest.parser.JsonRoot;
import com.example.tn_ma_l30000048.myjsontest.utils.JasonHelper;
import com.example.tn_ma_l30000048.myjsontest.utils.JsonUtils;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    Gson gson = new Gson(); // Or use new GsonBuilder().create();

    ListView lv;

    List<String> jsonFiles=new ArrayList<>();
    List<String> jsonLayouts=new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_test_layout).setOnClickListener(this);
        findViewById(R.id.btn_test_view).setOnClickListener(this);
        findViewById(R.id.btn_test_list).setOnClickListener(this);
        findViewById(R.id.btn_test_relative).setOnClickListener(this);
        findViewById(R.id.btn_grid).setOnClickListener(this);
        findViewById(R.id.btn_my_list).setOnClickListener(this);
        lv=(ListView)findViewById(R.id.main_lv);
        lv.setOnItemClickListener(this);
        lv.setOnItemLongClickListener(this);

        jsonLayouts= Arrays.asList(readLayoutAssets(jsonFiles));

        adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,jsonFiles);
        lv.setAdapter(adapter);

        JSONObject jsonObject = JsonHelper.readLocalDataJson(this, "testData.json");
        Map<String, Object> map = new HashMap<>();
        JsonRoot.constructDataMap(map, jsonObject);
        System.out.println(JsonRoot.getDataFromMap(map, Arrays.asList("nickName")));
//        System.out.println(jsonObject);

        JSONObject root = JsonHelper.readLocalUIJson(this, "Mytest1.json");
        JSONObject json = null;
        try {
            json = JsonHelper.getLayout(root.getJSONObject("rootNode"));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    String[] readLayoutAssets(List<String> list){

        AssetManager assetManager=getAssets();
        String[] jsonStrings=null;
        try {
            String[] fn = assetManager.list("jsons/contact");
            jsonStrings=new String[fn.length];
            int i=0;
            for(String name:fn){
                list.add(name);
                InputStream is = assetManager.open("jsons/contact/" + name);
                int size = is.available();
                byte[] buffer = new byte[size];
                is.read(buffer);
                is.close();
                String content = new String(buffer, "UTF-8");
                jsonStrings[i++]=content;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStrings;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_test_view:
                startActivity(new Intent(MainActivity.this,TestViewActivity.class));
                break;
            case R.id.btn_test_layout:
                startActivity(new Intent(MainActivity.this,TestLayoutActivity.class));
                break;
            case R.id.btn_test_list:
                startActivity(new Intent(MainActivity.this,TestListActivity.class));
                break;
            case R.id.btn_test_relative:
                startActivity(new Intent(MainActivity.this, TestRelativeActivity.class));
                break;
            case R.id.btn_my_list:
                startActivity(new Intent(MainActivity.this, TestMyListActivity.class));
//                adapter.notifyDataSetChanged();
                break;
            case R.id.btn_grid:
                startActivity(new Intent(MainActivity.this, TestGridActivity.class));
                break;
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,JsonToViewActivity.class);
        intent.putExtra("json",jsonLayouts.get(position));
        intent.putExtra("title",jsonFiles.get(position));
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(this,ShowJsonActivity.class);
        intent.putExtra("json",jsonLayouts.get(position));
        startActivity(intent);
        return true;
    }

    void testContactList(){
        String s1= JasonHelper.readLocalJsonToString("jsons://layout/TNChatContactList.json",this);
        Model model= JsonUtils.decode(s1,Model.class);
        System.out.println(model);
    }

    void testModule(){
        String s1=JasonHelper.readLocalJsonToString("jsons://view/module.json",this);
        System.out.println(s1);
        Module module=gson.fromJson(s1,Module.class);
        System.out.println(module.toString());
    }

    void testTextLabel(){
        String s1=JasonHelper.readLocalJsonToString("jsons://view/textLabel.json",this);
        TextLabel textLabel=gson.fromJson(s1,TextLabel.class);
        System.out.println(textLabel);
    }

    void testImage(){
        String s1=JasonHelper.readLocalJsonToString("jsons://view/image.json",this);
        Image image=gson.fromJson(s1,Image.class);
        System.out.println(image);
    }

    void testDivider(){
        String s1=JasonHelper.readLocalJsonToString("jsons://view/view.json",this);
        Divider divider=gson.fromJson(s1,Divider.class);
        System.out.println(divider);
    }

    void testIndicator(){
        String s1=JasonHelper.readLocalJsonToString("jsons://view/indicator.json",this);
        Indicator indicator=JsonUtils.decode(s1,Indicator.class);
        System.out.println(indicator);
    }

    void testRichText(){
        String s1=JasonHelper.readLocalJsonToString("jsons://view/richText.json",this);
        RichText richText=JsonUtils.decode(s1,RichText.class);
        System.out.println(richText);
    }

    void testTableView(){
        String s1=JasonHelper.readLocalJsonToString("jsons://view/tableview.json",this);
        TableView tableView=JsonUtils.decode(s1,TableView.class);
        System.out.println(tableView);
    }

    void testCollectionView(){
        String s1=JasonHelper.readLocalJsonToString("jsons://view/collectionview.json",this);
        CollectionView collectionView=JsonUtils.decode(s1,CollectionView.class);
        System.out.println(collectionView);
    }

    void test1(){
        JSONObject jo1=new JSONObject();
        JSONObject jo_in_jo1=new JSONObject();
        try {
            jo1.put("name1","n1");
            jo1.put("name2","n2");
            jo1.put("name3","n3");
            jo_in_jo1.put("inJo1","ij1");
            jo_in_jo1.put("injo2",2);
            jo1.put("recursive",jo_in_jo1);
            System.out.println(jo1.toString());

            MyTest1 mt1=gson.fromJson(jo1.toString(),MyTest1.class);
            System.out.println(mt1.name1);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JSONObject jsonObject=(JSONObject)JasonHelper.readLocalJsonToObj("jsons://loading.json",this);
        System.out.println(jsonObject);
        try {
            Iterator<String> iterator=jsonObject.keys();
            while(iterator.hasNext())
                System.out.println(iterator.next());

            JSONObject jo=jsonObject.getJSONObject("$jason");
            System.out.println(jo.get("head"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void gsonRelated(String strJson){
        JsonParser parser = new JsonParser();
        JsonElement el = parser.parse(strJson);

        //把JsonElement对象转换成JsonObject
        JsonObject jsonObj = null;
        if(el.isJsonObject()){
            jsonObj = el.getAsJsonObject();
        }

        //把JsonElement对象转换成JsonArray
        JsonArray jsonArray = null;
        if(el.isJsonArray()){
            jsonArray = el.getAsJsonArray();
        }

        //遍历JsonArray对象
        Iterator it = jsonArray.iterator();
        while(it.hasNext()){
            JsonElement e = (JsonElement)it.next();
            //JsonElement转换为JavaBean对象
            //jbDemo= gson.fromJson(e, JavaBeanDemo.class);
        }

    }


    static class MyTest1 {
        String name1;
        String name2;
        String name3;
        JoIn recursive;

        static class JoIn {
            String inJo1;
            String injo2;
        }
    }
}
