import React from 'react'
import { Link } from 'react-router-dom'

function Sidebar() {
    return (
        <div>
            <aside id="sidebar" className="sidebar">
                <ul className="sidebar-nav" id="sidebar-nav">
                    <li className="nav-item">
                        <Link className="nav-link collapsed" data-bs-target="#charts-nav" data-bs-toggle="collapse" to="#">
                            <i className="bi bi-bar-chart" /><span>Blog</span><i className="bi bi-chevron-down ms-auto" />
                        </Link>
                        <ul id="charts-nav" className="nav-content collapse " data-bs-parent="#sidebar-nav">
                            <li>
                                <a href="charts-chartjs.html">
                                    <i className="bi bi-circle" /><span>List Blog</span>
                                </a>
                            </li>
                            <li>
                                <a href="charts-apexcharts.html">
                                    <i className="bi bi-circle" /><span>Create Blog</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link collapsed" data-bs-target="#icons-nav" data-bs-toggle="collapse" to="#">
                            <i className="bi bi-gem" /><span>Comments</span><i className="bi bi-chevron-down ms-auto" />
                        </Link>
                        <ul id="icons-nav" className="nav-content collapse " data-bs-parent="#sidebar-nav">
                            <li>
                                <a href="icons-bootstrap.html">
                                    <i className="bi bi-circle" /><span>List Comments</span>
                                </a>
                            </li>
                            <li>
                                <a href="icons-remix.html">
                                    <i className="bi bi-circle" /><span>Create Comments</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li className="nav-heading">Pages</li>
                    <li className="nav-item">
                        <Link className="nav-link collapsed" to="/profile">
                            <i className="bi bi-person" />
                            <span>Profile</span>
                        </Link>
                    </li>
                </ul>
            </aside>{/* End Sidebar*/}
        </div>

    )
}

export default Sidebar
