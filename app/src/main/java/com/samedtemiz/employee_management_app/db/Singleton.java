package com.samedtemiz.employee_management_app.db;

public class Singleton {
    // Bitmap yüklü objeyi göndermek için

    private Employee employee;
    private static Singleton singleton;


    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public static Singleton getInstance(){
         if(singleton == null){
             singleton = new Singleton();
         }

         return singleton;
    }
}
