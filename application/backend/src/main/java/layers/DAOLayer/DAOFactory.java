package layers.DAOLayer;

import interfaces.DAOLayer;

public class DAOFactory {
    public static DAOLayer getDAO(){
        return  new DaoLayerImpl();
    }
}
