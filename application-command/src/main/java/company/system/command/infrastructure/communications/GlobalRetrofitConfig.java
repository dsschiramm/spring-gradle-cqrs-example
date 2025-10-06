package company.system.command.infrastructure.communications;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GlobalRetrofitConfig {

    @Bean
    public OkHttpClient okHttpClient() {
        return new OkHttpClient.Builder().build();
    }

    @Bean
    public Gson gson() {
        return new GsonBuilder().create();
    }
}
