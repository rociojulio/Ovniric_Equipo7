import React from "react";
import Header from "../Header/Header";
import Footer from "../Footer/Footer";
import { FaCalendarCheck } from "react-icons/fa";
import styles from "./SuccessfulReservation.module.css";
import { Link } from "react-router-dom";


const SuccessfulReservation = () =>{
    return <>
        <Header/>
        <section className={styles.container}>
            <article className={styles.cardContainer}>
                <FaCalendarCheck className={styles.icon}/>
                <div className={styles.div}>
                    <h3 className={styles.text}>¡Su reserva se ha realizado con éxito!</h3>
                    <p className={styles.text}>Te esperamos pronto para brindarte la mejor experiencia de tu vida.</p>
                </div>
              <Link to="/"><button className={styles.button}>OK</button></Link> 
            </article>
        </section>
        <Footer/>
    </>

}


export default SuccessfulReservation;