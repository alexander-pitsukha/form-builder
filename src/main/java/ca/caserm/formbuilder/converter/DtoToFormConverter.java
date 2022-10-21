package ca.caserm.formbuilder.converter;

import ca.caserm.formbuilder.dto.FormDto;
import ca.caserm.formbuilder.entity.Form;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class DtoToFormConverter implements Converter<FormDto, Form> {

    @Override
    public Form convert(FormDto source) {
        final var form = new Form();
        form.setTitle(source.getTitle());
        form.setDescription(source.getDescription());
        form.setSchema(source.getSchema());
        form.setAccountId(source.getAccountId());
        form.setThemeColor(source.getThemeColor());
        form.setBackgroundColor(source.getBackgroundColor());
        form.setTextFont(source.getTextFont());
        return form;
    }

}
