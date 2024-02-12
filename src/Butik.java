import java.io.IOException;
import java.util.List;
import java.util.Scanner;


public class Butik  {

    final Repository r = new Repository();
    Scanner scan = new Scanner(System.in);

    public Butik() throws IOException {
        final int kundNr;
        final int skoStorlek;
        int inteEttOrderNr = -1;
        final int orderNr;
        final List<Kund> kundLista = r.getKunder();
        final List<Sko> skoLista = r.getSkor();
        final List<Order> orderLista;

        while (true) {
        System.out.println("Skriv in din mail-adress:");
        String mail = scan.next();
        System.out.println("Skriv in ditt lösenord:");
        String lösen = scan.next();

        if (kundLista.stream().filter(k -> k.getMail().equalsIgnoreCase(mail) && k.getLösenord().equalsIgnoreCase(lösen)).findAny().isPresent()) {
            kundNr = kundLista.stream().filter(k -> k.getMail().equalsIgnoreCase(mail) && k.getLösenord().equalsIgnoreCase(lösen)).findAny().get().getId();

            System.out.println("Skriv in en skostorlek");
            skoStorlek = scan.nextInt();

            skoLista.stream().filter(sko -> sko.getStorlek() == skoStorlek).forEach(sko -> System.out.println(skoLista.indexOf(sko) + " " + sko.getMärke().getNamn() + " " + sko.getFärg() + " " + sko.getPris() + ":-"));
            break;
        } else {
            System.out.println("Fel användarnamn eller lösenord");
        }
    }
        while (true) {
        System.out.println("välj en sko att lägga till");
        inteEttOrderNr = r.addSko(skoLista.get(scan.nextInt()).getId(), kundNr, inteEttOrderNr);
        if (inteEttOrderNr != -1) {
            orderNr = inteEttOrderNr;
            break;
        }
    }

        while (true) {

        System.out.println("vill du lägga till något mer? Y/N");
        if (!scan.next().equalsIgnoreCase("y")) {
            break;
        }
        System.out.println("välj en sko att lägga till");
        int val = scan.nextInt();
        r.addSko(skoLista.get(val).getId(), kundNr, orderNr);

    }

    orderLista = r.getAllOrders();

    Order o = orderLista.stream().filter(order -> order.id==orderNr).findAny().get();
        System.out.println("Varukorg:");
        o.getSkor().forEach(sko -> System.out.println(sko.getMärke().getNamn()+ " "+ sko.getStorlek()+" "+sko.getFärg()+" "+ sko.getPris()+":-"));
        System.out.println("Välkommen åter!");

}


    public static void main(String[] args) throws IOException {
        Butik shoeStore = new Butik();
    }

}
