import {message} from 'antd';
import React, {useEffect, useState} from 'react'
import {Link, useNavigate} from 'react-router-dom'
import accountService from "../../../Service/AccountService";

function IsAdmin() {
    return (
        <li>
            <Link className="dropdown-item d-flex align-items-center" to="/dashboard">
                <i className="bi bi-gear"/>
                <span>Admin</span>
            </Link>
        </li>
    )
}

function Header() {
    const AuthName = sessionStorage.getItem("username");
    const tokenUser = sessionStorage.getItem("accessToken");
    const idUser = sessionStorage.getItem("id");
    const navigate = useNavigate();

    const handleLogout = () => {
        sessionStorage.clear();
        message.success("Logout")
        navigate("/")
    }

    const login = async () => {
        if (AuthName == null || tokenUser == null || idUser == null) {
            navigate("/not-found")
        }
    };

    let isAdmin = true;

    const [data, setData] = useState([]);

    const isUser = async () => {
        await accountService.findUserByUsername(AuthName)
            .then((res) => {
                if (res.status === 200) {
                    setData(res.data);
                    let itemAdmin = false;
                    let dataRole = res.data.roles;
                    for (let i = 0; i < dataRole.length; i++) {
                        let role = dataRole[i];
                        if (role.name === "ADMIN") {
                            itemAdmin = true;
                        }
                    }
                    if (itemAdmin) {
                        localStorage.setItem('isAdmin', 1);
                    } else {
                        navigate('/not-found')
                    }
                }
            })
    };

    let admin = localStorage.getItem('isAdmin')

    if (admin == null) {
        isAdmin = false;
    }

    useEffect(() => {
        isUser();
        login();
    }, []);

    return (
        <div>
            <header id="header" className="header fixed-top d-flex align-items-center">
                <div className="d-flex align-items-center justify-content-between">
                    <Link to="/#" className="logo d-flex align-items-center">
                        <img src="assets/img/logo.png" alt/>
                        <span className="d-none d-lg-block">FakeBook</span>
                    </Link>
                    <i className="bi bi-list toggle-sidebar-btn"/>
                </div>
                <div className="search-bar">
                    <form className="search-form d-flex align-items-center" method="POST" action="#">
                        <input type="text" name="query" placeholder="Search" title="Enter search keyword"/>
                        <button type="submit" title="Search"><i className="bi bi-search"/></button>
                    </form>
                </div>
                <nav className="header-nav ms-auto">
                    <ul className="d-flex align-items-center">
                        <li className="nav-item d-block d-lg-none">
                            <Link className="nav-link nav-icon search-bar-toggle " to="#">
                                <i className="bi bi-search"/>
                            </Link>
                        </li>
                        <li className="nav-item dropdown">
                            <Link className="nav-link nav-icon" to="#" data-bs-toggle="dropdown">
                                <i className="bi bi-bell"/>
                                <span className="badge bg-primary badge-number">4</span>
                            </Link>{/* End Notification Icon */}
                            <ul className="dropdown-menu dropdown-menu-end dropdown-menu-arrow notifications">
                                <li className="dropdown-header">
                                    You have 4 new notifications
                                    <Link to="#"><span
                                        className="badge rounded-pill bg-primary p-2 ms-2">View all</span></Link>
                                </li>
                                <li>
                                    <hr className="dropdown-divider"/>
                                </li>
                                <li className="notification-item">
                                    <i className="bi bi-exclamation-circle text-warning"/>
                                    <div>
                                        <h4>Lorem Ipsum</h4>
                                        <p>Quae dolorem earum veritatis oditseno</p>
                                        <p>30 min. ago</p>
                                    </div>
                                </li>
                                <li>
                                    <hr className="dropdown-divider"/>
                                </li>
                                <li className="notification-item">
                                    <i className="bi bi-x-circle text-danger"/>
                                    <div>
                                        <h4>Atque rerum nesciunt</h4>
                                        <p>Quae dolorem earum veritatis oditseno</p>
                                        <p>1 hr. ago</p>
                                    </div>
                                </li>
                                <li>
                                    <hr className="dropdown-divider"/>
                                </li>
                                <li className="notification-item">
                                    <i className="bi bi-check-circle text-success"/>
                                    <div>
                                        <h4>Sit rerum fuga</h4>
                                        <p>Quae dolorem earum veritatis oditseno</p>
                                        <p>2 hrs. ago</p>
                                    </div>
                                </li>
                                <li>
                                    <hr className="dropdown-divider"/>
                                </li>
                                <li className="notification-item">
                                    <i className="bi bi-info-circle text-primary"/>
                                    <div>
                                        <h4>Dicta reprehenderit</h4>
                                        <p>Quae dolorem earum veritatis oditseno</p>
                                        <p>4 hrs. ago</p>
                                    </div>
                                </li>
                                <li>
                                    <hr className="dropdown-divider"/>
                                </li>
                                <li className="dropdown-footer">
                                    <Link to="#">Show all notifications</Link>
                                </li>
                            </ul>
                        </li>
                        <li className="nav-item dropdown">
                            <Link className="nav-link nav-icon" to="#" data-bs-toggle="dropdown">
                                <i className="bi bi-chat-left-text"/>
                                <span className="badge bg-success badge-number">3</span>
                            </Link>
                            <ul className="dropdown-menu dropdown-menu-end dropdown-menu-arrow messages">
                                <li className="dropdown-header">
                                    You have 3 new messages
                                    <Link to="#"><span
                                        className="badge rounded-pill bg-primary p-2 ms-2">View all</span></Link>
                                </li>
                                <li>
                                    <hr className="dropdown-divider"/>
                                </li>
                                <li className="message-item">
                                    <Link to="#">
                                        <img src="assets/img/messages-1.jpg" alt className="rounded-circle"/>
                                        <div>
                                            <h4>Maria Hudson</h4>
                                            <p>Velit asperiores et ducimus soluta repudiandae labore officia est
                                                ut...</p>
                                            <p>4 hrs. ago</p>
                                        </div>
                                    </Link>
                                </li>
                                <li>
                                    <hr className="dropdown-divider"/>
                                </li>
                                <li className="message-item">
                                    <Link to="#">
                                        <img src="assets/img/messages-2.jpg" alt className="rounded-circle"/>
                                        <div>
                                            <h4>Anna Nelson</h4>
                                            <p>Velit asperiores et ducimus soluta repudiandae labore officia est
                                                ut...</p>
                                            <p>6 hrs. ago</p>
                                        </div>
                                    </Link>
                                </li>
                                <li>
                                    <hr className="dropdown-divider"/>
                                </li>
                                <li className="message-item">
                                    <Link to="#">
                                        <img src="assets/img/messages-3.jpg" alt className="rounded-circle"/>
                                        <div>
                                            <h4>David Muldon</h4>
                                            <p>Velit asperiores et ducimus soluta repudiandae labore officia est
                                                ut...</p>
                                            <p>8 hrs. ago</p>
                                        </div>
                                    </Link>
                                </li>
                                <li>
                                    <hr className="dropdown-divider"/>
                                </li>
                                <li className="dropdown-footer">
                                    <Link to="#">Show all messages</Link>
                                </li>
                            </ul>
                        </li>
                        <li className="nav-item dropdown pe-3">
                            <Link className="nav-link nav-profile d-flex align-items-center pe-0" to="#"
                                  data-bs-toggle="dropdown">
                                <img src="assets/img/profile-img.jpg" alt="Profile" className="rounded-circle"/>
                                <span className="d-none d-md-block dropdown-toggle ps-2">{AuthName}</span>
                            </Link>
                            <ul className="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile">
                                <li className="dropdown-header">
                                    <h6>{AuthName}</h6>
                                    <span>Web Designer</span>
                                </li>
                                <li>
                                    {isAdmin ?  <li>
                                        <hr className="dropdown-divider"/>
                                    </li> : ""}
                                </li>
                                <li>
                                    {isAdmin ? <IsAdmin /> : ""}
                                </li>
                                <li>
                                    <hr className="dropdown-divider"/>
                                </li>
                                <li>
                                    <Link className="dropdown-item d-flex align-items-center" to="/profile">
                                        <i className="bi bi-person"/>
                                        <span>My Profile</span>
                                    </Link>
                                </li>
                                <li>
                                    <hr className="dropdown-divider"/>
                                </li>
                                <li>
                                    <Link className="dropdown-item d-flex align-items-center" to="#">
                                        <i className="bi bi-question-circle"/>
                                        <span>Need Help?</span>
                                    </Link>
                                </li>
                                <li>
                                    <hr className="dropdown-divider"/>
                                </li>
                                <li>
                                    <div className="dropdown-item d-flex align-items-center" style={{cursor: "pointer"}}
                                         onClick={handleLogout}>
                                        <i className="bi bi-box-arrow-right"/>
                                        <span>Sign Out</span>
                                    </div>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </nav>
            </header>
        </div>
    )
}

export default Header
