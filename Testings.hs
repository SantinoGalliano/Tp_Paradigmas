import ModuloPoint
import ModuloCity
import ModuloQuality
import ModuloLink
import ModuloTunel
import ModuloRegion
import Errores

-- TESTING -- 

-- Moodulo Point --

-- newP
puntoA = newP 5 4 
puntoB = newP 8 (-10)

-- difP
distanciaAB = difP puntoA puntoB 
distanciaBA = difP puntoB puntoA -- devuelve lo mismo que disanciaAB

testingPoint = [testF distanciaAB,testF distanciaBA]

-- Modulo City --

-- newC
ciudadConNombreVacio = newC "" (newP 0 0) -- excepcion: ciudad con nombre vacio
ciudad0 = newC "tigre" (newP 5 6)
ciudad1 = newC "olivos" (newP (-3) 4)
ciudad2 = newC "martinez" (newP 8 10)
ciudad3 = newC "tigre" (newP 8 10) --creamos distintas ciudades

-- nameC
nombreCiudad1 = nameC ciudad1

-- distanceC
distanciaCiudades12 = distanceC ciudad1 ciudad2 -- devuelve la distancia entre ciudad1 y ciudad2
distanciaCiudades21 = distanceC ciudad2 ciudad1 -- devuelve lo mismo aunque se modifique el orden en el que se pasan las ciudades

ciudadesConMismaUbicacion = distanceC ciudad2 ciudad3 -- excepcion: ciudades con misma ubicacion
ciudadesConMismoNombre = distanceC ciudad0 ciudad3 -- excepcion: ciudades con mismo nombre

testingCity= [testF distanciaAB,testF distanciaBA,testF ciudadesConMismaUbicacion,testF ciudadesConMismoNombre]

-- Modulo Quality --

-- newQ
calidadBaja = newQ "bronze" 1 15.6 
calidadMedia = newQ "plata" 3 50.8
calidadAlta = newQ "oro" 5 103.2 -- creamos las distintas calidades 
calidadPrueba = newQ "prueba" (-10) (-10.1) -- transforma los valores negativos (absurdos) en positivos
calidadNombreVacio = newQ "" 5 9.6 -- excepcion: calidad con nombre vacio
calidadVelocidadNula = newQ "cero" 9 0 -- nos devuelve el error ya que la velocidad es 0
calidadCapacidadNula = newQ "cero" 0 7.4 -- nos devuelve el error ya que la capacidad es 0

-- capacityQ
capacidadCalidadBaja = capacityQ calidadBaja -- nos devuelve la capacidad de la calidad baja
capacidadCalidadMedia = capacityQ calidadMedia -- nos devuelve la capacidad de la calidad media
capacidadCalidadAlta = capacityQ calidadAlta -- nos devuelve la capacidad de la calidad alta

-- delayQ
velocidadCalidadBaja = delayQ calidadBaja -- nos devuelve la velocidad de la calidad baja
velocidadCalidadMedia = delayQ calidadMedia -- nos devuelve la velocidad de la calidad media
velocidadCalidadAlta = delayQ calidadAlta -- nos devuelve la velocidad de la calidad alta
velocidadCalidadPrueba = delayQ calidadPrueba -- nos devuelve la velocidad positiva (10.1)

testingQuality = [testF calidadBaja, testF calidadMedia, testF calidadAlta, testF calidadPrueba, testF calidadNombreVacio, testF calidadVelocidadNula, testF calidadCapacidadNula,
                  testF capacidadCalidadBaja, testF capacidadCalidadMedia, testF capacidadCalidadAlta, testF velocidadCalidadBaja,
                  testF velocidadCalidadMedia, testF velocidadCalidadAlta,testF velocidadCalidadPrueba]

-- Modulo Link --

ciudad4 = newC "munro" (newP 7 7) 
ciudad5 = newC "palermo" (newP 2 8) -- creamos otras dos ciudades

-- newL
link1 = newL ciudad4 ciudad5 calidadAlta
link2 = newL ciudad1 ciudad2 calidadMedia
link3 = newL ciudad3 ciudad2 calidadVelocidadNula
link4 = newL ciudad2 ciudad3 calidadMedia -- excepcion: un link entre ciudades semejantes

-- connectsL 
enLink01 = connectsL ciudad1 link1 -- nos devuevle False si no exisste dicha ciudad dentro de ese link
enLink11 = connectsL ciudad4 link1 -- nos devuelve True si existe dicha ciudad dentro de ese link

-- linksL
enlazaAmbasCiudades12 = linksL ciudad1 ciudad2 link2 -- nos devuelve True
enlazaAmbasCiudades23 = linksL ciudad1 ciudad3 link2 -- nos devuelve False

-- capacityL
capacidadLink1 = capacityL link1 
capacidadLink2 = capacityL link2 -- capacidad maxima de tuneles

-- delayL 
delayLink2 = delayL link2
delayLink3 = delayL link3 -- nos devuelve error del Modulo City

testingLink = [testF link1, testF link2, testF link3, testF link4, testF enLink01, testF enLink11, testF enlazaAmbasCiudades12, testF enlazaAmbasCiudades23,
                testF capacidadLink1, testF capacidadLink2, testF delayLink2, testF delayLink3]

-- Modulo Tunel -- 

ciudad6 = newC "lujan" (newP 5 5)
ciudad7 = newC "marazul" (newP 20 5)
ciudad8 = newC "rosario" (newP 4 17)
ciudad9 = newC "mardelplata" (newP 22 6)

link01 = newL ciudad1 ciudad2 calidadAlta
link02 = newL ciudad2 ciudad4 calidadMedia
link03 = newL ciudad4 ciudad5 calidadBaja
link04 = newL ciudad5 ciudad6 calidadMedia
link05 = newL ciudad6 ciudad7 calidadMedia
link06 = newL ciudad7 ciudad8 calidadMedia
link07 = newL ciudad8 ciudad9 calidadAlta

-- newT
tunel1 = newT [link01, link02]
tunel2 = newT [link02, link03]
tunelSinLinks = newT [] -- nos devuelve la excepcion: No se puede hacer un tunel con una lista vacia de links
tunelLinksRepetidos = newT [link01,link02,link01] -- nos duelve la excepcion: No se puede hacer un tunel con links repetidos
tunel3 = newT [link01, link02, link03, link04, link05, link06, link07]

-- connectsT
conecta1 = connectsT ciudad1 ciudad4 tunel1 -- True
conecta2 = connectsT ciudad1 ciudad2 tunel1 -- False
conecta3 = connectsT ciudad1 ciudad9 tunel3 -- True

-- usesT
usaTunel1 = usesT link01 tunel1 -- True
usaTunel2 = usesT link01 tunel2 -- False
usaTunel3 = usesT link06 tunel3 -- Tru

-- delayT
demora1 = delayT tunel1 -- devuelve demora existente dentro del tunel

testingTunel = [testF tunel1, testF tunel2, testF tunelSinLinks, testF tunelLinksRepetidos,testF tunel3, testF conecta1,testF conecta2, testF conecta3, 
                testF usaTunel1, testF usaTunel2,testF usaTunel3,testF demora1]

-- Modulo Region -- 

-- foundR
region1 = newR
region2 = foundR region1 ciudad1
ciudad1Repetida = newC "olivos" (newP 6 7)
ciudad1Repetida2 = newC "madrid" (newP (-3) 4)
ciudadConMismoNombre = foundR region2 ciudad1Repetida -- Excepcion: Ciudad con mismo nombre 
ciudadConMismaPosicion = foundR region2 ciudad1Repetida2  -- Excepcion: Ciudad con misma posicion
nuevaCiudadEnLaRegion = foundR region2 ciudad2 -- se agrega una nueva ciudad

-- linkR
algunaCiudadNoExiste = linkR region2 ciudad1 ciudad2 calidadAlta -- Excepcion: Al menos una ciudad no existe
enlazaDosCiudades = linkR nuevaCiudadEnLaRegion ciudad1 ciudad2 calidadBaja -- Crear el enlace entre dos ciudades existentes
enlaceConDistintaCalidad = linkR enlazaDosCiudades ciudad1 ciudad2 calidadAlta -- Excepcion: Ya existe el enlace con distinta calidad
enlaceConMismaCalidad = linkR enlazaDosCiudades ciudad1 ciudad2 calidadBaja -- Excepcion: Ya existe el enlace con misma calidad

-- tunelR 
listaCiudadesVacia = tunelR nuevaCiudadEnLaRegion [] -- Excepcion: No hay ciudades para hacer el tunel
listaCiudadesUnitaria = tunelR nuevaCiudadEnLaRegion [ciudad1] -- Excepcion: Solo hay una ciudad para hacer el tunel
ciudadFueraDeRegion1 = tunelR nuevaCiudadEnLaRegion [ciudad1, ciudad3] -- Excepcion: Al menos una de las ciudades de la lista no pertenece a la region
ciudadFueraDeRegion2 = tunelR nuevaCiudadEnLaRegion [ciudad1, ciudad2, ciudad3] -- Excepcion: Al menos una de las ciudades de la lista no pertenece a la region
ciudadRepetida1 = tunelR nuevaCiudadEnLaRegion [ciudad1, ciudad2, ciudad2] -- Excepcion: Hay al menos una ciudad repetida
agregarCiudad = foundR enlazaDosCiudades ciudad4 -- agrego una tercera ciudad a la region
insuficientesLinks = tunelR agregarCiudad [ciudad1, ciudad2, ciudad4] -- Excepcion: No hay suficientes links entre las ciudades

nuevoLinkEnRegion1 = linkR agregarCiudad ciudad2 ciudad4 calidadBaja -- agrego un link a la region entre ciudad2 y ciudad4
nuevoLinkEnRegion2 = linkR nuevoLinkEnRegion1 ciudad1 ciudad4 calidadBaja -- agrego un link a a region entre ciudad4 y ciudad1
nuevoTunel = tunelR nuevoLinkEnRegion2  [ciudad1, ciudad2, ciudad4]
extremosTunelUtilizados1 = tunelR nuevoTunel [ciudad1, ciudad4] --excepcion: No se puede volver a hacer un tunel que tengan iguales extremos que otro tunel de la region (con esta condicion se verifica tmb que no se vuelva a crear un tunel igual, que ya existe)
extremosTunelUtilizados2 = tunelR nuevoTunel [ciudad4, ciudad1] --excepcion: No se puede volver a hacer un tunel que tengan iguales extremos que otro tunel de la region (con esta condicion se verifica tmb que no se vuelva a crear un tunel igual, que ya existe)
capacidadInsuficienteEnlaces = tunelR nuevoTunel [ciudad1, ciudad2] -- excepcion: No hay capacidad suficiente para al menos uno de los enlaces

-- conectedR 
ciudadFueraDeRegion3 = connectedR region2 ciudad1 ciudad5 -- excepcion: Al menos una ciudad no esta en la region
ciudadRepetida2 = connectedR region2 ciudad1 ciudad1 -- excepcion: Las ciudades estan repetidas
ciudadesNoConectadas = connectedR nuevoTunel ciudad1 ciudad2 -- devuevele False si las ciudades no estan conectadas por un tunel de la region
ciudadesConectadas = connectedR nuevoTunel ciudad1 ciudad4  -- devuelve True si las ciudades estan conectadas por un tunel de la region

-- linkedR
ciudadFueraDeRegion4 = linkedR region2 ciudad1 ciudad5 -- excepcion: Al menos una ciudad no esta en la region
ciudadRepetida3 = linkedR region2 ciudad1 ciudad1 -- excepcion: Las ciudades estan repetidas
agregoCiudadRegion = foundR nuevoLinkEnRegion1 ciudad5 -- agrego una ciudad que no se va enlazar con alguna ciudad de la region

existeLink = linkedR nuevoLinkEnRegion1 ciudad2 ciudad4 -- devuelve True si existe un link en la region que enlace a estas dos ciudades
noExisteLink = linkedR agregoCiudadRegion ciudad1 ciudad5 -- devuelve False si no existe un link en la region que enlace a estas dos ciudades

-- delayR 
ciudadFueraDeRegion5 = delayR  region2 ciudad1 ciudad5 -- excepcion: Al menos una ciudad no esta en la region
ciudadRepetida4 = delayR region2 ciudad1 ciudad1-- excepcion: Las ciudades estan repetidas
noExisteTunel1 = delayR nuevaCiudadEnLaRegion ciudad1 ciudad2 -- excepcion: No existe un tunel que conecte estas dos ciudades
noExisteTunel2 = delayR nuevaCiudadEnLaRegion ciudad2 ciudad1 -- excepcion: No existe un tunel que conecte estas dos ciudades
demoraDeTunel1 = delayR nuevoTunel ciudad1 ciudad4 -- Devuelve la demora de un tunel que conecta estas dos ciudades
demoraDeTunel2 = delayR nuevoTunel ciudad1 ciudad4 -- Devuelve la demora de un tunel que conecta estas dos ciudades (modo inverso)

-- availableCapacityForR
ciudadFueraDeRegion6 = availableCapacityForR  region2 ciudad1 ciudad5 -- excepcion: Al menos una ciudad no esta en la region
ciudadRepetida5 = availableCapacityForR region2 ciudad1 ciudad1-- excepcion: Las ciudades estan repetidas
capacidadDisponible1 = availableCapacityForR nuevoLinkEnRegion1 ciudad1 ciudad2 --devuelve la capacidad disponible del link que enlaza a ambas ciudades sin haber estado en ningun tunel

capacidadDisponible2 = availableCapacityForR nuevoLinkEnRegion1 ciudad2 ciudad1 --devuelve la capacidad disponible del link que enlaza a ambas ciudades sin haber estado en ningun tunel (lo pruebo al reves)
capacidadUtilizada = availableCapacityForR nuevoTunel ciudad1 ciudad2 -- devuelve la capacidad disponible descontando lo ya usado para los tuneles 

testingRegion = [testF region1, testF region2, testF ciudad1Repetida, testF ciudad1Repetida2,testF ciudadConMismoNombre, testF ciudadConMismaPosicion, 
                testF nuevaCiudadEnLaRegion, testF algunaCiudadNoExiste, testF enlazaDosCiudades, testF enlaceConDistintaCalidad, testF enlaceConMismaCalidad,
                testF listaCiudadesVacia,testF listaCiudadesUnitaria,  testF ciudadFueraDeRegion1,testF ciudadFueraDeRegion2, testF ciudadRepetida1, 
                testF agregarCiudad, testF insuficientesLinks, testF nuevoLinkEnRegion1, testF nuevoLinkEnRegion2, testF nuevoTunel, 
                testF extremosTunelUtilizados1, testF extremosTunelUtilizados2, testF capacidadInsuficienteEnlaces,testF ciudadFueraDeRegion3, testF ciudadRepetida2, testF ciudadesNoConectadas,
                testF ciudadesConectadas, testF ciudadFueraDeRegion4, testF ciudadRepetida3, testF existeLink, testF agregoCiudadRegion,
                testF noExisteLink, testF ciudadFueraDeRegion5, testF ciudadRepetida4, testF noExisteTunel1,
                testF noExisteTunel2, testF demoraDeTunel1, testF demoraDeTunel2, testF ciudadFueraDeRegion6, testF ciudadRepetida5,
                testF capacidadDisponible1, testF capacidadDisponible2,testF capacidadUtilizada]