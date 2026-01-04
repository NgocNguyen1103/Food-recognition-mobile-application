import tensorflow as tf
from tensorflow.keras.applications import ResNet50
from tensorflow.keras.models import Model
from tensorflow.keras.utils import load_img, img_to_array
import numpy as np

def load_resnet50():
    base_model = ResNet50(weights='imagenet', include_top=False, pooling='avg')
    model = Model(inputs=base_model.input, outputs=base_model.output)
    return model


def preprocess_image(image_path, target_size=(224, 224)):
    img = load_img(image_path, target_size=target_size)
    img_array = img_to_array(img)
    img_array = np.expand_dims(img_array, axis=0)  # Thêm batch size
    img_array = tf.keras.applications.resnet50.preprocess_input(img_array)
      # In pixel mẫu (2x2 đầu tiên)

    return img_array

# Trích xuất đặc trưng từ một ảnh
def extract_features(image_path, model, image_size=(224, 224)):
    img = load_img(image_path, target_size=image_size)
    
    img_array = img_to_array(img)
    img_array = np.expand_dims(img_array, axis=0)
    img_array = tf.keras.applications.resnet50.preprocess_input(img_array)
    print(f"Image shape after resizing: {img_array.shape}")  # Kỳ vọng (1, 224, 224, 3)
    print(f"Sample pixel values after preprocessing: {img_array[0, :2, :2, :]}")  # In giá trị 2x2 đầu tiên
    if img_array.shape != (1, 224, 224, 3):
        raise ValueError(f"Expected input shape (1, 224, 224, 3), got {img_array.shape}")

    features = model.predict(img_array)
    print(f"Extracted features: shape={features.shape}, dtype={features.dtype}")
    print(f"Sample features: {features[0, :10]}")  # In 10 giá trị đặc trưng đầu tiên

    return features
