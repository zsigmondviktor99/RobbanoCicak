package hu.njit.zsigmondviktor;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class KartyaPakli {
    //Mezok
    private final int jatekosDb;
    private List<Kartya> kevertPakli;

    private final List<Kartya> robbanoCicaKartyak;        //db a jatekosok szamatol fugg
    private final List<Kartya> hatastalanitoKartyak;      //db a jatekosok szamatol fugg

    private final List<Kartya> visszaforditoKartyak;
    private final List<Kartya> tamadasKartyak;
    private final List<Kartya> ugrasKartyak;
    private final List<Kartya> szivessegKartyak;
    private final List<Kartya> ujrakeveresKartyak;
    private final List<Kartya> jovobeLatasKartyak;

    private final List<Kartya> macsKartyaSziarvanytOkadoMacska;
    private final List<Kartya> macsKartyaDinnyauMacska;
    private final List<Kartya> macsKartyaSzorosKrumplimacska;
    private final List<Kartya> macsKartyaSzormacska;
    private final List<Kartya> macsKartyaMacskaja;

    //Konstruktor
    public KartyaPakli(int jatekosDb){
        this.jatekosDb = jatekosDb;

        //Teljes nem megkevert pakli
        List<Kartya> pakli = new ArrayList<>();

        //Robbano cica kartyak (jatekosDb - 1 db), csak az osztas utan keverjuk bele, mivel jatekosnal nem lehet robbano cica
        robbanoCicaKartyak = new ArrayList<>();

        //Hatastalanito kartyak (6 - jatekosDb db)
        hatastalanitoKartyak = new ArrayList<>();

        for(int i = 0; i <= (6 - jatekosDb) - 1; i++){
            hatastalanitoKartyak.add(new Kartya("Hatástalanító kártya"));

            //Paklihoz adas
            pakli.add(hatastalanitoKartyak.get(i));
        }

        //Visszafordito kartyak (5 db) + jovobe latas kartyak (5 db)
        visszaforditoKartyak = new ArrayList<>();
        jovobeLatasKartyak = new ArrayList<>();

        for(int i = 0; i <= 4; i++){
            visszaforditoKartyak.add(new Kartya("Visszafordító"));
            jovobeLatasKartyak.add(new Kartya("Jövőbe látás"));

            //Paklihoz adas
            pakli.add(visszaforditoKartyak.get(i));
            pakli.add(jovobeLatasKartyak.get(i));
        }

        //Tamadas kartyak (4 db) + ugras kartyak (4 db) + szivesseg kartyak (4 db) + ujrakeveres kartyak (4 db) + mind az 5 kulonbozo macskartya (4-4 db)
        tamadasKartyak = new ArrayList<>();
        ugrasKartyak = new ArrayList<>();
        szivessegKartyak = new ArrayList<>();
        ujrakeveresKartyak = new ArrayList<>();

        macsKartyaSziarvanytOkadoMacska = new ArrayList<>();
        macsKartyaDinnyauMacska = new ArrayList<>();
        macsKartyaSzorosKrumplimacska = new ArrayList<>();
        macsKartyaSzormacska = new ArrayList<>();
        macsKartyaMacskaja = new ArrayList<>();

        for(int i = 0; i <= 3; i++){
            tamadasKartyak.add(new Kartya("Támadás"));
            ugrasKartyak.add(new Kartya("Ugrás"));
            szivessegKartyak.add(new Kartya("Szívesség"));
            ujrakeveresKartyak.add(new Kartya("Újrakeverés"));

            macsKartyaSziarvanytOkadoMacska.add(new Kartya("Szivárványt okádó macska (Macskártya)"));
            macsKartyaDinnyauMacska.add(new Kartya("Dinnyaú-macska (Macskártya)"));
            macsKartyaSzorosKrumplimacska.add(new Kartya("Szőrös krumplimacska (Macskártya)"));
            macsKartyaSzormacska.add(new Kartya("Szőrmacska (Macskártya)"));
            macsKartyaMacskaja.add(new Kartya("Macskaja (Macskártya)"));

            //Paklihoz adas
            pakli.add(tamadasKartyak.get(i));
            pakli.add(ugrasKartyak.get(i));
            pakli.add(szivessegKartyak.get(i));
            pakli.add(ujrakeveresKartyak.get(i));

            pakli.add(macsKartyaSziarvanytOkadoMacska.get(i));
            pakli.add(macsKartyaDinnyauMacska.get(i));
            pakli.add(macsKartyaSzorosKrumplimacska.get(i));
            pakli.add(macsKartyaSzormacska.get(i));
            pakli.add(macsKartyaMacskaja.get(i));
        }

        //Teljes megkevert pakli
        kevertPakli = new ArrayList<>();

        //Pakli keverese
        kevertPakli = pakliKeverese(pakli);
    }

    //Metodusok
    public Kartya huzas(){
        Kartya kartya = this.kevertPakli.get(0);
        this.kevertPakli.remove(0);

        return kartya;
    }

    public List<Kartya> osztas(){
        //8 kartyaval kezd mindenki, mindenkinel alapbol van egy hatastalanitas
        List<Kartya> osztottKartyak = new ArrayList<>();

        for(int i = 0; i <= 6; i++){
            osztottKartyak.add(huzas());
        }

        return osztottKartyak;
    }

    public void robbanoCicakPaklibaKeverese(){
        //-2 a 0-tol torteno indexeles, es a jatekodDb - 1 db miatt
        for (int i = 0; i <= jatekosDb - 2; i++){
            robbanoCicaKartyak.add(new Kartya("BUMM (Robbanó cica)"));

            //Paklihoz adas
            kevertPakli.add(robbanoCicaKartyak.get(i));

            //Pakli keverese
            kevertPakli = pakliKeverese(kevertPakli);
        }
    }

    public List<Kartya> pakliKeverese(List<Kartya> pakli){
        List<Kartya> localKevertPakli = new ArrayList<>();

        int h = pakli.size();
        Random r = new Random();
        int rnd;

        for(int i = 0; i <= h - 1; i++){
            rnd = r.nextInt(pakli.size());
            localKevertPakli.add(pakli.get(rnd));
            pakli.remove(rnd);
        }

        return localKevertPakli;
    }

    //Tulajdonsagok
    public List<Kartya> getKartyaPakli(){
        return kevertPakli;
    }

    public void setKartyaPakli(List<Kartya> kartyak){
        kevertPakli.addAll(kartyak);
    }

    public void setKartyaPakli(Kartya robbanoCicaKartya, Kartya hatastalanitoKartya){
        //Ha a robbano cicat leg"felulre" akarja tenni
        this.kevertPakli.add(0, robbanoCicaKartya);
        this.kevertPakli.add(0, hatastalanitoKartya);
    }

    public void setKartyaPakli(Kartya robbanoCicaKartya, Kartya hatastalanitoKartya, boolean random){
        //Ha a robbano cicat random helyre akarja tenni
        Random r = new Random();

        int rnd = r.nextInt(this.kevertPakli.size());
        this.kevertPakli.add(rnd, hatastalanitoKartya);

        rnd = r.nextInt(this.kevertPakli.size());
        this.kevertPakli.add(rnd, robbanoCicaKartya);
    }

    public void kartyaEltavolitasaPaklibol(Kartya k){
        this.kevertPakli.remove(k);
    }
}
