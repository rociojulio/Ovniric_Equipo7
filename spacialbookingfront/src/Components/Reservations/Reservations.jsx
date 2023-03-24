import React, { useContext, useEffect, useState } from "react";
import Header from "../Header/Header";
import Footer from "../Footer/Footer"
import { useParams } from "react-router-dom";
import ReservationCard from "../ReservationsCard/ReservationCard";
import GoBackHeader from "../GoBackHeader/GoBackHeader";
import BookingForm from "../BookingForm/BookingForm";
import styles from "./Reservations.module.css"
import { useNavigate } from "react-router-dom";
import { GlobalContext } from "../globalState/GlobalState";
import Policies from "../Policies/Policies";




const Reservations = () => {
    const [date, setDate] = useState(new Date());
    const [dataTime,setDataTime] = useState();
    const [time,setTime] = useState();
    const {emailUser, user,startDate, setStartDate,endDate, setEndDate}= GlobalContext();
    const navigate = useNavigate();

    const [userCopy, setUserCopy] = useState();
    const [city,setCity] = useState();
    const [correctBooking,setCorrectBooking] = useState(false);
    const {id} = useParams();
    // const [datos, setDatos] = useState({
    //     city: "datos"
    // })

    const productId = parseInt(id)
    const [reservation, setReservation] = useState({
        startHour: '',
        startDate: '',
        endDate: '',
        productId: '',
        userId: '',
      });

      function handleClick(){

        console.log("click working")
        if(city && startDate && endDate && time){
            setReservation({
                startHour: time,
                startDate: startDate,
                endDate: endDate,
                productId: productId,
                userId: user.id
              })
            console.log(time)
        } else{
            alert("Lamentablemente la reserva no ha podido realizarse. Por favor, intente más tarde")
        }
    }

    useEffect(()=>{
        console.log(reservation)
        const postReservation = async (reservation) =>{
            console.log(localStorage.getItem('token'))
            const response = await fetch('http://18.220.89.28:8080/api/reservaciones', {
                method: 'POST',
                body: JSON.stringify(reservation),
                headers: {
                    'Authorization': 'Bearer ' + localStorage.getItem('token'),
                    'Content-Type': 'application/json'
                }
            });

            if(response.ok){
                console.log('La reserva ha sido creada:');
                navigate("/successfulReservation")
            } else {
                alert('Hubo un error al crear la reserva:')
                console.log('Hubo un error al crear la reserva:');
            }
        }
        
        console.log(reservation)
        postReservation(reservation)
    },[reservation])



     // useEffect(() => {
    //     setDatos({...datos, city})
    // },[city])
    // console.log(datos.city, "datos")
    useEffect(()=>{
        console.log(dataTime)
        let timeSelected = dataTime?.$d.toLocaleTimeString([], {hour: '2-digit', minute:'2-digit',second:'2-digit', hour12: false})
        setTime(timeSelected)
        console.log(time)
    },[dataTime])


    // useEffect(()=>{
    //     const copy = dataUsers?.filter( user => user.email === emailUser)[0];
    //     setUserCopy(copy)
    // },[dataUsers])

    console.log(user)

    // Función para conectar la API de reservas
    async function confirmBooking(information){
        const url = "url de la Api/REservations";

        const booking = {
            method: "POST",
            headers: {
                "Content-Type": 'application/json'
            },
            body: JSON.stringify(information)
        }

        const bookingRequest = await fetch(url,booking);

        if(bookingRequest.status===201) {
            setCorrectBooking(true)
        } else{
            alert("“Lamentablemente la reserva no ha podido realizarse. Por favor, intente más tarde”.")
        }

        const responseBooking = await bookingRequest.json();

    }

    useEffect(() => {
        window.scrollTo(0,0)
    },[])

     return (
       <>
         <GoBackHeader />
         <section className={styles.reservationInfoContainer}>
            <article className={styles.form}>
            <BookingForm 
            setStartDate={setStartDate}
            setEndDate={setEndDate}
            time={dataTime}
            changeTime={setDataTime}
            user={user}
            changeCity={setCity}
           
           />
            </article>
         
           <ReservationCard 
             className={styles.reservationCard}
             id={productId}
             startDate={startDate}
             endDate={endDate}
             time={dataTime}
             user={userCopy}
             changeUser={setUserCopy}
             city={city}
             handleClick = {handleClick} 
             />
         </section>
         <Policies className={styles.policiesContainer} />
         <Footer />
       </>
     );

}

export default Reservations;