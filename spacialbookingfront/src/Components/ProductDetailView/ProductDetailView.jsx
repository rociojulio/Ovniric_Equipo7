import React from "react";
import { useState,useEffect} from "react";
import Header from "../Header/Header";
import Footer from "../Footer/Footer";
import styles from "./ProductDetailView.module.css";
import { IoLocationSharp,IoBarbellSharp,IoLibrarySharp,IoCloseCircleSharp } from "react-icons/io5";
import { FaGrav,FaDog,FaWifi,FaCheckCircle} from "react-icons/fa";
import { GoTelescope } from "react-icons/go";
import { ImLab } from "react-icons/im";
import { GiAstronautHelmet } from "react-icons/gi";
import BlockGallery from "../BlockGallery/BlockGallery";
import { GlobalContext } from "../globalState/GlobalState";
import { useParams } from 'react-router-dom';
import BookingCalendar from "../BookingCalendar/BookingCalendar";
import GoBackHeader from "../GoBackHeader/GoBackHeader";
import Policies from "../Policies/Policies";
import img from "../../assets/img/loader.gif"
import '../../stylesVariables/variables.css'

const ProductDetailView = () => {


    const [product, setProduct] = useState({
        title: "cargando",
        category: "cargando",
        locations: [{place : "cargando.."}],
        altitude: "cargando",
        images: [{imageUrl: img}],
        description: "cargando",
        features: []});
    const {id} = useParams();
    
    async function fetchDataProduct(url) {
        console.log(url)
        const response = await fetch(url);
        const data = await response.json();
        setProduct(data)
        
      }


        
    useEffect(()=>{
            fetchDataProduct(`http://18.220.89.28:8080/api/productos/id/${id}`)
    },[])
    const productFeatures = product.features.map(e => e.idFeature);
    console.log(`features ${productFeatures}`);


    console.log(product)
  return (
    !product  ? (
        <div>Cargando...</div>
    ) : (
    
    <main className={styles.container}>
        
        <GoBackHeader category={product.category.title} title={product.title}/>
        <section className={styles.locationContainer}>
            <h4 className={styles.location}>  <IoLocationSharp/> {product.locations.place}</h4>
            <h5 className={styles.location} >{`A ${product.altitude} metros sobre el nivel del mar apróximadamente`}</h5>
        </section>
        
            <BlockGallery images={product.images}></BlockGallery>
    
<h3 className={styles.featuresTitle}>¡La mejor experiencia de tu vida!</h3>
<section className={styles.descriptionContainer}>
<p className={styles.description}>{product.description}</p>
</section>
<h3 className={styles.featuresTitle}>Comodidades</h3>
<section className={styles.featuresContainer} >
<h5 className={styles.feature}> <FaWifi className={styles.featureIcon}/>  Internet: {productFeatures.includes(1) ? <FaCheckCircle className={styles.trueIcon}/> : <IoCloseCircleSharp className={styles.falseIcon}/>} </h5>
<h5 className={styles.feature}> <FaDog className={styles.featureIcon} />  Mascota de compañia: {productFeatures.includes(2) ? <FaCheckCircle className={styles.trueIcon}/> : <IoCloseCircleSharp className={styles.falseIcon}/>} </h5>
<h5 className={styles.feature}> <GoTelescope className={styles.featureIcon}  />Telescopio: {productFeatures.includes(3) ? <FaCheckCircle className={styles.trueIcon}/> : <IoCloseCircleSharp className={styles.falseIcon}/>} </h5>
<h5 className={styles.feature}> <FaGrav className={styles.featureIcon} /> Gravedad cero: {productFeatures.includes(4) ? <FaCheckCircle className={styles.trueIcon}/> : <IoCloseCircleSharp className={styles.falseIcon}/>}</h5>
<h5 className={styles.feature}> <IoLibrarySharp className={styles.featureIcon} /> Biblioteca: {productFeatures.includes(5) ? <FaCheckCircle className={styles.trueIcon}/> : <IoCloseCircleSharp className={styles.falseIcon}/>}</h5>
<h5 className={styles.feature}> <IoBarbellSharp className={styles.featureIcon} /> Entrenamiento astronauta: {productFeatures.includes(6) ? <FaCheckCircle className={styles.trueIcon}/> : <IoCloseCircleSharp className={styles.falseIcon}/>}</h5>
<h5 className={styles.feature}> <GiAstronautHelmet className={styles.featureIcon} /> Traje espacial: {productFeatures.includes(7) ? <FaCheckCircle className={styles.trueIcon}/> : <IoCloseCircleSharp className={styles.falseIcon}/>}</h5>
<h5 className={styles.feature}> <ImLab className={styles.featureIcon} /> Laboratorio : {productFeatures.includes(8) ? <FaCheckCircle className={styles.trueIcon}/> : <IoCloseCircleSharp className={styles.falseIcon}/>}</h5>
</section>
<Policies/>
<h2  className={styles.featuresTitle} >Fechas Disponibles</h2>
<div className={styles.calendarContainer}><BookingCalendar idProduct={id}></BookingCalendar></div>
</main>
    )
    )

};

export default ProductDetailView;


