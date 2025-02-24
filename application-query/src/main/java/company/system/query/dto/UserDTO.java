package company.system.query.dto;

import company.system.utils.CPFUtil;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.query.TupleTransformer;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO implements TupleTransformer {

    private Long id;
    private String fullName;
    private String email;
    private String cpf;

    @Override
    public UserDTO transformTuple(Object[] tuple, String[] aliases) {
        int i = 0;
        UserDTO userDTO = new UserDTO();
        userDTO.setId((Long) tuple[i++]);
        userDTO.setFullName((String) tuple[i++]);
        userDTO.setEmail((String) tuple[i++]);
        userDTO.setCpf((String) tuple[i++]);
        return userDTO;
    }

    public String getCpf() {
        return CPFUtil.format(cpf);
    }
}
