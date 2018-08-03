package com.wr.hadoop.common;

/**
 * @author: Alice
 * @email: chenzhangzju@yahoo.com
 * @date: 2016/12/21.
 */
public class DrinkBrandInfo {
    private String brand;
    private String category;
    private int index;

    public DrinkBrandInfo(String brand, String category, int index) {
        this.brand = brand;
        this.category = category;
        this.index = index;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
