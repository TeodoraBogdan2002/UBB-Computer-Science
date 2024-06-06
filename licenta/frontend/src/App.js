import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import HomePage from './components/HomePage';
import NavBar from './components/NavBar';
import LoginPage from './components/LoginPage';
import NotFoundPage from './components/NotFoundPage';
import SignUpPage from './components/SignUpPage';
import PredictPage from './components/PredictionResults';
import DiaryPage from './components/DiaryPage';
import DayInfoPage from './components/DayInfoPage';

function App() {
  return (
    <Router>
    <NavBar/>
      <div className="container">
        <br /><br />
        <Routes>
          <Route path='/' Component={LoginPage}/>
          <Route path='/home' Component={HomePage} />
          <Route path='/signup' Component={SignUpPage}/>
          <Route path='/upload' Component={HomePage} />
          <Route path='/*' Component={NotFoundPage} />
          <Route path="/predict" component={PredictPage} />
          <Route path="/calendar" Component={DiaryPage} />
          <Route path="/dayinfo/:userId/:date" Component={DayInfoPage} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
