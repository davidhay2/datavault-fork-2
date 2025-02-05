package org.datavaultplatform.broker.queue;

import static org.datavaultplatform.broker.config.QueueConfig.WORKER_QUEUE_NAME;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.nio.charset.StandardCharsets;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueInformation;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

public class RabbitSenderIT extends BaseRabbitTCTest {

  @Autowired
  RabbitTemplate template;

  @Autowired
  RabbitAdmin admin;

  @Autowired
  Sender sender;

  @Autowired
  @Qualifier("workerQueue")
  Queue dataVaultQueue;

  @Value(WORKER_QUEUE_NAME)
  String expectedQueueName;

  @BeforeEach
  void checkSendQueueIsEmptyBeforeTest() {
    QueueInformation info = admin.getQueueInfo(expectedQueueName);
    assertEquals(0, info.getMessageCount());
  }

  @Test
  void testSendToWorker() {
    assertEquals(expectedQueueName, dataVaultQueue.getActualName());
    String rand = UUID.randomUUID().toString();
    String messageId = sender.send(rand);
    Message message = template.receive(dataVaultQueue.getActualName(), 2500);
    assertEquals(rand, new String(message.getBody(), StandardCharsets.UTF_8));
    assertEquals(messageId, message.getMessageProperties().getMessageId());
    MessageProperties props = message.getMessageProperties();
    assertEquals(expectedQueueName, props.getReceivedRoutingKey());
  }


}
