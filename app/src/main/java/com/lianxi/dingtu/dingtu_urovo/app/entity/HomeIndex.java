package com.lianxi.dingtu.dingtu_urovo.app.entity;

import java.io.Serializable;
import java.util.List;

public class HomeIndex implements Serializable {
    public String code;
    public List<Pub_Dictionary_Item> pub_Dictionary_Item;

    public class Pub_Dictionary_Item{
        /**
         * ITEM_CODE : 1
         * ITEM_NAME : 手动消费
         * CHAR1 : 消费模式
         */

        public String ITEM_CODE;
        public String ITEM_NAME;
        public String CHAR1;
    }
}
