package fideburguesas.main;

import fideburguesas.enums.Rol;
import fideburguesas.model.Cajero;
import fideburguesas.model.Combo;
import fideburguesas.model.Producto;
import fideburguesas.model.Sucursal;
import fideburguesas.model.Usuario;
import fideburguesas.repository.DataStore;
import fideburguesas.ui.LoginFrame;

public class Main {

    public static void main(String[] args) {

        Sucursal s1 = new Sucursal("1", "San Pedro", "San José");
        Sucursal s2 = new Sucursal("2", "Curridabat", "San José");

        Usuario admin = new Usuario("A1", "Administrador", "admin", "123", Rol.ADMIN);
        Cajero cajero = new Cajero("C1", "Isabel O.", "isa.o", "123", s1);
        Usuario cocina = new Usuario("K1", "Cocina", "cocina", "123", Rol.COCINA);

        DataStore.usuarios.add(admin);
        DataStore.usuarios.add(cajero);
        DataStore.usuarios.add(cocina);

        Producto p1 = new Producto("P1", "Burger Clásica", 2500, "Hamburguesas");
        Producto p2 = new Producto("P2", "Papas Medianas", 1200, "Acompañamientos");
        Producto p3 = new Producto("P3", "Nuggets", 1800, "Acompañamientos");

        DataStore.productos.add(p1);
        DataStore.productos.add(p2);
        DataStore.productos.add(p3);

        Combo combo1 = new Combo("CB1", "Combo #1", 300);
        combo1.agregarProducto(p1);
        combo1.agregarProducto(p2);
        DataStore.combos.add(combo1);

        new LoginFrame().setVisible(true);
    }
}
