package company.system.command.infrastructure.communications.notification.services;

import company.system.command.domain.exceptions.InfrastructureException;
import company.system.command.domain.ports.dtos.NotificationDTO;
import company.system.command.infrastructure.communications.authorizer.exceptions.NetworkRequestException;
import company.system.command.infrastructure.communications.authorizer.exceptions.ResponseRequestException;
import company.system.command.infrastructure.communications.notification.interfaces.NotificationAPI;
import okhttp3.ResponseBody;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;

@Service
public class NotificationService {

    private NotificationAPI notificationAPI;

    public NotificationService(NotificationAPI notificationAPI) {
        this.notificationAPI = notificationAPI;
    }

    public void send(NotificationDTO notification) throws InfrastructureException {

        Call<Void> call = notificationAPI.send(notification);

        try {
            Response<Void> resp = call.execute(); // blocking

            if (!resp.isSuccessful()) {
                ResponseBody responseBody = resp.errorBody();
                String response = responseBody != null ? responseBody.string() : "";
                throw new ResponseRequestException(response, resp.code());
            }

        } catch (IOException ex) {
            throw new NetworkRequestException(ex.getMessage());
        }
    }
}
