Sistema POS - FideBurguesas
Aplicación de Punto de Venta (POS) desarrollada en Java & MySQL para la gestión de pedidos en una cadena de comida rápida.

Pasos para la Ejecución del sistema
1. Abrir NetBeans 
2. Abrir el proyecto FideBurguesasPOS  
3. Ejecutar la clase ServidorPOS.java
4. Ejecutar la clase Main.java

Accesos al sistema
Administrador
- Usuario: admin  
- Contraseña: 123  

Cajeros
- Cajero San Pedro  
  - Usuario: isa.o  
  - Contraseña: 123  
- Cajero Curridabat  
  - Usuario: isa.c  
  - Contraseña: 123  

Cocina
- Usuario: cocina  
- Contraseña: 123  

Funcionalidades
- Gestión de usuarios  
- Registro de productos  
- Creación de órdenes  
- Monitor de cocina (visualización de pedidos pendientes)  
- Facturación de órdenes  
- Generación de factura en archivo (.txt)  

Base de datos (MySQL)
El sistema utiliza MySQL para almacenar toda la información:
- Usuarios  
- Productos  
- Órdenes  
- Líneas de orden  
- Facturas  

Redes (Sockets)
Se implementa comunicación cliente-servidor mediante sockets:
- Caja envía órdenes al servidor  
- Cocina recibe notificaciones de nuevas órdenes  

Arquitectura
El sistema está organizado por capas:
- Interfaz gráfica (Swing)  
- Lógica de negocio  
- Persistencia (MySQL)  
- Comunicación en red  
