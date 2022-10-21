package ca.caserm.formbuilder.repositoty;

import ca.caserm.formbuilder.controller.data.FormTemplateDataView;
import ca.caserm.formbuilder.entity.FormTemplate;
import ca.caserm.formbuilder.controller.data.FormTemplateView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface FormTemplateRepository extends JpaRepository<FormTemplate, Long> {

    @Query(value = "select f.id, f.title, f.created_at, f.enabled from form_templates f", nativeQuery = true)
    List<FormTemplateDataView> findAllFormTemplateDataViews();

    @Query(value = "select f.id, f.title, f.description from form_templates f where f.enabled = true", nativeQuery = true)
    List<FormTemplateView> findAllFormTemplateViews();

    @Modifying(clearAutomatically = true)
    @Query("update FormTemplate f set f.enabled = :enabled, f.updateDate = :updateDate where f.id = :id")
    void updateEnabled(@Param(value = "id") Long id, @Param(value = "enabled") Boolean enabled, @Param(value = "updateDate") LocalDateTime updateDate);

}
