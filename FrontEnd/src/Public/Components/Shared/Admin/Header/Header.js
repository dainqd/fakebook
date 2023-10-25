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
        localStorage.clear();
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
                        <li className="nav-item dropdown pe-3">
                            <Link className="nav-link nav-profile d-flex align-items-center pe-0" to="#"
                                  data-bs-toggle="dropdown">
                                <img src={data['avt']} alt="Profile" className="rounded-circle"/>
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
