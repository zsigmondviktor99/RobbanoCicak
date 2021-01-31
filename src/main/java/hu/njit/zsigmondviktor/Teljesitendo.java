package hu.njit.zsigmondviktor;

public class Teljesitendo {
    //Mezok
    private final Jatekos keroJatekos;
    private final Jatekos teljesitoJatekos;
    private final Kartya kartya;
    private int hanyszorTeljesitendoMeg;
    private boolean teljesitve;

    //Konstruktor
    public Teljesitendo(Jatekos keroJatekos, Jatekos teljesitoJatekos, Kartya kartya, int hanyszorTeljesitendoMeg){
        this.keroJatekos = keroJatekos;
        this.teljesitoJatekos = teljesitoJatekos;
        this.kartya = kartya;
        this.hanyszorTeljesitendoMeg = hanyszorTeljesitendoMeg;
        teljesitve = false;
    }

    //Tulajdonsagok
    public Jatekos getKeroJatekos(){
        return keroJatekos;
    }

    public Jatekos getTeljesitoJatekos(){
        return teljesitoJatekos;
    }

    public Kartya getKartya(){
        return kartya;
    }

    public int getHanyszorTeljesitendoMeg(){
        return hanyszorTeljesitendoMeg;
    }

    public void setHanyszorTeljesitendoMeg(int h){
        hanyszorTeljesitendoMeg = h;
    }

    public boolean getTeljesitve(){
        return teljesitve;
    }

    public void setTeljesitve(boolean t){
        teljesitve = t;
    }
}
