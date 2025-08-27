package company.system.query.repositories;

import company.system.query.dto.CardholderDTO;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CardholderRepository {

    private final EntityManager entityManager;

    public CardholderRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<CardholderDTO> getAllUsers() {

        return entityManager.unwrap(Session.class)
                .createNativeQuery("SELECT id, full_name, email, document, type FROM cardholder", CardholderDTO.class)
                .setTupleTransformer(new CardholderDTO()::transformTuple)
                .list();
    }

    public CardholderDTO getUserById(Long id) {

        return entityManager.unwrap(Session.class)
                .createNativeQuery("SELECT id, full_name, email, document, type FROM cardholder where id = :id", CardholderDTO.class)
                .setParameter("id", id)
                .setTupleTransformer(new CardholderDTO()::transformTuple)
                .getSingleResult();
    }
}
