package state;

import communications.Utils;
import product.Product;

public enum State {
    PREPARING_TO_BUILD , PREPARING_TO_EDIT , CONFIRMED,PREPARING_TO_BE_DELETED , DELETED,
    BUILD_ACCEPTED , BUILD_DECLINED, EDIT_ACCEPTED , EDIT_DECLINED, DELETE_ACCEPTED, DELETE_DECLINED;

    public static State convertJsonStringToState(String jsonString){
        return (State) Utils.convertStringToObject(jsonString, "state.State");
    }
}
