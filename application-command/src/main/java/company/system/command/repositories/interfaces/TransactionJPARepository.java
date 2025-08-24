package company.system.command.repositories.interfaces;


import company.system.command.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionJPARepository extends JpaRepository<TransactionEntity, Long> {

}
