package service;

import domain.*;
import org.apache.log4j.Logger;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by syrovo01 on 26.08.2016.
 * this is an asyncronous watermark service used to watermark a set of documents, the service should return a ticket for each document
 * ticket can be used for polling the status of the document; if watermarking is finished the document can be retrieved with the ticket
 */
public class WatermarkService extends Observable{
	final static Logger logger = Logger.getRootLogger();
    private static PipelineWrapper pipelineWrapper = new PipelineWrapper();
	//private static Map<UUID, Document> pipeLine = new HashMap<UUID, Document>();



	// add document to the queue and return its ticket
	public static UUID addDocument(Document document){
		// check if document was already watermarked
		if (document.getWatermark() != null){
			logger.info("domain.Document " + document.getTitle() + " is watermarked already");
			return null;
		}
		// generate unique ID for a document
		UUID docID = UUID.randomUUID();

        pipelineWrapper.submitDocument(docID, document);
		//pipeLine.put(docID, document);

		return docID;
	}

	// check status of watermarking
	public static boolean isWatermarked(UUID uuid){
		Document document = pipelineWrapper.getDocumentByUUID(uuid);
		return document.getWatermark() != null;
	}

	public static Document pollDocument(UUID uuid){
		if (isWatermarked(uuid)){
			return pipelineWrapper.getDocumentByUUID(uuid);
		}
		return null;
	}

	// create watermark for a given document
	public static String watermarkDocument(Document document){
		Watermark watermark = null;
		if (document instanceof Book){
			watermark = new Watermark("book", document.getTitle(), document.getAuthor(), ((Book) document).getTopic());
		} else if (document instanceof Journal){
			watermark = new Watermark("journal", document.getTitle(), document.getAuthor());
		}
		document.setWatermark(watermark);
		return watermark.toString();
	}

	public static void runWatermarkService(){
		for (Map.Entry<UUID, Document> documentEntry : pipelineWrapper.getPipeLine().entrySet()){
			watermarkDocument(documentEntry.getValue());
		}

	}

    public static void main(String[] args){
        Document document1 = new Book("title1", "author1", Topic.BUSINESS);
        Document document2 = new Book("title2", "author2", Topic.SCIENCE);
        Document journal1 = new Journal("journal1", "author3");

        WatermarkService.addDocument(document1);
        WatermarkService.addDocument(document2);
        /*watched.setValue("New Value");

        watched.addObserver(watcher);

        watched.setValue("Latest Value");
        */

        final List<Document> documentList = new ArrayList<Document>();
        documentList.add(document1);
        documentList.add(document2);
        documentList.add(journal1);

        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < pipelineWrapper.getPipeLine().size(); i++){
            final int finalI = i;
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    WatermarkService.watermarkDocument(documentList.get(finalI));
                }
            });
        }


    }

}
