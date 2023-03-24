import React from "react";
import styles from "../Footer/Footer.module.css"
import {FaFacebook,FaInstagram,FaLinkedinIn,FaTwitter} from "react-icons/fa"
import '../../stylesVariables/variables.css'

const today = new Date();
const Footer = () =>{
   
    return <footer className={styles.footer}>
        <span className={styles.allrights}>® {today.getFullYear()} Ovniric </span>
        <section className={styles.section}><FaFacebook className={`${styles.icon} ${styles.fb}`}/>
        <FaInstagram className={`${styles.icon} ${styles.instagram}`}/>
        <FaLinkedinIn className={`${styles.icon} ${styles.linkedin}`}/>
        <FaTwitter className={`${styles.icon} ${styles.twitter}`}/></section>
        

    </footer>
} 

export default Footer