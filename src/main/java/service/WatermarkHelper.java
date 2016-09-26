package service;

import domain.Document;

import java.util.*;

/**
 * Created by olgasyrova on 08.09.16.
 */
public class WatermarkHelper implements Observer {

    @Override
    public void update(Observable o, Object pipeline) {
        for (Map.Entry<UUID, Document> documentEntry : ((HashMap<UUID, Document>) pipeline).entrySet()){
            WatermarkService.watermarkDocument(documentEntry.getValue());
        }
    }
}
