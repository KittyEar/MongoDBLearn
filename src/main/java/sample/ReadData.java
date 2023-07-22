package sample;

import com.mongodb.client.*;
import org.bson.Document;

public class ReadData {
    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // database and collection code goes here
            MongoDatabase db = mongoClient.getDatabase("bookstore");
            MongoCollection<Document> coll = db.getCollection("books");
            // find code goes here
            MongoCursor<Document> cursor = coll.find().iterator();
            // iterate code goes here
            try {
                while (cursor.hasNext()) {
                    System.out.println(cursor.next().toJson());
                }
            } finally {
                cursor.close();
            }
        }
    }
}
