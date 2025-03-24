package hu.szamalk.modell;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


public class Szak {
    UUID id;
    private String nev;
    private List<Tantargy> tantargyak;

    public Szak(String nev){
        id = UUID.randomUUID();
        this.nev = nev;
        this.tantargyak = new ArrayList<>();
        try {
            FileInputStream fs = new FileInputStream("tantargyak.txt");
            Scanner sc = new Scanner(fs);
            while (sc.hasNext()){
                String[] fields = sc.nextLine().split(";");
               try {
                   tantargyak.add(
                           new Tantargy(
                                   fields[0],
                                   Integer.parseInt(fields[1]),
                                   fields[2],
                                   fields[3],
                                   Integer.parseInt(fields[4]),
                                   fields[5]
                           )
                   );
               }
               catch (NumberFormatException exe){
                   System.out.println("HIBA: Az egyik sor egyik mezőjének integerré alakítása sikertelen");
               } catch (Exception e) {
                   System.out.println(e.getMessage());
               }
            }
        } catch (FileNotFoundException e) {
            System.out.println("HIBA: A szöveges fájl nem található.");
        }

    }

    public void szakKiirasa(){
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("targyak.dat"));
            oos.writeUnshared(tantargyak);
            oos.close();
        } catch (Exception e) {
            System.out.println("HIBA: A tantárgy lista kiírása a targyak.dat fájlba sikertelen.");
        }
    }

    public void szakBeolvasasa(){
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream("targyak.dat"));
            tantargyak = (List<Tantargy>)ois.readUnshared();
            ois.close();
        } catch (Exception e) {
            System.out.println("HIBA: targyak.dat beolvasása sikertelen.");
        }
    }

    public List<Tantargy> getTargyak(){
        return getTantargyListaMasolat();
    }

    public List<Tantargy> getTargyakNevSzerint(){
        tantargyak.sort(Tantargy.rendez(Tantargy.Rendez.NEV));
        return getTantargyListaMasolat();
    }

    public List<Tantargy> getTargyakKreditSzerint(){
        tantargyak.sort(Tantargy.rendez(Tantargy.Rendez.KREDIT));
        return getTantargyListaMasolat();
    }

    private List<Tantargy> getTantargyListaMasolat(){
        Tantargy[] masolat = new Tantargy[tantargyak.size()];
        for (int i = 0; i< masolat.length; i++){
            masolat[i] = tantargyak.get(i);
        }
        List<Tantargy> tmasolat = new ArrayList<>();
        for (Tantargy targy : masolat){
            try {
                tmasolat.add((Tantargy)targy.clone());
            } catch (Exception e) {
                // throw new RuntimeException(e);
                System.out.println(e.getMessage());
                System.out.println("Hiba: Az egyik objektum nem felelt meg a feltételeknek.");
            }
        }
        return tmasolat;
    }

    public void statisztika(){
        String statisztika = "";
        Set<Tantargy> kulon = new HashSet<>();
        double sum = 0;
        int min = Integer.MAX_VALUE, max=Integer.MIN_VALUE;
        String melyikmin = "", melyikmax = "";
        for (Tantargy targy : tantargyak){
            int mmin = Math.min(targy.getKredit(), min);
            int mmax = Math.max(targy.getKredit(), max);
            melyikmin = mmin != min ? targy.getNev() : melyikmin;
            melyikmax = mmin != max ? targy.getNev() : melyikmax;
            min = mmin;
            max = mmax;
            sum += targy.getKredit();
            kulon.add(targy);
        }
        statisztika += "különböző tárgy: " + kulon.size() + "\n\n";
        statisztika += "összes kredit: " + sum + "\n\n";

        statisztika += "min kredit: " + melyikmin + ", " + min+ "\n";
        statisztika += "max kredit: " + melyikmax + ", " + max+ "";


        try {
            Files.writeString(Path.of("statisztika.txt"), statisztika);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
