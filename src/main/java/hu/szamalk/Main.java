package hu.szamalk;

import hu.szamalk.modell.Szak;
import hu.szamalk.modell.Tantargy;

public class Main {
    public static void main(String[] args) {
        Szak szak = new Szak("Név");
        System.out.println("Hello world!");
        szak.szakKiirasa();
        szak.szakKiirasa();
        szak.szakBeolvasasa();
        szak.szakBeolvasasa();
        szak.statisztika();
        for (Tantargy targy : szak.getTargyakKreditSzerint()){
            System.out.println(targy.getKredit());
        }
    }
}