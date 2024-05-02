from confluent_kafka import Consumer,Producer, KafkaException, KafkaError

# consts to topics
TOPIC_LICENSE_PLATE = 'license-plate-topic-1'
RESPONSE_TOPIC = 'py-response-topic-1'

# Create Consumer instance
def create_consumer():
    conf = {
        'bootstrap.servers': 'kafka:9092',
        'group.id': "%s-consumer" % TOPIC_LICENSE_PLATE,
        'session.timeout.ms': 64000,
        'default.topic.config': {'auto.offset.reset': 'smallest'},
    }
    return c

def create_producer():
    conf = {
        'bootstrap.servers': 'kafka:9092',
        'client.id': 'py-producer',
        'default.topic.config': {'acks': 'all'}
    }
    return Producer(conf)

def consume(c):
    c.subscribe([TOPIC_LICENSE_PLATE])
    # try to connect to kafka and if it fails, retry until it connects
    while True:
        try:
            msg = c.poll(timeout=1.0)
            if msg is None:
                continue
            if msg.error():
                if msg.error().code() == KafkaError._PARTITION_EOF:
                    continue
                else:
                    print(msg.error())
                    raise KafkaException(msg.error())
            print('%% %s [%d] at offset %d with key %s:\n' %
                  (msg.topic(), msg.partition(), msg.offset(),
                   str(msg.key())))
            print(msg.value())
            # Mandar mensagem para 
        except KeyboardInterrupt:
            break
    c.close()

consumer = create_consumer()
consume(consumer)