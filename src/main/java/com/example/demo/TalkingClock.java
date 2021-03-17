package com.example.demo;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.time.Instant;

public class TalkingClock {

    public  void start(int interval,boolean beep){
        ActionListener listener=new ActionListener(){
            public void actionPerformed(ActionEvent event){
                System.out.println("At the tone, the time is "+
                        Instant.ofEpochMilli(event.getWhen()));
                if(beep) Toolkit.getDefaultToolkit().beep();
            }
        };
        Timer timer=new Timer(interval,listener);
        timer.start();

    }

}
