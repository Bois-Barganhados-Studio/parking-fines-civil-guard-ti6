import cv2 as cv
import numpy as np
from ultralytics import YOLO
import easyocr
import os
import math
from deskew import determine_skew
from typing import Tuple, Union

def deskewcustom(im, max_skew=10):
    height, width,zz = im.shape
    im_gs = cv.cvtColor(im, cv.COLOR_BGR2GRAY)
    im_gs = cv.fastNlMeansDenoising(im_gs, h=3)
    im_bw = cv.threshold(im_gs, 0, 255, cv.THRESH_BINARY_INV | cv.THRESH_OTSU)[1]
    lines = cv.HoughLinesP(
        im_bw, 1, np.pi / 180, 200, minLineLength=width / 12, maxLineGap=width / 150
    )
    angles = []
    for line in lines:
        x1, y1, x2, y2 = line[0]
        angles.append(np.arctan2(y2 - y1, x2 - x1))
    landscape = np.sum([abs(angle) > np.pi / 4 for angle in angles]) > len(angles) / 2
    if landscape:
        angles = [
            angle
            for angle in angles
            if np.deg2rad(90 - max_skew) < abs(angle) < np.deg2rad(90 + max_skew)
        ]
    else:
        angles = [angle for angle in angles if abs(angle) < np.deg2rad(max_skew)]
    if len(angles) < 5:
        print('Insufficient data to deskew')
        return im
    angle_deg = np.rad2deg(np.median(angles))
    if landscape:
        if angle_deg < 0:
            im = cv.rotate(im, cv.ROTATE_90_CLOCKWISE)
            angle_deg += 90
        elif angle_deg > 0:
            im = cv.rotate(im, cv.ROTATE_90_COUNTERCLOCKWISE)
            angle_deg -= 90
    M = cv.getRotationMatrix2D((width / 2, height / 2), angle_deg, 1)
    im = cv.warpAffine(im, M, (width, height), borderMode=cv.BORDER_REPLICATE)
    return im

def remove_noise(image):
    return cv.fastNlMeansDenoisingColored(image, None, 10, 10, 7, 15)

def rotate(
        image: np.ndarray, angle: float, background: Union[int, Tuple[int, int, int]]
) -> np.ndarray:
    old_width, old_height = image.shape[:2]
    angle_radian = math.radians(angle)
    width = abs(np.sin(angle_radian) * old_height) + abs(np.cos(angle_radian) * old_width)
    height = abs(np.sin(angle_radian) * old_width) + abs(np.cos(angle_radian) * old_height)

    image_center = tuple(np.array(image.shape[1::-1]) / 2)
    rot_mat = cv.getRotationMatrix2D(image_center, angle, 1.0)
    rot_mat[1, 2] += (width - old_width) / 2
    rot_mat[0, 2] += (height - old_height) / 2
    return cv.warpAffine(image, rot_mat, (int(round(height)), int(round(width))), borderValue=background)



model = YOLO('best.pt')
responses = []

def recognize(model, path):

    prediction = model.predict(path, save=True, save_crop=True, project="run",  name="resultados", exist_ok=True)
    
    #check if confidence is greater than 0.6
    print(prediction)

    cropped = 'run/resultados/crops/License_Plate/' + path.split('/')[-1]

    imcropped = cv.imread(cropped)

    norm_img = np.zeros((imcropped.shape[0], imcropped.shape[1]))
    img = cv.normalize(imcropped, norm_img, 0, 255, cv.NORM_MINMAX)
    upscale = 300 / img.shape[0]
    img = cv.resize(img, (0,0), fx=upscale, fy=upscale)
    img = cv.cvtColor(img, cv.COLOR_BGR2GRAY)
    angle = determine_skew(img)
    img = rotate(img, angle, (0, 0, 0))
    img = cv.fastNlMeansDenoising(img, h=3)
    img = cv.threshold(img, 64, 255, cv.THRESH_BINARY_INV + cv.THRESH_OTSU)[1]
    
    cv.imshow('threshold', img)
    cv.waitKey(0)

    kernel = np.ones((2,2),np.uint8)
    erosion = cv.dilate(img,kernel,iterations = 2)
    #Remover frestas e buracos em caracteres
    kernelmorph = np.ones((1,1),np.uint8)
    erosion = cv.morphologyEx(erosion, cv.MORPH_CLOSE, kernelmorph)
    kernel_erosion = np.ones((2,2),np.uint8)
    erosion = cv.erode(erosion, kernel_erosion, iterations=2)

    path_test = 'run/test_ft.jpg'

    cv.imwrite(path_test, erosion)
    cv.imshow('erosion', erosion)
    cv.waitKey(0)
    print(erosion.shape[1]/2)
    reader = easyocr.Reader(['en'], gpu=False)
    result = reader.readtext(path_test, allowlist='ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890-', blocklist='!@#$%^&*()_+=[]{}|.:;<>?/`~',
                             paragraph=False, min_size=erosion.shape[1]/2, rotation_info=[-30, 0, 30])

    textap = ''
    for (bbox, text, prob) in result:
        print(f'{text} ({prob:.2f})')
        textap += text + ' '
    return textap
    
def test_images(folder_path, model, responses=[]):
    if not os.path.isdir(folder_path):
        print("Invalid directory path.")
        return
    files = os.listdir(folder_path)
    image_files = [file for file in files if file.lower().endswith(('.png', '.jpg', '.jpeg', '.gif', '.bmp'))]
    for image_file in image_files:
        image_path = os.path.join(folder_path, image_file)
        image_path = image_path.replace('\\', '/')
        try:
            responses.append(recognize(model, image_path))
        except Exception as e:
            print(f"Error displaying image {image_file}: {e}")
    return responses

print('start testing...')     
print('responses:')    
print(test_images('resources', model))