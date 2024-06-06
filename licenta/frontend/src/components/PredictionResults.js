// PredictPage.js
import React, { useState, useEffect } from 'react';
import httpClient from '../httpClient';
import { useLocation } from 'react-router-dom';

export default function PredictPage() {
    const [predictions, setPredictions] = useState([]);
    const [averageNutrition, setAverageNutrition] = useState([]);
  
    useEffect(() => {
      const fetchPredictions = async () => {
        try {
          const response = await httpClient.post('//localhost:5000/predict');
          setPredictions(response.data.pack);
          setAverageNutrition(response.data.whole_nutrition);
        } catch (error) {
          console.error('Error fetching predictions:', error);
        }
      };
  
      fetchPredictions();
    }, []);
  
    return (
      <div>
        <h1>Prediction Results</h1>
        {predictions.map((prediction, index) => (
          <div key={index}>
            <img src={prediction.image} alt="Food" />
            <p>Food: {prediction.food}</p>
            <p>Predictions:</p>
            <ul>
              {Object.entries(prediction.result).map(([label, confidence]) => (
                <li key={label}>{label}: {confidence}%</li>
              ))}
            </ul>
            <p>Nutrition Information:</p>
            <ul>
              {prediction.nutrition.map((nutrient, index) => (
                <li key={index}>{nutrient.name}: {nutrient.value}</li>
              ))}
            </ul>
          </div>
        ))}
        <h2>Average Nutrition</h2>
        <ul>
          {averageNutrition.map((nutrient, index) => (
            <li key={index}>{nutrient.name}: {nutrient.value}</li>
          ))}
        </ul>
      </div>
    );
  }