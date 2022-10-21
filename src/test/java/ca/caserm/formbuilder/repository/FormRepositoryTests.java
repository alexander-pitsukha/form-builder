package ca.caserm.formbuilder.repository;

import ca.caserm.formbuilder.controller.data.FormView;
import ca.caserm.formbuilder.entity.Form;
import ca.caserm.formbuilder.entity.FormTemplate;
import ca.caserm.formbuilder.repositoty.FormRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class FormRepositoryTests extends AbstractRepositoryTests {

    @Autowired
    private FormRepository formRepository;

    @Test
    void testFindAllByAccountId() throws IOException {
        FormTemplate formTemplate = saveTestEntity("json/form_template.json", FormTemplate.class);

        long accountId = 1L;
        saveTestEntity(createFormWithId(accountId, formTemplate));
        saveTestEntity(createFormWithId(accountId, formTemplate));
        saveTestEntity(createFormWithId(2, formTemplate));

        List<Form> forms = formRepository.findAllByAccountId(accountId);

        assertNotNull(forms);
        assertEquals(2, forms.size());
        assertEquals(accountId, forms.get(0).getAccountId());
        assertEquals(accountId, forms.get(1).getAccountId());
    }

    @Test
    void testFindAllAllFormViewsByAccountId() throws IOException {
        FormTemplate formTemplate = saveTestEntity("json/form_template.json", FormTemplate.class);

        long accountId = 1L;
        Form form1 = saveTestEntity(createFormWithId(accountId, formTemplate));
        saveTestEntity(createFormWithId(2, formTemplate));

        List<FormView> formViews = formRepository.findAllFormViewsByAccountId(accountId);

        assertNotNull(formViews);
        assertNotNull(formViews.get(0).getCreateDate());
        assertEquals(1, formViews.size());
        assertEquals(form1.getId(), formViews.get(0).getId());
        assertEquals(form1.getTitle(), formViews.get(0).getTitle());
        assertEquals(form1.getDescription(), formViews.get(0).getDescription());
    }

    private Form createFormWithId(long accountId, FormTemplate formTemplate) {
        Form form = new Form();
        form.setTitle(UUID.randomUUID().toString());
        form.setDescription(UUID.randomUUID().toString());
        form.setSchema(UUID.randomUUID().toString());
        form.setAccountId(accountId);
        form.setCreateDate(LocalDateTime.now());
        form.setUpdateDate(LocalDateTime.now());
        form.setFormTemplate(formTemplate);
        return form;
    }

}
