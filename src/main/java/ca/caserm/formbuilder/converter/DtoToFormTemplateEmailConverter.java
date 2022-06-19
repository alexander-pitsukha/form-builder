package ca.caserm.formbuilder.converter;

import ca.caserm.formbuilder.dto.FormTemplateEmailDto;
import ca.caserm.formbuilder.entity.FormTemplateEmail;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToFormTemplateEmailConverter implements Converter<FormTemplateEmailDto, FormTemplateEmail> {

    @Override
    public FormTemplateEmail convert(FormTemplateEmailDto source) {
        final var formTemplateEmail = new FormTemplateEmail();
        formTemplateEmail.setActionName(source.getActionName());
        formTemplateEmail.setEmailAddress(source.getEmailAddress());
        formTemplateEmail.setReplyEmailAddress(source.getReplyEmailAddress());
        formTemplateEmail.setEmailSubject(source.getEmailSubject());
        formTemplateEmail.setEmailBody(source.getEmailBody());
        formTemplateEmail.setFromName(source.getFromName());
        formTemplateEmail.setFromAddress(source.getFromAddress());
        formTemplateEmail.setAttachment(source.getAttachment());
        formTemplateEmail.setEnabled(source.getEnabled());
        return formTemplateEmail;
    }

}
