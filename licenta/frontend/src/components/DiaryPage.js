// import React, { Fragment, useState } from 'react';
// import Calendar from 'react-calendar';
// import 'react-calendar/dist/Calendar.css';

// export default function DiaryPage() {
//   const [date, setDate] = useState(new Date());

//   const onDateChange = (newDate) => {
//     setDate(newDate);
//   };

//   return (
//     <Fragment>
//       <div className="container">
//         <div className="row justify-content-center">
//           <div className="col-md-6 text-center">
//             <h1>Select a Date</h1>
//             <div className="calendar-container">
//               <Calendar onChange={onDateChange} value={date} />
//             </div>
//           </div>
//         </div>
//       </div>
//     </Fragment>
//   );
// }

import dayjs from 'dayjs';
import { DemoContainer, DemoItem } from '@mui/x-date-pickers/internals/demo';
import { AdapterDayjs } from '@mui/x-date-pickers/AdapterDayjs';
import { LocalizationProvider } from '@mui/x-date-pickers/LocalizationProvider';
import { DateCalendar } from '@mui/x-date-pickers/DateCalendar';
import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import styles from './DiaryPage.module.css'; 

export default function DiaryPage() {
  // const [mealDates, setMealDates] = useState([]);
  // const navigate = useNavigate();

  // useEffect(() => {
  //   // Fetch meal dates from the backend
  //   const fetchMealDates = async () => {
  //     try {
  //       const response = await axios.get('//localhost:5000/meals/dates', { withCredentials: true });
  //       setMealDates(response.data.meal_dates);
  //     } catch (error) {
  //       console.error('Error fetching meal dates:', error);
  //     }
  //   };

  //   fetchMealDates();
  // }, []);

  // const handleDateClick = (date) => {
  //   // Navigate to the DayInfoPage with the selected date
  //   navigate(`/dayinfo/${date}`);
  // };
  const [mealDates, setMealDates] = useState([]);
  const [userId, setUserId] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    // Fetch the current user ID from the backend
    const fetchCurrentUser = async () => {
      try {
        const response = await axios.get('//localhost:5000/@me', { withCredentials: true });
        if (response.status === 200) {
          setUserId(response.data.id);
        } else {
          console.error('Error fetching current user:', response.data.msg);
        }
      } catch (error) {
        console.error('Error fetching current user:', error);
      }
    };

    fetchCurrentUser();
  }, []);

  useEffect(() => {
    // Fetch meal dates from the backend
    const fetchMealDates = async () => {
      try {
        const response = await axios.get('//localhost:5000/meals/dates', { withCredentials: true });
        setMealDates(response.data.meal_dates);
      } catch (error) {
        console.error('Error fetching meal dates:', error);
      }
    };

    fetchMealDates();
  }, []);

  const handleDateClick = (date) => {
    // Navigate to the DayInfoPage with the selected date and user ID
    if (userId) {
      navigate(`/dayinfo/${userId}/${date}`);
    } else {
      console.error('User ID is not available');
    }
  };

  return (
    <div className={styles.container}>
      <h1>Meal Dates</h1>
      <div>
        {mealDates.map((date) => (
          <button
            key={date}
            className={styles.button} // Apply the CSS module styles
            onClick={() => handleDateClick(date)}
          >
            {date}
          </button>
        ))}
      </div>
    </div>
  );
}

// const [value, setValue] = React.useState(dayjs('2024-06-1'));

//   return (
//     <LocalizationProvider dateAdapter={AdapterDayjs}>
//       <DemoContainer components={['DateCalendar', 'DateCalendar']}>
//         <DemoItem label="Uncontrolled calendar">
//           <DateCalendar defaultValue={dayjs('2024-06-1')} />
//         </DemoItem>
//         <DemoItem label="Controlled calendar">
//           <DateCalendar value={value} onChange={(newValue) => setValue(newValue)} />
//         </DemoItem>
//       </DemoContainer>
//     </LocalizationProvider>
//   );