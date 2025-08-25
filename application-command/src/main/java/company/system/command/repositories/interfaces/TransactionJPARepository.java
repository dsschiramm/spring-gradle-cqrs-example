package company.system.command.repositories.interfaces;


import company.system.command.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionJPARepository extends JpaRepository<TransactionEntity, Long> {

    List<TransactionEntity> findAllByCardholderId(Long cardholderId);
}
