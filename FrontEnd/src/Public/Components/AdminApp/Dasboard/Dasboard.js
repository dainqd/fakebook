import React from 'react'
import { Link } from 'react-router-dom';
import Header from '../../Shared/Admin/Header/Header';
import Sidebar from '../../Shared/Admin/Sidebar/Sidebar';
import Footer from "../../Shared/Admin/Footer/Footer";

function Dasboard() {
    return (
        <>
            <Header />
            <Sidebar />
            <main id="main" className="main">
                <div className="pagetitle">
                    <h1>Dashboard</h1>
                </div>
            </main>
            <Footer />
        </>
    )
}

export default Dasboard;
