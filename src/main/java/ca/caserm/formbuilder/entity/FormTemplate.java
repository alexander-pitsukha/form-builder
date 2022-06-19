package ca.caserm.formbuilder.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "form_templates")
public class FormTemplate extends AbstractEntity {

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "schema")
    private String schema;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "theme_color")
    private String themeColor;

    @Column(name = "background_color")
    private String backgroundColor;

    @Column(name = "text_font")
    private String textFont;

    @Column(name = "enabled")
    private Boolean enabled;

    @OneToMany(mappedBy = "formTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<FormTemplateEmail> formTemplateEmails = new ArrayList<>();

    @OneToMany(mappedBy = "formTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    @PrimaryKeyJoinColumn
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<FormTemplateAction> formTemplateActions = new ArrayList<>();

}
