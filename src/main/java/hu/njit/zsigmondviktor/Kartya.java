package hu.njit.zsigmondviktor;

public class Kartya {
    //Mezok
    private final String nev;

    //Konstruktor
    public Kartya(String nev){
        this.nev = nev;
    }

    //Metodusok
    @Override
    public String toString() {
        return nev;
    }

    //Tulajdonsagok
    public String getNev() {
        return this.nev;
    }
}
