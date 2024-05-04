# create kafka consumer with python
FROM python:3.7

WORKDIR /workspace/ai

#install dependencies
RUN apt-get update && apt-get install -y libgl1-mesa-glx libglib2.0-0 libsm6 ffmpeg
# install linux dependencies to python ai libraries (gcc, g++, omp, python)
RUN apt-get install -y build-essential gcc g++

COPY requirements.txt .
RUN pip install --no-cache-dir -r requirements.txt

RUN mkdir -p ~/.EasyOCR/model

COPY ocr_model/* ~/.EasyOCR/model/

# copy the content of the local src directory to the working directory
COPY src/ .

# command to run on container start
CMD [ "python", "./main.py" ]