package hu.szamalk.modell;

import java.io.Serializable;
import java.text.Collator;
import java.util.Comparator;
import java.util.Objects;

public class Tantargy implements Serializable, Cloneable {
    // NÉV;KREDIT;TANÁR1;TANÁR2;FÉLÉV;CSAK_VIZSGA
    private String nev;
    private int kredit;
    private String tanar1;
    private String tanar2;
    private int felev;
    private String csakVizsga;

    public Tantargy(String nev, int kredit, String tanar1, String tanar2, int felev, String csakVizsga) throws Exception {
        setNev(nev);
        setKredit(kredit);
        setTanar1(tanar1);
        setTanar2(tanar2);
        setFelev(felev);
        setCsakVizsga(csakVizsga);
    }

    public String getNev() {
        return nev;
    }

    public int getKredit() {
        return kredit;
    }

    public String getTanar1() {
        return tanar1;
    }

    public String getTanar2() {
        return tanar2;
    }

    public int getFelev() {
        return felev;
    }

    public String getCsakVizsga() {
        return csakVizsga;
    }

    public void setNev(String nev) throws Exception {
        this.nev = nev;
        if(nev.length() < 4) throw new Exception("Hiba: A név nem lehet rövidebb 4 karakternél.");
    }

    public void setKredit(int kredit) {
        this.kredit = kredit;
    }

    public void setTanar1(String tanar1) {
        this.tanar1 = tanar1;
    }

    public void setTanar2(String tanar2) {
        this.tanar2 = tanar2;
        if(this.tanar1.equals(tanar2)) this.tanar2 = "-";
    }

    public void setFelev(int felev) {
        this.felev = felev;
    }

    public void setCsakVizsga(String csakVizsga) {
        this.csakVizsga = csakVizsga;
    }

    public static CustomComparator rendez(Rendez rendez){
        return new CustomComparator(rendez);
    }

    private static class CustomComparator implements Comparator<Tantargy>{
        Rendez rendez;

        public CustomComparator(Rendez rendez){
            this.rendez = rendez;
        }

        @Override
        public int compare(Tantargy o1, Tantargy o2) {
            switch (rendez){
                case NEV:
                    Collator coll = Collator.getInstance();
                    return coll.compare(o1.nev, o2.nev);
                case KREDIT:
                    return o1.kredit - o2.kredit;
            }
            return 0;
        }
    }

    public static enum Rendez{
        NEV,
        KREDIT
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tantargy tantargy = (Tantargy) o;
        return Objects.equals(nev, tantargy.nev) && Objects.equals(kredit, tantargy.kredit) && Objects.equals(felev, tantargy.felev);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nev, kredit, felev);
    }

    @Override
    public String toString() {
        return "Tantargy{" +
                "nev='" + nev + '\'' +
                ", kredit=" + kredit +
                ", tanar1='" + tanar1 + '\'' +
                ", tanar2='" + tanar2 + '\'' +
                ", felev=" + felev +
                ", csakVizsga='" + csakVizsga + '\'' +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        try {
            return new Tantargy(nev, kredit, tanar1, tanar2, felev, csakVizsga);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
