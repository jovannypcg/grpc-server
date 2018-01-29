package mx.jovannypcg.grpcserver.amqp;

import mx.jovannypcg.grpcserver.messages.Repository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class Publisher {
    private static final Logger LOGGER = LoggerFactory.getLogger(Publisher.class);
    private static final String QUEUE_NAME = "repository_enrollments";

    private RabbitTemplate rabbitTemplate;

    public Publisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void publish(Repository repository) {
        byte[] repositoryBytes = repository.toByteArray();

        LOGGER.info("Sending message to AMQP: " + repository.getName());
        LOGGER.info("Byte representation: " + Arrays.toString(repositoryBytes));

        rabbitTemplate.convertAndSend(QUEUE_NAME, repositoryBytes);
    }
}
