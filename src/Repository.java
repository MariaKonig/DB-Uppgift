import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Repository {

    List<Sko> getSkor() throws IOException {

        List<Sko> skor = new ArrayList<>();

        Properties p = new Properties();
        p.load(new FileInputStream("src/Settings.properties"));
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "select sko.id, storlek, pris, märkeId, färg.namn as färg from sko " +
                             "inner join modell on sko.modellId = modell.id " +
                             "inner join färg on modell.färgId = färg.id ")


        ) {
            while (rs.next()) {

                int id = rs.getInt("id");
                int storlek = rs.getInt("storlek");
                String färg = rs.getString("färg");
                int pris = rs.getInt("pris");
                int märkeId = rs.getInt("märkeId");
                Märke märke = getMärke().stream().filter(m -> m.getId() == märkeId).findFirst().get();

                skor.add(new Sko(id, storlek, märke, färg, pris));
            }
            return skor;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    int addSko(int skoNr, int kundNr, int orderNr) throws IOException {
        ResultSet rs = null;

        Properties p = new Properties();
        p.load(new FileInputStream("src/Settings.properties"));
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             CallableStatement stmt = con.prepareCall("call VarukorgSP( ?, ?, ?)");


        ) {
            //Lägg till så man kan fortsätta välja skor med samma orderNr
            stmt.setInt(1, skoNr);
            stmt.setInt(2, kundNr);
            stmt.registerOutParameter(3, Types.INTEGER);
            stmt.setInt(3, orderNr);
            rs = stmt.executeQuery();

            while (rs != null && rs.next()) {
                System.out.println(rs.getString("meddelande"));
            }

            return stmt.getInt (3) ;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    List<Kund> getKunder() throws IOException {

        List<Kund> kundLista = new ArrayList<>();

        Properties p = new Properties();
        p.load(new FileInputStream("src/Settings.properties"));
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select id, förnamn, efternamn, adress, ort, mail, lösenord from Kund ");


        ) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Kund temp = new Kund();
                int id = rs.getInt("id");
                temp.setId(id);

                String förnamn = rs.getString("förnamn");
                temp.setNamn(förnamn);

                String efternamn = rs.getString("efternamn");
                temp.setEfternamn(efternamn);

                String adress = rs.getString("adress");
                temp.setAdress(adress);

                String ort = rs.getString("ort");
                temp.setOrt(ort);

                String mail = rs.getString("mail");
                temp.setMail(mail);

                String lösenord = rs.getString("lösenord");
                temp.setLösenord(lösenord);

                kundLista.add(temp);
            }
            return kundLista;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    List<Märke> getMärke() throws IOException {

        List<Märke> märkeLista = new ArrayList<>();

        Properties p = new Properties();
        p.load(new FileInputStream("src/Settings.properties"));
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select * from Märke ");


        ) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Märke temp = new Märke();
                int id = rs.getInt("id");
                temp.setId(id);

                String namn = rs.getString("namn");
                temp.setNamn(namn);

                märkeLista.add(temp);
            }
            return märkeLista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    List<Order> getAllOrders() throws IOException {

        final List<Order> orderLista = new ArrayList<>();
        final List<Kund> kundLista = getKunder();
        final List<Sko> skoLista = getSkor();

        Properties p = new Properties();
        p.load(new FileInputStream("src/Settings.properties"));
        try (Connection con = DriverManager.getConnection(
                p.getProperty("connectionString"),
                p.getProperty("name"),
                p.getProperty("password"));
             PreparedStatement stmt = con.prepareStatement("select id, kundId from Beställning");
             PreparedStatement stmt2 = con.prepareStatement("select skoId, beställningId from beställningsMappning")
        ) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Order temp = new Order();
                int id = rs.getInt("id");
                temp.setId(id);
                int kundId = rs.getInt("kundId");
                temp.setKund(kundLista.stream().filter(kund -> kund.getId()==kundId).findFirst().get());


                orderLista.add(temp);
            }
            ResultSet rs2 = stmt2.executeQuery();
            while(rs2.next()){
                int skoId = rs2.getInt("skoId");
                int beställningId = rs2.getInt("beställningId");
                orderLista.stream().filter(order -> order.id==beställningId).findFirst().get().getSkor().add(skoLista.stream().filter(sko -> sko.getId()==skoId).findFirst().get());

            }

            return orderLista;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
