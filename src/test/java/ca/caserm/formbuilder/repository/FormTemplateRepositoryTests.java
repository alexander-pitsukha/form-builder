package ca.caserm.formbuilder.repository;

import ca.caserm.formbuilder.controller.data.FormTemplateDataView;
import ca.caserm.formbuilder.entity.FormTemplate;
import ca.caserm.formbuilder.controller.data.FormTemplateView;
import ca.caserm.formbuilder.repositoty.FormTemplateRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class FormTemplateRepositoryTests extends AbstractRepositoryTests {

    @Autowired
    private FormTemplateRepository formTemplateRepository;

    @Test
    void testFindAllFormTemplateDataViews() throws IOException {
        FormTemplate formTemplate = saveTestEntity("json/form_template.json", FormTemplate.class);

        List<FormTemplateDataView> formTemplateDataViews = formTemplateRepository.findAllFormTemplateDataViews();

        assertNotNull(formTemplateDataViews);
        assertNotNull(formTemplate.getCreateDate());
        assertEquals(1, formTemplateDataViews.size());
        assertEquals(formTemplate.getId(), formTemplateDataViews.get(0).getId());
        assertEquals(formTemplate.getTitle(), formTemplateDataViews.get(0).getTitle());
        assertEquals(formTemplate.getEnabled(), formTemplateDataViews.get(0).getEnabled());
    }

    @Test
    void testFindAllFormTemplateViews() throws IOException {
        FormTemplate formTemplate = getObjectFromJson("json/form_template.json", FormTemplate.class);
        formTemplate.setEnabled(true);
        FormTemplate formTemplateEnabled = saveTestEntity(formTemplate);
        saveTestEntity("json/form_template.json", FormTemplate.class);

        List<FormTemplateView> formTemplateViews = formTemplateRepository.findAllFormTemplateViews();

        assertNotNull(formTemplateViews);
        assertEquals(1, formTemplateViews.size());
        assertEquals(formTemplateEnabled.getTitle(), formTemplateViews.get(0).getTitle());
        assertEquals(formTemplateEnabled.getDescription(), formTemplateViews.get(0).getDescription());
    }

    @Test
    void testUpdateEnabled() throws IOException {
        FormTemplate formTemplate = saveTestEntity("json/form_template.json", FormTemplate.class);
        LocalDateTime updateDate = LocalDateTime.now();

        formTemplateRepository.updateEnabled(formTemplate.getId(), !formTemplate.getEnabled(), updateDate);

        FormTemplate entity = formTemplateRepository.findById(formTemplate.getId()).orElse(null);

        assertNotNull(entity);
        assertNotNull(entity.getCreateDate());
        assertNotNull(entity.getUpdateDate());
        assertEquals(formTemplate.getId(), entity.getId());
        assertEquals(formTemplate.getTitle(), entity.getTitle());
        assertEquals(formTemplate.getDescription(), entity.getDescription());
        assertEquals(formTemplate.getSchema(), entity.getSchema());
        assertEquals(formTemplate.getAccountId(), entity.getAccountId());
        assertEquals(formTemplate.getThemeColor(), entity.getThemeColor());
        assertEquals(formTemplate.getBackgroundColor(), entity.getBackgroundColor());
        assertEquals(formTemplate.getTextFont(), entity.getTextFont());
        assertEquals(!formTemplate.getEnabled(), entity.getEnabled());
    }

}
