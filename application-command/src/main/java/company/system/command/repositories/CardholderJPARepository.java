package company.system.command.repositories;


import company.system.command.entities.CardholderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardholderJPARepository extends JpaRepository<CardholderEntity, Long> {

    CardholderEntity findByCpf(String cpf);
}
