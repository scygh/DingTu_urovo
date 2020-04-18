package com.lianxi.dingtu.dingtu_urovo.app.entity;

import java.io.Serializable;

public class EMGoodsTo2 implements Serializable {

    /**
     * Goods : {"GoodsNo":10,"GoodsType":"b1305df8-2ae6-4d28-944a-85161a6f0014","GoodsName":"家常豆腐","Price":8,"Total":999,"GoodsNature":0,"PackageDetails":null,"State":1,"Description":null,"GoodsLetter":"Jia Chang Dou Fu "}
     * ImgCount : 0
     */

    private GoodsBean Goods;
    private int ImgCount;

    public GoodsBean getGoods() {
        return Goods;
    }

    public void setGoods(GoodsBean Goods) {
        this.Goods = Goods;
    }

    public int getImgCount() {
        return ImgCount;
    }

    public void setImgCount(int ImgCount) {
        this.ImgCount = ImgCount;
    }

    public static class GoodsBean {
        /**
         * GoodsNo : 10
         * GoodsType : b1305df8-2ae6-4d28-944a-85161a6f0014
         * GoodsName : 家常豆腐
         * Price : 8.0
         * Total : 999
         * GoodsNature : 0
         * PackageDetails : null
         * State : 1
         * Description : null
         * GoodsLetter : Jia Chang Dou Fu
         */

        private int GoodsNo;
        private String GoodsType;
        private String GoodsName;
        private double Price;
        private int Total;
        private int GoodsNature;
        private String PackageDetails;
        private int State;
        private String Description;
        private String GoodsLetter;

        public int getGoodsNo() {
            return GoodsNo;
        }

        public void setGoodsNo(int GoodsNo) {
            this.GoodsNo = GoodsNo;
        }

        public String getGoodsType() {
            return GoodsType;
        }

        public void setGoodsType(String GoodsType) {
            this.GoodsType = GoodsType;
        }

        public String getGoodsName() {
            return GoodsName;
        }

        public void setGoodsName(String GoodsName) {
            this.GoodsName = GoodsName;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }

        public int getTotal() {
            return Total;
        }

        public void setTotal(int Total) {
            this.Total = Total;
        }

        public int getGoodsNature() {
            return GoodsNature;
        }

        public void setGoodsNature(int GoodsNature) {
            this.GoodsNature = GoodsNature;
        }

        public String getPackageDetails() {
            return PackageDetails;
        }

        public void setPackageDetails(String packageDetails) {
            PackageDetails = packageDetails;
        }

        public int getState() {
            return State;
        }

        public void setState(int State) {
            this.State = State;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String description) {
            Description = description;
        }

        public String getGoodsLetter() {
            return GoodsLetter;
        }

        public void setGoodsLetter(String GoodsLetter) {
            this.GoodsLetter = GoodsLetter;
        }
    }
}
