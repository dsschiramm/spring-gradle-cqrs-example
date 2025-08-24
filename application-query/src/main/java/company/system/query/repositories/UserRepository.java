package company.system.query.repositories;

import company.system.query.dto.UserDTO;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepository {

    private final EntityManager entityManager;

    public UserRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<UserDTO> getAllUsers() {

        return entityManager.unwrap(Session.class)
                .createNativeQuery("SELECT id, full_name, email, document FROM user", UserDTO.class)
                .setTupleTransformer(new UserDTO()::transformTuple)
                .list();
    }

    public UserDTO getUserById(Long id) {

        return entityManager.unwrap(Session.class)
                .createNativeQuery("SELECT id, full_name, email, document FROM user where id = :id", UserDTO.class)
                .setParameter("id", id)
                .setTupleTransformer(new UserDTO()::transformTuple)
                .getSingleResult();
    }
}
