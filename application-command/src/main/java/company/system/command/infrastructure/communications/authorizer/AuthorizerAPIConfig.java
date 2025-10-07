package company.system.command.infrastructure.communications.authorizer;

import com.google.gson.Gson;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class AuthorizerAPIConfig {

    @Bean
    public AuthorizerAPI authorizerAPI(
            @Value("${communications.api-authorize-url}") String authorizeURL,
            OkHttpClient okHttpClient,
            Gson gson) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(authorizeURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(AuthorizerAPI.class);
    }
}
