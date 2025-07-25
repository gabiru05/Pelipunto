<h1 align="center">
  Pelipunto: Explorador de Películas para Android
</h1>
<a name="readme-top"></a>

<h4 align="center">
  Aplicación Android nativa para explorar, descubrir y gestionar películas.<br>
  Construida con Arquitectura Limpia Multi-módulo, Kotlin, y un moderno UI/UX con Jetpack Compose.
</h4>

<img src="https://github.com/gabiru05/Gaby_Resource/blob/master/images/Gifs/1pxRainbowLine.gif" width= "300000" alt="línea horizontal RGB arcoíris súper delgada">

#### <img src="https://github.com/gabiru05/Gaby_Resource/blob/master/images/Gifs/accepted.gif" width= "30" alt="Icono verde de validación"> Proyecto de aprendizaje y demostración de arquitectura y UI/UX Android moderna.

<img src="https://github.com/gabiru05/Gaby_Resource/blob/master/images/Gifs/1pxRainbowLine.gif" width= "300000" alt="línea horizontal RGB arcoíris súper delgada">

<h2 align="center">Recorrido de la Aplicación</h2>

<p align="center">
  Un tour visual a través de las principales pantallas de la aplicación, mostrando el flujo de usuario completo desde la bienvenida hasta la gestión de la cuenta.
</p>

| Pantalla de Bienvenida y Autenticación | Pantallas Principales de Contenido |
| :------------------------------------: | :--------------------------------: |
| <img src="./screenshots/01_welcome.png" width="250" alt="Pantalla de Bienvenida"> | <img src="./screenshots/04_home.png" width="250" alt="Pantalla Principal"> |
| **Bienvenida y Elección.** El usuario es recibido y puede optar por iniciar sesión o registrarse. | **Pantalla Principal.** Un `Scaffold` con `BottomBar` presenta un carrusel interactivo y las tendencias. |
| <img src="./screenshots/03_login.png" width="250" alt="Pantalla de Login"> | <img src="./screenshots/05_detail.png" width="250" alt="Pantalla de Detalles"> |
| **Inicio de Sesión.** Interfaz limpia para autenticación con Firebase (Google o Email). | **Detalles de Película.** Diseño inmersivo que ocupa toda la pantalla para una experiencia sin distracciones. |

| Pantallas Secundarias y de Gestión |
| :----------------------------------: |
| <img src="./screenshots/06_cast_list.png" width="250" alt="Pantalla de Elenco"> |
| **Lista de Elenco.** Muestra todos los actores principales de la película seleccionada. |
| <img src="./screenshots/07_reviews_list.png" width="250" alt="Pantalla de Reseñas"> |
| **Lista de Reseñas.** Permite al usuario leer todas las reseñas disponibles. |
| <img src="./screenshots/08_settings.png" width="250" alt="Pantalla de Ajustes"> |
| **Ajustes y Cierre de Sesión.** El usuario puede gestionar su cuenta y cerrar sesión de forma segura. |

<h3 align="center">Creado y Adaptado por:</h3>
<p align="center">
  <a href="https://github.com/gabiru05" title="Gabriel Ruiz">
    <img src="https://github.com/gabiru05.png" width="60" height="60" style="border-radius:50%">
  </a>
     
  <a href="https://github.com/MrT4ttoo" title="Adolfo López">
    <img src="https://github.com/MrT4ttoo.png" width="60" height="60" style="border-radius:50%">
  </a>
</p>
<p align="center">
  Gabriel Ruiz        Adolfo López
</p>

<img src="https://github.com/gabiru05/Gaby_Resource/blob/master/images/Gifs/1pxRainbowLine.gif" width= "300000" alt="línea horizontal RGB arcoíris súper delgada">

<h2 align="center">Características Principales</h2>

<ul>
  <li><img src="https://github.com/gabiru05/Gaby_Resource/blob/master/images/PNGs/BlueCheckCircleMark.png" width="20" alt="icono de característica"> <strong>Rediseño de UI/UX con Material Design 3:</strong> Interfaz completamente renovada, con bordes definidos, espaciado consistente y una estética moderna y limpia.</li>
  <li><img src="https://github.com/gabiru05/Gaby_Resource/blob/master/images/PNGs/BlueCheckCircleMark.png" width="20" alt="icono de característica"> <strong>Navegación Centralizada con Scaffold:</strong> Arquitectura de UI robusta con un único `Scaffold` que gestiona de forma inteligente la `TopAppBar` y `BottomAppBar`.</li>
  <li><img src="https://github.com/gabiru05/Gaby_Resource/blob/master/images/PNGs/BlueCheckCircleMark.png" width="20" alt="icono de característica"> <strong>Carrusel Interactivo Mejorado:</strong> El carrusel de la pantalla principal ahora cuenta con animaciones de `HorizontalPager` y efectos visuales que mejoran la experiencia de descubrimiento.</li>
  <li><img src="https://github.com/gabiru05/Gaby_Resource/blob/master/images/PNGs/BlueCheckCircleMark.png" width="20" alt="icono de característica"> <strong>Listas Completas para Elenco y Reseñas:</strong> Pantallas dedicadas para explorar en profundidad la información de cada película.</li>
  <li><img src="https://github.com/gabiru05/Gaby_Resource/blob/master/images/PNGs/BlueCheckCircleMark.png" width="20" alt="icono de característica"> <strong>Sistema de Autenticación Completo:</strong> Inicio de sesión con Google y mediante correo/contraseña utilizando Firebase Authentication.</li>
  <li><img src="https://github.com/gabiru05/Gaby_Resource/blob/master/images/PNGs/BlueCheckCircleMark.png" width="20" alt="icono de característica"> <strong>Arquitectura Limpia Multi-módulo:</strong> El código está separado por capas (presentación, dominio, datos) y funcionalidades, facilitando el mantenimiento y la escalabilidad.</li>
  <li><img src="https://github.com/gabiru05/Gaby_Resource/blob/master/images/PNGs/BlueCheckCircleMark.png" width="20" alt="icono de característica"> <strong>Interfaz 100% Compose:</strong> UI completamente construida con Jetpack Compose y Material 3.</li>
  <li><img src="https://github.com/gabiru05/Gaby_Resource/blob/master/images/PNGs/BlueCheckCircleMark.png" width="20" alt="icono de característica"> <strong>Asincronía con Coroutines:</strong> Todas las operaciones de red y base de datos se manejan de forma eficiente con Coroutines y Flow.</li>
</ul>

<h3 align="center">Funcionalidades Planeadas (Roadmap)</h3>
<ul>
  <li><img src="https://github.com/gabiru05/Gaby_Resource/blob/master/images/PNGs/PencilWrite.png" width="20" alt="icono de planeado"> Añadir seguridad basada en <strong>Tokens (JWT)</strong> con tiempo de expiración.</li>
  <li><img src="https://github.com/gabiru05/Gaby_Resource/blob/master/images/PNGs/PencilWrite.png" width="20" alt="icono de planeado"> Desarrollar la funcionalidad de <strong>calificar películas</strong> y guardar la puntuación por usuario.</li>
  <li><img src="https://github.com/gabiru05/Gaby_Resource/blob/master/images/PNGs/PencilWrite.png" width="20" alt="icono de planeado"> Crear una lista de "Favoritos" o "Películas para ver".</li>
</ul>

<img src="https://github.com/gabiru05/Gaby_Resource/blob/master/images/Gifs/1pxRainbowLine.gif" width= "300000" alt="línea horizontal RGB arcoíris súper delgada">

<h2 align="center">Tecnologías Utilizadas</h2>

<ul>
  <li><img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/kotlin/kotlin-original.svg" width="20" alt="Icono de Kotlin"> <strong>Kotlin:</strong> Lenguaje de programación principal.</li>
  <li><img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/jetpackcompose/jetpackcompose-original.svg" width="20" alt="Icono de Jetpack Compose"> <strong>Jetpack Compose:</strong> Toolkit para construir UI nativas.</li>
  <li><img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/android/android-plain.svg" width="20" alt="Icono de Android"> <strong>Arquitectura Limpia Multi-módulo.</strong></li>
  <li><img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/google/google-original.svg" width="20" alt="Icono de Dagger Hilt"> <strong>Hilt:</strong> Inyección de dependencias.</li>
  <li><img src="https://i.imgur.com/T819n4G.png" width="20" alt="Icono de Firebase"> <strong>Firebase Authentication:</strong> Para el sistema de login y registro.</li>
  <li><img src="https://i.imgur.com/T819n4G.png" width="20" alt="Icono de Retrofit"> <strong>Retrofit & OkHttp:</strong> Cliente HTTP para consumir la API REST.</li>
  <li><img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/kotlin/kotlin-original.svg" width="20" alt="Icono de Kotlin Coroutines"> <strong>Coroutines & Flow:</strong> Para manejo de asincronía.</li>
  <li><strong>Room:</strong> Librería de persistencia para la base de datos local.</li>
  <li><strong>Coil:</strong> Carga de imágenes.</li>
  <li><img src="https://raw.githubusercontent.com/devicons/devicon/master/icons/androidstudio/androidstudio-original.svg" width="20" alt="Icono de Android Studio"> <strong>Android Studio.</strong></li>
</ul>

<img src="https://github.com/gabiru05/Gaby_Resource/blob/master/images/Gifs/1pxRainbowLine.gif" width= "300000" alt="línea horizontal RGB arcoíris súper delgada">

<h2 align="center">Instalación y Uso</h2>

1.  **Clonar el repositorio:**
    ```bash
    git clone https://github.com/gabiru05/Pelipunto.git
    cd Pelipunto
    ```

2.  **Configurar Clave de API:**
    *   Este proyecto requiere una clave de API de The Movie Database (TMDb).
    *   En la carpeta raíz del proyecto, crea un archivo llamado `local.properties`.
    *   Añade tu clave de API en este formato:
        ```properties
        tmdb_api_key=TU_CLAVE_DE_API_AQUI
        ```

3.  **Abrir en Android Studio:**
    *   Abre Android Studio.
    *   Selecciona `File > Open...` y elige la carpeta `Pelipunto` que clonaste.
    *   Espera a que Gradle sincronice el proyecto.

4.  **Ejecutar la aplicación:**
    *   Conecta un dispositivo Android o inicia un Emulador.
    *   Haz clic en el botón "Run 'app'" (▶️).

<img src="https://github.com/gabiru05/Gaby_Resource/blob/master/images/Gifs/1pxRainbowLine.gif" width= "300000" alt="línea horizontal RGB arcoíris súper delgada">
<p align="right"><a href="#readme-top">Volver arriba</a></p>