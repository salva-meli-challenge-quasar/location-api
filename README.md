# DESAFIO TÉCNICO - MELI
## Operación de fuego quasar

## location-api

### Objetivo
Esta API tiene como fin brindar el servicio para, a partir de 3 puntos (P1, P2, P3) y 3 distancias de un punto Pk hacia cada uno de estos puntos anteriores, calcular las coordenadas del punto Pk.

### Especificación

#### Tecnologías
Las tecnologías que se utilizaron son:
- Java 1.8
- Maven
- SpringBoot

La API se encuentra hosteada en **heroku**, que es un servicio de hosting que brinda opciones para hostear APIs de manera gratuita. Para más información puede ingresar a www.heroku.com.

**URL del servicio: https://salva-location-api.herokuapp.com/location**

#### Llamadas provistas
Realizando un POST a la URL https://salva-location-api.herokuapp.com/location se recibe como respuesta la ubicación del punto Pk.

#### Ejemplo del Body del POST

```JSON
{
  "points": [
    {
      "x": 50,
      "y": 0
    },
    {
      "x": 200,
      "y": 0
    },
    {
      "x": 200.7,
      "y": -150.1
    }
  ],
 "distances": [
    150,
    350,
    561.2
  ]
}
```
Y el Response con código 200 y body:
```JSON
{
  "location": {
    "x": -100,
    "y": 75.5
  },
}
```
En caso de no poderse determinar la posición del punto Pk se retorna un response con mensaje de error y código 404.

### Diagramas

#### Diagrama de Clases
![diagrama de clases](src/main/resources/documentation/diagrams/class-diagram.png)

#### Diagrama de Secuencia
##### Cálculo de ubicación - flujo normal
![diagrama de clases](src/main/resources/documentation/diagrams/calculate-location-happy-path.png)
