package com.training.inventory;
public record Stock(String warehouse,String sku,int onHand,int reserved,long version) {
 public int available(){return onHand-reserved;}
}
