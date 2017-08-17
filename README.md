Docker Jack Predictor
=====================

1. Run ```docker build -t jackpredictor:latest . ``` to build the image
2. Run ```docker run -p 8000:8000 jackpredictor``` to start the container
3. Run ```python teste.py``` to test the predictor

## Usage
``` curl -H "Content-Type: application/json" -d '{"features": [3, 0, 45]}' http://localhost:8000/queries.json ```

Elements in json:
1. social class (1 OR 2 OR 3)
2. sex (0: Man, 1: Woman)
3. age
