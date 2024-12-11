// src/components/Navbar.jsx
import React from 'react';
import { NavLink } from 'react-router-dom';

function Navbar() {
    return (
        <nav style={styles.navbar}>
            <div style={styles.navContainer}>
                <div style={styles.logo}>Ticket Simulation</div>
                <div style={styles.links}>
                    <NavLink
                        to="/config"
                        style={({ isActive }) => isActive ? { ...styles.link, ...styles.activeLink } : styles.link}
                    >
                        Configuration
                    </NavLink>
                    <NavLink
                        to="/dashboard"
                        style={({ isActive }) => isActive ? { ...styles.link, ...styles.activeLink } : styles.link}
                    >
                        Dashboard
                    </NavLink>
                </div>
            </div>
        </nav>
    );
}

const styles = {
    navbar: {
        backgroundColor: '#007bff',
        borderRadius : '20px',
        color: '#fff',
        margin : '30px',
        padding: '10px 20px',
        boxShadow: '0 4px 8px rgba(0,0,0,0.1)',
    },
    navContainer: {
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-between',
        maxWidth: '1200px',
        margin: '0 auto'
    },
    logo: {
        fontWeight: 'bold',
        fontSize: '20px'
    },
    links: {
        display: 'flex',
        gap: '20px'
    },
    link: {
        color: '#fff',
        textDecoration: 'none',
        fontSize: '16px'
    },
    activeLink: {
        textDecoration: 'underline'
    }
};

export default Navbar;
