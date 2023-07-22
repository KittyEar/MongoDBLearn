package sample;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.*;
import org.bson.Document;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CsvImporter {
    public static void main(String[] args) {
        String mongoUri = "mongodb://localhost:27017";
        String databaseName = "bookstore";
        String csvDirectory = "D:\\IdeaProject\\CSVToJava\\target\\classes\\csv";

        try (MongoClient mongoClient = MongoClients.create(mongoUri)) {
            MongoDatabase database = mongoClient.getDatabase(databaseName);

            File[] csvFiles = new File(csvDirectory).listFiles((dir, name) -> name.toLowerCase().endsWith(".csv"));

            if (csvFiles != null) {
                for (File csvFile : csvFiles) {
                    String collectionName = csvFile.getName().substring(0, csvFile.getName().lastIndexOf('.'));
                    MongoCollection<Document> collection = database.getCollection(collectionName);
                    String csvContent = new String(Files.readAllBytes(Paths.get(csvFile.getAbsolutePath())));
                    String[] csvLines = csvContent.split("\\r?\\n");

                    // 清空集合
                    collection.deleteMany(new Document());

                    for (String csvLine : csvLines) {
                        String[] fields = csvLine.split(",");
                        Document document = new Document();
                        for (int i = 0; i < fields.length; i++) {
                            document.append("field" + i, fields[i]);
                        }
                        collection.insertOne(document);
                    }

                    System.out.println("Imported " + csvLines.length + " records from " + csvFile.getName() + " into " + collectionName);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

