package hu.njit.zsigmondviktor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Jatekos {
    //Mezok
    private final String nev;
    private final List<Kartya> kartyakKezeben;
    private boolean felrobbantE;

    //Konstruktor
    public Jatekos(String nev){
        this.nev = nev;
        kartyakKezeben = new ArrayList<>();
        felrobbantE = false;

        //Kezdesnel minden jatekosnak van 1 db hatastalanitoja (a jatekosok szamatol fuggen a paklibol indulasnal ezeket mar kivesszuk)
        kartyakKezeben.add(new Kartya("Hatástalanító kártya"));
    }

    //Metodusok
    public List<Kartya> SajatKartyakMegkeverese(){
        List<Kartya> localKevertPakli = new ArrayList<>();

        int h = kartyakKezeben.size();
        Random r = new Random();
        int rnd;

        for(int i = 0; i <= h - 1; i++){
            rnd = r.nextInt(kartyakKezeben.size());
            localKevertPakli.add(kartyakKezeben.get(rnd));
            kartyakKezeben.remove(rnd);
        }

        return localKevertPakli;
    }

    //Tulajdonsagok
    public String getNev(){
        return nev;
    }

    public List<Kartya> getKartyakKezeben(){
        return kartyakKezeben;
    }

    public void setKartyakKezeben(Kartya kartya){
        kartyakKezeben.add(kartya);
    }

    public void setKartyakKezeben(List<Kartya> kartyak){
        kartyakKezeben.addAll(kartyak);
    }

    public void kartyaEltavolitasKezbol(Kartya kartya){
        kartyakKezeben.remove(kartya);
    }

    public void kartyaEltavolitasKezbol(int ind){
        kartyakKezeben.remove(ind);
    }

    public boolean getFelrobbantE(){
        return felrobbantE;
    }

    public void setFelrobbantE(boolean felrobbantE){
        this.felrobbantE = felrobbantE;
    }
}
