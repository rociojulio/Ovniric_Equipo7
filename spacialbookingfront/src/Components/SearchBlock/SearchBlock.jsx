import React from 'react'; 
import  styles from './SearchBlock.module.css'
import Serachform from '../SearchForm/SearchForm'

function SearchBlock(){      


    return(
    <div className={styles.shadow}>
        <div className={styles.searchBlock}>
            <div className={styles.header} id={styles.titleSearch}>
                <h1>Encuentra</h1>
                <p className={styles.searchP}>¡El lugar y momento donde guardaras el mejor recuerdo de tu vida! </p>                               
            </div> 
            <Serachform className={styles.Disp_grid}/>          
        </div>
    </div>
    );
}

export default SearchBlock;