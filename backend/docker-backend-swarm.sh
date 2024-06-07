# start backend swarm cluster
echo Starting swarm cluster configuration!

docker swarm init

export $(cat .env) > /dev/null 2>&1

# creating network overlay
docker network create --driver overlay load_balancer_network
docker network create --driver overlay backend
docker network create --driver overlay kafka_net

docker stack deploy -c docker-compose.yml --resolve-image always --with-registry-auth backend
docker service ls