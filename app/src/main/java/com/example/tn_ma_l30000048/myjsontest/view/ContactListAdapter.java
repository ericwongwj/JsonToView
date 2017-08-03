package com.example.tn_ma_l30000048.myjsontest.view;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by tn-ma-l30000048 on 17/8/2.
 */

public class ContactListAdapter extends RecyclerAdapter<Bean> {

    public ContactListAdapter(Context context) {
        super(context);
    }

    public ContactListAdapter(Context context, List<Bean> data) {
        super(context, data);
    }

    protected BaseViewHolder<Bean> onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(parent);
    }
}
