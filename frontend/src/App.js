import { Routes, Route, Link } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import "./App.css";
import { getCurrentUser, logout } from './services/auth_services';
import { useEffect, useState } from 'react';
import LoginForm from './components/loginForm';
import Home from './components/home';
import BoardAdmin from "./components/BoardAdmin";
import BoardInstructor from "./components/BoardInstructor";
import BoardStudent from "./components/BoardStudent";
import SignupInstructor from "./components/SignupInstructor";
import SignupAdmin from "./components/SignupAdmin";
import SignupStudent from "./components/SignupStudent";

function App() {

  const [isAdmin, setIsAdmin] = useState(false);
  const [isStudent, setIsStudent] = useState(false);
  const [isInstructor, setIsInstructor] = useState(false);
  const [currentUser, setCurrentUser] = useState(null);

  const resolveLogin = () => {
    const user = getCurrentUser();
    if (user) {
      setCurrentUser(user);
      setIsAdmin(user.user.roles[0].name === "ADMIN");
      setIsInstructor(user.user.roles[0].name === "INSTRUCTOR");
      setIsStudent(user.user.roles[0].name === "STUDENT");
    }
  };

  const Applogout = () => {
    logout();
    setCurrentUser(null);
    setIsAdmin(false);
    setIsInstructor(false);
    setIsStudent(false);
  }

  useEffect(() => {
    resolveLogin();
  }, []);
  
  return (
      <div>
        <nav className="navbar navbar-expand navbar-dark bg-dark">
          <Link to={"/"} className="navbar-brand">
            Elective Management
          </Link>
          <div className="navbar-nav mr-auto">
            <li className="nav-item">
              <Link to={"/home"} className="nav-link">
                Home
              </Link>
            </li>

            {isAdmin && (
              <li className="nav-item">
                <Link to={"/admin"} className="nav-link">
                  Admin Board
                </Link>
              </li>
            )}

            {isStudent && (
              <li className="nav-item">
                <Link to={"/student"} className="nav-link">
                  Student Board
                </Link>
              </li>
            )}
          

            {isInstructor&& (
              <li className="nav-item">
              <Link to={"/instructor"} className="nav-link">
                Instructor Board
              </Link>
            </li>
            )}
          
          </div>
            
          {currentUser ? (
            <div className="navbar-nav ml-auto">
              <li className="nav-item">
                <a href="/login" className="nav-link" onClick={Applogout}>
                  Log out
                </a>
              </li>

              {isAdmin && (
                <li>
                  <a href = "/registerAdmin" className="nav-link">
                    Register New Admin
                  </a>
                </li>
              )}

              {isAdmin && (
                <li>
                  <a href = "/registerInstructor" className="nav-link">
                    Register New Instructor
                  </a>
                </li>
              )}

              {isAdmin && (
                <li>
                  <a href = "/registerStudent" className="nav-link">
                    Register New Student
                  </a>
                </li>
              )}
            </div>
          ) : (
            <div className="navbar-nav ml-auto">
              <li className="nav-item">
                <Link to={"/login"} className="nav-link">
                  Login
                </Link>
              </li>

              <li className="nav-item">
                <Link to={"/register"} className="nav-link">
                  Sign Up
                </Link>
              </li>
            </div>
          )}
        </nav>

        <div className="container mt-3">
          <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/home" element={<Home />} />
            <Route path="/login" element={<LoginForm setCurrentUser = {setCurrentUser} setIsAdmin = {setIsAdmin} setIsStudent = {setIsStudent} setIsInstructor={setIsInstructor}/>} />
            <Route path="/registerStudent" element={<SignupStudent />} />
            <Route path="/registerAdmin" element={<SignupAdmin />} />
            <Route path="/registerInstructor" element={<SignupInstructor/>} />
            <Route path="/student" element={<BoardStudent/>} />
            <Route path="/admin" element={<BoardAdmin/>}/>
            <Route path="/instructor" element={<BoardInstructor/>}/>
          </Routes>
        </div>
      </div>
  );
}

export default App;
