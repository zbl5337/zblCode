package com.zbl.msb.designpatterns.abstractfactory;

/**
 * @Author: zbl
 * @Date: Created in 16:43 2020/1/2
 * @Description:
 * @Version: $
 */
public class Broom extends Vehicle implements VehicleInterface{

    public void go(){
        System.out.println("go go go Broom broom broom");
    }
}
