// import React from 'react'

// export default function NavBar() {
//     return (
//         <nav className="navbar navbar-expand-lg custom-navbar" data-bs-theme="dark">
//             <div className="container-fluid">
//                 <div className="navbar-brand">
//                     SmartPlateMate
//                 </div>
//             </div>
//         </nav>
//     )
// }
import React from 'react';
import { useNavigate } from 'react-router-dom';
import '@fortawesome/fontawesome-free/css/all.min.css'; // Import Font Awesome CSS

export default function NavBar() {
  const navigate = useNavigate();

  const goToCalendar = () => {
    navigate('/calendar');
  };

  return (
    <nav className="navbar navbar-expand-lg custom-navbar" data-bs-theme="dark">
      <div className="container-fluid">
        <div className="navbar-brand d-flex align-items-center">
          <span>SmartPlateMate</span>
          <button className="btn btn-link text-light ml-3" onClick={goToCalendar}>
            <i className="fas fa-calendar-alt"></i> {/* Font Awesome calendar icon */}
          </button>
        </div>
      </div>
    </nav>
  );
}
