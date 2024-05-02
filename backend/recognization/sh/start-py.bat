@echo off
pip install -r requirements.txt
pip uninstall opencv-python-headless -y 
pip install opencv-python --upgrade