package br.tottou.engine.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import br.tottou.data.TarefaDao;
import br.tottou.model.entities.Tarefa;


@FacesConverter(forClass = Tarefa.class, value = "tarefa")
public class TarefaConverter implements Converter {

	@Override
	public Object getAsObject(FacesContext facesContext, UIComponent component,
			String submittedValue) throws ConverterException {
		Tarefa tarefa = new Tarefa();
		if ((submittedValue != null) && (!submittedValue.equals(""))) {
			tarefa = TarefaDao.getTarefa(Long.valueOf(submittedValue));

		}
		return tarefa;

	}

	@Override
	public String getAsString(FacesContext arg0, UIComponent arg1, Object valor)
			throws ConverterException {

		if (valor != null) {
			Tarefa tarefa = new Tarefa();
			tarefa = (Tarefa) valor;
			Long a = tarefa.getId();
			return a.toString();
		}
		return null;
	}

}
