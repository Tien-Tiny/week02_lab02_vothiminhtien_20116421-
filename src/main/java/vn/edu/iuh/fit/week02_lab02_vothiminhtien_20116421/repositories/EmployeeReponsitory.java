package vn.edu.iuh.fit.week02_lab02_vothiminhtien_20116421.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;
import vn.edu.iuh.fit.week02_lab02_vothiminhtien_20116421.connection.ConnectDB;
import vn.edu.iuh.fit.week02_lab02_vothiminhtien_20116421.enums.EmployeeStatus;
import vn.edu.iuh.fit.week02_lab02_vothiminhtien_20116421.models.Employee;

import java.util.List;
import java.util.Optional;

public class EmployeeReponsitory {
    private final EntityManager em;
    private EntityTransaction tran;

    public EmployeeRepository(){
        em = ConnectDB.getInstance().getEntityManagerFactory().createEntityManager();
        tran = em.getTransaction();
    }

    public EmployeeReponsitory(EntityManager em) {
        this.em = em;
    }

    public Optional<Employee> getOneById(long id){
        TypedQuery<Employee> query = em.createNamedQuery("Employee.findAll", Employee.class);
        query.setParameter(1,id);
        Employee emp = query.getSingleResult();

        return emp == null ? Optional.empty() : Optional.of(emp);
    }

    public List<Employee> getAllEmp(){
        return  em.createNamedQuery("Employee.findAll",Employee.class)
                .setParameter(1, EmployeeStatus.ACTIVE)
                .getResultList();
    }

    public boolean insertEmp(Employee e){
        try{
            tran.begin();
            em.persist(e);
            tran.commit();
            return true;
        }catch (Exception ex){
            tran.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    public boolean updateEmp(Employee e){
        try{
            tran.begin();
            em.merge(e);
            tran.commit();
            return true;
        }catch (Exception ex){
            tran.rollback();
            ex.printStackTrace();
            return false;
        }
    }

    public boolean setStatusEmp(long id) {
        try {
            tran.begin();
            int n = em.createNamedQuery("Employee.findAll")
                    .setParameter(1, EmployeeStatus.TERMINATED)
                    .setParameter(2, id)
                    .executeUpdate();

            tran.commit();
            return n > 0;
        } catch (Exception ex) {
            tran.rollback();
            ex.printStackTrace();
            return false;
        }
    }
}
