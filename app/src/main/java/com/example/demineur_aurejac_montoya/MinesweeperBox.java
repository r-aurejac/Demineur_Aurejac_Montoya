package com.example.demineur_aurejac_montoya;

import java.io.Serializable;

public class MinesweeperBox implements Serializable {
    private int state = 0; //0 = rien; 1==révélée; 2=drapeau; 3=?; 4=mine déclenchée; 5=mine révélée; 6=erreur de drapeau(cas possible uniquement quand le jeu est perdu)
    private boolean isMine = false; //y'a t-il une mine sur la case ?
    private boolean isSelected = false;
    private boolean isBlocked = false; // bouton bloqué
    private int neighbours; //nb de mines voisines

    public int getState(){
        return state;
    }
    public void setState(int state){
        this.state = state;
    }
    public boolean isMine(){
        return isMine;
    }
    public void setMine(boolean isMine){
        this.isMine = isMine;
    }
    public int getNeighbours(){
        return neighbours;
    }
    public void setNeighbours(int neighbours){
        this.neighbours = neighbours;
    }
    public boolean isSelected(){
        return isSelected;
    }
    public void setSelected(boolean isSelected){
        this.isSelected = isSelected;
    }
    public boolean isBlocked(){
        return isBlocked;
    }
    public void setBlocked(boolean isBlocked){
        this.isBlocked = isBlocked;
    }
    public void setFlag(){
        if(getState()==0){
            setState(2);
        }
        else if(getState()==2) {
            setState(3);
        }
        else if(getState()==3) {
            setState(0);
        }
    }

    public void reset() {
        this.state = 0;
        this.isSelected = false;
        this.isBlocked = false;
        setMine(false);
    }
}
