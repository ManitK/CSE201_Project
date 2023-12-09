package com.example.project;

public class HandlerFactory {
        //Factory Design Pattern Implemented to Generate Handlers of Different Handlers
        public HandlerType getHandler(String temp){//factory methods
            if(temp == null){
                return null;
            }
            else if(temp.equalsIgnoreCase("PILLAR")){
                return new PillarHandler();
            }
            else if(temp.equalsIgnoreCase("STICK")){
                return new StickHandler();
            }
            else if(temp.equalsIgnoreCase("CHERRY")){
                return new CherryHandler();
            }
            return null;
        }
}
