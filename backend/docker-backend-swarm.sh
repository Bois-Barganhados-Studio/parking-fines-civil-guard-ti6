# start backend swarm cluster
echo Starting swarm cluster configuration!

export $(cat .env) > /dev/null 2>&1

# creating network overlay
docker network create --driver overlay load_balancer_network
docker network create --driver overlay backend
docker network create --driver overlay kafka_net

# start docker swarm
docker swarm init

# deploy app stacks
docker stack deploy -c docker-compose.yml --resolve-image always backend
docker service ls