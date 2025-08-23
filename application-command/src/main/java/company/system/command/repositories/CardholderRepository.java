package company.system.command.repositories;

import company.system.command.domain.CardholderDO;
import company.system.command.entities.CardholderEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CardholderRepository {

    private final CardholderJPARepository cardholderJPARepository;

    public CardholderRepository(CardholderJPARepository cardholderJPARepository) {
        this.cardholderJPARepository = cardholderJPARepository;
    }

    public boolean existsByCpf(String cpf) {
        CardholderEntity cardholderEntity = cardholderJPARepository.findByCpf(cpf);
        return cardholderEntity != null;
    }

    public Long save(CardholderDO cardholder) {
        return cardholderJPARepository.save(new CardholderEntity(cardholder)).getId();
    }

    public CardholderDO findById(Long id) {
        Optional<CardholderEntity> cardholderEntity = cardholderJPARepository.findById(id);
        return cardholderEntity.map(CardholderEntity::toDO).orElse(null);
    }

    public void update(Long id, CardholderDO cardholder) {
        CardholderEntity entity = new CardholderEntity(cardholder);
        entity.setId(id);
        cardholderJPARepository.save(entity);
    }

    public void delete(Long id) {
        cardholderJPARepository.deleteById(id);
    }
}
