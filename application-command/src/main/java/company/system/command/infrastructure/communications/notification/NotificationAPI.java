package company.system.command.infrastructure.communications.notification;

import company.system.command.domain.ports.dtos.NotificationDTO;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface NotificationAPI {

    @POST("/api/v1/notify")
    public Call<Void> send(@Body NotificationDTO notification);
}
