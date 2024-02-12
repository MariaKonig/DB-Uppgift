import java.util.ArrayList;
import java.util.List;

public class Order {
    int id;
    Kund kund;
    List<Sko> skor = new ArrayList<>();

    public Order(){}
    public Order(int id, Kund kund, List<Sko> skor) {
        this.id = id;
        this.kund = kund;
        this.skor = skor;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Kund getKund() {
        return kund;
    }

    public void setKund(Kund kund) {
        this.kund = kund;
    }

    public List<Sko> getSkor() {
        return skor;
    }

    public void setSkor(List<Sko> skor) {
        this.skor = skor;
    }
}
