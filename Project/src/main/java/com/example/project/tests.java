package com.example.project;

import org.junit.Test;

import static org.junit.Assert.*;

public class tests {
    private static final HandlerFactory FACTORY = new HandlerFactory();

    @Test
    public void test1(){
        PillarHandler P1 = new PillarHandler();
        boolean temp = false;
        if(P1.getClass()==FACTORY.getHandler("PILLAR").getClass()){
            temp = true;
        }
        assertTrue(temp);
    }

    @Test
    public void test2(){
        CherryHandler P2 = new CherryHandler();
        boolean temp = true;
        if(P2.getClass()!=FACTORY.getHandler("CHERRY").getClass()){
            temp = false;
        }
        assertFalse(!temp);
    }

}
