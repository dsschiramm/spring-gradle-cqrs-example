package company.system.command.infrastructure.communications.authorizer;

import company.system.command.infrastructure.communications.authorizer.response.AuthorizeResponseDTO;
import retrofit2.Call;
import retrofit2.http.GET;

public interface AuthorizerAPI {

    @GET("/api/v2/authorize")
    public Call<AuthorizeResponseDTO> authorize();
}
