package company.system.command.repositories;


import company.system.command.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByCpf(String cpf);

    @Query("SELECT COUNT(u) FROM UserEntity u WHERE u.cpf = ?1")
    long countByCpf(String cpf);
}
