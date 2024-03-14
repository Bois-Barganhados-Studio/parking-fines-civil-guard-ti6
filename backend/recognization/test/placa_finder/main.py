import finder as f

import another_finder as af

tests = ['resources/test1.jpg', 'resources/test2.jpg', 'resources/test3.jpg']
id = 1

for test in tests:
    f.preprocessing(test, id)
    af.find_license_plate(test, id)
    id += 1
    
print('Done!')