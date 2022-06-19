package ca.caserm.formbuilder.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractEntity extends AbstractIdentifiable<Long> {

    @CreationTimestamp
    @Column(name = "created_at", updatable = false, nullable = false)
    private LocalDateTime createDate;

    @UpdateTimestamp
    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updateDate;

}
