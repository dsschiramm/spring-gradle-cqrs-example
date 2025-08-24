package company.system.query.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.query.TupleTransformer;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO implements TupleTransformer<UserDTO> {

    private Long id;
    private String fullName;
    private String email;
    private String document;

    @Override
    public UserDTO transformTuple(Object[] tuple, String[] aliases) {
        int i = 0;
        UserDTO userDTO = new UserDTO();
        userDTO.setId((Long) tuple[i++]);
        userDTO.setFullName((String) tuple[i++]);
        userDTO.setEmail((String) tuple[i++]);
        userDTO.setDocument((String) tuple[i]);
        return userDTO;
    }
}
