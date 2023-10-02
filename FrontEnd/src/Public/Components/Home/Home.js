import React from 'react'
import { Link } from 'react-router-dom';
import Navbar from '../Shared/Navbar/Navbar';
import "./Home.scss"

function Home() {
    return (
        <div>
            <Navbar />
            <div id='home-page'>
                <div className="banner-area">
                    <div className="banner-text">
                        <h1>HomePage</h1>
                        <p>Lorem ipsum dolor sit, amet consectetur adipisicing elit. Hic autem harum eaque aut deserunt pariatur eum
                            ea, sequi minus nam veniam atque et quisquam molestiae aperiam! Iusto, ipsum.</p>
                        <Link to="#">Read More</Link>
                        <Link to="#">Watch More</Link>
                    </div>
                </div>
            </div>
        </div>
    )
}

export default Home;
