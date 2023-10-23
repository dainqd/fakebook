import React from 'react'
import { Link } from 'react-router-dom'

function Sidebar() {
    return (
        <div>
            <aside id="sidebar" className="sidebar">
                <ul className="sidebar-nav" id="sidebar-nav">
                    <li className="nav-item">
                        <Link className="nav-link " to="/dashboard">
                            <i className="bi bi-grid" />
                            <span>Dashboard</span>
                        </Link>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link collapsed" data-bs-target="#forms-nav" data-bs-toggle="collapse" to="#">
                            <i className="bi bi-journal-text" /><span>Account</span><i className="bi bi-chevron-down ms-auto" />
                        </Link>
                        <ul id="forms-nav" className="nav-content collapse " data-bs-parent="#sidebar-nav">
                            <li>
                                <Link to="/account/list">
                                    <i className="bi bi-circle" /><span>List Account</span>
                                </Link>
                            </li>
                            <li>
                                <Link to="/account/create">
                                    <i className="bi bi-circle" /><span>Create Account</span>
                                </Link>
                            </li>
                        </ul>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link collapsed" data-bs-target="#charts-nav" data-bs-toggle="collapse" to="#">
                            <i className="bi bi-bar-chart" /><span>Blog</span><i className="bi bi-chevron-down ms-auto" />
                        </Link>
                        <ul id="charts-nav" className="nav-content collapse " data-bs-parent="#sidebar-nav">
                            <li>
                                <a href="/admin/blog/list">
                                    <i className="bi bi-circle" /><span>List Blog</span>
                                </a>
                            </li>
                            <li>
                                <a href="/admin/blog/create">
                                    <i className="bi bi-circle" /><span>Create Blog</span>
                                </a>
                            </li>
                        </ul>
                    </li>
                    <li className="nav-item">
                        <Link className="nav-link collapsed" data-bs-target="#icons-nav" data-bs-toggle="collapse" to="#">
                            <i className="bi bi-gem" /><span>Marketing</span><i className="bi bi-chevron-down ms-auto" />
                        </Link>
                        <ul id="icons-nav" className="nav-content collapse " data-bs-parent="#sidebar-nav">
                            <li>
                                <a href="/marketing/list">
                                    <i className="bi bi-circle" /><span>List Marketing</span>
                                </a>
                            </li>
                            <li>
                                <a href="/marketing/create">
                                    <i className="bi bi-circle" /><span>Create Marketing</span>
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
