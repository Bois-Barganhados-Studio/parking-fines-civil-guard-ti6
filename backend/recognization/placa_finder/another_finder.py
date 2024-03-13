import cv2
import imutils

def preprocess_image(image):
    #Convertendo para GrayScale
    gray= cv2.cvtColor(image, cv2.COLOR_BGR2GRAY)
    
    # Reduzindo ruídos e melhorando contraste
    blurred = cv2.bilateralFilter(gray, 11, 17, 17)
    
    # Detectando bordas utilizando Canny
    edges = cv2.Canny(blurred, 30, 200)
    
    return edges

def find_license_plate(image, id):
    
    image = cv2.imread(image)
    
    # Pré-processamento da imagem
    edges = preprocess_image(image)
    
    # Encontrando contornos na imagem
    contours = cv2.findContours(edges.copy(), cv2.RETR_TREE, cv2.CHAIN_APPROX_SIMPLE)
    contours = imutils.grab_contours(contours)
    contours = sorted(contours, key=cv2.contourArea, reverse=True)[:5]

    # Loop sobre os contornos encontrados
    for contour in contours:
        # Aproximando o contorno para um polígono
        perimeter = cv2.arcLength(contour, True)
        approx = cv2.approxPolyDP(contour, 0.02 * perimeter, True)
        
        # Se o contorno aproximado tiver 4 vértices, é provável que seja a placa
        if len(approx) == 4:
            # Calculando o retângulo delimitador da placa
            (x, y, w, h) = cv2.boundingRect(approx)
            aspect_ratio = w / float(h)
            
            # Verificando se o aspect ratio está dentro de um intervalo razoável
            if 2.0 <= aspect_ratio <= 5.0:
                # Recortando a região da placa
                license_plate = image[y:y+h, x:x+w]
                cv2.waitKey(0)
                cv2.imwrite('./croped/an_crop'+str(id)+'.jpg', license_plate)
                return './croped/an_crop'+str(id)+'.jpg'
    
    return None