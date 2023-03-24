import React, { useEffect } from "react";
import styles from "./Card.module.css";
import { GlobalContext } from "../globalState/GlobalState";



import { Link, useNavigate } from "react-router-dom";





const Card = ({info}) =>{
    
    const navigate = useNavigate()
    const {SetActualproductId}= GlobalContext()
    
    let image = info.images[0] 
    console.log(image)
    return <section className={styles.container}>
        <article className={styles.imgContainer}> <img src={image.imageUrl} alt=""  className={styles.img} /></article>
        <article className={styles.infoContainer}>
            <div className={styles.cardMainInfo}>
                <span className={styles.category}>{info.category.title}</span>
                <h2 className={styles.title}>{info.title}</h2>
                <h3 className={styles.location}><b>Ubicación:</b> {info.locations.place}</h3>
            </div>
         
            <p className={styles.description}>{`${info.description.substring(0,70)} ...`}</p>
            <Link  className={styles.viewMore} to={`/productdetails/${info.idProduct}`}  >
                <button className={styles.button} >Ver más</button>
            </Link>
        </article>
    </section>
};

export default Card ;