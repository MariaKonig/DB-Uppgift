import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class Rapport {
    final Repository r = new Repository();
    final List<Order> orderList = r.getAllOrders();
    final List<Kund> kundLista = r.getKunder();
    Scanner scan = new Scanner(System.in);

    KundSökningInterface färgSök = (sko, o) -> sko.getFärg().equalsIgnoreCase(o);
    KundSökningInterface märkeSök = (sko, o) -> sko.getMärke().getNamn().equalsIgnoreCase(o);
    KundSökningInterface storlekSök = (sko, o) -> sko.getStorlek() == Integer.parseInt(o) ;

    /* 1.  En rapport som listar alla kunder, med namn och adress, som har handlat varor i en viss
           storlek, av en viss färg eller av ett visst märke. Storlek, färg och märke är inparametrar och
           ska kunna skrivas in från konsolen och varieras från ditt Java-program.  */
    public void sökKunder(String o, KundSökningInterface ksi){
        List<Order> matchandeOrdrar = orderList.stream().filter(order -> order.getSkor().stream().anyMatch(sko -> ksi.sök(sko,o))).toList();
        Set<Kund> matchandeKunder = matchandeOrdrar.stream().map(Order::getKund).collect(Collectors.toSet());
        matchandeKunder.forEach(Kund -> System.out.println(Kund.getNamn() +" "+ Kund.getEfternamn() +" " + Kund.getAdress()));

    }

    /* #2 En rapport som listar alla kunder och hur många ordrar varje kund har lagt. Skriv ut namn
          och sammanlagda antalet ordrar för varje kund. */
    public void hämtaOrderAntal(){
        Map<String, Integer> kundOrderAntal = new HashMap<>();

        kundLista.forEach(kund -> {

            String namn = kund.getNamn() +" " + kund.getEfternamn();
            int orderAntal = orderList.stream().filter(order -> order.getKund().getId()==kund.getId()).toList().size();
            kundOrderAntal.put(namn, orderAntal);
        });
        kundOrderAntal.forEach((namn,antal) -> System.out.println(namn + " " + antal ));
    }

    /* #3 En rapport som listar alla kunder och hur mycket pengar varje kund, sammanlagt, har
          beställt för. Skriv ut varje kunds namn och summa.  */
    public void hämtaKundTotalSumma(){
        Map<String, Integer> kundTotalSumma = new HashMap<>();

        kundLista.forEach(kund -> {

            String namn = kund.getNamn() +" " + kund.getEfternamn();
            List<Order> orders = orderList.stream().filter(order -> order.getKund().getId()==kund.getId()).toList();
            List<Integer> orderPriser = orders.stream().map(order -> order.getSkor().stream().map(Sko::getPris).reduce(0, (sum, pris) -> sum + pris)).toList();
            int totalSum = orderPriser.stream().reduce(0, (sum, pris) -> sum+pris);
            kundTotalSumma.put(namn, totalSum);
        });
        kundTotalSumma.forEach((namn,summa) -> System.out.println(namn + " " + summa ));
    }


    public void rapportVal(int val) {
        switch (val){
            case 1:{
                while(true){
                    System.out.println("\nVÄLJ ATTRIBUT: FÄRG, STORLEK eller MÄRKE");
                    String attribut = scan.next();
                    System.out.println("\nSKRIV " + attribut.toUpperCase() + " ATT SÖKA PÅ:");
                    String sökOrd = scan.next();

                    System.out.println("RAPPORT 1:");
                    if(attribut.equalsIgnoreCase("färg")){
                        sökKunder(sökOrd, färgSök);
                    } else if (attribut.equalsIgnoreCase("Märke")) {
                        sökKunder(sökOrd, märkeSök);
                    } else if (attribut.equalsIgnoreCase("Storlek")) {
                        sökKunder(sökOrd, storlekSök);
                    }else break;

                    System.out.println("\nVILL DU SÖKA IGEN? Y/N");
                    if(scan.next().equalsIgnoreCase("N")) break;
                }
                break;
            }
            case 2:
                System.out.println("RAPPORT 2:");
                hämtaOrderAntal();
                break;
            case 3:
                System.out.println("RAPPORT 3:");
                hämtaKundTotalSumma();
                break;
        };
    }
    public Rapport() throws IOException {

        do {
            System.out.println(
                    """
                            VÄLJ RAPPORT 1-3
                            1: Rapport som listar kunder som har handlat varor i en viss märke/färg/storlek
                            2: Rapport som listar alla kunder och hur många ordrar varje kund har lagt
                            3: Rapport som listar alla kunder och hur mycket pengar varje kund har beställt för""");

            rapportVal(scan.nextInt());

            System.out.println("\nVILL DU SE EN NY RAPPORT? Y/N");
        } while (!scan.next().equalsIgnoreCase("N"));

    }
     public static void main(String[] args) throws IOException {
        Rapport rapp = new Rapport();
    }
}
