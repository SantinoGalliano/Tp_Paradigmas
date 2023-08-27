module ModuloTunel (Tunel, newT, connectsT, usesT, delayT)
   where

import ModuloLink
import ModuloQuality
import ModuloCity
import ModuloPoint
import Errores

import Data.List

data Tunel = Tun [Link] deriving (Eq, Show)

newT :: [Link] -> Tunel
newT listaLinks
 | null listaLinks = error "No se puede hacer un tunel sin ningún link"
 | length listaLinks /= length (nub listaLinks) = error "No se puede hacer el tunel con links repetidos"
 | otherwise = Tun listaLinks

connectsT :: City -> City -> Tunel -> Bool
connectsT ciudad1 ciudad2 tunel 
 | ciudadesRepetidas ciudad1 ciudad2 = False
 | length (linkList tunel) == 1 && linksL ciudad1 ciudad2 (head (linkList tunel)) = True
 | length (linkList tunel) == 2 && (verificarExtremos1 || verificarExtremos2 ) = True
 | verificarExtremos3 || verificarExtremos4 = True
 | otherwise = False
         where verificarExtremos1 = connectsL ciudad1 (head (linkList tunel)) && connectsL ciudad2 (linkList tunel !! 1) && not (connectsL ciudad1 (linkList tunel !! 1)) && not (connectsL ciudad2 (head (linkList tunel))) 
               verificarExtremos2 = connectsL ciudad2 (head (linkList tunel)) && connectsL ciudad1 (linkList tunel !! 1) && not (connectsL ciudad2 (linkList tunel !! 1)) && not (connectsL ciudad1 (head (linkList tunel))) 
               verificarExtremos3 = not (connectsL ciudad1 (linkList tunel !! 1)) && not (connectsL ciudad2 (linkList tunel !! (length (linkList tunel)-2))) && connectsL ciudad1 (head (linkList tunel)) && connectsL ciudad2 (linkList tunel !! (length (linkList tunel)-1))
               verificarExtremos4 = not (connectsL ciudad2 (linkList tunel !! 1)) && not (connectsL ciudad1 (linkList tunel !! (length (linkList tunel)-2))) && connectsL ciudad2 (head (linkList tunel)) && connectsL ciudad1 (linkList tunel !! (length (linkList tunel)-1))

usesT :: Link -> Tunel -> Bool
usesT link tunel
 | link `elem` linkList tunel = True
 | otherwise = False

delayT :: Tunel -> Float
delayT (Tun enlaces) = sum [delayL enlace | enlace <- enlaces]

linkList :: Tunel -> [Link]
linkList (Tun listaLinks) = listaLinks

ciudadesRepetidas :: City -> City -> Bool

ciudadesRepetidas ciudad1 ciudad2 
       | ciudad1 == ciudad2 = True
       | testF (distanceC ciudad1 ciudad2) = True
       | nameC ciudad1 == nameC ciudad2 = True
       | otherwise = False
-- testing linkList -- 
ciudad0 = newC "olivos" (newP (-3) 4)
ciudad1 = newC "olivos" (newP (-3) 4)
ciudad2 = newC "martinez" (newP 8 10)
ciudad4 = newC "munro" (newP 7 7)

ciudad5 = newC "martinez" (newP (-3) 4)
ciudad6 = newC "munro" (newP 4 3)
calidadAlta = newQ "oro" 5 103.2
link01 = newL ciudad1 ciudad2 calidadAlta
link02 = newL ciudad2 ciudad4 calidadAlta
tunel1 = newT [link01, link02]
tunel2 = newT []

listaDeLinks1 = linkList tunel1 -- devuelve la lista de links del tunel
listaDeLinks2 = linkList tunel2 -- devuelve excepcion: lista de links vacia

--testing ciudadesRepetidas -- devuelve True si las ciudades estan repetidas o tienen semejanzas

ciudadesIguales = ciudadesRepetidas ciudad0 ciudad1 -- devuelve True si las ciudades son iguales
ciudadesConMismaUbicacion = ciudadesRepetidas ciudad0 ciudad5 -- devuelve True si las ciudades tienne la misma ubicación

ciudadesConMismoNombre = ciudadesRepetidas ciudad4 ciudad6 -- devuelve True si las ciudades tienen el mismo nombre

ciudadesDistintas = ciudadesRepetidas ciudad5 ciudad6 -- devuelve False si las ciudades son distintas




