import React, { Fragment, useState, useEffect, useRef } from 'react';
import httpClient from "../httpClient";
import axios from 'axios';
import Calendar from 'react-calendar';
import { useNavigate } from 'react-router-dom';
import 'react-calendar/dist/Calendar.css';
import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { Link } from 'react-router-dom'; 
import styles from '../HomePage.module.css';
import { Pie } from 'react-chartjs-2';

export default function HomePage() {

  const [username, setUsername] = useState('');
  const [selectedFile, setSelectedFile] = useState(null);
  const [previewImage, setPreviewImage] = useState(null);
  const fileInputRef = useRef(null);
  const [userId, setUserId] = useState(null);
  const [predictions, setPredictions] = useState(null);
  const [foodDetails, setFoodDetails] = useState(null);
  
  
  const fileSelectedHandler = event => {
    const file = event.target.files[0];
    
    setSelectedFile(file);

    // Preview the selected image
    const reader = new FileReader();
    reader.onloadend = () => {
      setPreviewImage(reader.result);
    };
    reader.readAsDataURL(file);
  }

  const fileUploadHandler = async () => {
    try {
      const response = await httpClient.get('//localhost:5000/@me');
      setUsername(response.data.username);
      
      // Proceed with file upload if the user is logged in
      const formData = new FormData();
      formData.append('img', selectedFile); // Use 'img' as the key
      await httpClient.post('//localhost:5000/upload', formData, {
        onUploadProgress: progressEvent => {
          console.log(progressEvent.loaded / progressEvent.total);
        }
      });
      toast.success('Uploaded successfully');
    } catch (error) {
      // Handle errors, such as redirecting to login page
      window.location.href = '/';
    }
  }
  
  const checkLogin = async () => {
    try {
      const response = await httpClient.get('//localhost:5000/@me');
      setUsername(response.data.username);
      setUserId(response.data.id); // Assuming you have a state for userId
    } catch (error) {
      window.location.href = '/';
    }
  }

  const handleLogOut = async () => {
    await httpClient.get('//localhost:5000/logout');
    window.location.href = '/';
  }

  useEffect(() => {
    checkLogin();
  }, []);
  const handlePredict = async () => {
    try {
      // Proceed with prediction if an image is selected
      if (selectedFile) {
        const formData = new FormData();
        formData.append('img', selectedFile); // Use 'img' as the key
        const predictResponse = await httpClient.post('//localhost:5000/predict', formData);

        // Display the prediction results
        setPredictions(predictResponse.data.pack);
        setFoodDetails({
          name: predictResponse.data.food_item_name,
          proteins: predictResponse.data.proteins,
          fats: predictResponse.data.fats,
          calories: predictResponse.data.calories,
          calcium: predictResponse.data.calcium,
          vitamins: predictResponse.data.vitamins,
        });
        toast.success('Prediction successful');
      } else {
        toast.error('Please select an image first');
      }
    } catch (error) {
      toast.error('Prediction failed');
      console.error('Prediction error:', error);
    }
  };

  // const data = {
  //   labels: ['Proteins', 'Fats', 'Calories', 'Calcium', 'Vitamins'],
  //   datasets: [
  //     {
  //       data: [
  //         foodDetails.proteins || null,
  //         foodDetails.fats || 0,
  //         foodDetails.calories || 0,
  //         foodDetails.calcium || 0,
  //         foodDetails.vitamins || 0,
  //       ],
  //       backgroundColor: [
  //         '#FF6384',
  //         '#36A2EB',
  //         '#FFCE56',
  //         '#FF9F40',
  //         '#FFCD56',
  //       ],
  //       hoverBackgroundColor: [
  //         '#FF6384',
  //         '#36A2EB',
  //         '#FFCE56',
  //         '#FF9F40',
  //         '#FFCD56',
  //       ],
  //     },
  //   ],
  // };
  return (
    <Fragment>
      <ToastContainer />
      
      <div style={{ position: 'absolute', top: 10, right: 10 }}>
        <form onClick={handleLogOut} className='mb-3'>
          <button type='button' className='btn btn-primary'>Logout</button>
        </form>
      </div>
      <div className="row">
        <div className="col-md-4"></div>
        <div className="col-md-4">
          <h1>Welcome {username}</h1>
          {previewImage && <img src={previewImage} alt="Preview" style={{ maxWidth: '100%', maxHeight: '300px' }} />}
          <input
            style={{ display: 'none' }}
            type="file"
            onChange={fileSelectedHandler}
            ref={fileInputRef} />
          <button onClick={() => fileInputRef.current.click()}>Pick File</button>
          {selectedFile && <button onClick={fileUploadHandler}>Upload</button>}
          <button onClick={handlePredict}>Predict</button>
          {/* {predictions && (
            <div>
              <h2>Prediction Results</h2>
              {predictions.map((prediction, index) => (
                <div key={index}>
                  {prediction.image && <img src={prediction.image} alt="Food" />}
                  <p>Food: <a href={prediction.food} target="_blank" rel="noopener noreferrer">{prediction.food}</a></p>
                  {prediction.result && (
                    <>
                      <p>Predictions:</p>
                      <ul>
                        {Object.entries(prediction.result).map(([label, confidence]) => (
                          <li key={label}>{label}: {confidence}%</li>
                        ))}
                      </ul>
                    </>
                  )}
                  {prediction.nutrition && (
                    <>
                      <p>Nutrition Information:</p>
                      <ul>
                        {prediction.nutrition.map((nutrient, index) => (
                          <li key={index}>{nutrient.name}: {nutrient.value}</li>
                        ))}
                      </ul>
                    </>
                  )}
                </div>
              ))}
            </div>
          )} */}
          {foodDetails && (
            <div>
              <h2>{foodDetails.name}</h2>
              <table className={styles.table}>
        <thead>
          <tr>
            <th>Nutrient</th>
            <th>Value</th>
          </tr>
        </thead>
        <tbody>
          <tr>
            <td>Proteins</td>
            <td>{foodDetails.proteins}</td>
          </tr>
          <tr>
            <td>Fats</td>
            <td>{foodDetails.fats}</td>
          </tr>
          <tr>
            <td>Calories</td>
            <td>{foodDetails.calories}</td>
          </tr>
          <tr>
            <td>Calcium</td>
            <td>{foodDetails.calcium}</td>
          </tr>
          <tr>
            <td>Vitamins</td>
            <td>{foodDetails.vitamins}</td>
          </tr>
        </tbody>
      </table>
      
            </div>
          )}
          
        </div>
        <div className="col-md-4"></div>
      </div>
    </Fragment>
  )
}
