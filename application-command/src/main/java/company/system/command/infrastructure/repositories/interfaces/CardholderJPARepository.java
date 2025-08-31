package company.system.command.infrastructure.repositories.interfaces;


import company.system.command.infrastructure.entities.CardholderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CardholderJPARepository extends JpaRepository<CardholderEntity, Long> {

    CardholderEntity findByDocument(String cpf);

    CardholderEntity findByEmail(String email);
}
