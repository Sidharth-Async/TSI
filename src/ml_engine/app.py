from flask import Flask, request, jsonify
import pandas as pd
from sklearn.linear_model import LinearRegression
import numpy as np

app = Flask(__name__)

# --- 1. THE "FAKE" TRAINING (Done once at startup) ---
# In a real app, you would load a saved .pkl file here.
# We will train a simple Linear Regression model right now.
# X = Vehicle Count, Y = Optimal Green Light Duration
X_train = np.array([[10], [30], [50], [80], [100], [120], [150]])
y_train = np.array([[15], [25], [40], [60], [75],  [90],  [120]])

model = LinearRegression()
model.fit(X_train, y_train)

print("🤖 ML Model Trained & Ready!")

# --- 2. PREDICTION ENDPOINT ---
@app.route('/predict', methods=['POST'])
def predict():
    try:
        data = request.json
        vehicle_count = data.get('vehicleCount', 0)

        # Run the AI Prediction
        prediction_input = np.array([[vehicle_count]])
        predicted_time = model.predict(prediction_input)[0][0]

        # Logic: Cap the time between 10s and 120s
        optimal_time = max(10, min(120, int(predicted_time)))

        # Determine Priority based on complexity
        priority = "Low"
        if optimal_time > 60: priority = "High"
        elif optimal_time > 30: priority = "Medium"

        return jsonify({
            "suggestedDuration": optimal_time,
            "priorityLevel": priority,
            "suggestedAction": f"Set Green Light to {optimal_time}s"
        })

    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == '__main__':
    # Run on Port 5000 (Different from Spring Boot's 8080)
    app.run(port=5000)