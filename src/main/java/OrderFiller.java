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
        String queueUrl = "https://sqs.us-east-1.amazonaws.com/451619603272/order-queue";
        BasicSessionCredentials awsCreds = new BasicSessionCredentials("ASIAWSJVAINEPU5YLD7I", "a4/cT961ikCDzpIay3h1waPg34+plPtIvso/j+AL", "FwoGZXIvYXdzEGsaDMGxPDnCPO2LsAQwAiLGAbbOsSvfgBNW6zMwSgDiicPQzekf2KONOj5BbyiQ8LoKbjAvtii2+/5IfH6EDmlNULdL86kfK+aOfboaujWDCP7yHhzVFEWf6ozFwrj0C3r4kgPhw02AUMWc/D7eVySPEiS3TpqF3opZ5gfNXPSYERbbLEdq5SNOSswdMajXti0nOMYzEV3MCSGId0PpFMtEUDZEX1WpAnwCgexE9YH6h2qSNWQAhKw3yUI6wLjn5HBZN/cA1hLt1MxzRnavEB8nuQVi1t3XMyjBypiVBjItWOCtJO5BHniBi83yJHNp8o27hNiqq9F3469YT8zKSP+73aL6NQ9irpxC19No");
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