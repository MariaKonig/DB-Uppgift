public class Kund {

    private int id;
    private String namn;
    private String efternamn;
    private String adress;
    private String ort;
    private String mail;
    private String lösenord;

    public Kund(){}
    public Kund(int id, String namn, String efternamn,String adress, String ort, String mail, String lösenord) {
        this.id = id;
        this.namn = namn;
        this.efternamn = efternamn;
        this.adress = adress;
        this.ort = ort;
        this.mail = mail;
        this.lösenord = lösenord;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNamn() {
        return namn;
    }

    public void setNamn(String namn) {
        this.namn = namn;
    }

    public String getEfternamn() {
        return efternamn;
    }

    public void setEfternamn(String efternamn) {
        this.efternamn = efternamn;
    }

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getLösenord() {
        return lösenord;
    }

    public void setLösenord(String lösenord) {
        this.lösenord = lösenord;
    }
}
