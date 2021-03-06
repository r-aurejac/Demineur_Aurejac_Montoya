package com.example.demineur_aurejac_montoya.Time;


//classe de gestion du temps
public class Time {
    public int nSeconds;
    public String display;

    public Time(int nSeconds){
        this.nSeconds = nSeconds;
        refresh();
    }

    public void increment(){
        nSeconds ++;
        refresh();
    }

    public void decrement(){
        if(nSeconds>0){
            nSeconds --;
            refresh();
        }
    }

    public void refresh(){
        display = "";
        int nMinutes = nSeconds/60;
        if(nMinutes>9){
            display = String.valueOf(nMinutes);
        }
        else{
            display = "0" + String.valueOf(nMinutes);
        }
        display += ":";
        int nRemainingSeconds = nSeconds%60;
        if(nRemainingSeconds>9){
            display += String.valueOf(nRemainingSeconds);
        }
        else{
            display += "0" + String.valueOf(nRemainingSeconds);
        }
    }
}
