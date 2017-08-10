package com.example.tn_ma_l30000048.myjsontest.view;

import android.content.Context;
import android.view.ViewGroup;

/**
 * Created by tn-ma-l30000048 on 17/8/2.
 */

public class ContactListAdapter extends RecyclerAdapter {

    public ContactListAdapter(Context context) {
        super(context);
    }

    protected BaseViewHolder onCreateBaseViewHolder(ViewGroup parent, int viewType) {
        return new ContactViewHolder(parent);
    }
}
