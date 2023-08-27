module ModuloRegion (Region, newR, foundR, linkR, tunelR, connectedR, linkedR, delayR, availableCapacityForR)
   where

import ModuloCity 
import ModuloLink
import ModuloQuality
import ModuloPoint
import ModuloTunel
import Errores
import Data.List (nub)
import Distribution.Simple.LocalBuildInfo (ComponentLocalBuildInfo(TestComponentLocalBuildInfo))

data Region = Reg [City] [Link] [Tunel] deriving (Show)

newR :: Region
newR = Reg [] [] []

foundR :: Region -> City -> Region
foundR (Reg listaCiudades listaEnlaces listaTuneles) ciudad
 | nameC ciudad `elem` [nameC city | city <- listaCiudades] = error "No se puede repetir el nombre de una ciudad ya existente en la región"
 | or [True | city <- listaCiudades, testF (distanceC city ciudad)] = error "No se puede fundar ciudad donde ya fue fundada otra anteriormente"
 | otherwise = Reg (ciudad : listaCiudades) listaEnlaces listaTuneles

linkR :: Region -> City -> City -> Quality -> Region
linkR (Reg listaCiudades listaEnlaces listaTuneles) ciudad1 ciudad2 calidad
 | not (ciudadEnRegion listaCiudades ciudad1 ciudad2) = error "No se pudo enlazar a las ciudades debido a la inexistencia de al menos alguna de ellas en la región"
 | ciudad1 == ciudad2 = error "Las ciudades están repetidas"
 | not(null (existeEnlace listaEnlaces ciudad1 ciudad2)) = error "El enlace ya existe"
 | otherwise = Reg listaCiudades (listaEnlaces ++ [newL ciudad1 ciudad2 calidad]) listaTuneles

tunelR :: Region -> [ City ] -> Region
tunelR (Reg listaCiudades listaEnlaces listaTuneles) listaCiudades1
 | length listaCiudades1 <= 1 = error "No hay ciudades suficientes para comunicar"
 | not (all (`elem` listaCiudades) listaCiudades1) = error "Al menos una de las ciudades para comunicar no se encuentra en la región"
 | tieneRepetidos listaCiudades1 = error "No se pudo generar la comunicación entre las dos ciudades, ya que hay al menos una ciudad repetida"
 | [] `elem` listaDePosiblesLinks1 = error "No hay enlaces suficientes entre las ciudades para comunicarlas"
 | connectedR region (head listaCiudades1) (last listaCiudades1) = error "No puede existir otro tunel que tenga como extremos a la primera y última ciudad de la lista"
 | False `elem` verificarSuficienciaDeCapacidades = error "Al menos uno de los posibles enlaces para hacer el tunel no tiene capacidad suficiente"
 | otherwise = Reg listaCiudades listaEnlaces (listaTuneles ++ [newT listaDePosiblesLinks2])

  where listaDePosiblesLinks1 = [existeEnlace listaEnlaces (fst posiblesEnlaces) (snd posiblesEnlaces)  | posiblesEnlaces <- iterarDeDos listaCiudades1]
        listaDePosiblesLinks2 =  [head (existeEnlace listaEnlaces (fst posiblesEnlaces) (snd posiblesEnlaces)) | posiblesEnlaces <- iterarDeDos listaCiudades1, not (null (existeEnlace (listaEnlaces) (fst posiblesEnlaces) (snd posiblesEnlaces)))]
        verificarSuficienciaDeCapacidades = map esPositivo [availableCapacityForR region (fst parDeCiudades) (snd parDeCiudades) | parDeCiudades <- iterarDeDos listaCiudades1]
        region = Reg listaCiudades listaEnlaces listaTuneles

connectedR :: Region -> City -> City -> Bool -- indica si estas dos ciudades estan conectadas por un tunel
connectedR (Reg listaCiudades listaEnlaces listaTuneles) ciudad1 ciudad2
  | not(ciudadEnRegion listaCiudades ciudad1 ciudad2) = error "Al menos una de las ciudades no está en la región"
  | ciudad1 == ciudad2 = error "Las ciudades están repetidas"
  | or [True| tunel <-listaTuneles,connectsT ciudad1 ciudad2 tunel] = True
  | otherwise = False

linkedR :: Region -> City -> City -> Bool
linkedR (Reg listaCiudades listaEnlaces listaTuneles) ciudad1 ciudad2
  |not(ciudadEnRegion listaCiudades ciudad1 ciudad2) = error "Al menos una de las ciudades no está en la región"
  | ciudad1 == ciudad2 = error "Las ciudades están repetidas"
  | otherwise = any (linksL ciudad1 ciudad2) listaEnlaces

delayR :: Region -> City -> City -> Float
delayR (Reg listaCiudades listaEnlaces listaTuneles) ciudad1 ciudad2
 | not(ciudadEnRegion listaCiudades ciudad1 ciudad2) = error "Al menos una de las ciudades no está en la región"
 | ciudad1 == ciudad2 = error "Las ciudades están repetidas"
 | not (connectedR (Reg listaCiudades listaEnlaces listaTuneles) ciudad1 ciudad2) = error "No existe un tunel que conecte estas ciudades"
 | otherwise = delayT (head [tunel | tunel <- listaTuneles, connectsT ciudad1 ciudad2 tunel])

availableCapacityForR :: Region -> City -> City -> Int
availableCapacityForR (Reg listaCiudades listaEnlaces listaTuneles) ciudad1 ciudad2
  | not(ciudadEnRegion listaCiudades ciudad1 ciudad2) = error "Al menos una de las ciudades no está en la región"
  | ciudad1 == ciudad2 = error "Las ciudades están repetidas"
  | null (existeEnlace listaEnlaces ciudad1 ciudad2) = error "No existe el link entre las dos ciudades"
  | capacidadLink == capacidadEmpleada = 0
  | otherwise = capacidadLink - capacidadEmpleada
        where capacidadLink = capacityL (head (existeEnlace listaEnlaces ciudad1 ciudad2))
              capacidadEmpleada = capacidadUtilizada listaTuneles (head (existeEnlace listaEnlaces ciudad1 ciudad2))

--Funciones auxiliares

esPositivo :: Int -> Bool
esPositivo x | x > 0 = True
             | otherwise = False

capacidadUtilizada :: [Tunel] -> Link -> Int
capacidadUtilizada listaTuneles link = length [link | tunel <- listaTuneles, usesT link tunel]

existeEnlace :: [Link] -> City -> City -> [Link]
existeEnlace listaLinks ciudad1 ciudad2 = [enlace | enlace <- listaLinks, linksL ciudad1 ciudad2 enlace]

listaEnlaces :: Region -> [Link]
listaEnlaces (Reg listaCities listaLinks listaTuneles) = listaLinks

tieneRepetidos :: Eq a => [a] -> Bool
tieneRepetidos xs = length xs /= length (nub xs)

iterarDeDos :: [a] -> [(a, a)]
iterarDeDos [] = []
iterarDeDos [_] = []
iterarDeDos (x:y:resto) = (x, y) : iterarDeDos (y:resto)

ciudadEnRegion :: [City] -> City -> City ->Bool

ciudadEnRegion listaCiudades ciudad1 ciudad2 
     | ciudad1 `elem` listaCiudades && ciudad2 `elem` listaCiudades = True
     | otherwise = False





-- testing esPositivo -- 
prueba1_esPositivo = esPositivo 0 -- devuelve False
prueba2_esPositivo = esPositivo 1 -- devuelve True
prueba3_esPositivo = esPositivo (-1) -- devuelve False

-- testing capacidadutilizada --
ciudad1 = newC "olivos" (newP (-3) 4)
ciudad2 = newC "martinez" (newP 8 10)
ciudad3 = newC "munro" (newP 7 7)

ciudad4 = newC "madrid" (newP 4 8)
calidadAlta = newQ "oro" 5 103.2
link01 = newL ciudad1 ciudad2 calidadAlta
link02 = newL ciudad2 ciudad3 calidadAlta
tunel1 = newT [link01]
tunel2 = newT [link01, link01]
tunel3 = newT [link01, link02]

prueba1_capacidadUtilizada = capacidadUtilizada [tunel1] link01 -- me tiene que dar 1
prueba2_capacidadUtilizada = capacidadUtilizada [tunel2] link01 -- tira error
prueba3_capacidadUtilizada = capacidadUtilizada [tunel1] link02 -- me tiene que dar 0

-- testing existeEnlace -- 
prueba1_existeEnlace = existeEnlace [link02] ciudad1 ciudad2 -- devuelve lista vacia ya que no existe el link
prueba2_existeEnlace = existeEnlace [link02] ciudad2 ciudad3 -- devuelve lista del link entre las ciudades ya que existe
prueba3_existeEnlace = existeEnlace [link01] ciudad1 ciudad3 -- devuelve lista vacia

-- testing listaEnlaces -- 
region000 = newR 
region100 = foundR region000 ciudad1
region200 = foundR region100 ciudad2
region210 = linkR region200 ciudad1 ciudad2 calidadAlta

prueba1_listaEnlaces = listaEnlaces region210 -- nos devuelve el link entre ciudad1 y ciudad2
prueba2_listaEnlaces = listaEnlaces region200 -- nos devuelve lista vacia

-- testing tieneRepetidos -- 
lista1 = ['a', 'a', 'b', 'b', 'c']
lista2 = ['a', 'b', 'c']

prueba1_tieneRepetidos = tieneRepetidos lista1 -- nos devuelve True
prueba2_tieneRepetidos = tieneRepetidos lista2 -- nos devuelve False

-- testing iterarDeDos -- 
lista3 = []
prueba1_iterarDeDos = iterarDeDos lista2 -- devuelve tuplas de dos elementos
prueba2_iterarDeDos = iterarDeDos lista3 -- devuelve lista vacia

--testing CiudadEnRegion

ciudadEnRegion1 = ciudadEnRegion [ciudad2,ciudad3,ciudad4] ciudad1 ciudad2 -- devuelve False si al menos una ciudad no está en la lista de ciudades de la región 

ciudadEnRegion2 = ciudadEnRegion [ciudad1,ciudad2,ciudad3,ciudad4] ciudad1 ciudad2 -- devuelve True si ambas ciudades está en la lista de ciudades de la región  