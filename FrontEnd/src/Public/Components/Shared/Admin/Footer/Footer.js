import React from 'react'
import { Link } from 'react-router-dom'

function Footer() {
    return (
        <div>
            <footer id="footer" className="footer">
                <div className="copyright">
                    © Copyright <strong><span>Fakebook</span></strong>. All Rights Reserved
                </div>
                <div className="credits">
                    Designed by <Link to="#">Fakebook Team</Link>
                </div>
            </footer>
        </div>
    )
}

export default Footer
