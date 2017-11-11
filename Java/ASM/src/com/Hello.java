package com;
public class Hello {
    public void testSwitch(int i){
      switch(i) {
        case 1:
        System.out.println("one");
        case 10:
        System.out.println("ten");
        case 5:
        System.out.println("five");
        case 3:
        System.out.println("three");
        default:
        System.out.println("other");
        }//switch
    }
    public void testGoto(int i){
     if(i==1)
     	System.out.println("i==1");
     else
     	System.out.println("i!=1");
    }
}