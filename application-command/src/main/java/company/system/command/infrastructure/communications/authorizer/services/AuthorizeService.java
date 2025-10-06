package company.system.command.infrastructure.communications.authorizer.services;

import company.system.command.domain.exceptions.InfrastructureException;
import company.system.command.domain.ports.services.IAuthorizeService;
import company.system.command.infrastructure.communications.authorizer.AuthorizerAPI;
import company.system.command.infrastructure.communications.authorizer.exceptions.NetworkRequestException;
import company.system.command.infrastructure.communications.authorizer.exceptions.NullableHttpResponseException;
import company.system.command.infrastructure.communications.authorizer.exceptions.ResponseRequestException;
import company.system.command.infrastructure.communications.authorizer.response.AuthorizeResponseDTO;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@Service
public class AuthorizeService implements IAuthorizeService {

    private AuthorizerAPI authorizerAPI;

    public AuthorizeService(AuthorizerAPI authorizerAPI) {
        this.authorizerAPI = authorizerAPI;
    }

    public Boolean authorize() throws InfrastructureException {

        Call<AuthorizeResponseDTO> call = authorizerAPI.authorize();

        try {
            Response<AuthorizeResponseDTO> resp = call.execute(); // blocking

            if (!resp.isSuccessful()) {
                ResponseBody responseBody = resp.errorBody();
                String response = responseBody != null ? responseBody.string() : "";
                throw new ResponseRequestException(response, resp.code());
            }

            AuthorizeResponseDTO body = resp.body();

            if (body == null) {
                throw new NullableHttpResponseException();
            }

            return body.data().authorization();

        } catch (IOException ex) {
            throw new NetworkRequestException(ex.getMessage());
        }
    }
}
