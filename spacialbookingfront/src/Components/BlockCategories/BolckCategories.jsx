import React, { useEffect, useState } from 'react';
import { GlobalContext } from "../globalState/GlobalState";
import styles from './BlockCategories.module.css'


const Categories = () => {

 // Creación de estado para guardar información de la API
    const [dataApi, setDataApi] = useState();

    const {setUrl}= GlobalContext()
    const urlAPICategories = "http://18.220.89.28:8080/api/categorias";


    // Creación función asincróna para consumir la API
    async function getData(url){
        const data = await fetch(url);
        const dataCategories = await data.json();
        setDataApi(dataCategories)
    }

    useEffect(()=>{
        getData(urlAPICategories);
    },[])


  return (
    <div className={styles.categories}>     
        <h3 className={styles.titleCategories}>Busca por tipo de experiencia:</h3>
        <ul className={styles.categoriesContainer} >

        {dataApi?.map((category) => (
            <li className={styles.category}  key={category.categoryId} onClick={()=>setUrl(`http://18.220.89.28:8080/api/productos/categoria/${category.categoryId}`)}>
                <div  > 
                    <img id={styles.categoryImg} src={category.urlImage} alt={category.title} />
                </div>
                <div className={styles.text}>
                    <h4 className={styles.categoryTitle}>{category.title}</h4>
                    <p className={styles.categoryDescription}>{`${category.description.substring(0,233)}...`}</p>

                </div>            
            </li>
        ))}
        </ul>
    </div>
  );
};

export default Categories;












