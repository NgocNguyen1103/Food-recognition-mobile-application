from flask import Flask, request, jsonify
from tensorflow.keras.models import load_model
from features_extractor import extract_features
import numpy as np
import os

app = Flask(__name__)

MODEL_PATH = "resnet50_model.h5"
FEATURE_PATH = "features.npy"
LABEL_PATH = "labels.npy"

model = load_model(MODEL_PATH)
features = np.load(FEATURE_PATH)
labels = np.load(LABEL_PATH)

@app.route("/predict", methods=["POST"])
def predict():
    if "file" not in request.files:
        return jsonify({"error": "No file uploaded"}), 400

    file = request.files["file"]
    if file.filename == "":
        return jsonify({"error": "No file selected"}), 400

    try:

        temp_dir = "temp"
        if not os.path.exists(temp_dir):
            os.makedirs(temp_dir)
        filepath = os.path.join(temp_dir, file.filename)
        file.save(filepath)
        query_features = extract_features(filepath, model)

        similarities = np.dot(features, query_features.T).flatten()
        best_index = np.argmax(similarities)
        best_label = labels[best_index]

        return best_label
    except Exception as e:
        return jsonify({"error": str(e)}), 500

if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)
