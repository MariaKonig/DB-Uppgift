public class Sko {
   private int id;
   private int storlek;
   private Märke märke;
   private String färg;
   private int pris;

    public Sko(){};
    public Sko(int id, int storlek, Märke märke, String färg, int pris) {
        this.id = id;
        this.storlek = storlek;
        this.märke = märke;
        this.färg = färg;
        this.pris = pris;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStorlek() {
        return storlek;
    }

    public void setStorlek(int storlek) {
        this.storlek = storlek;
    }

    public Märke getMärke() {
        return märke;
    }

    public void setMärke(Märke märke) {
        this.märke = märke;
    }

    public String getFärg() {
        return färg;
    }

    public void setFärg(String färg) {
        this.färg = färg;
    }

    public int getPris() {
        return pris;
    }

    public void setPris(int pris) {
        this.pris = pris;
    }
}
