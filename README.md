<div align="center">
  <img src="https://github.com/Isaac640/IncidenciasDI/blob/Imagenes/imagenes/IRIS%20sin%20fondo%20y%20nombre%20banner.png?raw=true" alt="Banner cabecera">
</div>

# IRIS

El proyecto I.R.I.S (Incidencias Resueltas de forma Inteligente y Segura) es una solución completa para la gestión de incidencias en el entorno educativo del Instituto Miguel Herrero de Pereda. Consta de dos componentes principales de código abierto, una aplicación móvil desarrollada en Kotlin destinada al profesorado como usuarios y una aplicación de escritorio desarrollada en Windows Form.NET, que cumple las funcionalidades de la aplicación móvil pero desde el escritorio, además de contar con un apartado para administradores que permite gestionar dichas incidencias y modificar sus estados según cómo vayan resolviéndose.

Si quiere saber más sobre el proyecto IRIS visite nuestro [repositorio principal](https://github.com/Isaac640/IncidenciasDI)

## Índice

* [Descripción](#descripción)
* [Documentación API](#documentación-de-la-api)
* [Instalación](#instalación)
  * [Emulación en Android Studio](#emulación-en-android-studio)
  * [Emular en dispositivo móvil](#emular-en-dispositivo-móvil)
  * [Instalación en dispositivo](#instalación-en-dispositivo)
* [Características](#características)
    * [Versión](#versión)
    * [Utilidades](#utilidades)
    * [Seguridad](#seguridad)
* [Korobot](#korobot)
* [Uso](#uso)
    * [Registro de incidencias](#registro-de-incidencias)
    * [Seguimiento de estado](#seguimiento-de-estado)
    * [Video tutorial](#video-tutorial)
* [Miembros](#miembros)
* [Contribución](#contribución)
* [Contacto](#contacto)
* [Licencia](#licencia)

## Descripción

El proyecto IRIS está diseñado para proporcionar una solución completa para la gestión de incidencias en el entorno educativo del Instituto Miguel Herrero de Pereda. La aplicación móvil, programada en Kotlin a través de Android Studio ofrece una serie de características que ayudan a los usuarios(profesorado)a gestionar eficazmente las incidencias y resolver los problemas de manera inteligente y segura. Nuestra máxima en el equipo de IRIS es la sencillez, la optimización, y la facilidad de uso, y eso es lo que reflejamos en nuestra aplicación mediante pantallas limpias, funciones siempre útiles y ligeras para nuestro dispositivo móvil.


## Documentación de la API

- [Documentación oficial de la API mediante Swagger](http://localhost:8089/swagger-ui/index.html)

La documentación oficial de nuestra API proporciona mediante Swagger una descripción detallada de los endpoints disponibles, los parámetros de solicitud, las respuestas esperadas y cualquier otra información relevante para interactuar con nuestra API.

**Notas adicionales**:
- Asegúrate de que la API esté en funcionamiento para acceder a la documentación.
- Si cambias el puerto por el que la API escucha (8089), recuerda actualizar también el puerto en la dirección del enlace.

- [Listado de endpoints](https://github.com/Isaac640/IncidenciasAPI/blob/main/ENDPOINTS.md)

Para más información sobre nuestra API, consulte el [repositorio API](https://github.com/Isaac640/IncidenciasAPI)

## Instalación

Actualmente la aplicación móvil solo puede emularse a través de Android Studio o un dispositivo móvil compatible, si se desea, aquí elaboramos una breve guía para realizarlo.

### Emulación en Android Studio

#### Requisitos previos
      - Java Development Kit (JDK) 1.8 o superior
      - Android Studio 28 o superior

#### Pasos de instalación

1. Clona el repositorio de GitHub en tu máquina local:

```bash
git clone https://github.com/Tania105/Android_Grupo1.git
```
2. Abre el proyecto en Android Studio.

3. Compila y ejecuta la aplicación en un emulador o dispositivo Android.

4. ¡Listo! Ahora puedes comenzar a utilizar la aplicación.

### Emular en dispositivo móvil
Si deseas emular la aplicación en un dispositivo móvil que conectes al PC desde Android Studio, sigue estos pasos:

1. Conecta tu dispositivo móvil al PC utilizando un cable USB. Antes debes haber activado las opciones de desarrollador y el modo depuración por USB.

2. En Android Studio, asegúrate de que tu dispositivo esté detectado y configurado correctamente. Puedes verificar esto en la ventana "Dispositivos" o "ADB".

3. Una vez que el dispositivo esté conectado y reconocido, selecciona tu dispositivo como destino de ejecución desde la barra de herramientas de Android Studio.

4. Compila y ejecuta la aplicación en tu dispositivo móvil seleccionado.

5. ¡Listo! Ahora puedes utilizar la aplicación en tu dispositivo móvil para probar su funcionalidad y rendimiento en un entorno real.

#### Configuración adicional
Si experimentas algún problema durante la instalación o configuración, asegúrate de consultar la documentación oficial de Android Studio para obtener ayuda adicional.

### Instalación en dispositivo
En desarrollo.

## Características
### Versión
En este proyecto, de momento utilizamos una única versión de prueba para nuestra aplicación móvil, ya que se trata de un proyecto educativo y no estamos realizando lanzamientos completos. 

Versión de prueba 1.0 (Prueba): Esta versión es una demostración de nuestro trabajo escolar, que incluye la aplicación móvil en Kotlin. Aunque no está destinada para su uso en producción, permite visualizar y probar las funcionalidades básicas desarrolladas durante el curso.

### Utilidades
1. Registro de incidencias: Los profesores pueden registrar fácilmente las incidencias relacionadas con equipos, conexiones o cuentas del centro.
2. Seguimiento de estado: Los usuarios pueden seguir el estado de las incidencias y recibir actualizaciones sobre su resolución.
3. Aspectos personalizables: configuración de aspectos de tema oscuro en la aplicación.
4. Asistente virtual: Contamos con un asistente virtual en la aplicación al que poder preguntarle dudas sobre la utilización de la aplicación, además de opciones de entretenimiento como chistes, canciones, etc.
5. Sección de contacto de la aplicación, que te muestra métodos para realizar tu consulta o reclamación.

<div align="center">
  <img src="https://github.com/Tania105/Android_Grupo1/blob/main/Multimedia/image%20(2).png" alt="Login">
</div>

### Seguridad
En nuestro proyecto, nos tomamos muy en serio la seguridad de nuestros usuarios y sus datos. La aplicación móvil cuenta con autentificación de datos para poder loggearse, siempre mediante el uso de contraseñas seguras encriptadas en nuestra base de datos.

- **Reporte de problemas de seguridad**: Si encuentras alguna vulnerabilidad en nuestra aplicación, por favor contáctanos de manera confidencial a [contacto@iris.com].

## Korobot
<div align="center">
  <img src="https://github.com/Tania105/Android_Grupo1/blob/main/Multimedia/korobot.png" alt="Korobot cabeza">
</div>

Korobot es nuestro asistente virtual de confianza, tenemos la posibilidad de utilizar palabras clave para que nos explique diferentes funciones de la aplicación.

Además de esto, tiene opciones más lúdicas como contar chistes o poner música.

Este asistente cuenta también con la opción de reconocimiento de voz, podrás hablarle de forma verbal para solucionar tus dudas.

<div align="center">
  <img src="https://github.com/Tania105/Android_Grupo1/blob/main/Multimedia/Screenshot_20240227-205154_IRIS.jpg" alt="Korobot">
</div>

## Uso

### Registro de incidencias:
Abre la aplicación IRIS en tu dispositivo móvil.
Inicia sesión con tus credenciales de profesorado.
Selecciona la opción de "Registrar incidencia" en el menú principal.
Completa los campos requeridos y envía el formulario para registrar la incidencia.

### Seguimiento de estado:
Para seguir el estado de una incidencia registrada, abre la aplicación IRIS.
Inicia sesión con tus credenciales de profesorado.
Selecciona la opción de "Mis incidencias" en el menú principal para ver el estado de tus incidencias registradas.

<div align="center">
  <img src="https://github.com/Tania105/Android_Grupo1/blob/main/Multimedia/image.png" alt="Captura estado">
</div>

### Video tutorial

Entre nuestros recursos multimedia, adjuntamos enlace a nuestro [videotutorial descargable](https://github.com/Tania105/Android_Grupo1/blob/main/Multimedia/IRIS_TUTORIAL_DEF.mp4)

## Miembros

- Tania Chocán (Desarrollador principal)
  - GitHub: [Tania105](https://github.com/Tania105)
  - Correo de contacto: [tania.chocanalexandre@iesmiguelherrero.com](mailto:tania.chocanalexandre@iesmiguelherrero.com)

- Raúl Casas Gómez (Desarrollador principal)
  - GitHub: [RaulKas07](https://github.com/RaulKas07)
  - Correo de contacto: [raul.casasgomez@iesmiguelherrero.com](mailto:raul.casasgomez@iesmiguelherrero.com)

- Miguel Ángel Calderón (Desarrollador principal)
  - GitHub: [miguelangelcalderon3](https://github.com/miguelangelcalderon3)
  - Correo de contacto: [miguelangel.calderonzuleta@iesmiguelherrero.com](mailto:miguelangel.calderonzuleta@iesmiguelherrero.com)

- Ignacio Sáez González (Desarrollador principal)
  - GitHub: [Ignaciosgh](https://github.com/Ignaciosgh)
  - Correo de contacto: [ignacio.saezgonzalez@iesmiguelherrero.com](mailto:ignacio.saezgonzalez@iesmiguelherrero.com)  

- Diego Sañudo Rodríguez (Desarrollador principal)
  - GitHub: [naego-sarophiel](https://github.com/naego-sarophiel)
  - Correo de contacto: [diego.sanudorodriguez@iesmiguelherrero.com](mailto:diego.sanudorodriguez@iesmiguelherrero.com) 

- Isaac Cabria Díez (Desarrollador principal)
  - GitHub: [isaac640](https://github.com/isaac640)
  - Correo de contacto: [isaac.cabriadiez@iesmiguelherrero.com](mailto:isaac.cabriadiez@iesmiguelherrero.com) 

Agradecemos a todos los miembros del equipo por su arduo trabajo y dedicación en el desarrollo y mantenimiento de este proyecto.

## Contribución

Si deseas contribuir al proyecto, sigue estos pasos:

1. Haz un fork del proyecto.
2. Crea una nueva rama (`git checkout -b el-nombre-de-tu-rama`).
3. Realiza tus cambios y haz commit (`git commit -am 'He cambiado el diseño de la pantalla inicial'`).
4. Haz push a la rama (`git push origin el-nombre-de-tu-rama`).
5. Crea un nuevo Pull Request.

Si las contribuciones son efectivas y corrigen u optimizan en algún sentido el código base, se añadirán a la rama main y pasarás a ser parte del apartado de miembros como colaborador.

## Contacto

Si tienes alguna pregunta, sugerencia o problema, no dudes en contactarnos a través de:

- Correo electrónico: [contacto@iris.com](mailto:contacto@iris.com)
- Twitter: [@ProyectoIris](https://twitter.com/ProyectoIris)
- Discord: [Servidor de Discord del Proyecto](enlace-al-servidor-de-discord)
- [Foro de discusión del proyecto](https://www.ProyectoIris.com/foro)
- [Formulario de contacto en mi sitio web](https://www.ProyectoIris.com/contacto)


## Licencia

Este proyecto está licenciado bajo la [Licencia MIT](LICENSE).

La Licencia MIT es una licencia de software libre que permite a los usuarios utilizar, modificar y distribuir el software con pocos o ningún requisito. Bajo esta licencia, los usuarios pueden hacer lo que deseen con el software, siempre y cuando se incluya el aviso de copyright y la renuncia de garantía.

Para obtener más detalles, consulta el [texto completo de la Licencia MIT](https://opensource.org/licenses/MIT).

Agradecemos a los autores de la Licencia MIT por proporcionar una estructura legal que permita compartir nuestro trabajo con otros de manera abierta y accesible.
