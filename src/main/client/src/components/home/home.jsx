import React from 'react';
import './home.css';
import TextField from '@mui/material/TextField';
import Create from '../create/create';

function Home() {
    const [childs, setChilds] = React.useState([]);
    const [attributes, setAttributes] = React.useState([]);
    const [tableName, setTableName] = React.useState("categoria");
    const [isEditing, setIsEditing] = React.useState(false);
    const [selectedIndexEditing, setSelectedIndexEditing] = React.useState(-1);
    const [inputValues, setInputValues] = React.useState({});
    const [isLoading, setIsLoading] = React.useState(true);
    const [tableName2, setTableName2] = React.useState("categoria");
    const [creating, setCreating] = React.useState(false);
    const [searchByIdText, setSearchByIdText] = React.useState("")

    // Obtiene los datos de la base de datos al cargar el componente
    React.useEffect(() => {
        getAll()
    }, [tableName]);

    function getAll() {
        fetch('http://localhost:8080/api-biblioteca/' + tableName2)
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
    }

    /**
     * Obtiene los datos de un registro de la base de datos
     * @param {*} id 
     * @param {*} nameTable 
     */
    const getById = (id, nameTable) => {
        setIsLoading(true);
        if(id === "") return getAll();
        fetch('http://localhost:8080/api-biblioteca/' + nameTable + '/' + id)
            .then(response => response.json())
            .then(data => {
                setChilds([data]);
                setAttributes(Object.keys(data));
            })
            .catch(error => {
                console.error('Error al obtener los datos:', error);
                getAll()
            })
            .finally(() => {
                setTableName2(nameTable);
                setIsLoading(false);
            })
    }

    /**
     * Mira de que tipo es el valor de un atributo y lo devuelve como string
     * para mostrarlo en la tabla
     * @param {*} attribute 
     * @param {*} child 
     * @returns 
     */
    const renderCellContent = (attribute, child) => {
        const value = child[attribute];
        const tablesPositibilities = {
            "categoria": {
                nameTable: "categoria",
                nameAttribute: "categoria",
            },
            "listLibros": {
                nameTable: "libro",
                nameAttribute: "nombre",
            },
            "listPrestamos": {
                nameTable: "prestamo",
                nameAttribute: "id",
            },
            "usuario": {
                nameTable: "usuario",
                nameAttribute: "nombre",
            },
            "libro": {
                nameTable: "libro",
                nameAttribute: "nombre",
            },
        };
        // Si el valor es un string o un número, se devuelve tal cual
        if (typeof value === 'string' || typeof value === 'number') {
            return value;
            // Si el valor es un array, se devuelve un string con los ids de los elementos del array
        } else if (Array.isArray(value)) {
            if (value.length === 0) return "-";
            // devuelve el id de los arrays para pulsarle y que te muestre los datos
            return value.length === 1 ? 
            <span className="clickableRoute" onClick={() => {
                if (tablesPositibilities[attribute]["nameTable"] !== undefined) {
                    getById(value[0].id, tablesPositibilities[attribute]["nameTable"]);
                }
            }}>{value[0][tablesPositibilities[attribute]["nameAttribute"]]}</span>
            // este caso es cuando hay mas de un elemento en el array
            : value.map((item) => {
                return <span key={item.id} className="clickableRoute" onClick={() => {
                    if (tablesPositibilities[attribute]["nameTable"] !== undefined) {
                        getById(item.id, tablesPositibilities[attribute]["nameTable"]);
                    }
                }}>{item[tablesPositibilities[attribute]["nameAttribute"]]}{
                        // Si no es el último elemento del array, se añade una coma y un espacio
                        value.indexOf(item) === value.length - 1 ? '' : ', '
                    }
                </span>;
            });
            // Si el valor es un objeto, se devuelve el id del objeto
        } else if (value && typeof value === 'object') {
            return <span className="clickableRoute" onClick={() => {
                if (tablesPositibilities[attribute]["nameTable"] !== undefined) {
                    getById(value.id, tablesPositibilities[attribute]["nameTable"]);
                }
            }
            }>{value[tablesPositibilities[attribute]["nameAttribute"]]}</span>;
        }
    };

    /**
 * Actualiza los datos de un registro en la base de datos
 */
const handleEditing = () => {
    setIsLoading(true);
    // Realizar una petición GET para obtener el objeto completo
    fetch('http://localhost:8080/api-biblioteca/' + tableName + '/' + childs[selectedIndexEditing].id)
        .then(response => response.json())
        .then(existingData => {
            // Obtener los nuevos valores de los inputs
            const updatedData = { ...existingData, ...inputValues };
            
            // Realizar la petición PUT con el objeto completo actualizado
            fetch('http://localhost:8080/api-biblioteca/' + tableName + '/' + childs[selectedIndexEditing].id, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(updatedData),
            })
                .then(response => response.text())
                .then(data => {
                    console.log('Datos actualizados:', data);
                    // Actualizar los datos locales con los datos actualizados del servidor
                    setInputValues({}); // Limpiar los valores de los inputs después de guardar
                    setIsEditing(false);
                    setSelectedIndexEditing(-1);
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
                        });
                })
                .catch(error => {
                    console.error('Error al actualizar los datos:', error);
                })
                .finally(() => {
                    setIsLoading(false);
                });
        })
        .catch(error => {
            console.error('Error al obtener los datos:', error);
            setIsLoading(false);
        });
};


    /**
     * Actualiza el valor de un input
     * @param {*} attribute 
     * @param {*} value 
     */
    const handleInputChange = (attribute, value) => {
        setInputValues(prevState => ({
            ...prevState,
            [attribute]: value,
        }));
    };

    /**
     * Borra un registro de la base de datos
     * @param {*} id 
     */
    const handleDelete = (id) => {
        fetch('http://localhost:8080/api-biblioteca/' + tableName + '/' + id, {
            method: 'DELETE',
            headers: {
                "Access-Control-Allow-Origin": "*",
                "Access-Control-Allow-Methods": "GET, POST, PATCH, PUT, DELETE, OPTIONS",
                "Access-Control-Allow-Headers": "Origin, Content-Type, Authorization",
                'Content-Type': 'application/json',
            },
        })
            .then(response => response.text())
            .then(data => {
                console.log('Datos eliminados:', data);
                // Actualizar los datos locales con los datos actualizados del servidor
            })
            .catch(error => {
                console.error('Error al eliminar los datos:', error);
            })
            .finally(() => {
                const updatedChilds = childs.filter(child => child.id !== id);
                setChilds(updatedChilds);
            });
    }

    /**
     * Funcion que se ejecuta cuando se cambia el valor del select
     * @param {*} e 
     */
    const handleSelectChange = (e) => {
        setTableName(e.target.value);
        setTableName2(e.target.value);
    }

    /**
     * Funcion que se ejecuta cuando se pulsa el boton de recargar
     */
    const handleReloadClick = () => {
        setIsLoading(true);
        fetch('http://localhost:8080/api-biblioteca/categoria')
            .then(response => response.json())
            .then(data => {
                setChilds(data);
                setAttributes(Object.keys(data[0]));
                setTableName("categoria");
                setTableName2("categoria");
            })
            .catch(error => {
                console.error('Error al obtener los datos:', error);
            })
            .finally(() => {
                setSearchByIdText("")
                setIsEditing(false);
                setIsLoading(false);
            })
    }

    if (isLoading) {
        return <p>Cargando...</p>;
    }

    if (creating) {
        return <Create tableName={tableName2} setCreating={setCreating} getAll={getAll}/>;
    }

    return (
        <>
        {/* Header */}
            <div className='header'>
                {/* Input para buscar */}
                <TextField label="Buscar por id" sx={{
                    width: 380,
                }} onChange={(e) => {
                    setSearchByIdText(e.target.value)
                }} value={searchByIdText}/>
                <button onClick={() => {
                    getById(searchByIdText, tableName2)
                    setSearchByIdText("")
                }} className='btnsito'>
                    Buscar
                </button>
                {/* Select para cambiar la tabla */}
                <select id="selectTable" onChange={handleSelectChange} defaultValue={tableName2}>
                    <option value="categoria">Categoria</option>
                    <option value="libro">Libro</option>
                    <option value="prestamo">Prestamo</option>
                    <option value="usuario">Usuario</option>
                </select>
                {/* Botón de recargar */}
                <img src='/reload.png' alt="Reload" onClick={handleReloadClick} className='imgReload' />
            </div>

            {/* Nav */}
            <div className='spanTableNameDiv'>
                <span className='spanTableName'>Tabla: {tableName2}</span>
                <img src='/add.png' alt="Add" className='imgAdd' onClick={() => {
                    setCreating(true);
                }}/>
            </div>

            {/* Tabla */}
            <table>
                <thead>
                    <tr>
                        {attributes.map((attribute, index) => {
                            // Muestra los atributos de la tabla de la base de datos en el thead
                            return <th key={index}>{attribute}</th>;
                        })}
                        <th>Editar</th>
                        <th>Eliminar</th>
                    </tr>
                </thead>
                <tbody>
                    {childs.map((child, index) => {
                        return (
                            <tr key={index}>
                                {attributes.map((attribute, index2) => {
                                    const value = child[attribute];
                                    let isObject = false;
                                    if(typeof value === 'object' || Array.isArray(value)) {
                                        isObject = true;
                                    }
                                    // Muestra el valor de cada atributo de la tabla de la base de datos
                                    // en una celda de la tabla
                                    return (
                                        <td key={index2}>
                                            {isEditing && index === selectedIndexEditing ?
                                            // Si se está editando, se muestra un input en lugar del valor
                                                <input
                                                    type="text"
                                                    defaultValue={isObject ? "" : value}
                                                    disabled={isObject}
                                                    onChange={e => handleInputChange(attribute, e.target.value)}
                                                /> :
                                                renderCellContent(attribute, child)
                                            }
                                        </td>
                                    );
                                })}
                                {isEditing && index === selectedIndexEditing ?
                                // Si se está editando, se muestra un botón de guardar en lugar de uno de editar
                                    <td>
                                        <img src='/guardar.png' className='icons' onClick={handleEditing} />
                                    </td> :
                                    <td>
                                        <img src='/editar.png' className='icons' onClick={() => {
                                            setIsEditing(true);
                                            setSelectedIndexEditing(index);
                                            setInputValues({});
                                        }} />
                                    </td>
                                }
                                {/* Imagen de borrar */}
                                <td>
                                    <img src='/basura.png' className='icons' onClick={() => {
                                        handleDelete(child.id);
                                    }} />
                                </td>
                            </tr>
                        );
                    })}
                </tbody>
            </table>
        </>
    );
}

export default Home;
