package ca.caserm.formbuilder.controller.data;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface FormTemplateDataView {

    Long getId();

    String getTitle();

    @Value("#{target.created_at}")
    @JsonFormat(pattern = Constants.DATE_FORMAT)
    LocalDateTime getCreateDate();

    Boolean getEnabled();

}
