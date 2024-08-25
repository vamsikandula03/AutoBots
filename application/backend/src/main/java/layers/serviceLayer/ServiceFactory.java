package layers.serviceLayer;

import interfaces.ServiceLayer;
import models.User;

public class ServiceFactory {
    public static ServiceLayer getService(User user){
        return new ServiceLayerProxyImpl(user);
    }
}
