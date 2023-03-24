import React, { useState, useEffect } from "react";
import styles from "./Header.module.css"
import { FaMoon,CiLight} from "react-icons/fa";
import { BsSunFill,BsMoonStarsFill } from "react-icons/bs";
import Switch from '@mui/material/Switch';
import logoLight from "../../assets/img/ovniricLogoLight.png";
import lemaLight from "../../assets/img/ovniricLemaLight.png";
import logoDark from "../../assets/img/ovniricLogoLight.png";
import lemaDark from "../../assets/img/ovniricLemaLight.png";
import {FaBars,FaTimes} from "react-icons/fa";
import { GlobalContext } from "../globalState/GlobalState";
import { Link } from "react-router-dom"
import '../../stylesVariables/variables.css'

const Header = ({changeTheme}) => {

  const {renderForm,setRenderForm,isLoged,setLogin, user,role, setRole,emailUser, setEmailUser}= GlobalContext();

  const [isOpen,setOpen] = useState(false);
  const [rolAdmin, setRolAdmin] = useState(true)
 

  

  const handleMenu = () => {
  setOpen(!isOpen);
  };

  const handleSingUp = () =>{    
    setRenderForm(()=>"SignUp")
}

const handleLogIn = () =>{
  setRenderForm(()=>"LogIn")
  console.log(renderForm)
}

const handleLogo = () => {
  setRenderForm(null)
}

async function logout() {
    setLogin(false);
    setRenderForm(null);
    console.log("se elimino tl token " + localStorage.getItem('token'))
    try {
      const response = await fetch("http://18.220.89.28:8080/api/v1/auth/logout", {
        method: 'POST',
        headers: {
          'Authorization': 'Bearer' + localStorage.getItem('token'),
          'Content-Type': 'application/json'
      }
      });
      if (response.status === 204) {
        // Logout successful, remove token from local storage and redirect to login page
        localStorage.removeItem('token');
        localStorage.removeItem('RoleUser');
        setRole("")
        setEmailUser("")
        
      } else {
        throw new Error('Logout failed');
      }
    } catch (error) {
      console.error(error);
    }
  
  }  




  useEffect(()=>{
    console.log(role)
  },[role])
  
  useEffect(()=>{
    let name = user.firstname ;
    console.log(name)
  },[isLoged])
  
  console.log(user)

 
    return (
    <header className={styles.header}>

      <Link to="/" className={styles.logo} onClick={handleLogo}><img  className={styles.logo} src={logoLight} alt="Logo Ovniric" /></Link>
      <Link to="/" className={styles.lema} onClick={handleLogo}><img  className={styles.lema} src={lemaLight} alt="Lema Ovniric" /></Link>
      <div className={styles.theme}>
        <BsSunFill className={styles.trueIcon}/>
        <Switch  onClick={changeTheme}>theme</Switch >
        <BsMoonStarsFill className={styles.trueIcon}/>
      </div>
      
  
      {isLoged ? 
          <div className={styles.login}>

              <div className={styles.avtContainer}>
                <span className={styles.avtSpan}>
                  {`${user.firstname} ${user.lastname}`.split(" ").reduce(( accum,current) => {return accum + current[0].toUpperCase() + "."},"")}
                </span>
              </div>
              <a href="#" className={styles.username}>{`Hola ${user.firstname}`}</a>
              {role == "ADMIN" ? (
              <Link to='/administrationProducts' className={styles.add}>
                <button className={styles.btn} id={styles.btnAdd}>+ Producto</button>
              </Link>
            ): role == "USER" ? (
              <Link to='/MisReservas' className={styles.add}>
                <button className={styles.btn} id={styles.btnAdd}>Mis reservas</button>
              </Link>): null}
              <button className={`${styles.btn} ${styles.logout}` } id={styles.logout} onClick={logout} >Log out</button>          
          </div> : 
        <div className={styles.login}>
          <Link to="/signup"  className={`${styles.loginbtn}` }>
            <button className={` ${styles.btn} ${renderForm == "SignUp" ? styles.Disp_none : "" }`} onClick={handleSingUp}>Sign up </button>
          </Link>
          <Link to="/login" className={`${styles.signupbtn} `}>
            <button className={` ${styles.btn} ${renderForm == "LogIn" ? styles.Disp_none : "" } `}  onClick={handleLogIn}>Log in</button>
          </Link>
        </div> 
        }
        <span  className={styles.menu} onClick={handleMenu}>{isOpen ? <FaTimes className={styles.menuIcon}/> : <FaBars />}</span>
      {isOpen ? <div className={styles.menuarea}>
         {isLoged ? 
        <>
          <div className={` ${styles.hamburgueravatar}`}> <span className={styles.avtSpanHamburguer}>{`${user.firstname} ${user.lastname}`.split(" ").reduce(( accum,current) => {return accum + current[0].toUpperCase() + "."},"")}</span> </div>
          <a href="#" className={` ${styles.hamburguerusername}`}>{user.firstname}</a>
          <a href="#" className={styles.hamburguerlogout}>Log out</a>
        </> :
        <>
          <Link to="/login" className={styles.hamburguerlogin} onClick={handleSingUp}> Log in</Link>
          <Link to="/signup" className={styles.hamburguersignup} onClick={handleLogIn}>Sign up</Link>
        </>  
        }
        </div> : 
       <></>
        
        
        }
       
       </header>
    )
   
}

export default Header;