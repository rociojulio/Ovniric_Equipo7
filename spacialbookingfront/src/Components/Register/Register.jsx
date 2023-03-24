import React from "react";
import { Link } from "react-router-dom";
import { useState } from "react";
import styles from './Register.module.css';
import { GlobalContext } from "../globalState/GlobalState";


function Register(){
    // Creación de los estados
    const [name, setName] = useState("");
    const [lastName, setLastName] = useState("");    
    const [passwordConfirm, setPasswordConfirm] = useState("");
    const [email1, setEmail1] = useState("");
    const [password1, setPassword1] = useState("");
    const [city, setCity] = useState('');

    // Variables estado global 
    const {setEmail,setPassword, setNameUser, setLastNameUser,setRenderForm}= GlobalContext()

    // Manejadores de eventos para cada input para actualziar los estados a medida que el usurio escribe en los inputs
    const onChangeName = (e) => setName(e.target.value);
    const onChangeLastName = (e) => setLastName(e.target.value);
    const onChangeEmail = (e) => setEmail1(e.target.value);
    const onChangeCity = (e) => setCity(e.target.value);
    const onChangePassword = (e) => setPassword1(e.target.value);
    const onChangePasswordConfirm = (e) => setPasswordConfirm(e.target.value);

    // Expresiones regulares para validar los inputs
    const validations = {
        name: /^[a-zA-ZÀ-ÿ\s]{1,100}$/, // Letras y espacios, pueden llevar acentos.
        lastName: /^[a-zA-ZÀ-ÿ\s]{1,100}$/, // Letras y espacios, pueden llevar acentos.
        email: /^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/,
        password: /^.{7,1000}$/, // 7 a 1000 digitos.
    }

    //funcion para manejar click a boton X
    const handleClick = ()=>{
        setRenderForm(null)
     }


    // Función para validación de los inputs
    function validateInputs (){
        if(validations.name.test(name) && validations.lastName.test(lastName) && validations.email.test(email1) 
        && validations.password.test(password1) && password1 ===passwordConfirm){
            return true;
        } else{
            return false;
        }
    }

    // Crear usuario ( hacer post)

    async function createUser(user) {
        const response = await fetch('http://18.220.89.28:8080/api/v1/auth/userRegister', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json'
          },
          body: JSON.stringify(user)
        });
        const data = await response.json();
        console.log(data)
        return data;
      }

    // Manejador del evento del envío de formulario
    const onSubmitForm = (e) =>{
        e.preventDefault();
        const isCorrectForm = validateInputs();
        if(isCorrectForm){
            setName("");
            setLastName("");
            setEmail1("");
            setPassword1("");
            setPasswordConfirm("");
            const newUser = {
                firstname: e.target.nombre.value,
                lastname: e.target.apellido.value,
                email: e.target.email.value,
                password: e.target.password.value,
                city: e.target.city.value,
            };

            createUser(newUser);           
            alert("Registro exitoso")
            console.log(newUser)
        }else{
            alert("Error. Verifica los campos ingresados")
        }
    }


    return (
        <div className={styles.registerTemplate}>
            <div className={styles.buttonClosed}>
            {/* Agregar funcionalidad para redireccionar al home o pagina principal usando
            la etiqueta Link*/}
            {/* <Link to='/home'> */}
                <Link to="/">
                    <button onClick={handleClick} >X</button>
                </Link>
            {/* </Link> */}
            </div>
            <form id="register" onSubmit={onSubmitForm} className={styles.form}>
                <h2 className={styles.inputsContainer}>Crear cuenta</h2>

                <div className={styles.inputsContainer} id={styles.inputName}>
                    <label htmlFor="nombre" className={styles.label}>Nombre:</label>
                    <input type="text" name="nombre" id="nombre" placeholder="Nombre" value={name} onChange={onChangeName} className={styles.inputs}></input>
                    <i class="formulario__validacion-estado fas fa-times-circle"></i>
                    <p class={styles.formulario__inputError}>El usuario tiene que ser de 1 a 100 dígitos y solo puede contener letras y tildes.</p>
                </div>
                <div className={styles.inputsContainer} id={styles.inputLastName}>
                    <label htmlFor="apellido" className={styles.label}>Apellido:</label>
                    <input type="text" name="apellido" id="apellido" placeholder="Apellido" value={lastName} onChange={onChangeLastName} className={styles.inputs}></input>
                </div>
                
                <div className={styles.inputsContainer} id={styles.inputEmail}>
                    <label htmlFor="email" className={styles.label}>Correo electrónico</label>
                    <input type="email" name="email" id="email" placeholder="Ingresa el correo electrónico" value={email1} onChange={onChangeEmail} className={styles.inputs}></input>
                </div>
                <div className={styles.inputsContainer} id={styles.inputEmail}>
                    <label htmlFor="city" className={styles.label}>Ciudad</label>
                    <input type="text" name="city" id="city" placeholder="Ingresa tu ciudad" value={city} onChange={onChangeCity} className={styles.inputs}></input>
                </div>
                <div className={styles.inputsContainer} id={styles.inputPassword}>
                    <label htmlFor="password" className={styles.label}>Contraseña</label>
                    <input type="password" name="password" id="password" placeholder="Ingresa tu contraseña" value={password1} onChange={onChangePassword} className={styles.inputs}></input>
                </div>
                <div className={styles.inputsContainer} id={styles.inputConfirm}>
                    <label htmlFor="passwordConfirm" className={styles.label}>Confirmar contraseña</label>
                    <input type="password" name="passwordConfirm" id="passwordConfirm" placeholder="Ingresa tu contraseña" value={passwordConfirm} onChange={onChangePasswordConfirm} className={styles.inputs}></input> 
                </div>
                <div className={styles.inputsContainer}>
                <button type="submit" className={`${styles.buttonSignup} ${styles.inputsContainer}`}>Crear cuenta</button>
                <p className={styles.inputsContainer}>¿Ya tienes una cuenta? <Link to='/login'>Iniciar sesión</Link></p>
                </div>
                
            </form>
        </div>
    )
}

export default Register;
