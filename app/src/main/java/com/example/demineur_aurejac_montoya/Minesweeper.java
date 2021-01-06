package com.example.demineur_aurejac_montoya;

import android.widget.Button;

import java.util.ArrayList;
import java.util.Collections;

//trucs à ajouter : option pause, première case trouvée ne peut pas etre une mine

//avec les hexagones, on considère les indices des cases de la manière suivante
//    0   1   2   3
//      4   5   6   7
//    7   8   9  10
//  ex : 6 = cases[1][2]

public class Minesweeper {
    private int height; //hauteur du jeu
    private int width; // largeur
    private int nMines; //nombre de mines total
    private ArrayList<Integer> mines = new ArrayList<Integer>(); //répartitions des mines sur le jeu, 1 = mine, 0 = pas de mine
    private int nRemainingMines; //pour afficher le nb de mines restantes en cours de jeu
    private int nFlags; //nombre de drapeaux posés
    private int nBoxes; //nombre de cases du jeu (largeur*hauteur)
    private int nRemainingBoxes;
    private MinesweeperBox[][] game; // jeu

    private boolean isLost;
    private boolean isWon;

    public Minesweeper(int height, int width, int nMines){
        this.height = height;
        this.width = width;
        this.nMines = nMines;
        nBoxes = width * height;
        nRemainingBoxes = nBoxes;
        game = new MinesweeperBox[height][width];

        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
                game[i][j] = new MinesweeperBox();
            }
        }
        Reset();
    }

    public void Reset(){
        isLost = false;
        isWon = false;
        nFlags = 0;
        nRemainingMines = nMines;

        //répartition des mines
        for(int i=0; i<nBoxes; i++){
            if(i<nMines) {
                mines.add(1);
            }
            else{
                mines.add(0);
            }
        }
        Collections.shuffle(mines);

        for(int i=0; i<height; i++) {
            for (int j = 0; j < width; j++) {
                game[i][j].reset();

                if (mines.get(i * width + j) == 1) {
                    game[i][j].setMine(true);
                }
            }
        }

        //on détermine le nombre de mines alentours aux cases non-minées
        for(int i=0; i<height; i++) {
            for (int j = 0; j < width; j++) {
                if (!game[i][j].isMine()) {
                    int n = 0;
                    //try car les cases n'existent pas forcément
                    try {
                        //voisin au dessus
                        if (game[i-2][j].isMine()) n++;
                    }
                    catch (java.lang.ArrayIndexOutOfBoundsException e) {}
                    try {
                        //voisin au dessus à gauche
                        if (game[i - 1][j - 1 + (i%2)].isMine()) n++;
                    }
                    catch (java.lang.ArrayIndexOutOfBoundsException e) {}
                    try {
                        //voisin au dessus à droite
                        if (game[i - 1][j + (i%2)].isMine()) n++;
                    }
                    catch (java.lang.ArrayIndexOutOfBoundsException e) {}
                    try {
                        //voisin en dessous à gauche
                        if (game[i + 1][j - 1 +(i%2)].isMine()) n++;
                    }
                    catch (java.lang.ArrayIndexOutOfBoundsException e) {}
                    try {
                        //voisin en dessous à droite
                        if (game[i + 1][j + (i%2)].isMine()) n++;
                    }
                    catch (java.lang.ArrayIndexOutOfBoundsException e) {}
                    try {
                        //voisin en dessous
                        if (game[i + 2][j].isMine()) n++;
                    }
                    catch (java.lang.ArrayIndexOutOfBoundsException e) {}
                    game[i][j].setNeighbours(n); //on indique à la case le nombre de mines
                }
            }
        }
    }

    public void Reveal(int y, int x){
        // y = ligne(i), x = colonne(j)
        //Si la case est normale ou avec un ?
        if ((game[y][x].getEtat() == 0 || game[y][x].getEtat() == 3) && !game[y][x].isMine()) {
            nRemainingBoxes --;
            game[y][x].setEtat(1); //on indique que la case est découverte
            if (game[y][x].getNeighbours() == 0) { // Si le nombre de mines autour est nul, on découvre les cases autour
                partialReveal1(x,y-2);
                partialReveal1(x - 1 + (y%2), y-1);
                partialReveal1(x + (y%2),y-1);
                partialReveal1(x - 1 + (y%2), y+1);
                partialReveal1(x + (y%2), y+1);
                partialReveal1(x, y+2);
            }
        }

        //Si on est au dessus d'un chiffre
        else if (game[y][x].getEtat() == 1 && game[y][x].getNeighbours() != 0) {
            int n = 0; //on compte le nombre de drapeaux placés
            if (partialReveal2(x, y-2)) n++;
            if (partialReveal2(x - 1 + (y%2), y-1)) n++;
            if (partialReveal2(x + (y%2),y-1)) n++;
            if (partialReveal2(x - 1 + (y%2), y+1)) n++;
            if (partialReveal2(x + (y%2), y+1)) n++;
            if (partialReveal2(x, y+2)) n++;

            if (n == game[y][x].getNeighbours()) { //si il y en a autant que le nombre de mines autour, on découvre les 8 cases autour par un appel récursif de decouvre(int, int)
                if(partialReveal3(x, y-2)) Reveal(y-2, x);
                if(partialReveal3(x - 1 + (y%2), y-1)) Reveal(y-1, x - 1 + (y%2));
                if(partialReveal3(x + (y%2),y-1)) Reveal(y-1, x + (y%2));
                if(partialReveal3(x - 1 + (y%2), y+1)) Reveal(y+1, x - 1 + (y%2));
                if(partialReveal3(x + (y%2), y+1)) Reveal(y+1, x + (y%2));
                if(partialReveal3(x, y+2)) Reveal(y+2, x);
            }
        }

        //Si on clique sur une mine
        else if ((game[y][x].getEtat() == 0 || game[y][x].getEtat() == 3) && game[y][x].isMine()) {
            game[y][x].setEtat(4); //boum
            isLost = true;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    game[i][j].setBlocked(true);
                    if (!(y == i && x == j) && mines.get(i * width + j) == 1 && game[i][j].getEtat() != 2)
                        //si il ya une mine, (recherche par rapport à la chaîne mines
                        game[i][j].setEtat(5); //on l' affiche
                }
            }
            //on affiche les erreurs
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (game[i][j].getEtat() == 2 && !game[i][j].isMine())
                        game[i][j].setEtat(6);
                }
            }
        }
        //Si on gagne, c'est à dire le nombre de cases restantes est égal au nombre de mines restantes
        if (nRemainingBoxes == nMines && !game[0][0].isBlocked()) {
            nRemainingMines = 0;
            isWon = true;
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    game[i][j].setBlocked(true);
                    if (game[i][j].isMine()) game[i][j].setEtat(2); //om met des drapeaux partout!!
                }
            }
        }
    }

    //si la case existe, on la découvre et si necessaire, on appelle le découvrement des cases autour
    public void partialReveal1(int x, int y) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            if (game[y][x].getEtat() == 0 && game[y][x].getNeighbours() != 0) {
                game[y][x].setEtat(1);
                nRemainingBoxes--;
            }
            if (game[y][x].getEtat() == 0 && game[y][x].getNeighbours() == 0)
                Reveal(y, x); //Si le nombre de mines autour est nul, on découvre les cases autour
        }
    }

    //vérifie si la case existe et si elle porte un drapeau
    public boolean partialReveal2(int x, int y) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            if (game[y][x].getEtat() == 2)
                return true;
        }
        return false;
    }

    //vérifie si la case existe et si elle n'est pas découverte ou si elle porte un '?'
    public boolean partialReveal3(int x, int y) {
        if (x >= 0 && y >= 0 && x < width && y < height) {
            if (game[y][x].getEtat() == 0 || game[y][x].getEtat() == 3)
                return true;
        }
        return false;
    }

    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }
    public MinesweeperBox[][] getGame(){
        return game;
    }
}
