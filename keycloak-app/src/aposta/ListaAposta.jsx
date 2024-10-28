import { useKeycloak } from "@react-keycloak/web";
import { useEffect, useState } from "react";


function ListaAposta() {

    
    const { keycloak, initialized } = useKeycloak();
    const [data, setData] = useState([]);

    useEffect(() => {
        if (initialized && keycloak.authenticated) {
          fetch('http://localhost:8080/aposta', {
            method: 'GET',
            headers: {
              Authorization: `Bearer ${keycloak.token}`, // Adiciona o token ao cabeçalho
            },
          }).then(response => {
            return response.json()
          }).then(data => {
            setData(data)
          })
        }
      }, [initialized, keycloak]);


    return (
        <div>

            <table>
                <tr>
                <td>Id</td>
                <td>Status</td>
                </tr>
                {data.map((aposta, index) => {

                return <tr>
                    <td>{aposta.id}</td>
                    <td>{aposta.status}</td>
                </tr>

                })}

            </table>

        </div>
    )

}

export default ListaAposta;