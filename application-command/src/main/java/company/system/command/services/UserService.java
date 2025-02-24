package company.system.command.services;

import company.system.command.entities.UserEntity;
import company.system.command.exceptions.DomainException;
import company.system.command.exceptions.user.UniqueCpfException;
import company.system.command.exceptions.user.UserNotFoundException;
import company.system.command.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserEntity getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity createUser(UserEntity user) throws DomainException {

        user.validate();
        validateIfCpfUnique(user.getCpf());

        return userRepository.save(user);
    }

    public UserEntity updateUser(Long id, UserEntity userDetails) throws DomainException {

        userDetails.validate();

        Optional<UserEntity> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            if (!optionalUser.get().getCpf().equals(userDetails.getCpf())) {
                validateIfCpfUnique(userDetails.getCpf());
            }

            userDetails.setId(id);
        } else {
            throw new UserNotFoundException();
        }

        userRepository.save(userDetails);

        return userDetails;
    }

    public boolean deleteUser(Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private void validateIfCpfUnique(String cpf) throws DomainException {

        if (userRepository.countByCpf(cpf) >= 1) {
            throw new UniqueCpfException();
        }
    }
}
