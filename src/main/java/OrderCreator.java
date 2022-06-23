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
        BasicSessionCredentials awsCreds = new BasicSessionCredentials("ASIAZJQSQVN4BMXN6ABX", "e8LQGzAEqgkxFy21lTNR0tnmPYFw6tAMQM/K/r4a", "FwoGZXIvYXdzEGIaDH8R32Q5ZT4OGlff5iLQAfNnpjiz0ICoZI9qc1vQ5IQFIsrRnebQjNzN9W1iQp8SWzPB87Yemhp2tVHDjQdayx62pFqK0AStkmZdqBu+1o6vQM/pux8dN2SKXTHDDaozJQqCbcHRDjBe95hmXmpt9R2d/lyZpp0w7PcDX1weW/iCzcKrijN9n4nJY2LXFPOtYzAVQmnqPiebYm5uMNOSqcIE16244wSfvOUvkSNFoNAo4gRF8OJ39JmN92YmJby/+wKBr/skDxn5gxjsfP4sqYFWeVK0Wai5K3rmh0j/3pgooerOlQYyLS+fndx7u7uryAKdFdsUvnSaZp2S0KPYgGX5SFkvO3aWxNCzfpoqoboCD1yFQw==");
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