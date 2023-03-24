import React from "react";
import cardsInfo from "../../assets/Json/cardsInfo.json"
import styles from "../ListContainer/ListContainer.module.css"
import Card from "../Card/Card"
import  {useState, useEffect} from 'react';
import { GlobalContext } from "../globalState/GlobalState";
import '../../stylesVariables/variables.css'
import { Link } from "react-router-dom";



const ListContainer = () => {

  const [displayedProducts, setDisplayedProducts] = useState([]);
  const {url, setUrl}= GlobalContext()  


  async function fetchDataProduct(url) {
    console.log(url)
    const response = await fetch(url);
    const data = await response.json();
    setDisplayedProducts(data);    
  }

  useEffect(()=>{
    //fetchDataProduct()
    console.log("renderizando el global")
    setUrl("http://18.220.89.28:8080/api/productos/random")
    console.log("Main")
},[])


  useEffect(()=>{
    console.log(url)
    if(url){
      fetchDataProduct(url)
    } 
    console.log(displayedProducts);
    
  },[url])

 

 

  return (
    <>
      <h3 className={styles.recomendations}>Recomendaciones:</h3>
      <div className={styles.container}>
        {displayedProducts.map((card) => (
          <Card className={styles.card} key={card.idProduct} info={card} />
        ))}
      </div>
    </>
  );
};

export default ListContainer;
