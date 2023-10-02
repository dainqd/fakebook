import React from 'react'
import { Link } from 'react-router-dom'

function Footer() {
    return (
        <div>
            <footer id="footer" className="footer">
                <div className="copyright">
                    © Copyright <strong><span>NiceAdmin</span></strong>. All Rights Reserved
                </div>
                <div className="credits">
                    Designed by <Link to="#">BootstrapMade</Link>
                </div>
            </footer>
        </div>
    )
}

export default Footer
