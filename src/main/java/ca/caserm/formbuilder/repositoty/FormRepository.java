package ca.caserm.formbuilder.repositoty;

import ca.caserm.formbuilder.controller.data.FormView;
import ca.caserm.formbuilder.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FormRepository extends JpaRepository<Form, Long> {

    @Query("select f from Form f where f.accountId = :accountId")
    List<Form> findAllByAccountId(@Param("accountId") Long accountId);

    @Query(value = "select f.id, f.title, f.description, f.created_at from forms f where f.account_id = :accountId", nativeQuery = true)
    List<FormView> findAllFormViewsByAccountId(@Param("accountId") Long accountId);

}
