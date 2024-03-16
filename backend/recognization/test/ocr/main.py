import easyocr
import cv2

reader = easyocr.Reader(['en','pt'], gpu=False)
result = reader.readtext('run/test_ft.jpg')

for (bbox, text, prob) in result:
    print(f'{text} ({prob:.2f})')