# Deen Connector

This is an Islamic desktop application built with JavaFX, enhanced with two AI-powered features:

  - Salah Steps API – Guides users through the steps of Salah (prayer).

  - Emotion Classifier API – Classifies emotions using a pre-trained model and returns a Dua or Quranic verse to help the user cope with that specific emotion.

Both AI features are written in Python and run locally through APIs, while Firebase is used for authentication and data storage.

## Overview

  - The app connects to two locally hosted AI APIs (run in Google Colab).

  - Users need to configure API URLs, Firebase, and Gmail credentials in the config.properties file.

  - Once setup is complete, the JavaFX application will run seamlessly.

## How to Use
#### 1) Set Up AI APIs

  - Download the API.ipynb notebook.

  - Open it in Google Colab.

  - Upload the contents of the models/ folder (all encoders and model files).

  - Run all cells in the notebook.

  - When the server starts, you will receive a public URL.

Copy this URL into your config.properties file for both:

  - SalahStepsAPIURL

  - emotionClassifierAPI

Note: If the Colab session restarts, you will get a new URL. Update config.properties again.

#### 2) Set Up Firebase

  1. Go to the Firebase Console and create a new project.

  2. In Project Settings, find your:

      - firebaseAuthAPIKey (Web API Key)

      - databaseURL (Realtime Database URL)
    
  3. Add these values into your config.properties file.

#### 3) Set Up Email Sending (Gmail)

  1. Go to your Google Account → Security → App passwords.

  2. Create a new App Password (select Mail → Your Device).

  3. Copy the generated password.

  4. Add it into config.properties as senderPassword.

#### 4) Run the App

  1. Make sure the Colab notebook is running.

  2. Make sure your config.properties file contains valid keys and URLs.

  3. Run the JavaFX project.

  4. Everything should work.

## Project Structure

  - models/ → Pre-trained models and encoders

  - API.ipynb → Python API notebook (run in Colab)

  - config.properties → Configuration file for API keys and URLs

  - src/ → JavaFX source code

  - README.md → Project documentation
