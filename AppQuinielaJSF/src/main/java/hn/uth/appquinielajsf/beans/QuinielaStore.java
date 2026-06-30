package hn.uth.appquinielajsf.beans;

import hn.uth.appquinielajsf.data.Partido;
import hn.uth.appquinielajsf.data.Pronostico;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Named("QuinielaStoreBean")
@ApplicationScoped
public class QuinielaStore implements Serializable {
    private final List<Pronostico> pronosticos = new ArrayList<>();
    private final List<Partido> resultados = new ArrayList<>();

    public synchronized void registrarPronostico(Pronostico pronostico) {
        this.pronosticos.add(pronostico);
    }

    public synchronized void registrarResultado(Partido partidoConResultado) {
        for (int i = 0; i < this.resultados.size(); i++) {
            if (this.resultados.get(i).equals(partidoConResultado)) {
                this.resultados.set(i, partidoConResultado);
                return;
            }
        }
        this.resultados.add(partidoConResultado);
    }

    public synchronized List<Pronostico> getPronosticos() {
        return new ArrayList<>(this.pronosticos);
    }

    public synchronized List<Partido> getResultados() {
        return new ArrayList<>(this.resultados);
    }

    public synchronized void reemplazarPronosticos(List<Pronostico> nuevosPronosticos) {
        this.pronosticos.clear();
        if (nuevosPronosticos != null) {
            this.pronosticos.addAll(nuevosPronosticos);
        }
    }

    public synchronized void reemplazarResultados(List<Partido> nuevosResultados) {
        this.resultados.clear();
        if (nuevosResultados != null) {
            this.resultados.addAll(nuevosResultados);
        }
    }
}

