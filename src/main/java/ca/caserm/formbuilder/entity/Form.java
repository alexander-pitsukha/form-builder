package ca.caserm.formbuilder.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "forms")
public class Form extends AbstractEntity {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_template_id", nullable = false)
    @NotNull
    private FormTemplate formTemplate;

}
