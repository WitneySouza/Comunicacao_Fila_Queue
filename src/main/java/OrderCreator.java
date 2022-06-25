import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicSessionCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClient;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.amazonaws.services.sqs.model.SendMessageResult;

import java.util.Random;

public class OrderCreator {
    public static void main(String[] args) {
        String queueUrl = "https://sqs.us-east-1.amazonaws.com/638915226488/order-queue";
        BasicSessionCredentials awsCreds = new BasicSessionCredentials("!!!!", "!!!!!!", "!!!!!");
        AmazonSQS sqs = AmazonSQSClient.builder().withRegion(Regions.DEFAULT_REGION).withCredentials(new AWSStaticCredentialsProvider(awsCreds)).build();
        int orderNum = 1;
        Random random = new Random();


        while (true) {
            long time = random.nextInt(30000);
            try {
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SendMessageRequest send_msg_request = new SendMessageRequest()
                    .withQueueUrl(queueUrl)
                    .withMessageBody("{ \"num\": " + orderNum + ", \"product\": \"x\"}");
            SendMessageResult result = sqs.sendMessage(send_msg_request);
            System.out.println("Criou pedido # " + orderNum + ". Message ID: " +
                    result.getMessageId());
            orderNum++;
        }
    }
}