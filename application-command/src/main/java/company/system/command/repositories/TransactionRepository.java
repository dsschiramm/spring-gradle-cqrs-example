package company.system.command.repositories;

import company.system.command.domain.models.TransactionDO;
import company.system.command.entities.TransactionEntity;
import company.system.command.repositories.interfaces.TransactionJPARepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TransactionRepository {

    private final TransactionJPARepository transactionJPARepository;

    public TransactionRepository(TransactionJPARepository transactionJPARepository) {
        this.transactionJPARepository = transactionJPARepository;
    }

    @Transactional
    public void save(List<TransactionDO> transactionDOList) {
        transactionJPARepository.saveAll(
                transactionDOList.stream().map(TransactionEntity::new).toList());
    }

    public List<TransactionDO> findAllByCardholder(Long cardholderId) {
        return transactionJPARepository.findAllByCardholderId(cardholderId)
                .stream()
                .map(TransactionEntity::toDO)
                .toList();
    }
}
