# start backend swarm cluster
echo Starting swarm cluster configuration!

export $(cat .env) > /dev/null 2>&1

# creating network overlay
#docker network create --driver overlay load_balancer_front_network
# start docker swarm
docker swarm init

# deploy app stacks
docker stack deploy -c docker-compose.yml --resolve-image always frontend
docker service ls