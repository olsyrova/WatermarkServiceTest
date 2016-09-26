package service;

import domain.Document;

import javax.print.Doc;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.UUID;

/**
 * Created by olgasyrova on 08.09.16.
 */
public class PipelineWrapper extends Observable {
    private static Map<UUID, Document> pipeLine;
    private WatermarkHelper watermarkHelper;

    PipelineWrapper(){
        pipeLine = new HashMap<UUID, Document>();
        watermarkHelper = new WatermarkHelper();
        this.addObserver(watermarkHelper);
    }

    public Map<UUID, Document> getPipeLine() {
        return pipeLine;
    }

    public void submitDocument(UUID uuid, Document document){
        pipeLine.put(uuid, document);
        setChanged();
        notifyObservers(pipeLine);
    }

    public Document getDocumentByUUID(UUID uuid){
        if (pipeLine.get(uuid) == null){
            //logger.info("No such document in the pipeline");
        }
        return pipeLine.get(uuid);
    }
}
