package hn.uth.appquinielajsf.converters;

import hn.uth.appquinielajsf.beans.Partidos;
import hn.uth.appquinielajsf.data.Partido;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.ConverterException;
import jakarta.faces.convert.FacesConverter;
import java.util.logging.Logger;

//@FacesConverter(forClass = Partido.class)
@FacesConverter(value = "partidoConverter")
public class PartidosConverter implements Converter<Partido> {
    private static final Logger LOGGER = Logger.getLogger(PartidosConverter.class.getName());

    @Override
    public Partido getAsObject(FacesContext facesContext, UIComponent uiComponent, String s) {
        if (s == null || s.isEmpty()) {
            return null;
        }
        try {
            String[] split = s.split("_", 2);
            if (split.length != 2) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Formato de partido invalido", s));
            }

            String[] rivales = split[0].split("-vs-");
            if (rivales.length != 2) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Rivales invalidos", split[0]));
            }

            long time =  Long.parseLong(split[1]);
            Partidos partidosBean = facesContext.getApplication().evaluateExpressionGet(facesContext, "#{PartidosBean}", Partidos.class);
            if (partidosBean == null) {
                throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo acceder a PartidosBean", null));
            }

            for (Partido partido : partidosBean.getPartidos()) {
                if (partido.getRival1().equals(rivales[0]) && partido.getRival2().equals(rivales[1]) && partido.getFechaHora().getTime() == time) {
                    return partido;
                }
            }

            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Partido no encontrado", s));
        } catch (ConverterException e) {
            throw e;
        } catch (Exception e) {
            LOGGER.warning("Error al convertir el partido: " + e.getMessage());
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al convertir el partido", s), e);
        }
    }

    @Override
    public String getAsString(FacesContext facesContext, UIComponent uiComponent, Partido partido) {
        if (partido == null ||  partido.getRival1() == null || partido.getRival2() == null) {
            return "";
        }
        return partido.getRival1() + "-vs-" + partido.getRival2() + "_" + partido.getFechaHora().getTime();
    }
}
