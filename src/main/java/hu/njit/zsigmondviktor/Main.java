package hu.njit.zsigmondviktor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> valaszLehetosegek = Arrays.asList("H", "K", "P", "O");
        List<Teljesitendo> teljesitendoKeresek = new ArrayList<>();
        List<Jatekos> jatekosok = new ArrayList<>();

        //Jatekosok szamanak bekerese
        JatekosokBekerese(jatekosok);

        //Itt meg nincsenek benne a robbano cicak
        KartyaPakli pakli = new KartyaPakli(jatekosok.size());

        //Kartyak osztasa a jatekosoknak
        for(Jatekos j : jatekosok){
            j.setKartyakKezeben(pakli.osztas());
        }

        //Robbano cicak bekeverese a pakliba
        pakli.robbanoCicakPaklibaKeverese();

        //Sortores
        System.out.println();

        System.out.println("START!");

        //Sortores
        System.out.println();

        //Elvileg a pakli nem tud elfogyni az eredeti szabalyok szerint, mindenkepp addig jatszunk, ameddig 1 ember kivetelevel mindenki felrobban
        //Ciklus, hogy addig menjen a jatek, ameddig 1 jatekos kivetelevel mindenki felrobbant
        do{
            //Ciklus, hogy minden jatekos sorra keruljon >> foreach helyett for, hogy a kor visszaforditasa rendben mukodjon
            for(int j = 0; j < jatekosok.size(); j++){
                //Mivel a felrobbant jatekosokat nem veszem ki a listabol, igy csak megnezem, hogy ha az aktualis jatekos mar felrobbant, akkor ugorjunk a kovetkezore
                if(jatekosok.get(j).getFelrobbantE()){
                    continue;
                }

                System.out.println("==== " + jatekosok.get(j).getNev() + " köre ================================================================================================================================================");
                System.out.println("ROBBANÓ CICA ESÉLYE: " + RobbanoCicaEselye(pakli) + "%");
                String v;

                //Sortores
                System.out.println();

                //Jatekosnak van-e vegrehajtando feladata
                List<Teljesitendo> jatekosTeljesitendoKeresei = new ArrayList<>();
                Jatekos teljesitendoJatekos = jatekosok.get(j);

                if(teljesitendoKeresek.stream().anyMatch(x -> x.getTeljesitoJatekos().getNev().equals(teljesitendoJatekos.getNev()) && !x.getTeljesitve())){
                    //Jatekoshoz tartozo vegrehajtando feladatok kivalogatasa
                    for(Teljesitendo t : teljesitendoKeresek){
                        if(t.getTeljesitoJatekos().getNev().equals(jatekosok.get(j).getNev()) && !t.getTeljesitve()){
                            jatekosTeljesitendoKeresei.add(t);
                        }
                    }
                }

                //Ciklus, hogy csak akkor legyen vege egy jatekos korenek, ha huzott a paklibol
                do{
                    //Ciklus, hogy biztos valasszon a jatekos lehetoseget
                    do{
                        System.out.print("Mit szeretnél tenni (H - lap húzása / K - lap kijátszása / P - kártyáid megnézése / O - saját kártyák megkeverése): ");

                        try{
                            v = br.readLine().toUpperCase();
                        }
                        catch(Exception e){
                            v = "";
                        }

                        if(v.length() == 0){
                            //Sortores
                            System.out.println();

                            System.out.println("Add meg, mit szeretnél tenni.");

                            //Sortores
                            System.out.println();
                        }
                        else if(!valaszLehetosegek.contains(v)){
                            //Sortores
                            System.out.println();

                            System.out.println("A megadott lehetőségek közül válassz.");

                            //Sortores
                            System.out.println();
                        }
                    }while(v.length() == 0 || !valaszLehetosegek.contains(v));

                    //Csak huzassal er veget egy kor, ezaltal csak H nyomasanal kell vegrehajtandonak tekinteni a feladatot
                    if (v.equals("H")){
                        //Jatekoshoz tartozo feladatok vegrehajtasa
                        for(Teljesitendo t : jatekosTeljesitendoKeresei){

                            if(t.getKartya().getNev().equals("Szívesség")){
                                System.out.println("Adnod kell egy kártyát " + t.getKeroJatekos().getNev() + " részére. Melyik legyen az?");
                                JatekosKartyainakKiirasa(jatekosok.get(j));

                                JatekosKartyatElvesz(t.getKeroJatekos(), t.getTeljesitoJatekos());
                                t.setHanyszorTeljesitendoMeg(t.getHanyszorTeljesitendoMeg() - 1);

                                if (t.getHanyszorTeljesitendoMeg() == 0){
                                    t.setTeljesitve(true);
                                }
                            }

                            if (t.getKartya().getNev().equals("Támadás")){
                                t.setHanyszorTeljesitendoMeg(t.getHanyszorTeljesitendoMeg() - 1);

                                if(t.getHanyszorTeljesitendoMeg() != 0){
                                    //Sortores
                                    System.out.println();

                                    System.out.println("Az előző körben " + t.getKeroJatekos().getNev() + " megtámadott Téged, ezért még " + t.getHanyszorTeljesitendoMeg() + "x Te jössz.");

                                    //Sortores
                                    System.out.println();
                                }

                                if (t.getHanyszorTeljesitendoMeg() == 0){
                                    t.setTeljesitve(true);
                                }
                            }
                        }
                    }

                    //Elkapni, ha a JatekosKartyatKijatszik VisszaforditoException-t, UgrasException-t vagy TamadasException-t dobna
                    try{
                        switch (v.toUpperCase()){
                                case "H":
                                    JatekosKartyatHuz(jatekosok.get(j), pakli);
                                    break;

                                case "K":
                                    if(jatekosok.get(j).getKartyakKezeben().size() == 0){
                                        System.out.println("Nincsen kijátszható kártyád.");
                                    }
                                    else{
                                        JatekosKartyatKijatszik(jatekosok.get(j), pakli, jatekosok, teljesitendoKeresek);
                                    }
                                    break;
                                case "P":
                                    JatekosKartyainakKiirasa(jatekosok.get(j));
                                    break;
                                case "O":
                                    jatekosok.get(j).setKartyakKezeben(jatekosok.get(j).SajatKartyakMegkeverese());
                                    break;
                            }
                        }
                    catch (VisszaforditoException e){
                        if (jatekosok.size() > 2){
                            //2 jatekos eseten nincs ertelme visszaforditasrol beszelni, ennek ellenere a kartya kijatszhato
                            //jatekosok = JatekosokListaMegforditasa(jatekosok.indexOf(j), jatekosok);
                            jatekosok = JatekosokListaMegforditasa(j, jatekosok);
                        }

                        System.out.println(e.getMessage());

                        //Sortores
                        System.out.println();

                        continue;
                    }
                    catch(UgrasException | TamadasException e){
                        //Tamadasnal laphuzas nelkul vegeter a kor + a kovetkezo jatekos 2x kell, hogy huzzon / 2 kore van
                        //Ugrasnal laphuzas nelkul vegeter a kor
                        System.out.println(e.getMessage());

                        //Sortores
                        System.out.println();

                        break;
                    }

                    //Sortores
                    System.out.println();
                }while((!v.equals("H") || jatekosTeljesitendoKeresei.stream().anyMatch(x -> !x.getTeljesitve())) && !jatekosok.get(j).getFelrobbantE());
            }
        }while(jatekosok.stream().filter(x -> !x.getFelrobbantE()).count() != 1);

        //Gyoztes kivalasztasa, majd kiirasa
        Jatekos winner = jatekosok.stream().filter(x -> !x.getFelrobbantE()).findFirst().get();
        System.out.println("FINISH! - A győztes játékos: " + winner.getNev());

        //Gyoztes hozzaadasa adatbazishoz >> user + password modositasa szukseges lehet
        AdatbazisKezelo adatbazisKezelo = new AdatbazisKezelo("RobbanoCicak", "root", "");
        adatbazisKezelo.insertWinnerIntoScore(winner);

        //Sortores
        System.out.println();

        //Top 3 kiirasa
        dobogosok(adatbazisKezelo);
    }

    //Jatekos kartyat kijatszik
    public static void JatekosKartyatKijatszik(Jatekos j, KartyaPakli p, List<Jatekos> jatekosok, List<Teljesitendo> teljesitendok) throws TamadasException, UgrasException, VisszaforditoException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //Jatekos kartyainak kiirasa
        JatekosKartyainakKiirasa(j);

        int ind;
        do{
            System.out.print("Melyik sorszámú kártyád szeretnéd kijátszani: ");

            try{
                ind = (Integer.parseInt(br.readLine())) - 1;
            }
            catch (Exception e){
                ind = -1;
            }

            if(ind < 0 || ind > (j.getKartyakKezeben().size() - 1)){
                //Sortores
                System.out.println();

                System.out.println("Nem megfelelő sorszám.");

                //Sortores
                System.out.println();
            }
        }while(ind < 0 || ind > (j.getKartyakKezeben().size() - 1));

        Kartya k = j.getKartyakKezeben().get(ind);

        switch (k.getNev()){
            case "Hatástalanító kártya":
                System.out.println("Ezt a kártyát csak robbanó cica húzása esetén használhatod.");

                //Sortores
                System.out.println();
                break;

            case "Jövőbe látás":
                //Kartya eltavolitasa jatekos kezebol
                j.kartyaEltavolitasKezbol(k);

                //Felso 3 kartya kiirasa
                System.out.print("A pakli tetején lévő 3 kártya: ");
                for(int i = 0; i <= 2; i++){
                    try{
                        System.out.print(p.getKartyaPakli().get(i) + " / ");
                    }
                    catch (Exception e){
                        //Ha nincs mar tobb kartya, akkor nem tud mit kiirni
                    }
                }

                //Sortores
                System.out.println();
                break;

            case "Újrakeverés":
                //Kartya eltavolitasa jatekos kezebol
                j.kartyaEltavolitasKezbol(k);

                //Pakli megkeverese
                p.setKartyaPakli(p.pakliKeverese(p.getKartyaPakli()));
                System.out.println("Megkeverted a paklit.");

                //Sortores
                System.out.println();
                break;

            case "Szívesség":
                //Kartya eltavolitasa jatekos kezebol
                j.kartyaEltavolitasKezbol(k);

                Jatekos teljesitendoJatekosSzivesseg = JatekosJatekostValaszt(jatekosok, j);
                Teljesitendo teljesitendoSzivesseg = new Teljesitendo(j, teljesitendoJatekosSzivesseg, k, 1);
                teljesitendok.add(teljesitendoSzivesseg);

                System.out.println(teljesitendoJatekosSzivesseg.getNev() + " fog Neked kártyát adni, amint az Ő köre következik.");
                break;

            case "Szivárványt okádó macska (Macskártya)":

            case "Dinnyaú-macska (Macskártya)":

            case "Szőrös krumplimacska (Macskártya)":

            case "Szőrmacska (Macskártya)":

            case "Macskaja (Macskártya)":
                if(j.getKartyakKezeben().stream().filter(x -> x.getNev().equals(k.getNev())).count() < 2){
                    System.out.println("A Macskártyákat csak párosával lehet elhaszálni.");
                }
                else{
                    //Kartya eltavolitasa jatekos kezebol (2 db)
                    int talalatSzamlalo = 0;
                    int[] talaltIndexek = new int[2];

                    for(int i = 0; i < j.getKartyakKezeben().size(); i++){
                        if(j.getKartyakKezeben().get(i).getNev().equals(k.getNev())){
                            //j.kartyaEltavolitasKezbol(k);
                            talaltIndexek[talalatSzamlalo] = i;
                            talalatSzamlalo++;
                        }

                        if (talalatSzamlalo == 2){
                            break;
                        }
                    }

                    j.kartyaEltavolitasKezbol(talaltIndexek[0]);

                    //Az elso eltavolitas miatt csokken egyel az indexek szama, emiatt a -1
                    j.kartyaEltavolitasKezbol(talaltIndexek[1] - 1);

                    //Jatekos kivalasztasa, akitol a kartyat elveszi (nem lehet sajat maga)
                    Jatekos akitolKartyatElvesz = JatekosJatekostValaszt(jatekosok, j);

                    //Melyik sorszamu kartyajat veszi el
                    System.out.println(akitolKartyatElvesz.getNev() + " egyik kártyáját fogod elvenni, akinek összesen " + akitolKartyatElvesz.getKartyakKezeben().size() + " kártyája van.");

                    //Jatekos elveszi a kartyat a masiktol
                    JatekosKartyatElvesz(j, akitolKartyatElvesz);

                    //Sortores
                    System.out.println();
                }
                break;

            case "Visszafordító":
                //Kartya eltavolitasa jatekos kezebol
                j.kartyaEltavolitasKezbol(k);

                throw new VisszaforditoException("Visszafordító kártya kijátszva: a kör megfordul!");

            case "Ugrás":
                //Kartya eltavolitasa jatekos kezebol
                j.kartyaEltavolitasKezbol(k);

                throw new UgrasException("Ugrás kártya kijátszva - ebben a körben nem kell kártyát húznod.");

            case "Támadás":
                //Kartya eltavolitasa jatekos kezebol
                j.kartyaEltavolitasKezbol(k);

                //Meg elo jatekosok kigyujtese
                List<Jatekos> megEloJatekosok = jatekosok.stream().filter(x -> !x.getFelrobbantE()).collect(Collectors.toList());

                Jatekos teljesitendoJatekosTamadas;
                try{
                    //A kovetkezo indexu jatekos lesz megtamadva
                    teljesitendoJatekosTamadas = megEloJatekosok.get(megEloJatekosok.indexOf(j) + 1);
                }
                catch (Exception e){
                    //Ha a listan kivulre mutatunk, akkor a 0. jatekos a kovetkezo
                    teljesitendoJatekosTamadas = megEloJatekosok.get(0);
                }

                //Teljesitendo feladat letrehozasa
                Teljesitendo teljesitendoTamadas = new Teljesitendo(j, teljesitendoJatekosTamadas, k, 2);
                teljesitendok.add(teljesitendoTamadas);

                throw new TamadasException("Támadás kártya kijátszva - ebben a körben nem kell kártyát húznod.");
        }
    }

    //Jatekos elvesz egy kartyat egy masik jatekostol
    public static void JatekosKartyatElvesz(Jatekos akiKartyatElvesz, Jatekos akitolKartyatElvesz){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //Sortores
        System.out.println();

        int elvevendoInd;
        do{
            System.out.print("Kártya sorszáma: ");
            try{
                elvevendoInd = (Integer.parseInt(br.readLine())) - 1;
            }
            catch (Exception e){
                elvevendoInd = -1;
            }

            if(elvevendoInd < 0 || elvevendoInd > (akitolKartyatElvesz.getKartyakKezeben().size() - 1)){
                //Sortores
                System.out.println();

                System.out.println("Nem megfelelő sorszám.");

                //Sortores
                System.out.println();
            }
        }while(elvevendoInd < 0 || elvevendoInd > (akitolKartyatElvesz.getKartyakKezeben().size() - 1));

        Kartya kivalasztottKartya = akitolKartyatElvesz.getKartyakKezeben().get(elvevendoInd);
        akiKartyatElvesz.setKartyakKezeben(kivalasztottKartya);
        akitolKartyatElvesz.kartyaEltavolitasKezbol(kivalasztottKartya);

        //Sortores
        System.out.println();

        System.out.println("A kártya mely gazdát cserélt: " + kivalasztottKartya.getNev());
    }

    //Jatekos kartyat huz
    public static void JatekosKartyatHuz(Jatekos j, KartyaPakli p){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        List<String> valaszLehetosegek = Arrays.asList("F", "R");

        Kartya huzottKartya = p.huzas();

        if(!huzottKartya.getNev().equals("BUMM (Robbanó cica)")){
            //A huzott kartya nem robbanocica
            j.setKartyakKezeben(huzottKartya);
            System.out.println("A húzott kártyád: " + huzottKartya.getNev());
        }
        else{
            //A huzott kartya robbanocica
            if(j.getKartyakKezeben().stream().anyMatch(x -> x.getNev().equals("Hatástalanító kártya"))){
                //A jatekosnak van hatastalanito kartyaja
                System.out.println(huzottKartya.getNev() + " - de szerencsére volt hatástalanító kártyád. Mit szeretnél tenni?");

                Kartya jatekosHatastalanitoKartyaja = j.getKartyakKezeben().stream().filter(x -> x.getNev().equals("Hatástalanító kártya")).findFirst().get();

                //Sortores
                System.out.println();

                String v;
                do{
                    System.out.print("F - a robbanó cicát legfelülre tenni / R - a robbanó cicát random helyre betenni - ");

                    try {
                        v = br.readLine().toUpperCase();
                    }
                    catch (Exception e){
                        v = "";
                    }

                    if(v.length() == 0){
                        //Sortores
                        System.out.println();

                        System.out.println("Add meg, mit szeretnél tenni.");

                        //Sortores
                        System.out.println();
                    }
                    else if(!valaszLehetosegek.contains(v)){
                        //Sortores
                        System.out.println();

                        System.out.println("A megadott lehetőségek közül válassz.");

                        //Sortores
                        System.out.println();
                    }
                }while(v.length() == 0 || !valaszLehetosegek.contains(v));

                switch (v) {
                    case "F" -> p.setKartyaPakli(jatekosHatastalanitoKartyaja, huzottKartya);
                    case "R" -> p.setKartyaPakli(jatekosHatastalanitoKartyaja, huzottKartya, true);
                }

                //Jatekos hatastalanito kartyajat visszakevertuk a pakliba
                j.kartyaEltavolitasKezbol(jatekosHatastalanitoKartyaja);
            }
            else{
                //A jatekosnak nincs hatastalanito kartyaja
                System.out.println(huzottKartya.getNev() + " - sajnos nincs hatástalanító kártyád, így kiestél.");
                j.setFelrobbantE(true);

                //Ha kiesik egy jatekos, egy robbano cicat ki kell venni a paklibol
                p.kartyaEltavolitasaPaklibol(huzottKartya);
            }
        }
    }

    //Jatekos kartyainak kiirasa
    public static void JatekosKartyainakKiirasa(Jatekos j){
        //Sortores
        System.out.println();

        System.out.print("Kártyáid: ");
        for(int i = 0; i < j.getKartyakKezeben().size(); i++){
            System.out.print("(" + (i + 1) + ".) " +j.getKartyakKezeben().get(i).getNev() + " / ");
        }

        //Sortores
        System.out.println();
    }

    //Jatekosok szamanak bekerese
    public static void JatekosokBekerese(List<Jatekos> jatekosok){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String nev;
        int db;

        do {
            System.out.print("Játékosok száma [2, 5]: ");
            try{
                db = Integer.parseInt(br.readLine());
            }
            catch (Exception e){
                db = 0;
            }

            if(db < 2 || db > 5){
                //Sortores
                System.out.println();

                System.out.println("A játékosok számának minimum 2-nek, maximum 5-nek kell lennie.");

                //Sortores
                System.out.println();
            }
        }while(db < 2 || db > 5);

        //Sortores
        System.out.println();

        //TODO: 2 azonos nevu jatekos ne engedjen
        for(int i = 0; i <= db - 1; i++){
            do{
                System.out.print((i + 1) + ". játékos neve: ");
                try{
                    nev = br.readLine();
                }
                catch (Exception e){
                    nev = "";
                }

                if(nev.length() == 0){
                    //Sortores
                    System.out.println();

                    System.out.println("Adja meg a(z) " + (i + 1) + ". játékos nevét.");

                    //Sortores
                    System.out.println();
                }
            }while(nev.length() == 0);

            jatekosok.add(new Jatekos(nev));
        }
    }

    //Jatekos kivalasztja kitol akar kartyat huzni
    public static Jatekos JatekosJatekostValaszt(List<Jatekos> jatekosok, Jatekos kezdemenyezoJatekos){
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        //List<String> jatekosokNevei = jatekosok.stream().map(Jatekos::getNev).collect(Collectors.toList());
        Jatekos jatekosAkitolElvesz;

        do{
            //Sortores
            System.out.println();

            System.out.print("Kitől szeretnél kártyát elvenni: ");
            try{
                String nev = br.readLine();
                jatekosAkitolElvesz = jatekosok.stream().filter(x -> x.getNev().equals(nev)).findFirst().get();
            }
            catch(Exception e){
                jatekosAkitolElvesz = null;
            }

            if(jatekosAkitolElvesz == null){
                //Sortores
                System.out.println();

                System.out.println("Nincs ilyen nevű játékos! Kérlek, válassz valaki mást.");

                //Sortores
                System.out.println();
            }
            else if(jatekosAkitolElvesz.getFelrobbantE()){
                //Sortores
                System.out.println();

                System.out.println(jatekosAkitolElvesz.getNev() + " sajnos már felrobbant... :( Kérlek, válassz valaki mást.");

                //Sortores
                System.out.println();
            }
            else if(jatekosAkitolElvesz.equals(kezdemenyezoJatekos)){
                //Sortores
                System.out.println();

                System.out.println("Saját magadtól nem kérhetsz kártyát! Kérlek, válassz valaki mást.");

                //Sortores
                System.out.println();
            }
        }while(jatekosAkitolElvesz == null || jatekosAkitolElvesz.getFelrobbantE() || jatekosAkitolElvesz.equals(kezdemenyezoJatekos));

        return jatekosAkitolElvesz;
    }

    //Visszafordito kartya altal hasznalt megforditas
    public static List<Jatekos> JatekosokListaMegforditasa(int forditoIndex, List<Jatekos> j){
        List<Jatekos> forditottJ = new ArrayList<>();

        //Biztos, hogy a jatekosok szama a [2, 5] zart intervallumon lesz
        /*if(j.size() == 2){
            if(forditoIndex == 0){
                forditottJ.add(forditoIndex, j.get(forditoIndex));
                forditottJ.add(1, j.get(1));
            }
            else{
                forditottJ.add(0, j.get(0));
                forditottJ.add(forditoIndex, j.get(forditoIndex));
            }
        }*/
        if(j.size() == 3){
            if(forditoIndex == 0){
                forditottJ.add(forditoIndex, j.get(forditoIndex));
                forditottJ.add(1, j.get(2));
                forditottJ.add(2, j.get(1));
            }
            else if(forditoIndex == 1){
                forditottJ.add(0, j.get(2));
                forditottJ.add(forditoIndex, j.get(forditoIndex));
                forditottJ.add(2, j.get(0));
            }
            else{
                forditottJ.add(0, j.get(1));
                forditottJ.add(1, j.get(0));
                forditottJ.add(forditoIndex, j.get(forditoIndex));
            }
        }
        else if(j.size() == 4){
            if(forditoIndex == 0){
                forditottJ.add(forditoIndex, j.get(forditoIndex));
                forditottJ.add(1, j.get(3));
                forditottJ.add(2, j.get(2));
                forditottJ.add(3, j.get(1));
            }
            else if(forditoIndex == 1){
                forditottJ.add(0, j.get(2));
                forditottJ.add(forditoIndex, j.get(forditoIndex));
                forditottJ.add(2, j.get(0));
                forditottJ.add(3, j.get(3));
            }
            else if(forditoIndex == 2){
                forditottJ.add(0, j.get(0));
                forditottJ.add(1, j.get(3));
                forditottJ.add(forditoIndex, j.get(forditoIndex));
                forditottJ.add(3, j.get(1));
            }
            else{
                forditottJ.add(0, j.get(2));
                forditottJ.add(1, j.get(1));
                forditottJ.add(2, j.get(0));
                forditottJ.add(forditoIndex, j.get(forditoIndex));
            }
        }
        else{
            if(forditoIndex == 0){
                forditottJ.add(forditoIndex, j.get(forditoIndex));
                forditottJ.add(1, j.get(4));
                forditottJ.add(2, j.get(3));
                forditottJ.add(3, j.get(2));
                forditottJ.add(4, j.get(1));
            }
            else if(forditoIndex == 1){
                forditottJ.add(0, j.get(2));
                forditottJ.add(forditoIndex, j.get(forditoIndex));
                forditottJ.add(2, j.get(0));
                forditottJ.add(3, j.get(4));
                forditottJ.add(4, j.get(3));
            }
            else if(forditoIndex == 2){
                forditottJ.add(0, j.get(4));
                forditottJ.add(1, j.get(3));
                forditottJ.add(forditoIndex, j.get(forditoIndex));
                forditottJ.add(3, j.get(1));
                forditottJ.add(4, j.get(0));
            }
            else if(forditoIndex == 3){
                forditottJ.add(0, j.get(1));
                forditottJ.add(1, j.get(0));
                forditottJ.add(2, j.get(4));
                forditottJ.add(forditoIndex, j.get(forditoIndex));
                forditottJ.add(4, j.get(2));
            }
            else{
                forditottJ.add(0, j.get(3));
                forditottJ.add(1, j.get(2));
                forditottJ.add(2, j.get(1));
                forditottJ.add(3, j.get(0));
                forditottJ.add(forditoIndex, j.get(forditoIndex));
            }
        }

        return forditottJ;
    }

    //Robbano cica huzasanak eselye
    public static double RobbanoCicaEselye(KartyaPakli p){
        double robbanoCicakDb = p.getKartyaPakli().stream().filter(x -> x.getNev().equals("BUMM (Robbanó cica)")).count();
        return (robbanoCicakDb * 100 / p.getKartyaPakli().size());
    }

    //Top 3 jatekos nevenek es pontszamanak kiirasa
    public static void dobogosok(AdatbazisKezelo ab){
        ab.dobogosok();
    }
}
