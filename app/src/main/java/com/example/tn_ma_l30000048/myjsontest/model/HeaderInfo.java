package com.example.tn_ma_l30000048.myjsontest.model;

import java.util.List;

/**
 * Created by tn-ma-l30000048 on 17/8/4.
 */

public class HeaderInfo {
    public int hashBackButton;
    public String headTitle;

    public List<ExtensionButton> extensionButtons;

    @Override
    public String toString() {
        return headTitle + " " + hashBackButton;
    }

    public static class ExtensionButton {
        String text;
        String action;
        String imageSource;

        public void setText(String text) {
            this.text = text;
        }

        public void setAction(String action) {
            this.action = action;
        }

        public void setImageSource(String imageSource) {
            this.imageSource = imageSource;
        }
    }
}
