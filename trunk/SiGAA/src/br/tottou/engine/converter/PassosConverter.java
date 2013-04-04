package br.tottou.engine.converter;



import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.tottou.data.PassosDao;
import br.tottou.model.entities.ProgPassos;

@FacesConverter(forClass = ProgPassos.class, value = "passos")
public class PassosConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String submittedValue) throws ConverterException {
		ProgPassos passos = new ProgPassos();
		if ((submittedValue != null) && (!submittedValue.equals(""))) {
			passos = PassosDao.getProgPassos(Long.valueOf(submittedValue));
		}
		return passos;
		
	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object valor)
			throws ConverterException {
	
		if (valor!=null) {
			ProgPassos passos = new ProgPassos();
			passos = (ProgPassos) valor;
			Long a =passos.getId();
			return a.toString();
		}
		return null;
	}

}
