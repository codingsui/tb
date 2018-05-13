package com.syl.tb.web.bean;

import org.apache.commons.lang3.StringUtils;

public class Item extends com.syl.tb.manage.pojo.Item {
    public String[] getImages(){
        return StringUtils.split(super.getImage(),",");
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
