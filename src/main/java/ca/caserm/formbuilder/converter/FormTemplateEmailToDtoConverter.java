package ca.caserm.formbuilder.converter;

import ca.caserm.formbuilder.dto.FormTemplateEmailDto;
import ca.caserm.formbuilder.entity.FormTemplateEmail;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class FormTemplateEmailToDtoConverter implements Converter<FormTemplateEmail, FormTemplateEmailDto> {

    @Override
    public FormTemplateEmailDto convert(FormTemplateEmail source) {
        final var formTemplateEmailDto = new FormTemplateEmailDto();
        formTemplateEmailDto.setId(source.getId());
        formTemplateEmailDto.setActionName(source.getActionName());
        formTemplateEmailDto.setEmailAddress(source.getEmailAddress());
        formTemplateEmailDto.setReplyEmailAddress(source.getReplyEmailAddress());
        formTemplateEmailDto.setEmailSubject(source.getEmailSubject());
        formTemplateEmailDto.setEmailBody(source.getEmailBody());
        formTemplateEmailDto.setFromName(source.getFromName());
        formTemplateEmailDto.setFromAddress(source.getFromAddress());
        formTemplateEmailDto.setAttachment(source.getAttachment());
        formTemplateEmailDto.setEnabled(source.getEnabled());
        Optional.ofNullable(source.getFormTemplate()).ifPresent(formTemplate -> formTemplateEmailDto.setFormTemplateId(formTemplate.getId()));
        return formTemplateEmailDto;
    }

}
