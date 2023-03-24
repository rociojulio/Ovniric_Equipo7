import React, { useEffect } from "react";
import {Link, Redirect} from 'react-router-dom';
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import styles from './Login.module.css'
import { GlobalContext } from "../globalState/GlobalState";





function Login(){

    const [emailLogin, setEmailLogin] = useState("")
    const [passwordLogin, setPasswordLogin] = useState("")
    const [tokenLogin, setTokenLogin] = useState()
    const [response, setresponse] = useState()
    const {setLogin,user,loginModal, setEmailUser, isLoged,fetchDataUser, getToken, setRenderForm}= GlobalContext()
    console.log(user, "datos usuarios")

    // datos usuraio de la api 
  


    
      
      useEffect(()=>{
        console.log(user)
      },[user])

     const handleClick = ()=>{
        setRenderForm(null)
     }


      

      

      console.log(user, "login")

    // Manejadores de eventos para cada input para actualziar los estados a medida que el usurio escribe en los inputs
    const onChangeEmail = (e) => setEmailLogin(e.target.value);
    const onChangePassword = (e) => setPasswordLogin(e.target.value);


    // Manejador del evento del envío de formulario
    const onSubmitLogin = (e) =>{
        console.log(emailLogin)
        console.log(passwordLogin)
        getToken(emailLogin,passwordLogin)
        e.preventDefault();
        fetchDataUser(`http://18.220.89.28:8080/api/usuarios/email/${emailLogin}`)
    }

  

  

    return (
        <div className={styles.loginTemplate}>
        <div className={styles.buttonClosed}>
            {/* Agregar funcionalidad para redireccionar al home o pagina principal usando
            la etiqueta Link*/}
            {/* <Link to='/home'> */}
            <Link to="/" >
                <button onClick={handleClick}>X</button>
            </Link>
            {/* </Link> */}
        </div>
        <form onSubmit={onSubmitLogin} className={styles.form}>
        { loginModal && <span className={styles.modal}>Para realizar una reserva debes de inciar sesión</span>}
        <h2>Iniciar sesión</h2>
        <div>
        <label htmlFor="email">Correo electrónico</label>
        <input type="email" name="email" id="email" placeholder="Ingresa el correo electrónico" value={emailLogin} onChange={onChangeEmail}></input>
        </div>
        <div>
        <label htmlFor="password">Contraseña</label>
        <input type="password" name="password" id="password" placeholder="Ingresa tu contraseña" value={passwordLogin} onChange={onChangePassword}></input>
        </div>
        <button type="submit" className={styles.buttonLogin}  >Ingresar</button>
        <p>¿Aún no tienes cuenta? <Link to='/signup'>Regístrate</Link></p>
        </form>
        
        </div>
        
    );
          }


export default Login;