package ca.caserm.formbuilder.repository;

import ca.caserm.formbuilder.entity.User;
import ca.caserm.formbuilder.repositoty.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@DataJpaTest
class UserRepositoryTests extends AbstractRepositoryTests {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testFindByEmail() throws IOException {
        User user = saveTestEntity("json/user.json", User.class);

        User entity = userRepository.findByEmail(user.getEmail());

        assertNotNull(entity);
        assertNotNull(entity.getId());
        assertEquals(user.getFirstName(), entity.getFirstName());
        assertEquals(user.getLastName(), entity.getLastName());
        assertEquals(user.getPhoneNumber(), entity.getPhoneNumber());
        assertEquals(user.getEmail(), entity.getEmail());
        assertEquals(user.getPassword(), entity.getPassword());
        assertEquals(user.getRole(), entity.getRole());
    }

}
