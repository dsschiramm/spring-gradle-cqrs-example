package company.system.command.infrastructure.communications.authorizer;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class AuthorizerAPIConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().create();
    }

    @Bean
    public Retrofit retrofit(
            @Value("${communications.api-authorize-url}") String authorizeURL,
            OkHttpClient okHttpClient,
            Gson gson) {

        return new Retrofit.Builder()
                .baseUrl(authorizeURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Bean
    public AuthorizerAPI authorizerAPI(Retrofit retrofit) {
        return retrofit.create(AuthorizerAPI.class);
    }
}
