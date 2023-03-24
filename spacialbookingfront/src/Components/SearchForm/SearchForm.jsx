import  {useState, useEffect, useRef} from 'react';
import React from 'react'; 
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome'
import Calendar from "react-calendar";
import {FaSearchLocation} from "react-icons/fa"
import styles from "./SearchForm.module.css"
import { GlobalContext } from "../globalState/GlobalState";
import logolocation from '../../assets/img/Vector.png'

function SearchForm(){

    const {setUrl,url}= GlobalContext()
    const [actualValue, setActualvalue] = useState(null); 
    const [renderList, SetRenderList] = useState(false);
    const [filteredPlaces, setFilteredPlaces] = useState([]);
    const [places, setPlaces] = useState([]);
    const [startDate, setStartDate] = useState();
    const [endDate, setEndDate] = useState();
    const [dateRange, setDateRange] = useState([new Date(), new Date()]);
    const [showCalendar, setShowCalendar] = useState(false);

    
    useEffect(() => {
        async function fetchDataLocation() {
          const response = await fetch(`http://18.220.89.28:8080/api/localizaciones`);
          const data = await response.json();
          setPlaces(data);
        }

        ///disponibles/${location}/${startDate}/${endDate}

        fetchDataLocation()
      }, []);


    const onchangeSearch = (event)=>{ 
        console.log(event.target.value)
        setActualvalue(event.target.value)        
        SetRenderList(true)            
        event.preventDefault() 
        
    } 


    function Seletvalue(location){
        setActualvalue(location)
        SetRenderList(false) 
}


    function handleSubmit(e){
        console.log(actualValue)
        e.preventDefault()
        if(startDate && endDate && !actualValue ){
            console.log("entro fechas")
            setUrl(`http://18.220.89.28:8080/api/productos/disponible/${startDate}/${endDate}`)
            console.log(url)            
        }else if (startDate && endDate ){
            console.log("entro 3")
            setUrl(`http://18.220.89.28:8080/api/productos/disponibles/${actualValue}/${startDate}/${endDate}`)
        }
        else{
            console.log("entro solo location")
            setUrl(`http://18.220.89.28:8080/api/productos/localizacion/${actualValue}`)
        }

        setActualvalue("")
        setEndDate("")
        setStartDate("")
        
        
        
    }



 
    useEffect(()=>{
        const filtered = places.filter((place) => place.place.includes(actualValue));
        setFilteredPlaces(filtered);
    },[actualValue])




    return(

    <form className={styles.formGrid} id={styles.FormSearch} onSubmit={handleSubmit}>    

        <div className={styles.Disp_grid} id={styles.location}>

            <div id={styles.SearchInput} className={styles.Disp_grid}> 

                <FaSearchLocation className={`${styles.Disp_grid} ${styles.locationIcon}`}/>        
                <label  className={`${styles.searchContainer} ${styles.Disp_grid}`}> 
                    <input             
                    placeholder="Les't explore the galaxy"
                    id={styles.value}   
                    className={styles.formInputs}
                    value={actualValue ? actualValue : ""}
                    onChange={onchangeSearch} 
                    type="search"/>           
                </label>            
            </div>
            <ul id="places" className={` ${!renderList || actualValue=="" ? styles.DisplayOff:styles.DisplayOn}`}>
                {filteredPlaces.map((place)=> (                        
                <li  key={place.idLocation}
                    id={styles.inSearch}
                    className={styles.Disp_grid}
                    onClick={() => Seletvalue(place.place)}                                       
                    >
                    <img src={logolocation}/>                                                                
                    <h4>{place.name}</h4>
                    <h6>{place.place}</h6>                     
                </li>                      
                ))}
            </ul>  
        </div> 

        <div className={styles.datecontainer}>
            <b className={`${styles.label} ${styles.lb}`}>Inicio:</b>
            <label className={styles.label} id={styles.Date} >
                <input value={startDate ? startDate : ""} min={new Date().toISOString().split("T")[0]} className={`${styles.formInputs} ${styles.divItem} `} type="date"  onChange={(e)=>setStartDate(e.target.value)}></input>
            </label>
            <b className={`${styles.label}, ${styles.lb}`}>Final:</b>
            <label className={styles.label} id='Date'>
                <input value={endDate ? endDate : ""}  min={new Date().toISOString().split("T")[0]} className={`${styles.formInputs} ${styles.divItem} `}   type="date" onChange={(e)=>setEndDate(e.target.value)}></input>
            </label>
        </div>     
    
        <button  id={styles.searchButon} className={styles.submit} type="submit" >Buscar</button>                     
    </form>
    );

    
}

export default SearchForm;
