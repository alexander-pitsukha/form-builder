package ca.caserm.formbuilder.converter;

import ca.caserm.formbuilder.dto.FormTemplateActionDto;
import ca.caserm.formbuilder.entity.FormTemplateAction;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FormTemplateActionToDtoConverter implements Converter<FormTemplateAction, FormTemplateActionDto> {

    @Override
    public FormTemplateActionDto convert(FormTemplateAction source) {
        final var formTemplateActionDto = new FormTemplateActionDto();
        formTemplateActionDto.setId(source.getId());
        formTemplateActionDto.setActionName(source.getActionName());
        formTemplateActionDto.setMessage(source.getMessage());
        formTemplateActionDto.setEnabled(source.getEnabled());
        Optional.ofNullable(source.getFormTemplate()).ifPresent(formTemplate -> formTemplateActionDto.setFormTemplateId(formTemplate.getId()));
        return formTemplateActionDto;
    }

}
