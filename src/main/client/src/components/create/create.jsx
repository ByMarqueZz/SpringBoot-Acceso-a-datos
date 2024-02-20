import React from 'react'

function Create(props) {
    const [categorias, setCategorias] = React.useState([])
    const [libros, setLibros] = React.useState([])
    const [usuarios, setUsuarios] = React.useState([])
    const [prestamos, setPrestamos] = React.useState([])
    //usuario
    const [nombre, setNombre] = React.useState("")
    const [apellidos, setApellidos] = React.useState("")
    //libro
    const [nombreLibro, setNombreLibro] = React.useState("")
    const [autor, setAutor] = React.useState("")
    const [editorial, setEditorial] = React.useState("")
    const [categoria, setCategoria] = React.useState("")
    // categoria
    const [nombreCategoria, setNombreCategoria] = React.useState("")
    // prestamo
    const [usuarioPrestamo, setUsuarioPrestamo] = React.useState("")
    const [libroPrestamo, setLibroPrestamo] = React.useState("")

    React.useEffect(() => {

        fetch("http://localhost:8080/api-biblioteca/categoria")
            .then((response) => response.json())
            .then((data) => setCategorias(data))
            .catch((error) => console.log(error))

        fetch("http://localhost:8080/api-biblioteca/libro")
            .then((response) => response.json())
            .then((data) => setLibros(data))
            .catch((error) => console.log(error))

        fetch("http://localhost:8080/api-biblioteca/usuario")
            .then((response) => response.json())
            .then((data) => setUsuarios(data))
            .catch((error) => console.log(error))

        fetch("http://localhost:8080/api-biblioteca/prestamo")
            .then((response) => response.json())
            .then((data) => setPrestamos(data))
            .catch((error) => console.log(error))
    }, [])


    const handleClickUser = (e) => {
        e.preventDefault()
        fetch("http://localhost:8080/api-biblioteca/usuario", {
            method: "POST",
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                "nombre": nombre,
                "apellidos": apellidos
            })
        })
            .then((response) => response.json())
            .then((data) => console.log(data))
            .catch((error) => console.log(error))
            .finally(() => {
                props.setCreating(false)
                props.getAll()
            })
    }

    const handleClickLibro = (e) => {
        e.preventDefault()
        fetch("http://localhost:8080/api-biblioteca/libro", {
            method: "POST",
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                "nombre": nombreLibro,
                "autor": autor,
                "editorial": editorial,
                "categoria": {
                    "id": categoria
                }
            })
        })
            .then((response) => response.json())
            .then((data) => console.log(data))
            .catch((error) => console.log(error))
            .finally(() => {
                props.setCreating(false)
                props.getAll()
            })
    }

    const handleClickCategoria = (e) => {
        e.preventDefault()
        fetch("http://localhost:8080/api-biblioteca/categoria", {
            method: "POST",
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                "categoria": nombreCategoria
            })
        })
            .then((response) => response.json())
            .then((data) => console.log(data))
            .catch((error) => console.log(error))
            .finally(() => {
                props.setCreating(false)
                props.getAll()
            })
    }

    const handleClickPrestamo = (e) => {
        e.preventDefault();
    
        // Obtener la fecha actual en formato ISO 8601
        const fechaPrestamo = new Date().toISOString();
    
        fetch("http://localhost:8080/api-biblioteca/prestamo", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                "fechaPrestamo": fechaPrestamo,
                "libro": {
                    "id": libroPrestamo
                },
                "usuario": {
                    "id": usuarioPrestamo
                }
            })
        })
        .then((response) => {
            if (!response.ok) {
                throw new Error('La solicitud no pudo ser procesada correctamente');
            }
            return response.json();
        })
        .then((data) => {
            console.log(data);
            props.setCreating(false);
            props.getAll();
        })
        .catch((error) => {
            console.log(error);
        });
    }
    

    if (props.tableName == "usuario") {
        return (
            <div>
                <h3>Crear Usuario</h3>
                <div className='mb-3'>
                    <span onClick={() => {
                        props.setCreating(false)
                    }} className='mb-3 spansito'>
                        Volver
                    </span>
                </div>
                <form className='form'>
                    <div className='mb-3'>
                        <input className="form-control" type="text" placeholder="Nombre" onChange={(e) => {
                            setNombre(e.target.value)
                        }} />
                    </div>
                    <div className='mb-3'>
                        <input className="form-control" type="text" placeholder="Apellidos" onChange={(e) => {
                            setApellidos(e.target.value)
                        }} />
                    </div>

                    <button className='btn btn-primary' onClick={handleClickUser}>Crear</button>
                </form>
            </div>
        )
    } else if (props.tableName == "libro") {
        return (
            <div>
                <h3>Crear Libro</h3>
                <div className='mb-3'>
                    <span onClick={() => {
                        props.setCreating(false)
                    }} className='mb-3 spansito'>
                        Volver
                    </span>
                </div>
                <form className='form'>
                    <div className='mb-3'>
                        <input className="form-control" type="text" placeholder="Nombre" onChange={(e) => {
                            setNombreLibro(e.target.value)
                        }} />
                    </div>
                    <div className='mb-3'>
                        <input className="form-control" type="text" placeholder="Autor" onChange={(e) => {
                            setAutor(e.target.value)
                        }} />
                    </div>
                    <div className='mb-3'>
                        <input className="form-control" type="text" placeholder="Editorial" onChange={(e) => {
                            setEditorial(e.target.value)
                        }} />
                    </div>
                    <div className='mb-3'>
                        <select className="form-control" onChange={(e) => {
                            setCategoria(e.target.value)
                        }}>
                            <option value="">Seleccionar Categoria</option>
                            {categorias.map((categoria, index) => {
                                return <option key={index} value={categoria.id}>{categoria.categoria}</option>
                            })}
                        </select>
                    </div>

                    <button className="btn btn-primary" onClick={handleClickLibro}>Crear</button>
                </form>
            </div>
        )
    } else if (props.tableName == "categoria") {
        return (
            <div>
                <h3>Crear Categoria</h3>
                <div className='mb-3'>
                    <span onClick={() => {
                        props.setCreating(false)
                    }} className='mb-3 spansito'>
                        Volver
                    </span>
                </div>
                <form className='form'>
                    <div className='mb-3'>
                        <input type="text" placeholder="Nombre" onChange={(e) => {
                            setNombreCategoria(e.target.value)
                        }} className='form-control' />
                    </div>

                    <button className="btn btn-primary" onClick={handleClickCategoria}>Crear</button>
                </form>
            </div>
        )
    } else if (props.tableName == "prestamo") {
        return (
            <div>
                <h3>Crear Prestamo</h3>
                <div className='mb-3'>
                    <span onClick={() => {
                        props.setCreating(false)
                    }} className='mb-3 spansito'>
                        Volver
                    </span>
                </div>
                <form className='form'>
                    <div className='mb-3'>
                        <select onChange={(e) => {
                            setUsuarioPrestamo(e.target.value)
                        }} className='form-control'>
                            <option value="">Seleccionar Usuario</option>
                            {usuarios.map((usuario, index) => {
                                return <option key={index} value={usuario.id}>{usuario.nombre} {usuario.apellidos}</option>
                            })}
                        </select>
                    </div>
                    <div className='mb-3'>
                        <select onChange={(e) => {
                            setLibroPrestamo(e.target.value)
                        }} className='form-control'>
                            <option value="">Seleccionar Libro</option>
                            {libros.map((libro, index) => {
                                return <option key={index} value={libro.id}>{libro.nombre}</option>
                            })}
                        </select>
                    </div>

                    <button className='btn btn-primary' onClick={handleClickPrestamo}>Crear</button>
                </form>
            </div>
        )
    }
    return (
        <div>

        </div>
    )
}

export default Create