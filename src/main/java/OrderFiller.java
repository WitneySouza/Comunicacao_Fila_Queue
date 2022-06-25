import java.util.List;

import com.amazonaws.regions.Regions;
import org.json.JSONObject;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.Message;


public class OrderFiller {
    public static void main(String[] args) {
        String queueUrl = "https://sqs.us-east-1.amazonaws.com/638915226488/order-queue";
        BasicSessionCredentials awsCreds = new BasicSessionCredentials("!!!!!!", "!!!!!", "!!!");
        AmazonSQS sqs = AmazonSQSClient.builder().withRegion(Regions.DEFAULT_REGION).withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();

        while (true) {
            List<Message> messages = sqs.receiveMessage(queueUrl).getMessages();
            for (Message m : messages) {
                sqs.deleteMessage(queueUrl, m.getReceiptHandle());
                System.out.println("Recebeu mensagem ID " + m.getMessageId());
                String body = m.getBody();
                JSONObject json = new JSONObject(body);
                Object num = json.get("num");
                System.out.println("Preenchendo pedido # " + num);
            }
        }
    }
}