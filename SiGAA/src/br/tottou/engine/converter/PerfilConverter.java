package br.tottou.engine.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.tottou.data.PerfilDao;
import br.tottou.model.entities.Perfil;

@FacesConverter(forClass = Perfil.class, value = "perfil")
public class PerfilConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String submittedValue) throws ConverterException {
		Perfil perfil = new Perfil();
		if ((submittedValue != null) && (!submittedValue.equals(""))) {
			perfil = PerfilDao.getPerfil(Long.valueOf(submittedValue));

		}
		return perfil;

	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object valor)
			throws ConverterException {

		if (valor != null) {
			Perfil perfil = new Perfil();
			perfil = (Perfil) valor;
			Long a = perfil.getId();
			return a.toString();
		}
		return null;
	}

}
