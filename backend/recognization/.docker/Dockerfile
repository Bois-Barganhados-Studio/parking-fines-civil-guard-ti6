# create kafka consumer with python
FROM python:3.7

WORKDIR /workspace/ai

#install dependencies
RUN apt-get update && apt-get install -y

# install linux dependencies to python ai libraries (gcc, g++, omp, python)
RUN apt-get install -y build-essential gcc g++

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

# copy the content of the local src directory to the working directory
COPY . .

# command to run on container start

CMD [ "python", "main.py" ]