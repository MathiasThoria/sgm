## 06/26 - Primer Incremento  
### Se entrega primer ingremento. Tutor corrige arquitectura y comunicacion 
### de capas
---
### Requerimientos del tutor:
#### Clases obligatorias
- `Datos`
- `Usuario`
- `Préstamo`
- `ManejadorDatos` (o `MDatos`)
- `ColecciónUsuarios` (opcional como clase o atributo)
- `ColecciónPréstamos` (dentro de cada `Usuario`)
---
#### Detalles por clase
#### Clase `Datos`
- Atributos:
  - `String ruta`: contiene la ruta del archivo XLS/XML.
  - `String cadena`: contiene todos los datos concatenados del archivo.
- Constructor por defecto:
  - Lee el archivo.
  - Guarda el contenido en `cadena` con:
    - `,` como separador de campos.
    - `;` como separador de registros (puede usarse temporalmente `--`).
- Solo lee datos. **No crea objetos** de usuario ni préstamo.
- Incluir `getters` y `setters`.
#### Clase `Usuario`
- Atributos:
  - `id`, `nombre`, `apellido`, `correo`.
  - Una colección de `Préstamo`.
- La colección puede ser:
  - `ArrayList`, `LinkedList` o `HashTable`.
- Justificar la estructura elegida.
- Cada `Usuario` debe contener su propia colección de préstamos.
#### Clase `Préstamo`
- Atributos:
  - `fechaPedido`
  - `fechaDevolucion`
  - `idEjemplar`
  - `tituloEjemplar`

#### Clase `ManejadorDatos` (o `MDatos`)
- Atributos:
  - Un objeto de tipo `Datos`.
  - Una colección de `Usuario`.
- Responsabilidades:
  - Procesa el atributo `cadena` de `Datos`.
  - Crea objetos `Usuario` y `Préstamo`.
  - Estructura los datos leídos desde el archivo.
  - Actúa como frontera con el MVC.
---
### Estructura y arquitectura
- `Datos` debe estar en la capa `persistencia`.
- La comunicación entre capas debe ser **pura** (sin paso de objetos, solo datos primitivos o `String`).
- El módulo puede nombrarse como `DatosExcel` (nombre tentativo).
---
### Extras sugeridos
- Incluir un **plan de pruebas** para estas clases (opcional).
- Generar:
  - Diagrama conceptual.
  - Diagrama de clases (implementación).
- La interfaz puede ser por consola, no se requiere GUI todavía.

### 07/08 - Implementacion de correcciones  
- Se implementan correcciones dadas por tutor. 
- Se impelmentan primeras versiones de vista-controlador-logica. Se intenta
- distribuir corrrectamente las responsabilidades en cada capa. Quedan dudas al respecto.
- Las capas se comunican mayoritariamente mediante Strings.
- Resta reflexionar sobre "historial" o registro de deudas para el seguimeinto de morosos,
- lo cual fue mencionado por el tutor para su posterior analisis.

### 30/8

- Datos gestiona la conexion con el Xls.
- Servicio de Mensajeria se encarga de la conexion con el servidor de correo y el envio de mails.
- ManejadorMensajes se encarga de levantar los datos del modelo y solicitar a ServicioMensajeria
- ConexionBD se encarga de conexion a la base de datos, cerrarla, ejecutar querys y updates.
- MenasjeEnviado es una clase que representa el mensaje automaticamente enviado. Tiene un String
para guardar los titulos que se adeudan y la suma de dias. Esto ultimo fue bajo solicitud del tutor (se 
reemplazo map que representaban titulos y dias de atraso). 
- Historias es una coleccion que guarda los mensajes.
- UsuariosSistemaBD se encarga de preparar el SQL para solicitar su ejecucion al ConexionBD
- XlsParser obsoleto
- En el paquete test tenemos pruebas para cada nueva implementacion. Se ejecutan por separado.



