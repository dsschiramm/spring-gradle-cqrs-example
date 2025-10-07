package company.system.command.infrastructure.repositories;

import company.system.command.domain.models.TransactionDO;
import company.system.command.infrastructure.entities.TransactionEntity;
import company.system.command.infrastructure.repositories.interfaces.TransactionJPARepository;
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

    public Long countByCardholderId(Long cardholderId) {
        return transactionJPARepository.countByCardholderId(cardholderId);
    }

    public void save(TransactionDO credit) {
        transactionJPARepository.save(new TransactionEntity(credit));
    }
}
