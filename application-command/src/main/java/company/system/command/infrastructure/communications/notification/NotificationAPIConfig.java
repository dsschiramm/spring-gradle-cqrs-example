package company.system.command.infrastructure.communications.notification;

import com.google.gson.Gson;
import company.system.command.infrastructure.communications.notification.interfaces.NotificationAPI;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class NotificationAPIConfig {

    @Bean
    public NotificationAPI notificationAPI(
            @Value("${communications.api-notification-url}") String notificationURL,
            OkHttpClient okHttpClient,
            Gson gson) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(notificationURL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        return retrofit.create(NotificationAPI.class);
    }
}
