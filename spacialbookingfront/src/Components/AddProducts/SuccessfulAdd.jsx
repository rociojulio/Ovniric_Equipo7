import React from "react";
import styles from './SuccessfulAdd.module.css';
import {Link, Redirect} from 'react-router-dom';

function  SuccessfulAdd (){
    return(
        <div className={styles.contenedor}>
        <h1>El producto fue creado de manera exitosa</h1>
        <img src="../img/astronauta.png" className={styles.imageSuccess}/>
        <Link to="/" >
                <button className={styles.backHome} >Volver al Home</button>
            </Link>
        </div>
    )
}

export default  SuccessfulAdd;