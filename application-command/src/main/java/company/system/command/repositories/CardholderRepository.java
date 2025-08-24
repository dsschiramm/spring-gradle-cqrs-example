package company.system.command.repositories;

import company.system.command.domain.models.CardholderDO;
import company.system.command.entities.CardholderEntity;
import company.system.command.repositories.interfaces.CardholderJPARepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class CardholderRepository {

    private final CardholderJPARepository cardholderJPARepository;

    public CardholderRepository(CardholderJPARepository cardholderJPARepository) {
        this.cardholderJPARepository = cardholderJPARepository;
    }

    public Long findCardholderIdByDocument(String document) {
        CardholderEntity cardholderEntity = cardholderJPARepository.findByDocument(document);
        return cardholderEntity != null ? cardholderEntity.getId() : null;
    }

    public boolean existsByDocument(String document) {
        CardholderEntity cardholderEntity = cardholderJPARepository.findByDocument(document);
        return cardholderEntity != null;
    }

    public boolean existsByEmail(String email) {
        CardholderEntity cardholderEntity = cardholderJPARepository.findByEmail(email);
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
