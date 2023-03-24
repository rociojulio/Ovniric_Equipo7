import React from "react";
import img from "../../assets/img/loader.gif";
import { useEffect, useState } from "react";
import styles from "./MyBookings.module.css";
import { IoLocationSharp} from "react-icons/io5";
import { GlobalContext } from "../globalState/GlobalState";
import { Link, useNavigate } from "react-router-dom";

function MyBookings (){
    const[bookingsProduct,setBookingsProduct] = useState([]);
    const {user, emailUser} = GlobalContext();
    const navigation = useNavigate();
    console.log(bookingsProduct)
    console.log(user)

   
    const urlMyBookings =`http://18.220.89.28:8080/api/reservaciones/cliente/${user.id}`
    const urlTest = 'http://18.220.89.28:8080/api/reservaciones/cliente/100'

    async function getMyBookings(url){
        console.log(" entrando al efec")
        console.log(localStorage.getItem('token'))
        const token = localStorage.getItem('token')
        const data = await fetch(url, {
            headers: {
                'Authorization': 'Bearer ' + localStorage.getItem('token'),
                'Content-Type': 'application/json'
            }
        });
        const myBookings = await data.json();
        console.log(url)
        console.log(localStorage.getItem('token'))
        setBookingsProduct(myBookings)
    };

    useEffect(() => {
        getMyBookings(urlMyBookings)
    }, [emailUser] )


    return(
        <>
        
        {bookingsProduct.length > 0 ?
        <>
        <h1 className={styles.titleP}>Mis Reservas</h1>
        <div className={styles.containerBooking}>
        {bookingsProduct && bookingsProduct.map(booking => (
            <section key={booking.id} className={styles.mainContainer}>
            <h2 className={styles.cardTitle}>Detalles de la reserva</h2>
            <img src={booking?.product?.images[0]?.imageUrl} alt="product img" className={styles.img} />
            <span className={styles.category}>{booking?.product?.category?.title}</span>
            <h3 className={styles.title}>{booking?.product?.title}</h3>
            <span  className={styles.location}> <IoLocationSharp/> {booking?.product?.locations?.place}</span>
            <span className={styles.location}>{booking?.product?.altitude}mts s.n.m</span>
            <p className={styles.checkInOut}>Check in: {booking?.startDate}</p>
            <p className={styles.checkInOut}>Check out: {booking?.endDate}</p>
            <p className={styles.checkInOut}>Hora: {booking?.startHour}</p>
        </section>
        ))
            }
            </div>
            </>
            : <div className={styles.notBooking}>
                <div className={styles.message}>
                <h3>Aún no has realizado ninguna reserva</h3>
                <img src="../img/not-found.png" alt="not-Booking" />
                <button onClick={() => navigation("/")}> Regresar al Home</button>
                </div>
            </div>}
            </>
    )
}

export default MyBookings;
