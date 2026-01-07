import requests
import random
import time

# API Endpoint
URL = "http://localhost:8080/api/traffic/log"

# Intersection IDs (1 to 5)
INTERSECTIONS = [1, 2, 3, 4, 5]

print("🚀 Starting City-Wide Traffic Simulation...")

while True:
    for i_id in INTERSECTIONS:
        # Generate random traffic data
        cars = random.randint(10, 120) # 10 to 120 cars
        speed = random.randint(5, 80)  # 5 to 80 km/h

        payload = {
            "intersectionId": i_id,
            "vehicleCount": cars,
            "averageSpeed": speed
        }

        try:
            requests.post(URL, json=payload)
            print(f"📡 Update sent for Intersection {i_id}: {cars} cars")
        except:
            print("❌ Server Down?")

    # Wait 3 seconds before next batch update
    time.sleep(3)