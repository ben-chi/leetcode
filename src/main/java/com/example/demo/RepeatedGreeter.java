package com.example.demo;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.time.Instant;

public class RepeatedGreeter extends Greeter{
    public void greet(ActionEvent event){
        Timer timer=new Timer(1000,super::greet);
        timer.start();
    }
}
class Greeter{
    public void greet(ActionEvent event){
        System.out.println("Hello, the time is "+ Instant.ofEpochMilli(event.getWhen()));
    }
}
