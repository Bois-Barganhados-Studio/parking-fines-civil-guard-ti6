@echo off

@REM start backend swarm cluster
echo Starting swarm cluster configuration!

@REM start docker swarm
docker swarm init

@REM Export environment variables from .env file
for /f "delims=" %%x in ('type .env') do set "%%x"

@REM creating network overlay
docker network create --driver overlay load_balancer_front_network

@REM deploy app stacks
docker stack deploy -c docker-compose.yml --resolve-image always --with-registry-auth frontend
docker service ls
