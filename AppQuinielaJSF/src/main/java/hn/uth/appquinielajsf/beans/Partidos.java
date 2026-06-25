package hn.uth.appquinielajsf.beans;

import hn.uth.appquinielajsf.data.Partido;
import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Named("PartidosBean")
@SessionScoped
public class Partidos implements Serializable {
    private List<Partido> partidos;
    private Partido partido;

    public Partidos() {
        this.partidos = new ArrayList<>();
        this.partido = new Partido();
        cargarPartidosMundial2026();
    }

    private void cargarPartidosMundial2026() {
        partidos.add(new Partido("Mexico", "South Africa", crearFechaHonduras(2026, 6, 11, 13, 0), 0, 0));
        partidos.add(new Partido("South Korea", "Czechia", crearFechaHonduras(2026, 6, 11, 20, 0), 0, 0));
        partidos.add(new Partido("Canada", "Bosnia-Herzegovina", crearFechaHonduras(2026, 6, 12, 13, 0), 0, 0));
        partidos.add(new Partido("United States", "Paraguay", crearFechaHonduras(2026, 6, 12, 19, 0), 0, 0));
        partidos.add(new Partido("Qatar", "Switzerland", crearFechaHonduras(2026, 6, 13, 13, 0), 0, 0));
        partidos.add(new Partido("Brazil", "Morocco", crearFechaHonduras(2026, 6, 13, 16, 0), 0, 0));
        partidos.add(new Partido("Haiti", "Scotland", crearFechaHonduras(2026, 6, 13, 19, 0), 0, 0));
        partidos.add(new Partido("Australia", "Türkiye", crearFechaHonduras(2026, 6, 13, 22, 0), 0, 0));
        partidos.add(new Partido("Germany", "Curaçao", crearFechaHonduras(2026, 6, 14, 11, 0), 0, 0));
        partidos.add(new Partido("Netherlands", "Japan", crearFechaHonduras(2026, 6, 14, 14, 0), 0, 0));
        partidos.add(new Partido("Ivory Coast", "Ecuador", crearFechaHonduras(2026, 6, 14, 17, 0), 0, 0));
        partidos.add(new Partido("Sweden", "Tunisia", crearFechaHonduras(2026, 6, 14, 20, 0), 0, 0));
        partidos.add(new Partido("Spain", "Cape Verde", crearFechaHonduras(2026, 6, 15, 10, 0), 0, 0));
        partidos.add(new Partido("Belgium", "Egypt", crearFechaHonduras(2026, 6, 15, 13, 0), 0, 0));
        partidos.add(new Partido("Saudi Arabia", "Uruguay", crearFechaHonduras(2026, 6, 15, 16, 0), 0, 0));
        partidos.add(new Partido("Iran", "New Zealand", crearFechaHonduras(2026, 6, 15, 19, 0), 0, 0));
        partidos.add(new Partido("France", "Senegal", crearFechaHonduras(2026, 6, 16, 13, 0), 0, 0));
        partidos.add(new Partido("Iraq", "Norway", crearFechaHonduras(2026, 6, 16, 16, 0), 0, 0));
        partidos.add(new Partido("Argentina", "Algeria", crearFechaHonduras(2026, 6, 16, 19, 0), 0, 0));
        partidos.add(new Partido("Austria", "Jordan", crearFechaHonduras(2026, 6, 16, 22, 0), 0, 0));
        partidos.add(new Partido("Portugal", "Congo DR", crearFechaHonduras(2026, 6, 17, 11, 0), 0, 0));
        partidos.add(new Partido("England", "Croatia", crearFechaHonduras(2026, 6, 17, 14, 0), 0, 0));
        partidos.add(new Partido("Ghana", "Panama", crearFechaHonduras(2026, 6, 17, 17, 0), 0, 0));
        partidos.add(new Partido("Uzbekistan", "Colombia", crearFechaHonduras(2026, 6, 17, 20, 0), 0, 0));
        partidos.add(new Partido("Czechia", "South Africa", crearFechaHonduras(2026, 6, 18, 10, 0), 0, 0));
        partidos.add(new Partido("Switzerland", "Bosnia-Herzegovina", crearFechaHonduras(2026, 6, 18, 13, 0), 0, 0));
        partidos.add(new Partido("Canada", "Qatar", crearFechaHonduras(2026, 6, 18, 16, 0), 0, 0));
        partidos.add(new Partido("Mexico", "South Korea", crearFechaHonduras(2026, 6, 18, 19, 0), 0, 0));
        partidos.add(new Partido("United States", "Australia", crearFechaHonduras(2026, 6, 19, 13, 0), 0, 0));
        partidos.add(new Partido("Scotland", "Morocco", crearFechaHonduras(2026, 6, 19, 16, 0), 0, 0));
        partidos.add(new Partido("Brazil", "Haiti", crearFechaHonduras(2026, 6, 19, 18, 30), 0, 0));
        partidos.add(new Partido("Türkiye", "Paraguay", crearFechaHonduras(2026, 6, 19, 21, 0), 0, 0));
        partidos.add(new Partido("Netherlands", "Sweden", crearFechaHonduras(2026, 6, 20, 11, 0), 0, 0));
        partidos.add(new Partido("Germany", "Ivory Coast", crearFechaHonduras(2026, 6, 20, 14, 0), 0, 0));
        partidos.add(new Partido("Ecuador", "Curaçao", crearFechaHonduras(2026, 6, 20, 18, 0), 0, 0));
        partidos.add(new Partido("Tunisia", "Japan", crearFechaHonduras(2026, 6, 20, 22, 0), 0, 0));
        partidos.add(new Partido("Spain", "Saudi Arabia", crearFechaHonduras(2026, 6, 21, 10, 0), 0, 0));
        partidos.add(new Partido("Belgium", "Iran", crearFechaHonduras(2026, 6, 21, 13, 0), 0, 0));
        partidos.add(new Partido("Uruguay", "Cape Verde", crearFechaHonduras(2026, 6, 21, 16, 0), 0, 0));
        partidos.add(new Partido("New Zealand", "Egypt", crearFechaHonduras(2026, 6, 21, 19, 0), 0, 0));
        partidos.add(new Partido("Argentina", "Austria", crearFechaHonduras(2026, 6, 22, 11, 0), 0, 0));
        partidos.add(new Partido("France", "Iraq", crearFechaHonduras(2026, 6, 22, 15, 0), 0, 0));
        partidos.add(new Partido("Norway", "Senegal", crearFechaHonduras(2026, 6, 22, 18, 0), 0, 0));
        partidos.add(new Partido("Jordan", "Algeria", crearFechaHonduras(2026, 6, 22, 21, 0), 0, 0));
        partidos.add(new Partido("Portugal", "Uzbekistan", crearFechaHonduras(2026, 6, 23, 11, 0), 0, 0));
        partidos.add(new Partido("England", "Ghana", crearFechaHonduras(2026, 6, 23, 14, 0), 0, 0));
        partidos.add(new Partido("Panama", "Croatia", crearFechaHonduras(2026, 6, 23, 17, 0), 0, 0));
        partidos.add(new Partido("Colombia", "Congo DR", crearFechaHonduras(2026, 6, 23, 20, 0), 0, 0));
        partidos.add(new Partido("Bosnia-Herzegovina", "Qatar", crearFechaHonduras(2026, 6, 24, 13, 0), 0, 0));
        partidos.add(new Partido("Switzerland", "Canada", crearFechaHonduras(2026, 6, 24, 13, 0), 0, 0));
        partidos.add(new Partido("Morocco", "Haiti", crearFechaHonduras(2026, 6, 24, 16, 0), 0, 0));
        partidos.add(new Partido("Scotland", "Brazil", crearFechaHonduras(2026, 6, 24, 16, 0), 0, 0));
        partidos.add(new Partido("Czechia", "Mexico", crearFechaHonduras(2026, 6, 24, 19, 0), 0, 0));
        partidos.add(new Partido("South Africa", "South Korea", crearFechaHonduras(2026, 6, 24, 19, 0), 0, 0));
        partidos.add(new Partido("Curaçao", "Ivory Coast", crearFechaHonduras(2026, 6, 25, 14, 0), 0, 0));
        partidos.add(new Partido("Ecuador", "Germany", crearFechaHonduras(2026, 6, 25, 14, 0), 0, 0));
        partidos.add(new Partido("Japan", "Sweden", crearFechaHonduras(2026, 6, 25, 17, 0), 0, 0));
        partidos.add(new Partido("Tunisia", "Netherlands", crearFechaHonduras(2026, 6, 25, 17, 0), 0, 0));
        partidos.add(new Partido("Paraguay", "Australia", crearFechaHonduras(2026, 6, 25, 20, 0), 0, 0));
        partidos.add(new Partido("Türkiye", "United States", crearFechaHonduras(2026, 6, 25, 20, 0), 0, 0));
        partidos.add(new Partido("Norway", "France", crearFechaHonduras(2026, 6, 26, 13, 0), 0, 0));
        partidos.add(new Partido("Senegal", "Iraq", crearFechaHonduras(2026, 6, 26, 13, 0), 0, 0));
        partidos.add(new Partido("Cape Verde", "Saudi Arabia", crearFechaHonduras(2026, 6, 26, 18, 0), 0, 0));
        partidos.add(new Partido("Uruguay", "Spain", crearFechaHonduras(2026, 6, 26, 18, 0), 0, 0));
        partidos.add(new Partido("Egypt", "Iran", crearFechaHonduras(2026, 6, 26, 21, 0), 0, 0));
        partidos.add(new Partido("New Zealand", "Belgium", crearFechaHonduras(2026, 6, 26, 21, 0), 0, 0));
        partidos.add(new Partido("Croatia", "Ghana", crearFechaHonduras(2026, 6, 27, 15, 0), 0, 0));
        partidos.add(new Partido("Panama", "England", crearFechaHonduras(2026, 6, 27, 15, 0), 0, 0));
        partidos.add(new Partido("Colombia", "Portugal", crearFechaHonduras(2026, 6, 27, 17, 30), 0, 0));
        partidos.add(new Partido("Congo DR", "Uzbekistan", crearFechaHonduras(2026, 6, 27, 17, 30), 0, 0));
        partidos.add(new Partido("Algeria", "Austria", crearFechaHonduras(2026, 6, 27, 20, 0), 0, 0));
        partidos.add(new Partido("Jordan", "Argentina", crearFechaHonduras(2026, 6, 27, 20, 0), 0, 0));
//        partidos.add(new Partido("Group A 2nd Place", "Group B 2nd Place", crearFechaHonduras(2026, 6, 28, 13, 0), 0, 0));
//        partidos.add(new Partido("Group C Winner", "Group F 2nd Place", crearFechaHonduras(2026, 6, 29, 11, 0), 0, 0));
//        partidos.add(new Partido("Germany", "Third Place Group A/B/C/D/F", crearFechaHonduras(2026, 6, 29, 14, 30), 0, 0));
//        partidos.add(new Partido("Group F Winner", "Group C 2nd Place", crearFechaHonduras(2026, 6, 29, 19, 0), 0, 0));
//        partidos.add(new Partido("Group E 2nd Place", "Group I 2nd Place", crearFechaHonduras(2026, 6, 30, 11, 0), 0, 0));
//        partidos.add(new Partido("Group I Winner", "Third Place Group C/D/F/G/H", crearFechaHonduras(2026, 6, 30, 15, 0), 0, 0));
//        partidos.add(new Partido("Mexico", "Third Place Group C/E/F/H/I", crearFechaHonduras(2026, 6, 30, 19, 0), 0, 0));
//        partidos.add(new Partido("Group L Winner", "Third Place Group E/H/I/J/K", crearFechaHonduras(2026, 7, 1, 10, 0), 0, 0));
//        partidos.add(new Partido("Group G Winner", "Third Place Group A/E/H/I/J", crearFechaHonduras(2026, 7, 1, 14, 0), 0, 0));
//        partidos.add(new Partido("United States", "Third Place Group B/E/F/I/J", crearFechaHonduras(2026, 7, 1, 18, 0), 0, 0));
//        partidos.add(new Partido("Group H Winner", "Group J 2nd Place", crearFechaHonduras(2026, 7, 2, 13, 0), 0, 0));
//        partidos.add(new Partido("Group K 2nd Place", "Group L 2nd Place", crearFechaHonduras(2026, 7, 2, 17, 0), 0, 0));
//        partidos.add(new Partido("Group B Winner", "Third Place Group E/F/G/I/J", crearFechaHonduras(2026, 7, 2, 21, 0), 0, 0));
//        partidos.add(new Partido("Group D 2nd Place", "Group G 2nd Place", crearFechaHonduras(2026, 7, 3, 12, 0), 0, 0));
//        partidos.add(new Partido("Group J Winner", "Group H 2nd Place", crearFechaHonduras(2026, 7, 3, 16, 0), 0, 0));
//        partidos.add(new Partido("Group K Winner", "Third Place Group D/E/I/J/L", crearFechaHonduras(2026, 7, 3, 19, 30), 0, 0));
//        partidos.add(new Partido("Round of 32 1 Winner", "Round of 32 3 Winner", crearFechaHonduras(2026, 7, 4, 11, 0), 0, 0));
//        partidos.add(new Partido("Round of 32 2 Winner", "Round of 32 5 Winner", crearFechaHonduras(2026, 7, 4, 15, 0), 0, 0));
//        partidos.add(new Partido("Round of 32 4 Winner", "Round of 32 6 Winner", crearFechaHonduras(2026, 7, 5, 14, 0), 0, 0));
//        partidos.add(new Partido("Round of 32 7 Winner", "Round of 32 8 Winner", crearFechaHonduras(2026, 7, 5, 18, 0), 0, 0));
//        partidos.add(new Partido("Round of 32 11 Winner", "Round of 32 12 Winner", crearFechaHonduras(2026, 7, 6, 13, 0), 0, 0));
//        partidos.add(new Partido("Round of 32 9 Winner", "Round of 32 10 Winner", crearFechaHonduras(2026, 7, 6, 18, 0), 0, 0));
//        partidos.add(new Partido("Round of 32 14 Winner", "Round of 32 16 Winner", crearFechaHonduras(2026, 7, 7, 10, 0), 0, 0));
//        partidos.add(new Partido("Round of 32 13 Winner", "Round of 32 15 Winner", crearFechaHonduras(2026, 7, 7, 14, 0), 0, 0));
//        partidos.add(new Partido("Round of 16 1 Winner", "Round of 16 2 Winner", crearFechaHonduras(2026, 7, 9, 14, 0), 0, 0));
//        partidos.add(new Partido("Round of 16 5 Winner", "Round of 16 6 Winner", crearFechaHonduras(2026, 7, 10, 13, 0), 0, 0));
//        partidos.add(new Partido("Round of 16 3 Winner", "Round of 16 4 Winner", crearFechaHonduras(2026, 7, 11, 15, 0), 0, 0));
//        partidos.add(new Partido("Round of 16 7 Winner", "Round of 16 8 Winner", crearFechaHonduras(2026, 7, 11, 19, 0), 0, 0));
    }

    private Date crearFechaHonduras(int anio, int mes, int dia, int hora, int minuto) {
        Calendar calendario = Calendar.getInstance(TimeZone.getTimeZone("America/Tegucigalpa"));
        calendario.set(Calendar.YEAR, anio);
        calendario.set(Calendar.MONTH, mes - 1);
        calendario.set(Calendar.DAY_OF_MONTH, dia);
        calendario.set(Calendar.HOUR_OF_DAY, hora);
        calendario.set(Calendar.MINUTE, minuto);
        calendario.set(Calendar.SECOND, 0);
        calendario.set(Calendar.MILLISECOND, 0);
        return calendario.getTime();
    }

    public List<Partido> getPartidosNoJugados(){
        Date ahora = new Date();
        return partidos
                .stream()
                .filter(p -> p.getFechaHora().after(ahora))
                .collect(Collectors.toList());
    }

    public List<Partido> getPartidosYaJugados(){
        Date ahora = new Date();
        return partidos
                .stream()
                .filter(p -> p.getFechaHora().before(ahora))
                .collect(Collectors.toList());
    }

    public List<Partido> getPartidos() {
        return partidos;
    }

    public void setPartidos(List<Partido> partidos) {
        this.partidos = partidos;
    }

    public Partido getPartido() {
        return partido;
    }

    public void setPartido(Partido partido) {
        this.partido = partido;
    }
}
