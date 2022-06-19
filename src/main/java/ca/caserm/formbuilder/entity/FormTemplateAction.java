package ca.caserm.formbuilder.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Entity
@Table(name = "form_template_actions")
public class FormTemplateAction extends AbstractIdentifiable<Long> {

    @Column(name = "action_name")
    private String actionName;

    @Column(name = "message")
    private String message;

    @Column(name = "enabled")
    private Boolean enabled;

    @ManyToOne
    @JoinColumn(name = "form_template_id", nullable = false)
    @NotNull
    private FormTemplate formTemplate;

}
