from ultralytics import YOLO
import os
from PIL import Image

def list_images_in_folder(folder_path):
    image_paths = []
    allowed_image_extensions = ['.jpg', '.jpeg', '.png', '.gif', '.bmp']

    try:
        for filename in os.listdir(folder_path):
            if any(filename.lower().endswith(ext) for ext in allowed_image_extensions):
                image_paths.append(os.path.join(folder_path, filename))
    except Exception as e:
        print(f"Error: {e}")

    return image_paths


def open_and_store_images(image_paths):
    images = []

    try:
        for path in image_paths:
            img = Image.open(path)
            images.append(img)
    except Exception as e:
        print(f"Error opening images: {e}")

    return images

# Specify the path to the folder containing images
folder_path = "teste/"

 # Get the list of image paths in the specified folder
# image_paths = list_images_in_folder(folder_path)

# Open and store the images in a Python array
# images = open_and_store_images(image_paths)

 # Print the list of opened images
# print("List of opened images:")
# for img in images:
    # print(img)


# # Load a model
# model = YOLO("yolov8n.yaml")  # build a new model from scratch

# # Use the model
# model.train(data="config.yaml", epochs=3)  # train the model

model = YOLO("runs\\detect\\train7\\weights\\best.pt")

results = model.predict('carro.jpg', save=True, save_crop=True, project="run",  name="resultados", exist_ok=True)
