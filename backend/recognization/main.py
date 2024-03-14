import cv2 as cv
import numpy as np
from ultralytics import YOLO
import easyocr

def deskew(im, max_skew=10):
    height, width,zz = im.shape

    # Create a grayscale image and denoise it
    im_gs = cv.cvtColor(im, cv.COLOR_BGR2GRAY)
    im_gs = cv.fastNlMeansDenoising(im_gs, h=3)

    # Create an inverted B&W copy using Otsu (automatic) thresholding
    im_bw = cv.threshold(im_gs, 0, 255, cv.THRESH_BINARY_INV | cv.THRESH_OTSU)[1]

    # Detect lines in this image. Parameters here mostly arrived at by trial and error.
    lines = cv.HoughLinesP(
        im_bw, 1, np.pi / 180, 200, minLineLength=width / 12, maxLineGap=width / 150
    )

    # Collect the angles of these lines (in radians)
    angles = []
    for line in lines:
        x1, y1, x2, y2 = line[0]
        angles.append(np.arctan2(y2 - y1, x2 - x1))

    # If the majority of our lines are vertical, this is probably a landscape image
    landscape = np.sum([abs(angle) > np.pi / 4 for angle in angles]) > len(angles) / 2

    # Filter the angles to remove outliers based on max_skew
    if landscape:
        angles = [
            angle
            for angle in angles
            if np.deg2rad(90 - max_skew) < abs(angle) < np.deg2rad(90 + max_skew)
        ]
    else:
        angles = [angle for angle in angles if abs(angle) < np.deg2rad(max_skew)]

    if len(angles) < 5:
        # Insufficient data to deskew
        return im

    # Average the angles to a degree offset
    angle_deg = np.rad2deg(np.median(angles))

    # If this is landscape image, rotate the entire canvas appropriately
    if landscape:
        if angle_deg < 0:
            im = cv.rotate(im, cv.ROTATE_90_CLOCKWISE)
            angle_deg += 90
        elif angle_deg > 0:
            im = cv.rotate(im, cv.ROTATE_90_COUNTERCLOCKWISE)
            angle_deg -= 90

    # Rotate the image by the residual offset
    M = cv.getRotationMatrix2D((width / 2, height / 2), angle_deg, 1)
    im = cv.warpAffine(im, M, (width, height), borderMode=cv.BORDER_REPLICATE)
    return im

def remove_noise(image):
    return cv.fastNlMeansDenoisingColored(image, None, 10, 10, 7, 15)

model = YOLO('best.pt')

results = model.predict('./resources/test2.jpg', save=True, save_crop=True, project="run",  name="resultados", exist_ok=True)

cropped = 'run/resultados/crops/License_Plate/test2.jpg'

imcropped = cv.imread(cropped)

# license_plate_crop_gray = cv.cvtColor(imcropped, cv.COLOR_BGR2GRAY)
# _, license_plate_crop_thresh = cv.threshold(license_plate_crop_gray, 64, 255, cv.THRESH_OTSU)

# cv.imshow('thresh', license_plate_crop_thresh)
# cv.waitKey(0)

#invert colors of image
#imcropped = cv.bitwise_not(imcropped)
norm_img = np.zeros((imcropped.shape[0], imcropped.shape[1]))
img = cv.normalize(imcropped, norm_img, 0, 255, cv.NORM_MINMAX)
upscale = 300 / img.shape[0]
img = cv.resize(img, (0,0), fx=upscale, fy=upscale)

img = deskew(img)

img = cv.cvtColor(img, cv.COLOR_BGR2GRAY)
img = cv.fastNlMeansDenoising(img, h=3)
img = cv.threshold(img, 0, 255, cv.THRESH_BINARY + cv.THRESH_OTSU)[1]

cv.imshow('crop', img)
cv.waitKey(0)

kernel = np.ones((5,5),np.uint8)
erosion = cv.erode(img,kernel,iterations = 1)


blur=cv.GaussianBlur(erosion, (5,5), 0)

cv.imshow('thresh', blur)
cv.waitKey(0)

cv.imwrite('run/test_ft.jpg', erosion)

reader = easyocr.Reader(['en'], gpu=False)
result = reader.readtext('run/test_ft.jpg')

for (bbox, text, prob) in result:
    print(f'{text} ({prob:.2f})')