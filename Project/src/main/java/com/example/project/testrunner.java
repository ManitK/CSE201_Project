package com.example.project;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class testrunner{
    public testrunner(){
        Result result = JUnitCore.runClasses(tests.class);
        for (Failure i : result.getFailures()) {
            System.out.println(i.toString());
        }
        System.out.println("ALL TESTS PASSED - " + result.wasSuccessful());
    }
}