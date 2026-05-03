# 💸 iBank Microservices

Proyecto de sistema bancario usando microservicios y arquitectura orientada a eventos.

La idea es separar responsabilidades (clientes, cuentas, transacciones, etc.) y que los servicios se comuniquen entre sí:

- 🔗 Síncrono → Feign  
- 📡 Asíncrono → Kafka  

---

## 🧠 Cómo funciona (resumen rápido)

- El cliente entra por el **gateway**
- Los servicios core manejan datos base (clientes, cuentas)
- Los servicios operacionales hacen la lógica (transacciones, préstamos, inversiones)
- Kafka se usa para enviar eventos
- Otros servicios consumen esos eventos (notificaciones, logs, etc.)

---

## 📦 Servicios

### Core
- `clientes` → datos del cliente  
- `contas` → cuentas y saldo  
- `salud` → análisis financiero  

---

### Operacionales
- `transacoes` → depósitos, retiros, transferencias  
- `prestamos` → créditos  
- `investimentos` → inversiones  

---

### Asíncronos (Kafka)
- `notificacoes` → genera PDF (Jasper) y envía email  
- `portafolio` → calcula estado financiero  
- `logs` → auditoría  

---

## 🛠 Tecnologías

- Java 17 + Spring Boot  
- Feign (comunicación interna)  
- Kafka (eventos)  
- PostgreSQL / H2  
- JasperReports (PDFs)  

---

## 🚀 Cómo correr

1. Levantar Kafka  
2. Ejecutar servicios base (`clientes`, `contas`)  
3. Ejecutar `transacoes`  
4. Ejecutar `notificacoes` para ver eventos  

---

## 💡 Nota

Proyecto pensado para practicar:
- microservicios  
- eventos (Kafka)  
- integración entre servicios  
- generación de PDFs  