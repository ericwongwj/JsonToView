package com.example.tn_ma_l30000048.myjsontest.model;

import java.util.List;

/**
 * Created by tn-ma-l30000048 on 17/7/24.
 */

public class CollectionView extends BaseView {
    static class Styles{
        String backgroundColor;
        CollectionStyle collectionStyle;
    }

    Styles styles;

    @Override
    public String toString() {
        return super.toString()+" "+styles.backgroundColor+" "+styles.collectionStyle.cellTemplateName;
    }
}
