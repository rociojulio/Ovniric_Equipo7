import React from "react";
import img from "../../assets/img/loader.gif";
import { useEffect, useState } from "react";
import styles from "./ReservationCard.module.css";
import { IoLocationSharp} from "react-icons/io5";
import { Link } from "react-router-dom";


const ReservationCard = (props) => {

    const[reservationProduct,setReservationProduct] = useState({
        img: img,
        category: "cargando",
        name: "cargando",
        location: "cargando",
        altitude: "cargando"
    });

    useEffect( () => {
        const fetchData= async () => {
            const data = await fetch(`http://18.220.89.28:8080/api/productos/id/${props.id}`);
            const card = await data.json();

            setReservationProduct({
                img : card.images[0].imageUrl,
                category: card.category.title,
                name: card.title,
                location: card.locations.place,
                altitude: card.altitude
            })

         };

         fetchData()
        });

        

        let altitudeFormated = new Intl.NumberFormat().format(reservationProduct.altitude);

        return <>
            <section className={styles.mainContainer}>
                <h2 className={styles.cardTitle}>Detalles de la reserva</h2>
                <img src={reservationProduct.img} alt="product img" className={styles.img} />
                <span className={styles.category}>{reservationProduct.category}</span>
                <h3 className={styles.title}>{reservationProduct.name}</h3>
                <span  className={styles.location}> <IoLocationSharp/> {reservationProduct.location}</span>
                <span className={styles.location}>{altitudeFormated}mts s.n.m</span>
                <p className={styles.checkInOut}>Check in: {props.startDate}</p>
                <p className={styles.checkInOut}>Check out:  {props.endDate}</p>
              <button className={styles.reservationbtn} onClick={props.handleClick}>Confirmar Reserva</button> 
                
            </section>
        </>
};

export default ReservationCard;