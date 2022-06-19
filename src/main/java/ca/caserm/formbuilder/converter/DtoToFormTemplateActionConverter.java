package ca.caserm.formbuilder.converter;

import ca.caserm.formbuilder.dto.FormTemplateActionDto;
import ca.caserm.formbuilder.entity.FormTemplateAction;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToFormTemplateActionConverter implements Converter<FormTemplateActionDto, FormTemplateAction> {

    @Override
    public FormTemplateAction convert(FormTemplateActionDto source) {
        final var formTemplateAction = new FormTemplateAction();
        formTemplateAction.setActionName(source.getActionName());
        formTemplateAction.setMessage(source.getMessage());
        formTemplateAction.setEnabled(source.getEnabled());
        return formTemplateAction;
    }

}
