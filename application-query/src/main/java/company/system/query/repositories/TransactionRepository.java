package company.system.query.repositories;

import company.system.query.dto.TransactionDTO;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepository {

    private final EntityManager entityManager;

    public TransactionRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<TransactionDTO> getAllTransactions() {

        return entityManager.unwrap(Session.class)
                .createNativeQuery("SELECT id, data, valor, cardholder_id FROM transaction", TransactionDTO.class)
                .setTupleTransformer(new TransactionDTO()::transformTuple)
                .list();
    }

    public List<TransactionDTO> getTransactionsByCardHolder(Long cardholderId) {

        String sql = """
                SELECT tr.id, tr.data, tr.valor, tr.cardholder_id
                FROM transaction tr
                WHERE tr.cardholder_id = :cardholderId
                """;

        return entityManager.unwrap(Session.class)
                .createNativeQuery(sql, TransactionDTO.class)
                .setParameter("cardholderId", cardholderId)
                .setTupleTransformer(new TransactionDTO()::transformTuple)
                .list();
    }
}
