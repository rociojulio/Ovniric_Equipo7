import React from "react";
import { policies } from "../../assets/Json/policies";
import styles from "./Policies.module.css"


const Policies = () => {
    
return (
  <>
    <h3 className={styles.policiesTitle}>¡Información importante!</h3>
    <section className={styles.policiesContainer}>
      <article>
        <h4 className={styles.policyTitle}>Normas</h4>
        {policies.normas.map((norma) => (
          <p className={styles.policyContent}>{norma}</p>
        ))}
      </article>
      <article>
        <h4 className={styles.policyTitle}>Salud</h4>
        {policies.salud.map((element) => (
          <p className={styles.policyContent}>{element}</p>
        ))}
      </article>
      <article>
        <h4 className={styles.policyTitle}>Cancelaciones</h4>
        {policies.cancelacion.map((element) => (
          <p className={styles.policyContent}>{element}</p>
        ))}
      </article>
    </section>
  </>
); 

}


export default Policies;