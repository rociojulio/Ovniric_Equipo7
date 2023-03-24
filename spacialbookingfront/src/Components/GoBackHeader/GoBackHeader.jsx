import React from "react";
import styles from "./GoBackHeader.module.css";
import { IoIosArrowBack } from "react-icons/io";

const GoBackHeader = (props) => {
 const back = () => {
     window.history.back();
 }

return (
<section className={styles.headerContainer}>
    <article>
        <span className={styles.category}>{props.category}</span>
        <h2 className={styles.title}> {props.title}</h2> 
    </article>
    <IoIosArrowBack className={styles.goBack} onClick={back}/>
</section>
);
};

export default GoBackHeader;