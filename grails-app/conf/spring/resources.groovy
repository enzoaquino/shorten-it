import org.springframework.orm.hibernate3.HibernateTransactionManager

beans {
    transactionManager(HibernateTransactionManager) {
        sessionFactory = ref('sessionFactory')
    }
}
