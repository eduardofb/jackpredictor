import predictionio

engine_client = predictionio.EngineClient(url="localhost:8000")
print(engine_client.send_query({"features": [3, 0, 45]}))
