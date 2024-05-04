from confluent_kafka import Consumer, Producer, KafkaException, KafkaError
from recognization import start_recognization
import os

# consts to topics
TOPIC_LICENSE_PLATE = 'license-plate-topic-1'
RESPONSE_TOPIC = 'py-response-topic-1'

def delivery_callback(err, msg):
    if err:
        print('%% Message failed delivery: %s\n', err)
    else:
        print('%% Message delivered to %s [%d]\n',
                          (msg.topic(), msg.partition()))

# Create Consumer instance
def create_consumer():
    conf = {
        'bootstrap.servers': 'kafka:9092',
        'group.id': "%s-consumer" % TOPIC_LICENSE_PLATE,
        'session.timeout.ms': 64000,
        'default.topic.config': {'auto.offset.reset': 'earliest'},
        'enable.auto.commit': True,
        'auto.offset.reset': 'earliest',
        'enable.partition.eof': True
    }
    return Consumer(conf) 

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
            print(os.path.isfile(msg.value()))
            print(os.path.exists(msg.value()))
            print(os.path.abspath(msg.value()))
            # after reading mark the message as consumed
            c.commit(asynchronous=False)
            # Mandar mensagem para AI
            response = start_recognization(msg.value())
            topic = RESPONSE_TOPIC
            p = create_producer()
            try:
                data = response
                p.produce(topic, data, callback=delivery_callback)
            except BufferError as e:
                print('%% Local producer queue is full (%d messages awaiting delivery): try again\n',len(p))
            p.poll(0)
            print('%% Waiting for %d deliveries\n' % len(p))
            p.flush()
        except KeyboardInterrupt:
            break
    c.close()

consumer = create_consumer()
consume(consumer)