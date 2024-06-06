import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import styles from '../DayInfoPage.module.css'; 

const DayInfoPage = () => {
  const { date } = useParams();
  const [meals, setMeals] = useState([]);
  const [loading, setLoading] = useState(true);
  const [userId, setUserId] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    const fetchUserId = async () => {
      try {
        const response = await fetch('//localhost:5000/@me', { credentials: 'include' });
        if (!response.ok) {
          throw new Error('Failed to fetch user ID');
        }
        const data = await response.json();
        setUserId(data.id);
      } catch (error) {
        console.error('Error fetching user ID:', error);
      }
    };

    fetchUserId();
  }, []);

  useEffect(() => {
    if (!userId) return;

    const fetchMeals = async () => {
      try {
        const response = await fetch(`//localhost:5000/meals/${userId}/${date}`, { credentials: 'include' });
        if (!response.ok) {
          throw new Error('Failed to fetch meals');
        }
        const data = await response.json();
        setMeals(data);
        setLoading(false);
      } catch (error) {
        console.error(error);
        setLoading(false);
      }
    };

    fetchMeals();
  }, [userId, date]);

  const handleHomeClick = () => {
    navigate('/home');
  };

  return (
    <div style={{ position: 'relative' }}>
      <button
        onClick={handleHomeClick}
        className={styles['button-89']} // Apply the CSS module class
        style={{
          position: 'absolute',
          top: '10px',
          right: '10px',
        }}
      >
        Home
      </button>
      <h1>Meals for {date}</h1>
      {loading ? (
        <p>Loading...</p>
      ) : (
        meals.length > 0 ? (
          meals.map((meal) => (
            <div key={meal.MealID} className={styles.mealContainer}>
              <div className={styles.square}>
                <h2 className={styles.foodName}><strong>Food Name:</strong> {meal.FoodName || 'N/A'}</h2>
              </div>
              <div className={styles.mealDetails}>
                <p>Date added: {meal.Timestamp}</p>
                {/* <p>UserID: {meal.UserID}</p> */}
                {/* <p>Image Path: {meal.ImagePath}</p> */}
                <h3>Food Item Details</h3>
                <table style={{ width: '200%', borderCollapse: 'collapse' }}>
                  <thead>
                    <tr>
                      <th style={{ border: '4px solid #0A6847', padding: '12px' }}>Calories</th>
                      <th style={{ border: '4px solid #0A6847', padding: '12px' }}>Proteins</th>
                      <th style={{ border: '4px solid #0A6847', padding: '12px' }}>Fats</th>
                      <th style={{ border: '4px solid #0A6847', padding: '12px' }}>Carbohydrates</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td style={{ border: '3px solid #7ABA78', padding: '12px' }}>{meal.Calories || 'N/A'}</td>
                      <td style={{ border: '3px solid #7ABA78', padding: '12px' }}>{meal.Proteins || 'N/A'}</td>
                      <td style={{ border: '3px solid #7ABA78', padding: '12px' }}>{meal.Fats || 'N/A'}</td>
                      <td style={{ border: '3px solid #7ABA78', padding: '12px' }}>{meal.Carbohydrates || 'N/A'}</td>
                    </tr>
                  </tbody>
                </table>
              </div>
            </div>
          ))
        ) : (
          <p>No meals found for this date.</p>
        )
      )}
    </div>
  );
};

export default DayInfoPage;
