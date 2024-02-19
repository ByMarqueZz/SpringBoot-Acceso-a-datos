# Proyecto SpringBoot Acceso a Datos

Este es un proyecto realizado con el framework **SpringBoot**, con el que se va a modelar un microservicio API REST, desarrollado partiendo de la base del proyecto de la unidad 3, en el que se gestionó la administración de la base de datos *Bibiloteca.sql* con Hibernate.

## Elaboración del proyecto paso a paso

La elaboración de las tareas, en orden cronológico, ha sido la siguiente:

1. **Creación de las Entidades**

2. **Creación de los Controladores**

   - 2.1 Creación del Controlador de Usuario

   - 2.2 Creación del Controlador de Libro

   - 2.3 Creación del Controlador de Categoría

   - 2.4 Creación del Controlador de Prestamos

3. **Test de los Controladores**

    - 3.1 Test del Controlador de Usuario

   - 3.2 Test del Controlador de Libro

   - 3.3 Test del Controlador de Categoría

   - 3.4 Test del Controlador de Prestamos

4 **Creación del cliente**

   - 3.1 Todos los métodos GET de la API

   - 3.2 Arreglar problema CORS con los métodos POST, PUT y DELETE

   - 3.3 Creación formularios para añadir

   - 3.4 Test de la aplicación cliente



# Estructura del Proyecto

## Creación de las Entidades

A continuación , se va a explicar la implementación del patrón de diseño DAO

En primer lugar tenemos las entidades , que son en esencia una copia de las tablas de la base de datos , pero vamos a entrar más en detalle

**Entidad Categoria**

```java
@Entity
@Table(name = "categoria", schema = "BIBLIOTECA", catalog = "")
public class EntidadCategoria {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "categoria", nullable = true, length = -1)
    private String categoria;
    @OneToMany(mappedBy = "categoria")
    @JsonIgnoreProperties("categoria")
    private Collection<EntidadLibro> listLibros;
```
En esta entidad , la clave está en el uso de *@JsonIgnoreProperties("categoria")* , pues nos evita tener bucles infinitos debido a que usamos la referencia de la clase en lugar de las claves ajenas , es decir ,  indicamos a Spring que no incluya una propiedad específica en el conjunto JSON final devuelto


**Entidad Libro**
```java
    @JsonIgnoreProperties("libro")
    private Collection<EntidadPrestamo> listPrestamos;
```

**Entidad Prestamo**
```java
    @ManyToOne
    @JoinColumn(name = "idlibro", referencedColumnName = "id")
    @JsonIgnoreProperties("listPrestamos")
    private EntidadLibro libro;

    @ManyToOne
    @JoinColumn(name = "idusuario", referencedColumnName = "id")
    @JsonIgnoreProperties("listPrestamos")
    private EntidadUsuario usuario;
```

**Entidad Usuario**
```java
    @JsonIgnoreProperties("usuario")
    private Collection<EntidadPrestamo> listPrestamos;
```


# Patrón de Diseño DAO

Este patrón consta de 3 partes:

1. Interfaz
2. DTOs
3. Implementación/Controlador

## Interfaces

Comenzaremos con las interfaces , y la clave de estas está en la herencia de la clase *CRUDRepository* , que indicará tanto que la entidad/clase para los datos es la que especifiquemos , como el tipo de dato del campo clave. Veamos un ejemplo


**Interfaz ICategoriaDAO**
```java
   @Repository
    public interface ICategoriaDAO extends CrudRepository<EntidadCategoria, Integer> {
    EntidadCategoria findByCategoriaIgnoreCase(String categoria);
}
```

En este código , básicamente estamos especificando que la entidad que vamos a usar es EntidadCategoría , y que el tipo de datos de su campo clave es Integer. Además , usamos la anotación @Repository para marcar que esta clase para marcar las clases que manejan el acceso a datos en la capa de persistencia.

## DTOs

Los DTOs son esencia POJOS de las tablas de la base de datos , que se rellenan de manera dinámica con la información necesaria cuando se accede al endpoint correspondiente. Este método soluciona la problemática de tener que recuperar información de más de una tabla , debido a que se rellena la información en el mismo momento , además de darnos la posibilidad de recuperar una parte de la tabla que el usuario desee

A modo de ejemplo , este sería el DTO de Categoría

**Clase CategoriaDTO**
```java
   public class CategoriaDTO {
    private int id;
    private String categoria;

    public CategoriaDTO(int id, String categoria) {
        this.id = id;
        this.categoria = categoria;
    }

    public int getId() {
        return id;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}
```

## Creación de los Controladores

El primer paso en la implentación de los controladores es añadirle las anotaciones @RestController y @RequestMapping. Con esto conseguimos marcarlos como una *API - Rest* , además de indicar que el controlador responderá con datos solicitados en el
cuerpo de la página en lugar de mostrar páginas web.

**Clase ControladorCategoria**
```java
    @RestController
    @RequestMapping("/api-biblioteca/categoria")
    public class ControladorCategoria
```

Lo siguiente es inicializar la instancia de la interfaz(DAO) , puesto que el controlador depende de él para realizar sus funciones , por lo que procedemos a usar la anotación *Autowired* , que realiza esto mismo

**Clase ControladorCategoria**
```java
    @Autowired
    ICategoriaDAO categoriaDAO;
```

Con esta instancia , hacemos uso de las diferentes anotaciones que tenemos disponibles para realizar las funciones de la gestión

**Clase ControladorCategoria**
```java
    @GetMapping("/{id}") //endpoint para buscar un categoria por id
    public ResponseEntity<EntidadCategoria> buscarCategoriaPorId(@PathVariable(value = "id") int id) {
        Optional<EntidadCategoria> categoria = categoriaDAO.findById(id);
        if (categoria.isPresent())
            return ResponseEntity.ok().body(categoria.get());// HTTP 200 OK
        else return ResponseEntity.notFound().build();      // HTTP 404
    }

    @PostMapping
    public EntidadCategoria guardarCategoria(@Validated @RequestBody EntidadCategoria categoria) {
        return categoriaDAO.save(categoria);
    }

    @CrossOrigin("http://localhost:5173")
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarCategoria(@RequestBody EntidadCategoria nuevaCategoria, @PathVariable(value = "id") int id) {
        Optional<EntidadCategoria> categoria = categoriaDAO.findById(id);
        if (categoria.isPresent()) {
            categoria.get().setCategoria(nuevaCategoria.getCategoria());
            categoriaDAO.save(categoria.get());
            return ResponseEntity.ok().body("Actualizado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<?> borrarCategoria(@PathVariable(value = "id") int id) {
        Optional<EntidadCategoria> categoria = categoriaDAO.findById(id);
        if (categoria.isPresent()) {
            categoriaDAO.deleteById(id);
            return ResponseEntity.ok().body("Borrado");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
```

En este código , usamos @GetMapping("/{id}") , @PostMapping , @PutMapping("/{id}") y @DeleteMapping("/{id}") para crear endpoints que realicen una acción según la anotación usada. Otros aspectos interesantes serían:

- **@Validated y @RequestBody** : Asegura que los datos proporcionados sobre el empleado sean válidos antes de guardarlos en la base de datos.

- **@PathVariable**: Extrae los valores de las variables de la URL y los pasa como parámetros

- **@PutMapping** : Tiene la misma función que **@PutMapping** , solo que se usa para la actualización parcial

- **@CrossOrigin** : Se usa para permitir solicitudes desde un origen diferente al del servidor en el que se encuentra el backend.

## Test de los Controladores

## Creación del cliente
**Todos los métodos GET de la API**
```javascript
    // Obtiene los datos de la base de datos al cargar el componente
    React.useEffect(() => {
        fetch('http://localhost:8080/api-biblioteca/' + tableName)
            .then(response => response.json())
            .then(data => {
                setChilds(data);
                setAttributes(Object.keys(data[0]));
            })
            .catch(error => {
                console.error('Error al obtener los datos:', error);
            })
            .finally(() => {
                setIsLoading(false);
            })
    }, [tableName]);

    /**
     * Obtiene los datos de un registro de la base de datos
     * @param {*} id 
     * @param {*} nameTable 
     */
    const getById = (id, nameTable) => {
        setIsLoading(true);
        fetch('http://localhost:8080/api-biblioteca/' + nameTable + '/' + id)
            .then(response => response.json())
            .then(data => {
                setChilds([data]);
                setAttributes(Object.keys(data));
            })
            .catch(error => {
                console.error('Error al obtener los datos:', error);
            })
            .finally(() => {
                setTableName2(nameTable);
                setIsLoading(false);
            })
    }
```
Primero de todo en el cliente nada más cargar la página se hace una petición
GET a la API dependiendo de la tabla que tengas seleccionada y muestra los datos
obtenidos en una tabla
#####
**Arreglar problema CORS con los métodos POST, PUT y DELETE**
```java
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("http://localhost:5173");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("PATCH");
        config.addAllowedMethod("DELETE");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}
```
Para las peticiones GET no había problema pero para las demás, el servidor
de Springboot por defecto bloquea cualquier petición que no sea GET para
los origenes desconocidos, por lo que se ha tenido que añadir una configuración
y así con esto controla y acepta que peticiones puede aceptar y que otros peticiones no debe aceptar

**Creación formularios para añadir**
```javascript
    const handleSubmit = (e) => {
        e.preventDefault();
        fetch('http://localhost:8080/api-biblioteca/' + tableName, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(form)
        })
            .then(response => response.json())
            .then(data => {
                console.log('Success:', data);
            })
            .catch((error) => {
                console.error('Error:', error);
            });
    }
```
Se ha creado un formulario para cada tabla y asi enviar una petición POST a la API