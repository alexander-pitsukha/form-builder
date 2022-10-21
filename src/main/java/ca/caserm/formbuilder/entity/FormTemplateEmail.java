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
@Table(name = "form_template_emails")
public class FormTemplateEmail extends AbstractIdentifiable<Long> {

    @Column(name = "action_name")
    private String actionName;

    @Column(name = "email_address")
    private String emailAddress;

    @Column(name = "reply_email_address")
    private String replyEmailAddress;

    @Column(name = "email_subject")
    private String emailSubject;

    @Column(name = "email_body")
    private String emailBody;

    @Column(name = "from_name")
    private String fromName;

    @Column(name = "from_address")
    private String fromAddress;

    @Column(name = "attachment")
    private String attachment;

    @Column(name = "enabled")
    private Boolean enabled;

    @ManyToOne
    @JoinColumn(name = "form_template_id", nullable = false)
    @NotNull
    private FormTemplate formTemplate;

}
