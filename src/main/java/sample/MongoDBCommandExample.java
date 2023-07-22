package sample;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

public class MongoDBCommandExample {

    public static void main(String[] args) {
        String uri = "mongodb://localhost:27017";
        // 连接到 MongoDB 服务器
        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // 选择数据库
            MongoDatabase database = mongoClient.getDatabase("bookstore");

            // 执行原生的 MongoDB 更新命令
            Document command = Document.parse("{ insert: 'books', documents: [ { \"title\": \"双水\", \"author\": \"喜茶\", \"pages\": 566, \"genres\": [\"科幻\", \"爱情\", \"悬疑\"], \"rating\": 9 } ] }");
            Document result = database.runCommand(command);

            System.out.println("更新命令执行结果：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
